/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.definition;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum IndexType {

	NO("no"),
    NOT_ANALYZED("not_analyzed"),
    ANALYZED("analyzed")
    ;

    private final String desc;
    
    IndexType(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }

    public static IndexType fromDesc(String desc) {
        for (final IndexType type : IndexType.values()) {
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
