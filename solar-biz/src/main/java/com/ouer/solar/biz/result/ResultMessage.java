/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;

/**
 * 代表一个包含结果信息的接口
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 下午6:06:03
 */

public interface ResultMessage {

    /**
     * 获取结果代码
     * 
     * @return 处理结果的代码 @see ResultCode
     */
    ResultCode getResultCode();

}
