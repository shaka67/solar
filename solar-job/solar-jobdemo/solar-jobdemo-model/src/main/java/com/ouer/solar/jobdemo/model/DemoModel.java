/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.jobdemo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DemoModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private Date date;
	private DemoInnerModel inner;
	private List<Integer> integers;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public DemoInnerModel getInner() {
		return inner;
	}
	
	public void setInner(DemoInnerModel inner) {
		this.inner = inner;
	}
	
	public List<Integer> getIntegers() {
		return integers;
	}
	
	public void setIntegers(List<Integer> integers) {
		this.integers = integers;
	}

	@Override
	public String toString() {
		return "DemoModel [id=" + id + ", name=" + name + ", date=" + date
				+ ", inner=" + inner + ", integers=" + integers + "]";
	}
	
}
