/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server.dynamic;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ouer.solar.config.sms.SmsConfigLocator;
import com.ouer.solar.config.sms.SmsServiceObject;
import com.ouer.solar.config.sms.SmsServiceObjects;
import com.ouer.solar.protocol.ObjectConverter;
import com.ouer.solar.sms.api.SmsApi;
import com.ouer.solar.sms.api.SmsMessage;
import com.ouer.solar.sms.api.SmsNotifyApi;
import com.ouer.solar.sms.api.SmsResponse;
import com.ouer.solar.sms.server.SmsNotifier;
import com.ouer.solar.sms.server.SmsService;
import com.ouer.solar.sms.server.SmsServiceProxy;
import com.ouer.solar.sms.server.mapper.SmsMobileMapMapper;
import com.ouer.solar.sms.server.mapper.SmsRecordMapper;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SmsServiceManager implements SmsApi, SmsNotifyApi {

	private final static Logger LOG = LoggerFactory.getLogger(SmsServiceManager.class);

	private final SmsConfigLocator locator;
	private final SmsDalManager dalManager;
	private final ObjectConverter converter;

	private final ConcurrentMap<String, Future<SmsServiceProxy>> services = Maps.newConcurrentMap(); // appId -> service
	private final ConcurrentMap<String, Future<SmsService>> smsServices = Maps.newConcurrentMap(); // class -> smsService
	private final ConcurrentMap<String, Future<SmsNotifier>> notifiers = Maps.newConcurrentMap(); // class -> notifier

	public SmsServiceManager(SmsConfigLocator locator, SmsDalManager dalManager, ObjectConverter converter) {
		this.locator = locator;
		this.dalManager = dalManager;
		this.converter = converter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SmsResponse<String> send(SmsMessage message) {
		final String appId = message.getAppId();
		return getSmsProxy(appId).send(message);
	}

	@Override
	public boolean notified(String appId, String notifyClass, Object args) {
		final SmsNotifier notifier = getSmsNotifier(notifyClass);
		return notifier.notified(appId, args);
	}

	public SmsServiceProxy getSmsProxy(final String appId) {
		Future<SmsServiceProxy> future = services.get(appId);
		if (future == null) {
			final Callable<SmsServiceProxy> callable = new Callable<SmsServiceProxy>() {
				@Override
				public SmsServiceProxy call() {
					try {
						return createSmsServiceProxy(appId);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<SmsServiceProxy> task = new FutureTask<SmsServiceProxy>(
					callable);
			future = services.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			services.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			services.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new SmsDynamicException(e.getCause());
		}
	}

	public void removeSmsProxy(String appId) throws Exception {
		final Future<SmsServiceProxy> future = services.remove(appId);
		if (future != null) {
			if (future.isDone()) {
//				future.get().shutdown();
			}
			else if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
		}

		dalManager.removeSqlSessionFactory(appId);
		// FIXME smsServices and notifiers?
	}

	public SmsService getSmsService(final SmsServiceObject sso) {
		final String clazz = sso.getSmsServiceClass();
		Future<SmsService> future = smsServices.get(clazz);
		if (future == null) {
			final Callable<SmsService> callable = new Callable<SmsService>() {
				@Override
				public SmsService call() {
					try {
						final SmsService service = (SmsService) Class.forName(clazz).newInstance();
						service.initialize();
						return service;
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<SmsService> task = new FutureTask<SmsService>(
					callable);
			future = smsServices.putIfAbsent(clazz, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			smsServices.remove(clazz, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			smsServices.remove(clazz, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new SmsDynamicException(e.getCause());
		}
	}

	public SmsNotifier getSmsNotifier(final String clazz) {
		Future<SmsNotifier> future = notifiers.get(clazz);
		if (future == null) {
			final Callable<SmsNotifier> callable = new Callable<SmsNotifier>() {
				@Override
				public SmsNotifier call() {
					try {
						final SmsNotifier service = (SmsNotifier) Class.forName(clazz).newInstance();
						service.initialize();
						return service;
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<SmsNotifier> task = new FutureTask<SmsNotifier>(
					callable);
			future = notifiers.putIfAbsent(clazz, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			notifiers.remove(clazz, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			notifiers.remove(clazz, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new SmsDynamicException(e.getCause());
		}
	}

	private SmsServiceProxy createSmsServiceProxy(String appId) throws Exception {
		final String smsServiceObjects = locator.getConfig(appId).getSmsServiceObjects();
		final SmsServiceObjects objects = converter.fromString(smsServiceObjects, SmsServiceObjects.class);
		final List<SmsServiceObject> list = objects.getList();
		final List<SmsService> services = Lists.newArrayList();
		SmsService smsService;
		double rate;
		for (final SmsServiceObject sso : list) {
			smsService = getSmsService(sso);
			rate = sso.getRate();
			if (rate <= 0) {
				rate = Double.MAX_VALUE;
			}
			smsService.setRate(rate);
			services.add(smsService);
		}
		final SmsRecordMapper smsRecordDalMapper = dalManager
				.newMapperFactoryBean(appId, SmsRecordMapper.class).getObject();
		final SmsMobileMapMapper smsMobileMapMapper = dalManager
				.newMapperFactoryBean(appId, SmsMobileMapMapper.class).getObject();

		return new SmsServiceProxy(services, smsRecordDalMapper, smsMobileMapMapper);
	}

}
