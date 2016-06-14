/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rns;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ouer.solar.CollectionUtil;
import com.ouer.solar.able.Keyable;
import com.ouer.solar.pool.IBatchExecutor;
import com.ouer.solar.search.api.UpdateType;
import com.ouer.solar.search.common.KeyableMap;
import com.ouer.solar.search.dal.cursor.DataUpdate;
import com.ouer.solar.search.definition.DefinitionFactory;
import com.ouer.solar.search.definition.IndexClass;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class RealtimeUpdateTemplate implements RealtimeUpdateService, IBatchExecutor<UpdateMap> {

	private static final Logger LOG = LoggerFactory.getLogger(RealtimeUpdateTemplate.class);

	protected final int id;
	protected final RealtimeUpdateConfig config;
	protected final TransportClient client;
	protected final DefinitionFactory factory;

	protected final AtomicInteger deleted = new AtomicInteger(0);
	protected final AtomicInteger updated = new AtomicInteger(0);
	protected final Set<Long> deletedIds = Sets.newHashSet();
	protected Set<Long> updatedIds = Sets.newHashSet();
	protected volatile boolean maintaining;

    public RealtimeUpdateTemplate(int id,
                                      RealtimeUpdateConfig config,
                                      TransportClient client,
                                      DefinitionFactory factory) {
    	 this.id = id;
    	 this.config = config;
    	 this.client = client;
    	 this.factory = factory;
	}

    @Override
	public void execute(List<UpdateMap> records) {
    	Map<UpdateType, List<Long>> params;
    	for (final UpdateMap record : records) {
    		params = record.getParams();
    		if (params.containsKey(UpdateType.DELETE)) {
				this.deletedIds.addAll(params.get(UpdateType.DELETE));
			}
    		if (params.containsKey(UpdateType.UPDATE)) {
				this.updatedIds.addAll(params.get(UpdateType.UPDATE));
			}
    	}

    	try {
			final Set<KeyableMap<String, Object, Long>> set = getUpdateDatas();
			this.compare(set);
			this.updateIndex(deletedIds, set);
			deleted.getAndAdd(deletedIds.size());
			updated.getAndAdd(updatedIds.size());
			log();
		} finally {
			deletedIds.clear();
			updatedIds.clear();
		}
    }

    @Override
	public void updateIndex(Set<Long> deletedIds, Set<KeyableMap<String, Object, Long>> updateDatas) {
    	final IndexClass clazz = factory.getIndexClass(id);
		final String index = clazz.getIndex();
		final String type = clazz.getType();
		final BulkRequestBuilder bulkRequest = client.prepareBulk();

		for (final Long deletedId : deletedIds) {
			bulkRequest.add(client.prepareDelete(index, type, deletedId.toString()).request());
		}

		IndexRequest indexRequest;
		UpdateRequest updateRequest;
		if (!CollectionUtil.isEmpty(updateDatas)) {
			for (final KeyableMap<String, Object, Long> updateData : updateDatas) {
				indexRequest = new IndexRequest(index, type, updateData.getId().toString()).source(updateData);
				updateRequest = new UpdateRequest(index, type, updateData.getId().toString()).doc(updateData).upsert(indexRequest);
				bulkRequest.add(updateRequest);
			}
		}

		final BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			LOG.error(bulkResponse.buildFailureMessage());
			return;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("updated index {}, type {}, contextSize {}", index, type, bulkResponse.contextSize());
		}

    }

	private void compare(Set<KeyableMap<String, Object, Long>> set) {
		if (CollectionUtil.isEmpty(set)) {
			deletedIds.addAll(updatedIds);
			updatedIds.clear();
			return;
		}

		final Set<Long> ids = Sets.newHashSetWithExpectedSize(set.size());
		for (final Keyable<Long> key : set) {
			ids.add(key.getId());
		}
		updatedIds.removeAll(ids);
		deletedIds.addAll(updatedIds);
		updatedIds = ids;
	}

	private void log() {
		if (CollectionUtil.isNotEmpty(updatedIds) && LOG.isInfoEnabled()) {
		    LOG.info("updated index[{}],dataIds=[{}].", name(), updatedIds);
		}

		if (CollectionUtil.isNotEmpty(deletedIds) && LOG.isInfoEnabled()) {
		    LOG.info("deleted index[{}],dataIds=[{}].", name(), deletedIds);
		}
	}

	private Set<KeyableMap<String, Object, Long>> getUpdateDatas() {
		if (CollectionUtil.isEmpty(updatedIds)) {
            return null;
        }
		final List<Long> list = Lists.newArrayListWithCapacity(updatedIds.size());
		list.addAll(updatedIds);
		return getDataUpdate().findUpdate(name(), list);
	}

	public abstract DataUpdate<KeyableMap<String, Object, Long>> getDataUpdate();

	public String name() {
        return factory.getIndexClass(id).getType();
    }

}
