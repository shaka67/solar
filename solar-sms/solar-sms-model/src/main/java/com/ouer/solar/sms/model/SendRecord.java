/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.sms.model;


/**
 *
 * @author: <a href="horus@ixiaopu.com">horus</a>
 */

public class SendRecord {
	private Integer channelId;
	private String mobile;
	private String content;
	//第三方平台发送短信批次号
	private String thirdBatchId;
	//第三方短信平台ID (漫道:0，其它依次加1)
	private String thirdId;
	private String status;
	
	public SendRecord(){
		super();
	}
	public SendRecord(int channelId, String mobile, String content, String thirdBatchId, String thirdId,
			String status) {
		super();
		this.channelId = channelId;
		this.mobile = mobile;
		this.content = content;
		this.thirdBatchId = thirdBatchId;
		this.thirdId = thirdId;
		this.status = status;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getThirdBatchId() {
		return thirdBatchId;
	}
	public void setThirdBatchId(String thirdBatchId) {
		this.thirdBatchId = thirdBatchId;
	}
	public String getThirdId() {
		return thirdId;
	}
	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
