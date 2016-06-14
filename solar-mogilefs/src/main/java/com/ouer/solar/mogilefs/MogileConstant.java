/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import java.util.regex.Pattern;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface MogileConstant {

	Pattern ERROR_PATTERN = Pattern.compile("^ERR\\s+(\\w+)\\s*(\\S*)");

	Pattern OK_PATTERN = Pattern.compile("^OK\\s+\\d*\\s*(\\S*)");

	Pattern HOST_PORT_PATTERN = Pattern.compile("^(\\S+):(\\d+)$");

	Pattern VALID_HTTP_RESPONSE = Pattern
			.compile("^HTTP/\\d+\\.\\d+\\s+(\\d+)");

	int ERR_PART = 1;

	int ERRSTR_PART = 2;

	int ARGS_PART = 1;

	int DEAD_TIME_CHECK = 5000;

	int TRY_MAX = 15;

	int SOCKET_TIMEOUT = 60000;

	int CONNECT_TIMEOUT = 3000;

	int RETRY_SLEEP_TIME = 2000;

	String MOGILE_FID = "fid";

	String MOGILE_PATH = "path";

	String MOGILE_DEVID = "devid";

	int ERROR_MAX = 100;

}
