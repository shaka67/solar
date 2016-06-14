/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import redis.clients.jedis.JedisCluster;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.bus.DefaultBusRegistry;
import com.ouer.solar.cache.CacheClient;
import com.ouer.solar.cache.DefaultCacheClient;
import com.ouer.solar.cache.DefaultLevelableCache;
import com.ouer.solar.cache.LevelableCache;
import com.ouer.solar.cache.local.EhCacheProvider;
import com.ouer.solar.cache.serialize.FSTSerializer;
import com.ouer.solar.cache.serialize.Serializer;
import com.ouer.solar.cache.shared.ClusteredRedisCacheProvider;
import com.ouer.solar.cache.shared.ClusteredRedisConfig;
import com.ouer.solar.cache.signal.CacheSignalAssistant;
import com.ouer.solar.cache.signal.CacheSignalBroadcaster;
import com.ouer.solar.cache.signal.ClusteredRedisSignalBroadcaster;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@ImportResource("classpath:/META-INF/applicationContext-cache.xml")
public class CacheSpringConfig {

	@Bean
	Serializer serializer() {
		return new FSTSerializer();
	}

	@Bean
	BusSignalManager busSignalManager() {
		return new BusSignalManager(new DefaultBusRegistry());
	}

	@Autowired
	@Bean
	EhCacheProvider ehCacheProvider(BusSignalManager bsm) {
		return new EhCacheProvider(bsm);
	}

	@Autowired
	@Bean
	CacheSignalAssistant cacheCommandAssistant(Serializer serializer) {
		return new CacheSignalAssistant(serializer);
	}

//	@Autowired
//	@Bean
//	JedisPool jedisPool(RedisConfig config, JedisPoolConfig poolConfig) {
//		return new JedisPool(poolConfig,
//	   			   			   config.getHost(),
//	   			   			   config.getPort(),
//	   			   			   config.getTimeout(),
//	   			   			   config.getPassword(),
//	   			   			   config.getDatabase());
//	}

//	@Autowired
//	@Bean
//	ShardedJedisPool shardedJedisPool(JedisPoolConfig poolConfig, ShardedRedisConfig config) {
//		return new ShardedJedisPool(poolConfig, config.getShardInfos());
//	}

	@Autowired
	@Bean
	JedisCluster jedisCluster(ClusteredRedisConfig config) {
		return new JedisCluster(config.getClusterNodes());
	}

//	@Autowired
//	@Bean
//	RedisCacheProvider redisCacheProvider(JedisPool pool, Serializer serializer) {
//		return new RedisCacheProvider(pool, serializer);
//	}

//	@Autowired
//	@Bean
//	ShardedRedisCacheProvider shardedRedisCacheProvider(ShardedJedisPool pool, Serializer serializer) {
//		return new ShardedRedisCacheProvider(pool, serializer);
//	}

	@Autowired
	@Bean
	ClusteredRedisCacheProvider clusteredRedisCacheProvider(JedisCluster cluster, Serializer serializer) {
		return new ClusteredRedisCacheProvider(cluster, serializer);
	}

//	@Autowired
//	@Bean
//	LevelableCache levelableCache(EhCacheProvider localProvider, RedisCacheProvider sharedProvider) {
//		return new DefaultLevelableCache(localProvider, sharedProvider);
//	}

//	@Autowired
//	@Bean
//	LevelableCache levelableCache(EhCacheProvider localProvider, ShardedRedisCacheProvider sharedProvider) {
//		return new DefaultLevelableCache(localProvider, sharedProvider);
//	}

	@Autowired
	@Bean
	LevelableCache levelableCache(EhCacheProvider localProvider, ClusteredRedisCacheProvider sharedProvider) {
		return new DefaultLevelableCache(localProvider, sharedProvider);
	}

	@Autowired
	@Bean
	CacheClient cacheClient(LevelableCache cache,
			 				  CacheSignalAssistant csa,
			 				  CacheSignalBroadcaster csb,
			 				  BusSignalManager bsm) {
		return new DefaultCacheClient(cache, csa, csb, bsm);
	}

//	@Autowired
//	@Bean
//	CacheSignalBroadcaster cacheSignalBroadcaster(LevelableCache cache, CacheSignalAssistant csa, JedisPool pool) {
//		return new RedisSignalBroadcaster(cache, csa, pool);
//	}

//	@Autowired
//	@Bean
//	CacheSignalBroadcaster cacheSignalBroadcaster(LevelableCache cache, CacheSignalAssistant csa, ShardedJedisPool pool) {
//		return new ShardedRedisSignalBroadcaster(cache, csa, pool);
//	}

	@Autowired
	@Bean
	CacheSignalBroadcaster cacheSignalBroadcaster(LevelableCache cache, CacheSignalAssistant csa, JedisCluster cluster) {
		return new ClusteredRedisSignalBroadcaster(cache, csa, cluster);
	}

//	@Autowired
//	@Bean
//	CacheSignalBroadcaster cacheSignalBroadcaster(LevelableCache cache, CacheSignalAssistant csa) {
//		return new JGroupsSignalBroadcaster(cache, csa);
//	}

}
