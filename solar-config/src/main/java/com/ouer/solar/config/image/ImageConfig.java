/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.image;

import com.ouer.solar.config.BaseConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ImageConfig extends BaseConfig {

	private String thirdparty;
	private String accessKey;
	private String secretKey;
	private String namespace;
	private String storeConfig;

	public String getThirdparty() {
		return thirdparty;
	}

	public void setThirdparty(String thirdparty) {
		this.thirdparty = thirdparty;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getStoreConfig() {
		return storeConfig;
	}

	public void setStoreConfig(String storeConfig) {
		this.storeConfig = storeConfig;
	}

}
