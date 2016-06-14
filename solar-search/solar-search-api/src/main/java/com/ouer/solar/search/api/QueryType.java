/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum QueryType {

	MATCH("match"),
	TERM("term"),
    PHRASE("phrase"),
    RANGE("range")
    ;

    private final String desc;
    
    QueryType(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }

    public static QueryType fromDesc(String desc) {
        for (final QueryType type : QueryType.values()) {
            if (type.desc().equals(desc)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return desc();
    }
}
