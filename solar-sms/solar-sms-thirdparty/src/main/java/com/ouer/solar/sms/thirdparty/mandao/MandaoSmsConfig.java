/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.thirdparty.mandao;

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.ouer.solar.protocol.JsonObjectConverter;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MandaoSmsConfig {

	private String appId;
	private String sn;
	private String password;
	private String specialServiceNumber;
	private String soapAction;
	private String serviceURL;
	private String standbyServiceURL;
	private String whitelist;

	private String extConfig;

	private final Map<String,MandaoExtConfigObj> map = Maps.newHashMap();

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSpecialServiceNumber() {
		return specialServiceNumber;
	}

	public void setSpecialServiceNumber(String specialServiceNumber) {
		this.specialServiceNumber = specialServiceNumber;
	}

	public String getSoapAction() {
		return soapAction;
	}

	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}

	public String getServiceURL() {
		return serviceURL;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	public String getStandbyServiceURL() {
		return standbyServiceURL;
	}

	public void setStandbyServiceURL(String standbyServiceURL) {
		this.standbyServiceURL = standbyServiceURL;
	}

	public String getWhitelist() {
		return whitelist;
	}

	public void setWhitelist(String whitelist) {
		this.whitelist = whitelist;
	}

	public String getExtConfig() {
		return extConfig;
	}

	public void setExtConfig(String extConfig) throws IOException {
		final JsonObjectConverter joc = new JsonObjectConverter();
		final MandaoExtConfigObj[] mecos = joc.fromString(extConfig, MandaoExtConfigObj[].class);
		String channelCode ;
		for(final MandaoExtConfigObj m : mecos){
			channelCode = m.getChannelCode();
			final MandaoExtConfigObj meco = map.get(channelCode);
			if(meco == null) {
				map.put(channelCode, m);
			}
		}

		this.extConfig = extConfig;
	}



	public String getSign(String channelCode){
		final MandaoExtConfigObj meco = map.get(channelCode);
		if(meco != null) {
			return meco.getSign();
		}
		return null;
	}
	public String getExt(String channelCode){
		final MandaoExtConfigObj meco = map.get(channelCode);
		if(meco != null) {
			return meco.getMandaoExtCode();
		}
		return null;
	}


	public static void main(String[] args) {
		final MandaoExtConfigObj mec1 = new MandaoExtConfigObj("【快快开店】","1","101");
		final MandaoExtConfigObj mec2 = new MandaoExtConfigObj("【快快开店】","2","102");
		final MandaoExtConfigObj mec3 = new MandaoExtConfigObj("【快快开店】","3","103");
		final MandaoExtConfigObj mec4 = new MandaoExtConfigObj("【买到手抽筋】","4","201");
		final MandaoExtConfigObj mec5 = new MandaoExtConfigObj("【买到手抽筋】","5","202");
		final MandaoExtConfigObj mec6 = new MandaoExtConfigObj("【买到手抽筋】","6","203");
		final MandaoExtConfigObj mec7 = new MandaoExtConfigObj("【寻宝之旅】","7","301");
		final MandaoExtConfigObj mec8 = new MandaoExtConfigObj("【寻宝之旅】","8","302");
		final MandaoExtConfigObj mec9 = new MandaoExtConfigObj("【寻宝之旅】","9","303");

		final MandaoExtConfigObj[] mecos = new MandaoExtConfigObj[]{mec1,mec2,mec3,mec4,mec5,mec6,mec7,mec8,mec9};
		final JsonObjectConverter joc = new JsonObjectConverter();
		try {
			final String str = joc.toString(mecos);
			System.out.print(str);
			final MandaoExtConfigObj[] c = joc.fromString(str, MandaoExtConfigObj[].class);
			System.out.println();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class MandaoExtConfigObj {

	private String sign;
	private String channelCode;
	private String mandaoExtCode;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getMandaoExtCode() {
		return mandaoExtCode;
	}

	public void setMandaoExtCode(String mandaoExtCode) {
		this.mandaoExtCode = mandaoExtCode;
	}

	public MandaoExtConfigObj( String sign, String channelCode, String mandaoExtCode) {
		super();
		this.channelCode = channelCode;
		this.mandaoExtCode = mandaoExtCode;
		this.sign = sign;
	}
	public MandaoExtConfigObj() {
		super();
	}

}
