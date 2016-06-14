/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.location.api;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.spring.ProfileSpringConfig;

/**
*
* @author tonghu
* @version $Id: LocationServiceTest.java, v 0.1 2015年10月13日 下午4:55:48 tonghu Exp $
*/

@ActiveProfiles("dev")
@ContextConfiguration(classes = {LocationApiSpringConfig.class, ProfileSpringConfig.class})
public class LocationTest extends AbstractJUnit4SpringContextTests {

	@Autowired
    private LocationApi locationService;

    @Test
    public void test_load() {
        final ZoneBO zone = locationService.load(1L);
        Assert.assertNotNull(zone);
        Assert.assertEquals("中国", zone.getName());
    }

    @Test
    public void test_findParent() {
        final ZoneBO zone = locationService.findParent(16L);
        Assert.assertNotNull(zone);
    }

    @Test
    public void test_listRoots() {
    	final List<ZoneBO> zones = locationService.listRoots();
    	Assert.assertNotNull(zones);
    }

    @Test
    public void test_listChildren() {
    	final List<ZoneBO> zones = locationService.listChildren(1L);
    	Assert.assertNotNull(zones);
    }

    @Test
    public void test_listSiblings() {
    	final List<ZoneBO> zones = locationService.listSiblings(4L);
    	Assert.assertNotNull(zones);
    }

    @Test
    public void test_listParents() {
    	final List<ZoneBO> zones = locationService.listParents(521L);
    	Assert.assertNotNull(zones);
    }

    @Test
    public void test_isAncestor() {
    	final Boolean result = locationService.isAncestor(1009L,2118L);
    	Assert.assertNotNull(result);
    }
}
