/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.api;

import java.io.Serializable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SmsResponse<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private SmsStatus status;
	private String thirdBatchId;
	private String third;
	private T data;

	public SmsResponse() {}

	public SmsResponse(T data, String third) {
		this.data = data;
		this.status = SmsStatus.PENDING;
		this.third = third;
	}

	public SmsStatus getStatus() {
		return status;
	}

	public void setStatus(SmsStatus status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getThirdBatchId() {
		return thirdBatchId;
	}

	public void setThirdBatchId(String thirdBatchId) {
		this.thirdBatchId = thirdBatchId;
	}

	public String getThird() {
		return third;
	}

	public void setThird(String third) {
		this.third = third;
	}


}
