/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.db;

import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.google.common.collect.Maps;
import com.ouer.solar.config.CommonConfig;
import com.ouer.solar.config.DynamicConfig;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { ConfigDalStoreSpring.class, ProfileSpringConfig.class })
public class DynamicConfigDBTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private DynamicConfigMapper mapper;

	@Test
	public void testInsertSms() throws Exception {
		final Map<String, Object> configs = Maps.newHashMap();
		configs.put("sn", "SDK-BBX-010-23618");
		configs.put("pwd", "abd-eb3#");
		configs.put("special_service_number", "249407");
		configs.put("mainserviceurl", "http://sdk.entinfo.cn:8060/webservice.asmx");
		configs.put("standbyserviceurl", "http://sdk2.entinfo.cn:8060/webservice.asmx");
		configs.put("soap_action", "http://tempuri.org/Register");
		configs.put("whitelist", "hehe");
		configs.put("ext_config", "[{\"sign\":\"【快快开店】\",\"channelCode\":\"1\",\"mandaoExtCode\":\"101\"},{\"sign\":\"【快快开店】\",\"channelCode\":\"2\",\"mandaoExtCode\":\"102\"},{\"sign\":\"【快快开店】\",\"channelCode\":\"3\",\"mandaoExtCode\":\"103\"},{\"sign\":\"【买到手抽筋】\",\"channelCode\":\"4\",\"mandaoExtCode\":\"201\"},{\"sign\":\"【买到手抽筋】\",\"channelCode\":\"5\",\"mandaoExtCode\":\"202\"},{\"sign\":\"【买到手抽筋】\",\"channelCode\":\"6\",\"mandaoExtCode\":\"203\"},{\"sign\":\"【寻宝之旅】\",\"channelCode\":\"7\",\"mandaoExtCode\":\"301\"},{\"sign\":\"【寻宝之旅】\",\"channelCode\":\"8\",\"mandaoExtCode\":\"302\"},{\"sign\":\"【寻宝之旅】\",\"channelCode\":\"9\",\"mandaoExtCode\":\"303\"}]");
		final DynamicConfig config = new DynamicConfig();
		config.setAppId("indra");
		config.setConfigType("sms");
		config.setThirdparty("mandao");
		config.setConfigs(configs);
		mapper.insert(config);
	}

	@Test
	public void testInsertQiniu() throws Exception {
		final Map<String, Object> configs = Maps.newHashMap();
		configs.put("access_key", "pvq0yhV90DZRWntM0jZLI_KkSgpwT2rWg6NUDNkT");
		configs.put("secret_key", "iGExckccKW6zoh67BKq0etiYB2bZ6gPS0_xNAKEX");
		configs.put("buckets", "xaya=http://xaya.qiniudn.com,kk-other=http://kk-other.qiniudn.com");
		final DynamicConfig config = new DynamicConfig();
		config.setAppId("indra");
		config.setConfigType("image");
		config.setThirdparty("qiniu");
		config.setConfigs(configs);
		mapper.insert(config);
	}

	@Test
	public void testUpdate() throws Exception {
		final Map<String, Object> configs = Maps.newHashMap();
		configs.put("whitelist", "updated!!");
		configs.put("soap_action", "updated!!");
		final DynamicConfig config = new DynamicConfig();
		config.setAppId("indra");
		config.setConfigType("sms");
		config.setThirdparty("mandao");
		config.setConfigs(configs);
		mapper.update(config);
	}

	@Test
	public void testSelectSms() throws Exception {
		final CommonConfig config = new CommonConfig();
//		config.setAppId("indra");
		config.setConfigType("sms");
		config.setThirdparty("mandao");
		config.setConfigOptions("sn, pwd, mainserviceurl, standbyserviceurl, soap_action, whitelist, ext_config");

		final String appId = "indra";
		final Map<String, Object> result = mapper.select(appId, config);
		final Iterator<String> it = result.keySet().iterator();
		String key;
		Object value;
		while (it.hasNext()) {
			key = it.next();
			value = result.get(key);
			System.err.println("key=" + key + ", value=" + value);
		}
	}

	@Test
	public void testExists() throws Exception {
		final DynamicConfig config = new DynamicConfig("indra", "sms", "mandao");
		final boolean exists = mapper.exists(config);
		System.err.println(exists);
	}

}
