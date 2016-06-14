/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.publisher;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ouer.solar.config.sync.ConfigSyncPublisher;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

//@Controller
@RequestMapping("/publisher")
public class PublisherController {

	private final ConfigSyncPublisher publisher;

	public PublisherController(ConfigSyncPublisher publisher) {
		this.publisher = publisher;
	}

	@RequestMapping(value = "/sync", method = { RequestMethod.POST })
	public void sync(@RequestParam String appId,
					  @RequestParam String configType) {
		publisher.sync(appId, configType);
	}
}
