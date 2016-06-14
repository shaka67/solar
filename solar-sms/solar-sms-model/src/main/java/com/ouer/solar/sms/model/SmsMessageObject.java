/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.sms.model;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author: <a href="horus@ixiaopu.com">horus</a>
 */

public class SmsMessageObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7650780226659366134L;
	
	private  String password;
	private  String sn;
	private  String soapAction;
	private  String serviceURL;
	private  String standbyserviceurl;
	private  String sign;
	private  String channelExt;
	private  Map<Integer,Integer> channelExtMap;
	
	
	public SmsMessageObject(String password, String sn, String soapAction, String serviceURL, String standbyserviceurl,
			String sign, String channelExt) {
		super();
		this.password = password;
		this.sn = sn;
		this.soapAction = soapAction;
		this.serviceURL = serviceURL;
		this.standbyserviceurl = standbyserviceurl;
		this.sign = sign;
		this.channelExt = channelExt;
		String[] strs = channelExt.split(",");
		for(String str :strs) {
			String[] channel_ext = str.split("=") ;
			channelExtMap.put(Integer.valueOf(channel_ext[0]), Integer.valueOf(channel_ext[1]));
		}
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
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
	public String getStandbyserviceurl() {
		return standbyserviceurl;
	}
	public void setStandbyserviceurl(String standbyserviceurl) {
		this.standbyserviceurl = standbyserviceurl;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getChannelExt() {
		return channelExt;
	}
	public void setChannelExt(String channelExt) {
		this.channelExt = channelExt;
	}
	
	public Integer getExt(Integer channel) {
		return channelExtMap.get(channel);
	}
}
