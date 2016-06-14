/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.thirdparty.mandao;

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.ouer.solar.config.CommonConfigHeader;
import com.ouer.solar.config.DynamicConfig;
import com.ouer.solar.config.DynamicConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MandaoConfigLocator {

	private static final String CONFIG_TYPE = "sms";
	private static final String THIRDPARTY = "mandao";

	private final DynamicConfigLocator dcLocator;
	private final MandaoAppIdSSNMapper mapper;

	private final Map<String, String> ssnAppIdMapping = Maps.newHashMap();

	public MandaoConfigLocator(DynamicConfigLocator dcLocator, MandaoAppIdSSNMapper mapper) {
		this.dcLocator = dcLocator;
		this.mapper = mapper;
	}

	public MandaoSmsConfig getConfig(String appId) throws IOException {
		final DynamicConfig config = dcLocator.loadDynamicConfig(appId, new CommonConfigHeader(CONFIG_TYPE, THIRDPARTY));
		final MandaoSmsConfig mandao = new MandaoSmsConfig();
		mandao.setSn((String) config.getConfig(MandaoSmsConstants.KEY_SN));
		mandao.setPassword((String) config.getConfig(MandaoSmsConstants.KEY_PWD));
		final String ssn = (String) config.getConfig(MandaoSmsConstants.KEY_SPECIAL_SERVICE_NUMBER);
		mandao.setSpecialServiceNumber(ssn);
		mandao.setServiceURL((String) config.getConfig(MandaoSmsConstants.KEY_SERVICE_URL));
		mandao.setStandbyServiceURL((String) config.getConfig(MandaoSmsConstants.KEY_STANDBY_SERVICE_URL));
		mandao.setSoapAction((String) config.getConfig(MandaoSmsConstants.KEY_SOAP_ACTION));
		mandao.setWhitelist((String) config.getConfig(MandaoSmsConstants.KEY_WHITELIST));
		mandao.setExtConfig((String) config.getConfig(MandaoSmsConstants.KEY_EXTCONFIG));
		if (!ssnAppIdMapping.containsKey(ssn)) {
//			mapper.insert(appId, ssn);
			mapper.insert(new MandaoAppIdSsn(appId, ssn));
			ssnAppIdMapping.put(ssn, appId);
		}
		return mandao;
	}

	public String locateAppId(String ssn) {
		if (ssnAppIdMapping.containsKey(ssn)) {
			return ssnAppIdMapping.get(ssn);
		}

		final String appId = mapper.selectAppId(ssn);
		ssnAppIdMapping.put(ssn, appId);
		return appId;
	}
}
