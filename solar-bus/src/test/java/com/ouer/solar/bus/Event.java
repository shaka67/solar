/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

/**
 *
 * @auther chenxi
 */

public class Event {

	public Event(String name) {
		name_ = name;
	}

	public String getName() {
		return name_;
	}

	private final String name_;
}
