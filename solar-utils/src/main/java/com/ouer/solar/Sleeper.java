/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.Uninterruptibles;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class Sleeper {

    public static void sleep(long duration, TimeUnit unit)
    {
        Uninterruptibles.sleepUninterruptibly(duration, unit);
    }

}