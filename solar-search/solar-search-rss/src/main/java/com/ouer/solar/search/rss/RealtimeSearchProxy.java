/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rss;

import java.util.Iterator;
import java.util.Map;

import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.ouer.solar.search.api.CustomizedSearchRequest;
import com.ouer.solar.search.api.RealtimeSearchResponse;
import com.ouer.solar.search.api.RssException;
import com.ouer.solar.search.api.SimpleSearchRequest;
import com.ouer.solar.search.definition.DefinitionFactory;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RealtimeSearchProxy implements RealtimeSearch {

	private static final Logger LOG = LoggerFactory.getLogger(RealtimeSearchProxy.class);

	private final RealtimeSearchConfig config;
	private final TransportClient client;
	private final DefinitionFactory factory;

	private final Map<Integer, RealtimeSearch> searchMap = Maps.newHashMap();

	public RealtimeSearchProxy(RealtimeSearchConfig config,
								  TransportClient client,
								  DefinitionFactory factory) {
		this.config = config;
		this.client = client;
		this.factory = factory;
        init();
	}

	private void init() {
		final Iterator<Integer> it = factory.indexIterator();
        int id;
        while (it.hasNext()) {
        	id = it.next();
            register(id);
        }
    }

	void register(int id) {
	    final RealtimeSearchService service = new RealtimeSearchService(id, config, client, factory);
	    searchMap.put(id, service);
    }


	@Override
	public RealtimeSearchResponse search(SimpleSearchRequest request) throws RssException {
		try {
			final int indexId = factory.getIndexIdByResultId(request.getResultId());
			return searchMap.get(indexId).search(request);
		} catch (final Exception e) {
			LOG.error("", e);
			throw new RssException(e);
		}
	}

	@Override
	public RealtimeSearchResponse search(CustomizedSearchRequest request) throws RssException {
		try {
			final int indexId = factory.getIndexIdByResultId(request.getResultId());
			return searchMap.get(indexId).search(request);
		} catch (final Exception e) {
			LOG.error("", e);
			throw new RssException(e);
		}
	}

	static class RealtimeSearchService extends RealtimeSearchTemplate {

		public RealtimeSearchService(int id,
										RealtimeSearchConfig config,
										TransportClient client,
										DefinitionFactory factory) {
			super(id, config, client, factory);
		}

	}

}
