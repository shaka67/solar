/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.thirdparty.mandao;

import org.apache.ibatis.annotations.Param;


/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface MandaoAppIdSSNMapper {

//	public int insert(@Param("appId") String appId, @Param("specialServiceNumber") String specialServiceNumber);

	public int insert(MandaoAppIdSsn appIdSsn);

	public String selectAppId(@Param("specialServiceNumber") String specialServiceNumber);

	public boolean exist(String appId);

	public boolean delete(String appId);
}
