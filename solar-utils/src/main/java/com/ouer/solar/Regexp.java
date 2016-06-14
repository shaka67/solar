/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用的正则表达式及方法
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-3-5 上午11:56:00
 */
public abstract class Regexp {

    /**
     * 数字
     */
    public static final String NUMERIC = "^[0-9]+$";

    /**
     * 数字结尾
     */
    public static final String NUMBER_END = "\\d+$";

    /** 匹配url(较完整的匹配所有) */
    // static final String URL =
    // "\\b((ftp|https?)://[-\\w]+(\\.\\w[-\\w]*)+|(?i:[a-z0-9](?:[-a-z0-9]*[a-z0-9])?\\.)+(?-i:com\\b|edu\\b|biz\\b|gov\\b|in(?:t|fo)\\b|mil\\b|net\\b|org\\b|[a-z][a-z]\\b))(:\\d+)?(/[^.!,?;\"'<>()\\[\\]{}\\s\\x7F-\\xFF]*(?:[.!,?]+[^.!,?;\"'<>()\\[\\]{}\\s\\x7F-\\xFF]+)*)?";
    public static final String URL =
            "((ftp|https?)://[-\\w]+(\\.\\w[-\\w]*)+|(?i:[a-z0-9](?:[-a-z0-9]*[a-z0-9])?\\.)+(?-i:com\\b|edu\\b|biz\\b|gov\\b|in(?:t|fo)\\b|mil\\b|net\\b|org\\b|[a-z][a-z]\\b))(:\\d+)?(/[^.!,?;\"'<>()\\[\\]{}\\s\\x7F-\\xFF]*(?:[.!,?]+[^.!,?;\"'<>()\\[\\]{}\\s\\x7F-\\xFF]+)*)?";

    /**
     * 电话号码
     */
    public static final String PHONE = "(\\(\\d{3}\\)|\\d{3,4}-)?\\d{7,8}$";

    /**
     * 手机号码
     */
    public static final String MOBILE = "0?1[3584]\\d{9}";

    /**
     * 邮政编码
     */
    public static final String POSTCODE = "^[0-9]\\d{5}$";

    /**
     * 电子邮箱
     */
    public static final String EMAIL = "^[a-zA-Z0-9_\\-\\.]{1,}@([a-zA-Z0-9_\\-]{1,}\\.[a-zA-Z0-9]{1,}){1,}$";

    /**
     * 判断字符串<code>content</code>是否包含正则表达式<code>regex</code>如果是则返回<ocde>true</code>
     * 
     * @param content 字符串
     * @param regex 正则表达式
     * @return 是否包含
     */
    public static boolean contain(String content, String regex) {
        if (content == null || regex == null) {
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        return matcher.find();
    }

}
