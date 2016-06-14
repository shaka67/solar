/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.access.fs;

import com.ouer.solar.access.Access;
import com.ouer.solar.access.AccessDecorator;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 下午5:43:22
 */
public class MooseFSAccess extends AccessDecorator {

    public MooseFSAccess(Access access) {
        super(access, "MooseFS");
    }

}
