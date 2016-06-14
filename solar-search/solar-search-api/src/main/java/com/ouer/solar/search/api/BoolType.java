/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;


/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum BoolType {

	MUST("must"),
    MUST_NOT("mustNot"),
    SHOULD("should")
    ;

    private final String desc;
    
    BoolType(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }

    public static BoolType fromDesc(String desc) {
        for (final BoolType type : BoolType.values()) {
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
