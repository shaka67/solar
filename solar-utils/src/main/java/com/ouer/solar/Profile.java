/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum Profile {

    DEV("dev"),
    TEST("test"),
    PREPROD("preprod"),
    PROD("prod"),
    UCLOUD("ucloudprod"),
    BETA("beta"),
    GAMMA("gamma")
    ;

    private final String desc;

    Profile(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }

    public static Profile fromDesc(String desc) {
        for (final Profile profile : Profile.values()) {
            if (profile.desc().equals(desc)) {
                return profile;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return desc();
    }

}