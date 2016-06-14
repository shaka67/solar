/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rns;

import java.util.Iterator;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;

import com.google.common.collect.Maps;
import com.ouer.solar.CollectionUtil;
import com.ouer.solar.pool.BatchBuffer;
import com.ouer.solar.pool.DelayExecuteBuffer;
import com.ouer.solar.search.api.IncrementUpdateFieldRequest;
import com.ouer.solar.search.api.IncrementUpdateRequest;
import com.ouer.solar.search.common.KeyableMap;
import com.ouer.solar.search.dal.IndexMapper;
import com.ouer.solar.search.dal.cursor.DataUpdate;
import com.ouer.solar.search.dal.cursor.IndexCursor;
import com.ouer.solar.search.definition.DefinitionFactory;
import com.ouer.solar.search.definition.IndexClass;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RealtimeUpdateClient {

	private final RealtimeUpdateConfig config;
	private final TransportClient client;
	private final DefinitionFactory factory;
	private final IndexMapper mapper;
	private final BatchBuffer<UpdateMap> batchBuffer;

	private final Map<Integer, RealtimeUpdateService> nameMap = Maps.newHashMap();

    public RealtimeUpdateClient(RealtimeUpdateConfig config,
            					  TransportClient client,
            					  DefinitionFactory factory,
            					  IndexMapper mapper,
            					  BatchBuffer<UpdateMap> batchBuffer) {
    	this.config = config;
   	 	this.client = client;
   	 	this.factory = factory;
   	 	this.mapper = mapper;
   	 	this.batchBuffer = batchBuffer;
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
    	final RealtimeNotifier notifier = new RealtimeNotifier(id,
											                config,
											                client,
											                factory,
											                mapper);
        nameMap.put(id, notifier);
        final DelayExecuteBuffer<UpdateMap> buffer = new DelayExecuteBuffer<UpdateMap>(factory.getIndexClass(id).getType());
        buffer.setPoolSize(config.getBufferPoolSize());
        buffer.setBatchSize(config.getBufferBatchSize());
        buffer.setThreads(config.getBufferThreadSize());
        buffer.setCheckInterval(config.getBufferDelay());
        buffer.setBatchExecutor(notifier);
        buffer.setName(factory.getIndexClass(id).getType());
        buffer.start();
        batchBuffer.add(buffer);
    }

    public void stop() throws Exception {
    	nameMap.clear();
    	batchBuffer.stop();
    	// FIXME
//    	client.close();
    }

//    @Override
	public void updateField(IncrementUpdateFieldRequest request) {
    	final IndexClass clazz = factory.getIndexClass(request.getIndexId());
		final String index = clazz.getIndex();
		final String type = clazz.getType();
		final BulkRequestBuilder bulkRequest = client.prepareBulk();
		UpdateRequest updateRequest;
		final Map<Long, Map<String,Object>> updateDatas = request.getRecords();
		if (!CollectionUtil.isEmpty(updateDatas)) {
			// vialdate the field is existed
			vialdate(request.getIndexId(),updateDatas);
			for (final Long id : updateDatas.keySet()) {
				updateRequest = new UpdateRequest(index, type, id.toString()).doc(updateDatas.get(id));
				bulkRequest.add(updateRequest);
			}
		}

		final BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			return;
		}
	}

	private boolean vialdate(int indexId,Map<Long, Map<String, Object>> updateDatas) {
		Map<String,Object> tmpMap ;
		for (final Long id : updateDatas.keySet()) {
			tmpMap = updateDatas.get(id);
			if (tmpMap != null) {
				for (final String filedName :tmpMap.keySet()) {
					if (factory.getIndexClass(indexId).getField(filedName) == null) {
						throw new RuntimeException("【" + filedName + "】 is not exist in index");
					}
				}
			}

		}
		return false;
	}

//	@Override
	public void update(IncrementUpdateRequest request) {
		final String type = factory.getIndexClass(request.getIndexId()).getType();
		batchBuffer.add(new UpdateMap(type, request.getRecords()));
	}

//    public void update(int id, Map<Integer, List<Long>> record) {
//    	final String type = factory.getIndexClass(id).getType();
//    	batchBuffer.add(new UpdateMap(type, record));
//    }

    public Map<String, RealtimeUpdateService> services() {
        final Map<String, RealtimeUpdateService> services = Maps.newHashMap();
        final Iterator<Integer> it = factory.indexIterator();
        int id;
        while (it.hasNext()) {
        	id = it.next();
            services.put(factory.getIndexClass(id).getType(), getIndexUpdate(id));
        }
        return services;
    }

	public RealtimeUpdateService getIndexUpdate(int id) {
		return nameMap.get(id);
	}

	void register(RealtimeUpdateService realtimeUpdate) {
		nameMap.put(realtimeUpdate.id(), realtimeUpdate);
	}

	static class RealtimeNotifier extends RealtimeUpdateTemplate {

		private final IndexMapper mapper;

        public RealtimeNotifier(int id,
                				 RealtimeUpdateConfig config,
                				 TransportClient client,
                				 DefinitionFactory factory,
                				 IndexMapper mapper) {
            super(id, config, client, factory);
            this.mapper = mapper;
        }

        @Override
        public int id() {
            return id;
        }

		@Override
		public DataUpdate<KeyableMap<String, Object, Long>> getDataUpdate() {
			return new IndexCursor(name(), mapper);
		}

    }



}
