/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rns;

import java.util.List;
import java.util.Map;

import com.ouer.solar.able.Keyable;
import com.ouer.solar.search.api.UpdateType;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class UpdateMap implements Keyable<String> {

	private final String type;
	private final Map<UpdateType, List<Long>> params;

	public UpdateMap(String type, Map<UpdateType, List<Long>> params) {
		this.type = type;
		this.params = params;
	}

	@Override
	public String getId() {
		return type;
	}

	public Map<UpdateType, List<Long>> getParams() {
		return params;
	}
}
