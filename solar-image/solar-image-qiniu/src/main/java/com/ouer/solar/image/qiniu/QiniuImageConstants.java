/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image.qiniu;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class QiniuImageConstants {

	static final String THIRDPARTY = "qiniu";

	static final String KEY_ACCESS_KEY = "access_key";
	static final String KEY_SECRET_KEY = "secret_key";
	static final String KEY_BUCKETS = "buckets";
	static final String KEY_XML_URL = "xml_url";

	// magic variables
	static final String MV_BUCKET = "$(bucket)";
	static final String MV_KEY = "$(key)";
	static final String MV_ETAG = "$(etag)";
	static final String MV_FNAME = "$(fname)";
	static final String MV_FSIZE = "$(fsize)";
	static final String MV_MIMETYPE = "$(mimeType)";
	static final String MV_ENDUSER = "$(endUser)";
	static final String MV_IMAGE = "$(imageInfo)";
	static final String MV_IMAGE_WIDTH = "$(imageInfo.width)";
	static final String MV_IMAGE_HEIGHT = "$(imageInfo.height)";

	static final String QINIU_RETURN_RESP = "returnBody";
	static final String QINIU_DEFAULT_RETURN_RESP = "{\"key\": $(key), \"hash\": $(etag), \"size\": $(fsize), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}";
}
