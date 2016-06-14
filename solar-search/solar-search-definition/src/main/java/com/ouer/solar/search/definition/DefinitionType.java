/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.definition;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum DefinitionType {

	INDEX_CONFIG_FILE("definition-index.xml"),
	RESULT_CONFIG_FILE("definition-result.xml"),
	SEARCH_CONFIG_FILE("definition-search.xml")
    ;

    private final String file;

    DefinitionType(String file) {
    	this.file = file;
    }

	public String file() {
		return file;
	}
}
