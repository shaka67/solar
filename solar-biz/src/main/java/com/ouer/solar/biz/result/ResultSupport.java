/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;

import com.ouer.solar.biz.resourcebundle.ResourceBundleConstant;

/**
 * 一个调用结果的简单实现
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 下午6:08:52
 * @param <T>
 */

public class ResultSupport<T> implements Result<T>, ResourceBundleConstant {

    private static final long serialVersionUID = 3976733653567025460L;

    /** 是否执行成功 */
    protected boolean success;

    /** 结果码 */
    protected ResultCode resultCode;

    /** 结果对象 */
    protected T result;

    /** 出错信息 */
    protected Throwable error;

    /** 结果信息 */
    protected String message;

    /** code值 */
    protected int code;

    /** 毫秒吧 */
    protected long runtime = -1;

    /**
     * 创建一个result。
     */
    protected ResultSupport() {
    }

    /**
     * 工厂只是用来屏蔽泛型，创建一个默认的result。
     * 
     * @param <T>
     * @return 默认的result
     */
    public static <T> Result<T> create() {
        return new ResultSupport<T>();
    }

    /**
     * 工厂只是用来屏蔽泛型，创建一个默认的result。
     * 
     * @param <T>
     * @param success 是否成功
     * @return 默认的result
     */
    public static <T> Result<T> create(boolean success) {
        return new ResultSupport<T>(success);
    }

    /**
     * 工厂只是用来屏蔽泛型，创建一个默认的result。
     * 
     * @param result 结果对象
     * @return 默认的result
     */
    public static <T> Result<T> create(T result) {
        Result<T> output = new ResultSupport<T>();
        output.setResult(result);

        return output;
    }

    /**
     * 工厂只是用来屏蔽泛型，创建一个默认的result。
     * 
     * @param result 结果对象
     * @param success 是否成功
     * @return 默认的result
     */
    public static <T> Result<T> create(T result, boolean success) {
        Result<T> output = new ResultSupport<T>(success);
        output.setResult(result);
        // FIXME
        output.setResultCode(ResultCode.SUCCESS);

        return output;
    }

    /**
     * 工厂只是用来屏蔽泛型，创建一个默认的result。
     * 
     * @param e 异常
     * @return 默认的result
     */
    public static <T> Result<T> create(Throwable e) {
        Result<T> output = new ResultSupport<T>(false);
        output.setError(e);

        return output;
    }

    /**
     * 工厂只是用来屏蔽泛型，创建一个默认的result。
     * 
     * @param result 结果对象
     * @param e 异常
     * @return 默认的result
     */
    public static <T> Result<T> create(T result, Throwable e) {
        Result<T> output = new ResultSupport<T>(false);
        output.setResult(result);
        output.setError(e);

        return output;
    }

    /**
     * 创建一个result。
     * 
     * @param success 是否成功
     */
    public ResultSupport(boolean success) {
        this.success = success;
    }

    /**
     * 创建一个result。
     * 
     * @param success 是否成功
     */
    public ResultSupport(boolean success, ResultCode resultCode) {
        this.success = success;
        this.resultCode = resultCode;
    }

    /**
     * 请求是否成功。
     * 
     * @return 如果成功，则返回<code>true</code>
     */
    @Override
	public boolean isSuccess() {
        return success;
    }

    /**
     * 设置请求成功标志。
     * 
     * @param success 成功标志
     */
    @Override
	public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    /**
     * 设置结果代码。
     * 
     * @param resultCode 结果代码
     */
    @Override
	public Result<T> setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.message = resultCode.getMessage().getMessage();
        this.code = resultCode.getMessage().getCode();
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 取得结果对象。
     * 
     * @return 结果对象
     */
    @Override
	public T getResult() {
        return result;
    }

    /**
     * 设置结果对象。
     * 
     * @param T 结果对象
     */
    @Override
	public Result<T> setResult(T result) {
        this.result = result;
        return this;
    }

    /**
     * 转换成字符串的表示。
     * 
     * @return 字符串表示 FIXME
     */

    @Override
	public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Result {\n");
        builder.append("    success    = ").append(success).append(",\n");
        builder.append("    result     = ").append(result).append(",\n");
        if (resultCode != null) {
            builder.append("    message     = ").append(message).append(",\n");
        }

        if (error != null) {
            builder.append("    error     = ").append(error).append(",\n");
        }

        if (runtime >= 0) {
            builder.append("    runtime     = ").append(runtime).append("ms,\n");
        }
        builder.append("}");
        return builder.toString();
    }

    @Override
    public Throwable getError() {
        return error;
    }

    @Override
    public Result<T> setError(Throwable error) {
        this.error = error;
        return this;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
	public long getRuntime() {
        return runtime;
    }

    @Override
	public Result<T> setRuntime(long runtime) {
        this.runtime = runtime;
        return this;
    }

    @Override
	public ResultCode getResultCode() {
        return resultCode;
    }

}
