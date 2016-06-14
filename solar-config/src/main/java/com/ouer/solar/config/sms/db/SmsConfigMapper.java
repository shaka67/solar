/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sms.db;

import java.util.List;

import com.ouer.solar.config.sms.SmsConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface SmsConfigMapper {

	public int insert(SmsConfig config);

	public int update(SmsConfig config);

	public SmsConfig select(String appId);

	public boolean exists(String appId);

	public boolean delete(String appId);

	public List<SmsConfig> selectAll();
}
