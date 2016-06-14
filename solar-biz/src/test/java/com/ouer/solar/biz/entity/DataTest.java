/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.ouer.solar.DateUtil;
import com.ouer.solar.biz.entity.Data;
import com.ouer.solar.biz.sample.SampleObject;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月23日 下午11:50:00
 */

public class DataTest {

	@Test
	public void _toString() {
		Data data = new Data();

		data.setId(11111);

		String sDate = "2000-01-01 09:30:20";
		data.setCreatedAt(DateUtil
				.parseDate(sDate, DateUtil.FULL_CHINESE_PATTERN));
		data.setUpdatedAt(DateUtil
				.parseDate(sDate, DateUtil.FULL_CHINESE_PATTERN));

		assertEquals(
				"Data[addTime=Sat Jan 01 09:30:20 CST 2000,modTime=Sat Jan 01 09:30:20 CST 2000,id=11111]",
				data.toString());
	}

	@Test
	public void equals() {
		SampleObject sample1 = new SampleObject();
		sample1.setStringField("xxx");
		sample1.setBytesField("hello world".getBytes());
		
		SampleObject sample2 = new SampleObject();
		sample2.setStringField("yyy");
		sample2.setBytesField("hello world".getBytes());
		
		assertNotEquals(sample1,sample2);
		sample2.setStringField("xxx");
		assertEquals(sample1,sample2);
		
		sample1.setBytesField("HELLO WORLD".getBytes());
		assertNotEquals(sample1,sample2);
	}

}
