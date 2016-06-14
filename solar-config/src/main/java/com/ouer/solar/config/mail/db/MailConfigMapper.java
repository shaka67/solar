/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.mail.db;

import com.ouer.solar.config.mail.MailConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface MailConfigMapper {

	public int insert(MailConfig config);

	public int update(MailConfig config);

	public MailConfig select(String appId);

	public boolean exists(String appId);

	public boolean delete(String appId);
}
