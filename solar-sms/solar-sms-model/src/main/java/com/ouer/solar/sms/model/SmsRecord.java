package com.ouer.solar.sms.model;

import java.util.Date;

public class SmsRecord {

	private Long id;
	//应用ID
	private Integer channelId;
	//发送内容
	private String content;
	
	private Date created_at;
	
	private Date updated_at;
	//第三方平台发送短信批次号
	private String thirdBatchId;
	//第三方短信平台ID (漫道:0，其它依次加1)
	private String thirdId;
	
	private Long idRaw;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public String getThirdBatchId() {
		return thirdBatchId;
	}
	public void setThirdBatchId(String thirdBatchId) {
		this.thirdBatchId = thirdBatchId;
	}
	public Long getIdRaw() {
		return idRaw;
	}
	public void setIdRaw(Long idRaw) {
		this.idRaw = idRaw;
	}
	public String getThirdId() {
		return thirdId;
	}
	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}
}
