/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

import com.ouer.solar.bus.BusSignalManager;

/**
 *
 * @auther chenxi
 */

public class Sender {

	private final BusSignalManager bsm;
	
	public Sender(BusSignalManager bsm) {
		this.bsm = bsm;
	}

	public void send() {
		bsm.signal(new Event("test"));
	}

}
