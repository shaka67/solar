/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.op;

import com.ouer.solar.StringUtil;


/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum Operation {

	SAVE,
	DELETE,
	UPDATE,
	SAVE_OR_UPDATE;

	@Override
    public String toString() {
		return StringUtil.toCamelCase(this.name().toLowerCase());
	}


}
