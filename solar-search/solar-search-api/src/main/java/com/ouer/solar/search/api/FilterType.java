/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum FilterType {

	TERM("term"),
    IN("in"),
    NOT("not"),
    RANGE("range")
    // TODO
    ;

    private final String desc;

    FilterType(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }

    public static FilterType fromDesc(String desc) {
        for (final FilterType type : FilterType.values()) {
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
