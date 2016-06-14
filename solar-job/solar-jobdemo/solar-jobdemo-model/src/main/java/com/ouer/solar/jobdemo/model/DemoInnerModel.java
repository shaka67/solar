/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.jobdemo.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DemoInnerModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> list;
	private Map<Integer, String> map;
	
	public List<String> getList() {
		return list;
	}
	
	public void setList(List<String> list) {
		this.list = list;
	}
	
	public Map<Integer, String> getMap() {
		return map;
	}
	
	public void setMap(Map<Integer, String> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "DemoInnerModel [list=" + list + ", map=" + map + "]";
	}
	
}
