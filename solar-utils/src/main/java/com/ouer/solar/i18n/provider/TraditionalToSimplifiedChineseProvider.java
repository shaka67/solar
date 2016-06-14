/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.i18n.provider;

/**
 * 繁体转简体
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午3:49:10
 */
public class TraditionalToSimplifiedChineseProvider extends ChineseCharConverterProvider {
    @Override
    protected String getCodeTableName() {
        return "TraditionalToSimplifiedChinese";
    }
}
