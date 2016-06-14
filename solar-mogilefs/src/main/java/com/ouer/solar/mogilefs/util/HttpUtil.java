/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public final class HttpUtil {

	private HttpUtil() {}

	public static void writePutHead(OutputStream out,URL url,long totalBytes) throws IOException {
		final Writer writer = new OutputStreamWriter(out);
		writer.write("PUT ");
		writer.write(url.getPath());
		writer.write(" HTTP/1.0\r\nContent-length: ");
		// writer.write(" HTTP/1.1\r\nContent-length: ");
		writer.write(Long.toString(totalBytes));
		writer.write("\r\n\r\n");

		writer.close();
	}

}
