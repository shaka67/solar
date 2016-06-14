/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

import java.util.List;

/**
 * A BusMember binds to one or more buses to participate in a one way conversation as listener
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface BusMember
{
    public void bind(String busName);

    public void unbind(String busName);

    public List<String> getRegisteredBuses();

    public Object getMemberHandle();

    public void setMemberHandle(Object handle);
}
