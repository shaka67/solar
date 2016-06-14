/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.ConcurrentUtil;
import com.ouer.solar.concurrent.ReusedCountLatch;
import com.ouer.solar.lang.SimpleThreadFactory;
import com.ouer.solar.search.common.KeyableMap;
import com.ouer.solar.search.dal.QueryWrapper;
import com.ouer.solar.search.dal.cursor.CursorTemplate;
import com.ouer.solar.search.definition.DefinitionFactory;
import com.ouer.solar.search.definition.IndexField;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class IndexBuilderTemplate implements IndexIntegrate {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	protected final int id;
	protected final IndexBuildConfig config;
	protected final TransportClient client;
	protected final DefinitionFactory factory;
	protected final ReusedCountLatch latch;

	protected final AtomicInteger total = new AtomicInteger(0);
    protected int pageSize;

	public IndexBuilderTemplate(int id,
								   TransportClient client,
								   IndexBuildConfig config,
								   DefinitionFactory factory) {
		this.id = id;
		this.client = client;
		this.config = config;
		this.factory = factory;
		latch = new ReusedCountLatch(config.getIndexPartionNum());
		pageSize = config.getPageSize();
	}

	protected void boforeBuild() throws IbsException {
		if (factory.getIndexClass(id).isPreIndex()) {
			try {
				final String index = factory.getIndexClass(id).getIndex();
				final String type = factory.getIndexClass(id).getType();
//				final IndicesExistsResponse resp = client.admin().indices().prepareExists(index).execute().actionGet();
//				if (resp.isExists()) {
//					client.admin().indices().prepareDelete(index).execute().actionGet();
//				}
//				client.admin().indices().prepareCreate(index).execute().actionGet();
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
			} catch (final IOException e) {
				throw new IbsException(e);
			}
		}
	}

	protected void afterBuild() {

	}

	protected int begin() throws Exception {
		buildMapping(id);
		final Exception error = ConcurrentUtil.sleep(config.getWaitTime(),
				TimeUnit.SECONDS);
		if (error != null) {
			LOG.error("thread wait error", error);
			throw new RuntimeException(error);
		}
		boforeBuild();

		final int totalItems = getTotal();
		if (totalItems == 0) {
			LOG.warn("No [{}] found in database.", name());
			return totalItems;
		}
		LOG.info("[{}] {} found in database.", totalItems, name());

		return totalItems;
	}

	@Override
	public void buildMapping(int id) throws Exception {
		final String index = factory.getIndexClass(id).getIndex();
		final String type = factory.getIndexClass(id).getType();
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

	abstract protected int getTotal();

	public String name() {
	    return factory.getIndexClass(id).getType();
	}

	@Override
	public void buildIndex() throws Exception {
		final long totalItems = begin();
		if (totalItems == 0) {
			return;
		}

		try {
			buildDatas();
		} catch (final Exception e) {
			LOG.error("build index error.", e);
		}

		if (LOG.isInfoEnabled()) {
			LOG.info("building [{}] index finished, create [{}] records .",
					name(), total);
		}

		if (totalItems != total.get()) {
			LOG.warn(
					" the completed {}[{}] is not equals the find datas[{}]! ",
					new Object[] { name(), totalItems, total.get() });
		}

		finished();
	}

	protected void finished() {
		afterBuild();
		total.set(0);
	}

	protected void buildDatas() throws InterruptedException, IOException {
		final ExecutorService executor = Executors.newFixedThreadPool(
		        config.getIndexPartionNum(), new SimpleThreadFactory(name() + "-"
						+ config.getIndexPartionNum() + "fixed-indexBuilder"));
		if (LOG.isInfoEnabled()) {
			LOG.info("[{}] index builder starting [{}] threads. ",
					name(), config.getIndexPartionNum());
		}

		for (int num = 0; num < config.getIndexPartionNum(); num++) {
			final PartionBuildExecutor partionExecutor = new PartionBuildExecutor(id, num);
			executor.execute(partionExecutor);
		}

		latch.await();
		latch.reset();

		ConcurrentUtil.shutdownAndAwaitTermination(executor);
	}

	public abstract CursorTemplate<KeyableMap<String, Object, Long>> createCursor();

	class PartionBuildExecutor implements Runnable {

	    private final int id;
	    private final String type;
        private final int partionNum;
        private final IndexWriter indexWriter;

		public PartionBuildExecutor(int id, int partionNum) {
		    this.id = id;
		    type = factory.getIndexClass(id).getType();
			this.partionNum = partionNum;
			indexWriter = new IndexWriter(config, client, factory);
			indexWriter.setName(name() + "(partion_" + partionNum + ")");
		}

		@Override
		public void run() {
			final CursorTemplate<KeyableMap<String, Object, Long>> cursor = createCursor();
			final QueryWrapper query = QueryWrapper.createQueryWrapper(type, pageSize,
					partionNum, config.getIndexPartionNum());
			cursor.setQuery(query);

			while (cursor.hasNext()) {
				final List<KeyableMap<String, Object, Long>> datas = cursor.next();
				indexWriter.executeAdd(id, datas);
				if (LOG.isInfoEnabled()) {
					LOG.info("[{}] build [{}] {}.", new Object[] {
							indexWriter.getName(), cursor.count(), name() });
				}
			}

			total.getAndAdd(cursor.count());
			latch.decrement();
		}

	}
}
