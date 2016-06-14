/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.search.common.KeyableMap;
import com.ouer.solar.search.definition.DefinitionFactory;
import com.ouer.solar.search.definition.IndexClass;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class IndexWriter {

	private static final Logger LOG = LoggerFactory.getLogger(IndexWriter.class);

	private final IndexBuildConfig config;
	private final TransportClient client;
	private final DefinitionFactory factory;
	private String name;

	public IndexWriter(IndexBuildConfig config, TransportClient client, DefinitionFactory factory) {
		this.config = config;
		this.client = client;
		this.factory = factory;
	}

	public <T> void executeAdd(int id, List<KeyableMap<String, Object, T>> records) {
		if (CollectionUtil.isEmpty(records)) {
			return;
		}

		try {
			createBulk(id, records, true);
		} catch (final IOException e) {
			LOG.error("failed to add document.", e);
		}
	}

	private <T> void create(int id, List<KeyableMap<String, Object, T>> records) throws IOException {
		create(id, records, false);
	}

	private <T> void create(int id, List<KeyableMap<String, Object, T>> records, boolean recreate) throws IOException {
		final IndexClass clazz = factory.getIndexClass(id);
		final String index = clazz.getIndex();
		final String type = clazz.getType();
		String documentId;
		for (final KeyableMap<String, Object, T> record : records) {
			if (recreate) {
				documentId = client.prepareDelete(index, type, record.getId().toString())
								.execute().actionGet(config.getTimeoutMillis()).getId();
				if (LOG.isDebugEnabled()) {
					LOG.debug("deleted index {}, type {}, documentId {}", index, type, documentId);
				}
			}
			documentId = client
					.prepareIndex(index, type, record.getId().toString())
					.setSource(record)
					.execute()
					.actionGet(config.getTimeoutMillis())
					.getId();
			if (LOG.isDebugEnabled()) {
				LOG.debug("created index {}, type {}, documentId {}", index, type, documentId);
			}
		}
	}

	private <T> void createBulk(int id, List<KeyableMap<String, Object, T>> records) throws IOException {
		createBulk(id, records, false);
	}

	private <T> void createBulk(int id, List<KeyableMap<String, Object, T>> records, boolean recreate) throws IOException {
		final IndexClass clazz = factory.getIndexClass(id);
		final String index = clazz.getIndex();
		final String type = clazz.getType();
		final BulkRequestBuilder bulkRequest = client.prepareBulk();
		BulkResponse bulkResponse;

		for (final KeyableMap<String, Object, T> record : records) {
			if (recreate) {
				bulkRequest.add(client.prepareDelete(index, type, record.getId().toString()).request());
			}
			bulkRequest.add(client.prepareIndex(index, type, record.getId().toString())
					.setSource(record).request());
		}

//		bulkResponse = bulkRequest.execute().actionGet();
//		if (bulkResponse.hasFailures()) {
//			LOG.error(bulkResponse.buildFailureMessage());
//			return;
//		}
//
//		if (LOG.isDebugEnabled()) {
//			LOG.debug("deleted index {}, type {}, contextSize {}", index, type, bulkResponse.contextSize());
//		}
//
//		for (final BtoMap<String, Object, T> record : records) {
//			bulkRequest.add(client.prepareIndex(index, type, record.getId().toString())
//					.setSource(record).request());
//		}
		bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			final String error = bulkResponse.buildFailureMessage();
			LOG.error(error);
			throw new RuntimeException(error);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("created index {}, type {}, contextSize {}", index, type, bulkResponse.contextSize());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
