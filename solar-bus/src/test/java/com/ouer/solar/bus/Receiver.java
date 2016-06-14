/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

import com.ouer.solar.bus.BusSignalListener;
import com.ouer.solar.bus.BusSignalManager;

/**
 *
 * @auther chenxi
 */

public class Receiver implements BusSignalListener<Event> {

	public Receiver(BusSignalManager bsm) {
		bsm.bind(Event.class, this);
	}

	@Override
	public void signalFired(Event signal) {
		System.out.println(signal.getName());
	}

}
