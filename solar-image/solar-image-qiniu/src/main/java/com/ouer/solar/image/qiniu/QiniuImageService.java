/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image.qiniu;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.ouer.solar.StringPool;
import com.ouer.solar.config.image.ImageConfig;
import com.ouer.solar.image.Image;
import com.ouer.solar.image.ImageKey;
import com.ouer.solar.image.ImageRetrieveDetails;
import com.ouer.solar.image.ImagePublicRetrieve;
import com.ouer.solar.image.ImageService;
import com.ouer.solar.image.ImageStoreException;
import com.ouer.solar.io.StreamUtil;
import com.ouer.solar.protocol.GsonObjectConverter;
import com.ouer.solar.protocol.ObjectConverter;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * Qiniu image store implementation, please refer to http://developer.qiniu.com/docs/v6/api/reference/up/upload.html
 * for store(upload) and
 * http://developer.qiniu.com/docs/v6/api/reference/fop/image/imageview2.html for retrieve(download)
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class QiniuImageService implements ImageService {

	private static final String QINIU = "qiniu";
	private static final String QINIU_BUCKETS_FILE = "buckets.properties";
	private static final String BASIC_IMAGE_PREFIX = "imageView2";

	private final static Logger LOG = LoggerFactory.getLogger(QiniuImageService.class);

	private UploadManager uploadManager;
	private ObjectConverter converter;

	private final Map<String, String> buckets = Maps.newHashMap();

//	public QiniuImageService(UploadManager uploadManager, ObjectConverter converter) {
//		this.uploadManager = uploadManager;
//		this.converter = converter;
//	}

	@Override
	public void initialize() {
		uploadManager = new UploadManager();
		converter = new GsonObjectConverter();
		initBuckets();
	}

	private void initBuckets() {
		final Properties properties = new Properties();
        final InputStream resource = Thread.currentThread()
                                     .getContextClassLoader()
                                     .getResourceAsStream(QINIU_BUCKETS_FILE);
        try {
            properties.load(resource);
            final Iterator<Object> it = properties.keySet().iterator();
            String bucket;
            String bucketUrl;
            while (it.hasNext()) {
            	bucket = (String) it.next();
            	bucketUrl = properties.getProperty(bucket);
            	buckets.put(bucket, bucketUrl);
            }
        } catch (final Exception e) {
            throw new RuntimeException("Failed to load buckets", e);
        }
	}

	@Override
	public boolean isInitialized() {
		return uploadManager != null && converter != null;
	}

	@Override
	public Image store(ImageConfig config, InputStream input, String key) throws ImageStoreException {
		final Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
		QiniuStoreConfig context;
		try {
			context = converter.fromString(config.getStoreConfig(), QiniuStoreConfig.class);
			return storeQinuImage(auth, config, input, key, context);
		} catch (final IOException e) {
			LOG.error("", e);
			throw new ImageStoreException(e);
		}
	}

	@Override
	public String retrieve(ImageConfig config, ImageRetrieveDetails details) {
		final int width = details.getWidth();
		final int height = details.getHeight();

		final String bucketUrl = buckets.get(config.getNamespace());
		if (width <= 0 || height <= 0) {
			return bucketUrl + StringPool.Symbol.SLASH + details.getKey();
		}

		final int mode = QiniuImageMode.fromMode(details.getMode()).value();
		if (mode < 0) {
			LOG.warn("invalid mode [{}]", details.getMode());
			return bucketUrl + StringPool.Symbol.SLASH + details.getKey();
		}

		if (width <= 0 && height <= 0) {
			LOG.warn("invalid width and height [{},{}]", width, height);
			return bucketUrl + StringPool.Symbol.SLASH + details.getKey();
		}

		final StringBuilder sb = new StringBuilder(bucketUrl)
						.append(StringPool.Symbol.SLASH)
						.append(details.getKey())
						.append(StringPool.Symbol.QUESTION_MARK)
						.append(BASIC_IMAGE_PREFIX)
						.append(StringPool.Symbol.SLASH)
						.append(mode)
						.append(StringPool.Symbol.SLASH);
		if (width > 0) {
			sb.append("w").append(StringPool.Symbol.SLASH).append(width);
		}
		if (height > 0) {
			if (width > 0) {
				sb.append(StringPool.Symbol.SLASH);
			}
			sb.append("h").append(StringPool.Symbol.SLASH).append(height);
		}
		return sb.toString();
	}


	@Override
	public String retrieve(ImagePublicRetrieve request) {
		final int width = request.getWidth();
		final int height = request.getHeight();

		final String bucketUrl = buckets.get(request.getKey().getNamespace());
		final String key = request.getKey().getKey();
		if (width <= 0 || height <= 0) {
			return bucketUrl + StringPool.Symbol.SLASH + key;
		}

		final int mode = QiniuImageMode.fromMode(request.getMode()).value();
		if (mode < 0) {
			LOG.warn("invalid mode [{}]", request.getMode());
			return bucketUrl + StringPool.Symbol.SLASH + key;
		}

		if (width <= 0 && height <= 0) {
			LOG.warn("invalid width and height [{},{}]", width, height);
			return bucketUrl + StringPool.Symbol.SLASH + key;
		}

		final StringBuilder sb = new StringBuilder(bucketUrl)
						.append(StringPool.Symbol.SLASH)
						.append(key)
						.append(StringPool.Symbol.QUESTION_MARK)
						.append(BASIC_IMAGE_PREFIX)
						.append(StringPool.Symbol.SLASH)
						.append(mode)
						.append(StringPool.Symbol.SLASH);
		if (width > 0) {
			sb.append("w").append(StringPool.Symbol.SLASH).append(width);
		}
		if (height > 0) {
			if (width > 0) {
				sb.append(StringPool.Symbol.SLASH);
			}
			sb.append("h").append(StringPool.Symbol.SLASH).append(height);
		}
		return sb.toString();
	}

	private QiniuImage storeQinuImage(Auth auth, ImageConfig config,
			InputStream input, String key, QiniuStoreConfig context) throws ImageStoreException {
		if (config == null || config.getNamespace() == null) {
			throw new ImageStoreException("you must provide bucket value at least");
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("start to upload image to bucket[{}], with key value[{}]...",
					config.getNamespace(), key);
		}
		final String token = auth.uploadToken(config.getNamespace(),
				key, 3600, defaultStringMap());
        try {
        	final StringMap params = genCustomParams(context);
        	final Response resp = uploadManager.put(StreamUtil.readBytes(input).getRawBytes(),
        			key, token, params, null, true);
        	if (resp.statusCode != 200) {
        		LOG.warn("failed to upload image to bucket[{}], with key value[{}], with http status code=[{}].",
        				config.getNamespace(), key, resp.statusCode);
        	}
        	if (LOG.isInfoEnabled()) {
    			LOG.info("successfully to upload image to bucket[{}], with key value[{}].",
    					config.getNamespace(), key);
    		}
        	final QiniuImage image = resp.jsonToObject(QiniuImage.class);
        	image.setUrl(buckets.get(config.getNamespace()) + StringPool.Symbol.SLASH + image.getKey());
        	image.setImageKey(new ImageKey(QINIU, config.getNamespace(), image.getKey()));
        	return image;
        } catch (final QiniuException e) {
        	throw new ImageStoreException(e);
        } catch (final IOException e) {
        	throw new ImageStoreException(e);
        } catch (final Exception e) {
        	throw new ImageStoreException(e);
        }
	}

	private StringMap defaultStringMap() {
		return new StringMap()
        .putNotEmpty(QiniuImageConstants.QINIU_RETURN_RESP, QiniuImageConstants.QINIU_DEFAULT_RETURN_RESP);
	}

	private StringMap genCustomParams(Object context) throws Exception {
		final StringMap params = new StringMap();
		if (context != null) {
			final PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(context);
			for (final PropertyDescriptor descriptor : descriptors) {
				params.put("x:" + descriptor.getName(), PropertyUtils.getProperty(context, descriptor.getName()));
			}
		}
		return params;
	}

}
