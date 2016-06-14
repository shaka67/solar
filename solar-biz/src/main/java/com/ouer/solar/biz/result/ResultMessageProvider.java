/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;

import com.ouer.solar.MessageUtil;
import com.ouer.solar.biz.resourcebundle.AbstractResourceBundle;
import com.ouer.solar.biz.resourcebundle.ResourceBundleConstant;

/**
 * 支持自定义枚举类的ResultCodeMessage
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 下午5:57:24
 */

public class ResultMessageProvider implements ResultCodeMessage, ResourceBundleConstant {

    private ResultCode resultCode;
    private AbstractResourceBundle resourceBundle;
    private Object[] params;

    public ResultMessageProvider(ResultCode resultCode, AbstractResourceBundle resourceBundle) {
        this(resultCode, resourceBundle, null);
    }

    public ResultMessageProvider(ResultCode resultCode, AbstractResourceBundle resourceBundle, Object[] params) {
        this.resultCode = resultCode;
        this.resourceBundle = resourceBundle;
        this.params = params;

        if (resultCode == null) {
            throw new IllegalArgumentException("ResultCode is null");
        }

    }

    @Override
	public String getName() {
        return this.resultCode.getName();
    }

    @Override
	public ResultCode getResultCode() {
        return this.resultCode;
    }

    @Override
	public String getMessage() {
        return MessageUtil.getMessage(this.resourceBundle, this.resultCode.getName(), this.params);
    }

    @Override
	public int getCode() {
        String code = MessageUtil.getMessage(resourceBundle, resultCode.getName() + XPATH_RESOURCE_CODE_SUFFIX, params);
        return (code == null) ? 0 : Integer.valueOf(code);
    }

    @Override
	public String toString() {
        return getMessage();
    }

}
