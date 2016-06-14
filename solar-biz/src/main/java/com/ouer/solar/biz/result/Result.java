/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;

import java.io.Serializable;

/**
 * 代表一个处理的结果。
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 下午4:13:54
 * @param <T>
 */

public interface Result<T> extends Serializable {

    /**
     * 请求是否成功。
     * 
     * @return 如果成功，则返回<code>true</code>
     */
    boolean isSuccess();

    /**
     * 设置请求成功标志。
     * 
     * @param success 成功标志
     * @return 处理的结果 @see Result
     */
    Result<T> setSuccess(boolean success);

    // /**
    // * 取得结果代码。
    // *
    // * @return 结果代码
    // */
    // ResultCode getResultCode();

    /**
     * 设置结果代码。
     * 
     * @param resultCode 结果代码
     * @return 处理的结果 @see Result
     */
    Result<T> setResultCode(ResultCode resultCode);

    /**
     * 取得结果信息
     * 
     * @return 结果信息
     */
    String getMessage();

    /**
     * 取得结果值。
     * 
     * @return 结果值
     */
    T getResult();

    /**
     * 设置结果值。
     * 
     * @param T 结果值
     * @return 处理的结果 @see Result
     */
    Result<T> setResult(T result);

    /**
     * 获取错误信息
     * 
     * @return Throwable
     */
    Throwable getError();

    /**
     * 设置错误信息
     * 
     * @param error 错误信息
     * @return 处理的结果 @see Result
     */
    Result<T> setError(Throwable error);

    /**
     * 设置code值
     * 
     * @return code值
     */
    int getCode();

    /**
     * 获取运行时间，单位毫秒。
     * 
     * @return 运行时间，单位毫秒。
     */
    long getRuntime();

    /**
     * 设置运行时间
     * 
     * @param runtime 运行时间，单位毫秒。
     * @return 处理的结果 @see Result
     */
    Result<T> setRuntime(long runtime);
    
    ResultCode getResultCode();

}
