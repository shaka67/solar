/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.Sets;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.ouer.solar.search.common.KeyableMap;
import com.ouer.solar.search.dal.IndexMapper;
import com.ouer.solar.search.dal.cursor.CursorTemplate;
import com.ouer.solar.search.dal.cursor.IndexCursor;
import com.ouer.solar.search.definition.DefinitionFactory;
import com.ouer.solar.search.definition.IndexField;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class IndexBuildProxy implements IndexBuildService {

	private static final Logger LOG = LoggerFactory.getLogger(IndexBuildProxy.class);

	private final IndexBuildConfig config;
	private final TransportClient client;
	private final DefinitionFactory factory;
	private final IndexMapper mapper;

	private final Map<Integer, IndexIntegrate> nameMap = Maps.newHashMap();

	public IndexBuildProxy(IndexBuildConfig config,
							 TransportClient client,
							 DefinitionFactory factory,
            				 IndexMapper mapper) {
		this.config = config;
		this.client = client;
		this.factory = factory;
		this.mapper = mapper;
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

	public IndexIntegrate getIndexBuild(int id) {
		return nameMap.get(id);
	}

	public void buildIndex(int id) throws Exception {
		nameMap.get(id).buildIndex();
	}

	@Override
	public void buildIndex() throws IbsException {
		try {
			final Iterator<Integer> it = factory.indexIterator();
	        int id;
	        String index;
	        IndicesExistsResponse resp;
	        final Set<String> indices = Sets.newHashSet();
	        while (it.hasNext()) {
	        	id = it.next();
	        	if (factory.getIndexClass(id).isPreIndex()) {
		        	index = factory.getIndexClass(id).getIndex();
		        	if (indices.contains(index)) {
						continue;
					}
		    		resp = client.admin().indices().prepareExists(index).execute().actionGet();
		    		if (resp.isExists()) {
		    			client.admin().indices().prepareDelete(index).execute().actionGet();
		    		}
		    		client.admin().indices().prepareCreate(index).execute().actionGet();
		    		indices.add(index);
	        	}
	        }

			for (final Map.Entry<Integer, IndexIntegrate> entry : nameMap.entrySet()) {
				entry.getValue().buildIndex();
			}
		} catch (final Throwable t) {
			LOG.error("", t);
		}
	}

	@Override
	public void deleteIndex(String index) throws IbsException {
		client.admin().indices().prepareDelete(index).execute().actionGet();
	}


	@Override
	public void buildMapping(int id) throws Exception {
		final String index = factory.getIndexClass(id).getIndex();
		final String type = factory.getIndexClass(id).getType();
//		final IndicesExistsResponse resp = client.admin().indices().prepareExists(index).execute().actionGet();
//		if (resp.isExists()) {
//			client.admin().indices().prepareDelete(index).execute().actionGet();
//		}
//		client.admin().indices().prepareCreate(index).execute().actionGet();
		final DeleteMappingRequest delete = Requests.deleteMappingRequest(index).types(type);
		client.admin().indices().deleteMapping(delete).actionGet();

		final XContentBuilder mapping = XContentFactory.jsonBuilder().startObject()
		          .startObject(type).startObject("properties");

		final Collection<IndexField> fields = factory.getIndexClass(id).getFields().values();
		for (final IndexField field : fields) {
			mapping.startObject(field.getName()).field("type", field.getType())
					.field("index", field.getIndexType().toString())
					.field("store", field.getStoreType().toString())
					.endObject();
		}
		mapping.endObject().endObject().endObject();
		final PutMappingRequest mappingRequest = Requests.putMappingRequest(index).type(type).source(mapping);
		client.admin().indices().putMapping(mappingRequest).actionGet();
	}

	void register(IndexIntegrate buildService) {
		nameMap.put(buildService.id(), buildService);
	}

	void register(int id) {
		nameMap.put(id, new IndexBuilder(id,
											  client,
	                                          config,
	                                          factory,
	                                          mapper));
	}

	public Set<Map.Entry<Integer, IndexIntegrate>> entryView() {
		return nameMap.entrySet();
	}

	static class IndexBuilder extends IndexBuilderTemplate {

        private final String type;
        private final IndexMapper mapper;

        public IndexBuilder(int id,
				   			 TransportClient client,
				   			 IndexBuildConfig config,
				   			 DefinitionFactory factory,
                             IndexMapper mapper) {
            super(id, client, config, factory);
            type = factory.getIndexClass(id).getType();
            this.mapper = mapper;
        }

        @Override
        public int id() {
            return id;
        }

        @Override
        protected int getTotal() {
            return mapper.count(type);
        }

        @Override
        protected void boforeBuild() throws IbsException {
        	super.boforeBuild();
        	pageSize = pageSize * 10;
        }

        //FIXME
        @Override
        protected void afterBuild() {
            pageSize = pageSize / 10;
        }

        @Override
        public CursorTemplate<KeyableMap<String, Object, Long>> createCursor() {
            return new IndexCursor(type, mapper);
        }

		@Override
		public void deleteIndex(String index) throws IbsException {
			// TODO Auto-generated method stub

		}

		@Override
		public void buildMapping(int id) throws Exception {
			// TODO Auto-generated method stub

		}

	}

	IndexBuildConfig getConfig() {
		return config;
	}

}
