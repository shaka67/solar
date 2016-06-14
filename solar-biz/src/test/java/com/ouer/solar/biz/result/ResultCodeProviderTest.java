/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;

import org.junit.Assert;
import org.junit.Test;

import com.ouer.solar.biz.result.ResultCode;
import com.ouer.solar.biz.result.ResultCodeProvider;
import com.ouer.solar.biz.sample.SampleResultCode;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月3日 上午6:19:01
 */

public class ResultCodeProviderTest {

	@Test
	public void resultCode() {
		// ResultCode resultCode = new ResultCodeProvider();

		ResultCode code = SampleResultCode.DAO_FAILURE;
		Assert.assertEquals("DAO_FAILURE", code.getName());
		Assert.assertEquals(4, code.getCode());
		Assert.assertEquals("执行数据操作失败", code.getMessage().getMessage());
		code = ResultCodeProvider.SUCCESS;
		Assert.assertEquals("SUCCESS", code.getName());
		Assert.assertEquals(1, code.getCode());
		Assert.assertEquals("成功执行命令", code.getMessage().getMessage());

	}

}
