/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs.request;

import java.util.Map;

import com.google.common.collect.Maps;
import com.ouer.solar.mogilefs.MogileOperation;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MogileRequest {

	private String command;
	private String domain;
	private String storageClass;
	private String key;

	private final Map<String, String> arguments = Maps.newHashMap();

	public String getCommand() {
		return command;
	}

	public MogileRequest setOperation(MogileOperation operation) {
		this.command = operation.value();
		return this;
	}

	public String getDomain() {
		return domain;
	}

	public MogileRequest setDomain(String domain) {
		this.domain = domain;
		arguments.put("domain", domain);
		return this;
	}

	public String getStorageClass() {
		return storageClass;
	}

	public MogileRequest setStorageClass(String storageClass) {
		this.storageClass = storageClass;
		arguments.put("class", storageClass);
		return this;
	}

	public String getKey() {
		return key;
	}

	public MogileRequest setKey(String key) {
		this.key = key;
		arguments.put("key", key);
		return this;
	}

	public Map<String, String> getArguments() {
		return arguments;
	}

	public MogileRequest addArgument(String key, String value) {
		arguments.put(key, value);
		return this;
	}

	@Override
	public String toString() {
		return "MogileRequest [command=" + command + ", domain=" + domain
				+ ", storageClass=" + storageClass + ", key=" + key
				+ ", arguments=" + arguments + "]";
	}
}
