/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image.qiniu;

import com.ouer.solar.able.Valuable;
import com.ouer.solar.image.BasicImageMode;

/**
 * Refer to http://developer.qiniu.com/docs/v6/api/reference/fop/image/imageview2.html for more information
 * about qiniu processing mode.
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum QiniuImageMode implements Valuable<Integer> {

	MODE_1(BasicImageMode.MIN_WH_CUT),
	MODE_2(BasicImageMode.MAX_WH),
	MODE_3(BasicImageMode.MIN_WH)
	;

	private int value;

	QiniuImageMode(BasicImageMode mode) {
		this.value = mode.code();
	}

	@Override
	public Integer value() {
		return value;
	}

	public static QiniuImageMode fromMode(BasicImageMode mode) {
		for (final QiniuImageMode qiniu : QiniuImageMode.values()) {
            if (qiniu.value() == mode.code()) {
                return qiniu;
            }
        }
        return null;
	}
}
