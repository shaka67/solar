/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import com.ouer.solar.able.Valuable;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum MogileOperation implements Valuable<String> {

	GET_PATHS,
	DELETE,
	FILE_INFO,
	GET_DEVICES,
	CREATE_OPEN,
	CREATE_CLOSE,
	LIST_KEYS,
	RENAME,
	UPDATECLASS,
	SLEEP;

	@Override
	public String value() {
		return name().toLowerCase();
	}

}
