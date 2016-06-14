/**
 * Ouer.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.ouer.solar.location.server;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.location.api.LocationApi;
import com.ouer.solar.location.api.ZoneBO;

/**
 *
 * @author tonghu
 * @version $Id: LocationApi.java, v 0.1 2015年9月17日 下午7:42:52 tonghu Exp $
 */

public class LocationService implements LocationApi {

	private static final Logger LOG = LoggerFactory.getLogger(LocationService.class);

	private final ZoneMapper zoneMapper;
	private final Mapper dozerMapper;

	public LocationService(ZoneMapper zoneMapper, Mapper dozerMapper) {
		this.zoneMapper = zoneMapper;
		this.dozerMapper = dozerMapper;
	}

	/**
	 * @see com.ouer.solar.location.api.LocationApi#load(java.lang.Long)
	 */
	@Override
	public ZoneBO load(Long id) {
		if(id > 0){
			final Zone zone = zoneMapper.selectByPrimaryKey(id);
			if(zone!=null) {
				return dozerMapper.map(zone, ZoneBO.class);
			} else {
				return null;
			}
		}
		else{
			LOG.error("id无效");
			return null;
		}
	}

	/**
	 * @see com.ouer.solar.location.api.LocationApi#listRoots()
	 */
	@Override
	public List<ZoneBO> listRoots() {
		final List<Zone> zones = zoneMapper.listRoots();
		final List<ZoneBO> result = new ArrayList<ZoneBO>();
		for (final Zone zone : zones) {
			result.add(dozerMapper.map(zone, ZoneBO.class));
		}
		return result;
	}

	/**
	 * @see com.ouer.solar.location.api.LocationApi#listChildren(java.lang.Long)
	 */
	@Override
	public List<ZoneBO> listChildren(Long zoneId) {
		final List<Zone> zones = zoneMapper.listChildren(zoneId);
		final List<ZoneBO> result = new ArrayList<ZoneBO>();
		for (final Zone zone : zones) {
			result.add(dozerMapper.map(zone, ZoneBO.class));
		}
		return result;
	}

	/**
	 * @see com.ouer.solar.location.api.LocationApi#listSiblings(java.lang.Long)
	 */
	@Override
	public List<ZoneBO> listSiblings(Long zoneId) {
		final List<Zone> zones = zoneMapper.listSiblings(zoneId);
		final List<ZoneBO> result = new ArrayList<ZoneBO>();
		for (final Zone zone : zones) {
			result.add(dozerMapper.map(zone, ZoneBO.class));
		}
		return result;
	}

	/**
	 * @see com.ouer.solar.location.api.LocationApi#findParent(java.lang.String)
	 */
	@Override
	public ZoneBO findParent(Long zoneId) {
		final Zone zone = zoneMapper.findParent(zoneId);
		return dozerMapper.map(zone, ZoneBO.class);
	}

	/**
	 * @see com.ouer.solar.location.api.LocationApi#listParents(java.lang.String)
	 */
	@Override
	public List<ZoneBO> listParents(Long zoneId) {
		final List<Zone> zones = zoneMapper.listParents(zoneId);
		final List<ZoneBO> result = new ArrayList<ZoneBO>();
		for (final Zone zone : zones) {
			result.add(dozerMapper.map(zone, ZoneBO.class));
		}
		return result;
	}

	@Override
	public Boolean isAncestor(Long srcId,Long destId) {
		final Zone zoneSrc = zoneMapper.selectByPrimaryKey(srcId);
		final Zone zoneDest = zoneMapper.selectByPrimaryKey(destId);
		if(zoneSrc.getParentId().equals(zoneDest.getParentId())){
			return true;
		}
		else{
			return zoneDest.getPath().contains(zoneSrc.getPath()+zoneSrc.getId()+">");
		}
	}
}
