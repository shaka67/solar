/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sms;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SmsServiceObjects implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<SmsServiceObject> list;

	public List<SmsServiceObject> getList() {
		return list;
	}

	public void setList(List<SmsServiceObject> list) {
		this.list = list;
	}

}
