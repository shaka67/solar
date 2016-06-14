/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.definition;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum StoreType {
	
	NO("no"),
    YES("yes"),
    WITH_OFFSETS("with_offsets"),
    COMPOUND("compound")
    ;

    private final String desc;
    
    StoreType(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }

    public static StoreType fromDesc(String desc) {
        for (final StoreType type : StoreType.values()) {
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
