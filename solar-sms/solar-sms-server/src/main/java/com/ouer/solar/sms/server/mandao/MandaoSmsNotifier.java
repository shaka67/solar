/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server.mandao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;
import com.ouer.solar.sms.api.SmsStatus;
import com.ouer.solar.sms.model.SmsMobileMap;
import com.ouer.solar.sms.server.RecordUpdater;
import com.ouer.solar.sms.server.SmsNotifier;
import com.ouer.solar.sms.server.dynamic.SmsDalManager;
import com.ouer.solar.sms.server.mapper.SmsMobileMapMapper;
import com.ouer.solar.sms.server.mapper.SmsRecordMapper;
import com.ouer.solar.spring.SpringContextUtil;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MandaoSmsNotifier implements SmsNotifier {

	private static final Logger LOG = LoggerFactory.getLogger(MandaoSmsNotifier.class);

//	private final SmsRecordMapper smsRecordMapper;
//	private final SmsMobileMapMapper smsMobileMapMapper;

	private SmsDalManager dalManager;

	private final ExecutorService executor = Executors.newCachedThreadPool();

	@Override
	public void initialize() {
		dalManager = SpringContextUtil.getBean(SmsDalManager.class);
	}

	@Override
	public boolean notified(String appId, Object args) {
		final String strArgs = (String) args;
		final Map<String, List<SmsMobileMap>> result = Maps.newHashMap();
        try {
            if (!StringUtils.isEmpty(strArgs)) {
                final String[] batchStrs = strArgs.split(";");
                for (int i = 0; i < batchStrs.length; i++) {
                    final String batchStr = batchStrs[i];
                    if (!StringUtils.isEmpty(batchStr)) {
                        final String[] msg = batchStr.split(",");
                        final String  thirdBatchId = msg[3];
                        int status;
                        if (msg[4].equals("0") || msg[4].equals("DELIVRD")) {
                           status = SmsStatus.SUCCESS.code();
                        } else {
                            status = SmsStatus.FAILURE.code();
                        }
                        List<SmsMobileMap> mobileList = result.get(thirdBatchId);
                        final SmsMobileMap smm  = new SmsMobileMap();
            			smm.setStatus(String.valueOf(status));
            			smm.setMobile(msg[2]);
                        if(mobileList == null) {
                        	mobileList = new ArrayList<SmsMobileMap>();
                        	result.put(thirdBatchId, mobileList);
                        }
                        mobileList.add(smm);
                        LOG.debug("更新成功！"+batchStr);
                    }
                }

                final SmsMobileMapMapper smsMobileMapMapper = dalManager.newMapperFactoryBean(appId, SmsMobileMapMapper.class).getObject();
                final SmsRecordMapper smsRecordMapper = dalManager.newMapperFactoryBean(appId, SmsRecordMapper.class).getObject();
                executor.execute(new RecordUpdater(result, smsMobileMapMapper, smsRecordMapper));
            } else {
                LOG.error("接受通知参数异常");
                return false;
            }
        } catch (final Exception e) {
        	LOG.error("漫道通知返回失败");
        	return false;
        }
        return true;
	}

}
