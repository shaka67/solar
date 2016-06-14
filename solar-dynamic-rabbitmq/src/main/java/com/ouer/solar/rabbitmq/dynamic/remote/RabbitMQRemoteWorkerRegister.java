/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.ouer.solar.remote.RemoteClassDefinitions;
import com.ouer.solar.remote.RemoteMethod;
import com.ouer.solar.remote.RemoteWorker;

/**
 * Register remote worker implementation object.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQRemoteWorkerRegister implements RabbitMQRemoteWorkerInterface, ApplicationContextAware {

	private ApplicationContext context;
    /* remote method group to remote worker implementation mapping */
    private final Map<String, Object> workers;
    /* remote method group to remote method name mapping */
    private final Multimap<String, String> groups;
    /* remote method name to local method name mapping */
    private final Map<String, String> methods;

    private final RemoteClassDefinitions definitions;

    private boolean initialized = false;

    /**
     * @param definitions A set of injected remote worker class definitions.
     */
    public RabbitMQRemoteWorkerRegister(RemoteClassDefinitions definitions)
    {
        Preconditions.checkArgument(definitions != null);
        groups = HashMultimap.create();
        methods = Maps.newHashMap();
        workers = Maps.newHashMap();
        this.definitions = definitions;
    }

    public void initialize() {
    	if (initialized) {
    		return;
    	}
    	final Set<Class<?>> remoteClasses = definitions.getRemoteClasses();
        for (final Class<?> remoteClass : remoteClasses) {
        	registerWorker(remoteClass);
        }
        initialized = true;
    }

    void registerWorker(Class<?> workerClass)
    {
        final RemoteWorker rw = workerClass.getAnnotation(RemoteWorker.class);
        if (rw == null) {
            return;
        }

        final String group = rw.group();
        final Object worker = context.getBean(workerClass);
        if (!workers.containsKey(group)) {
            workers.put(group, worker);
        }

        for (final Method method : workerClass.getMethods()) {
            final RemoteMethod rm = method.getAnnotation(RemoteMethod.class);
            if (rm == null) {
                continue;
            }
            String name = rm.name();
            if (name.isEmpty()) {
                name = method.getName();
            }
            if (!methods.containsKey(name)) {
                methods.put(name, method.getName());
            }
            if (!groups.containsEntry(group, name)) {
                groups.put(group, name);
            }
        }
    }

    /**
     * @return Size of registered remote methods.
     */
    public int sizeOfRemoteMethods()
    {
        return groups.size();
    }

    /**
     * @return Size of registered remote groups.
     */
    public int sizeOfRemoteGroups()
    {
        return groups.keySet().size();
    }

    /**
     * @return Size of registered remote workers.
     */
    public int sizeOfRemoteWorkers()
    {
        return workers.size();
    }

    /**
     * @param group The name of the remote method group
     * @param method The name of the remote method
     * @return True if the given group and method have been correctly registered.
     */
    public boolean containsRemoteMethod(String group, String method)
    {
        return groups.containsEntry(group, method);
    }

    /**
     * @param methodGroup The group name of the remote method
     * @return An instance of remote worker implementation that correspond to
     * the given method name.
     */
    public Object getRemoteWorker(String group)
    {
        return workers.get(group);
    }

    /**
     * @param group The group name of the remote method
     * @return A set of remote method names
     */
    public Set<String> getRemoteMethods(String group)
    {
        return Sets.newHashSet(groups.get(group));
    }

    /**
     * Get registered local method from the given remote method
     *
     * @param routing The name of the routing key
     * @return The registered remote method that corresponds to the given routing key.
     */
    public String getRemoteMethod(String routing)
    {
        if (methods.containsKey(routing)) {
            return methods.get(routing);
        }
        return "";
    }

    /**
     * @return A set of registered remote method group names
     */
    public Set<String> getRemoteGroups()
    {
        return Sets.newHashSet(groups.keySet());
    }

    /**
     * Extract remote group name from the given key. Empty string would be returned
     * if no register remote group can be found.
     *
     * @param exchange The full name of the exchange
     * @return The name of the remote method group extracted from the given exchange
     */
    public String getRemoteGroup(String exchange)
    {
        final String group = StringUtils.substringAfter(exchange, DEFAULT_REMOTE_WORKER_EXCHANGE_PREFIX);
        if (groups.containsKey(group)) {
            return group;
        }
        return "";
    }

    /**
     * @return A set of registered remote worker instances.
     */
    public Set<Object> getRemoteWorkers()
    {
        return Sets.newHashSet(workers.values());
    }

    public String getRemoteExchange(String group)
    {
        return DEFAULT_REMOTE_WORKER_EXCHANGE_PREFIX + group;
    }

    public String getRemoteQueue(String group)
    {
        return DEFAULT_REMOTE_WORKER_QUEUE_PREFIX + group;
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}

}