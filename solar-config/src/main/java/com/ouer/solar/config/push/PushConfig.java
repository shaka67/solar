/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.push;

import com.google.common.base.Objects;
import com.ouer.solar.config.BaseConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class PushConfig extends BaseConfig {

	private String thirdparty;
	private String packageName;
	private String iosSecret;
	private String androidSecret;
	private String iosEnv;
	private String androidEnv;
	private int notifyType;
	private int passThrough;
	private int retries;

	public String getThirdparty() {
		return thirdparty;
	}

	public void setThirdparty(String thirdparty) {
		this.thirdparty = thirdparty;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getIosSecret() {
		return iosSecret;
	}

	public void setIosSecret(String iosSecret) {
		this.iosSecret = iosSecret;
	}

	public String getAndroidSecret() {
		return androidSecret;
	}

	public void setAndroidSecret(String androidSecret) {
		this.androidSecret = androidSecret;
	}

	public String getIosEnv() {
		return iosEnv;
	}

	public void setIosEnv(String iosEnv) {
		this.iosEnv = iosEnv;
	}

	public String getAndroidEnv() {
		return androidEnv;
	}

	public void setAndroidEnv(String androidEnv) {
		this.androidEnv = androidEnv;
	}

	public int getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}

	public int getPassThrough() {
		return passThrough;
	}

	public void setPassThrough(int passThrough) {
		this.passThrough = passThrough;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(thirdparty, iosSecret, androidSecret);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        final PushConfig another = (PushConfig) obj;
        return Objects.equal(thirdparty, another.thirdparty)
        		&& Objects.equal(androidSecret, another.androidSecret)
        		&& Objects.equal(iosSecret, another.iosSecret);
	}

}
