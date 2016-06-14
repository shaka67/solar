/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.BitSet;

import com.ouer.solar.i18n.LocaleUtil;
import com.ouer.solar.internal.Entities;

/**
 * 字符串转义工具类。
 * 
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月21日 上午7:57:32
 */
public abstract class StringEscapeUtil {

    // ==========================================================================
    // Java和JavaScript。
    // ==========================================================================

    /**
     * 按Java的规则对字符串进行转义。
     * <p>
     * 将双引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn't say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @return 转义后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeJava(String str) {
        return escapeJavaStyleString(str, false, false);
    }

    /**
     * 按Java的规则对字符串进行转义。
     * <p>
     * 将双引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn't say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @param strict 是否以严格的方式编码字符串
     * @return 转义后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeJava(String str, boolean strict) {
        return escapeJavaStyleString(str, false, strict);
    }

    /**
     * 按Java的规则对字符串进行转义。
     * <p>
     * 将双引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn't say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void escapeJava(String str, Appendable out) throws IOException {
        escapeJavaStyleString(str, false, out, false);
    }

    /**
     * 按Java的规则对字符串进行转义。
     * <p>
     * 将双引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn't say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @param strict 是否以严格的方式编码字符串
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void escapeJava(String str, Appendable out, boolean strict) throws IOException {
        escapeJavaStyleString(str, false, out, strict);
    }

    /**
     * 按JavaScript的规则对字符串进行转义。
     * <p>
     * 将双引号、单引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn\'t say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @return 转义后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeJavaScript(String str) {
        return escapeJavaStyleString(str, true, false);
    }

    /**
     * 按JavaScript的规则对字符串进行转义。
     * <p>
     * 将双引号、单引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn\'t say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @param strict 是否以严格的方式编码字符串
     * @return 转义后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeJavaScript(String str, boolean strict) {
        return escapeJavaStyleString(str, true, strict);
    }

    /**
     * 按JavaScript的规则对字符串进行转义。
     * <p>
     * 将双引号、单引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn\'t say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void escapeJavaScript(String str, Appendable out) throws IOException {
        escapeJavaStyleString(str, true, out, false);
    }

    /**
     * 按JavaScript的规则对字符串进行转义。
     * <p>
     * 将双引号、单引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn\'t say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @param strict 是否以严格的方式编码字符串
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void escapeJavaScript(String str, Appendable out, boolean strict) throws IOException {
        escapeJavaStyleString(str, true, out, strict);
    }

    /**
     * 按Java或JavaScript的规则对字符串进行转义。
     * 
     * @param str 要转义的字符串
     * @param javascript 是否对单引号和slash进行转义
     * @param strict 是否以严格的方式编码字符串
     * @return 转义后的字符串
     */
    private static String escapeJavaStyleString(String str, boolean javascript, boolean strict) {
        if (str == null) {
            return null;
        }

        try {
            StringBuilder out = new StringBuilder(str.length() * 2);

            if (escapeJavaStyleString(str, javascript, out, strict)) {
                return out.toString();
            }

            return str;
        } catch (IOException e) {
            return str; // StringBuilder不可能发生这个异常
        }
    }

    /**
     * 按Java或JavaScript的规则对字符串进行转义。
     * 
     * @param str 要转义的字符串
     * @param javascript 是否对单引号和slash进行转义
     * @param out 输出流
     * @param strict 是否以严格的方式编码字符串
     * @return 如果字符串没有变化，则返回<code>false</code>
     */
    private static boolean escapeJavaStyleString(String str, boolean javascript, Appendable out, boolean strict)
            throws IOException {
        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        int length = str.length();

        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);

            if (ch < 32) {
                switch (ch) {
                    case '\b':
                        out.append('\\');
                        out.append('b');
                        break;

                    case '\n':
                        out.append('\\');
                        out.append('n');
                        break;

                    case '\t':
                        out.append('\\');
                        out.append('t');
                        break;

                    case '\f':
                        out.append('\\');
                        out.append('f');
                        break;

                    case '\r':
                        out.append('\\');
                        out.append('r');
                        break;

                    default:

                        if (ch > 0xf) {
                            out.append("\\u00" + Integer.toHexString(ch).toUpperCase());
                        } else {
                            out.append("\\u000" + Integer.toHexString(ch).toUpperCase());
                        }

                        break;
                }

                // 设置改变标志
                needToChange = true;
            } else if (strict && ch > 0xff) {
                if (ch > 0xfff) {
                    out.append("\\u").append(Integer.toHexString(ch).toUpperCase());
                } else {
                    out.append("\\u0").append(Integer.toHexString(ch).toUpperCase());
                }

                // 设置改变标志
                needToChange = true;
            } else {
                switch (ch) {
                    case '\'':
                    case '/': // 注意：对于javascript，对/进行escape是重要的安全措施。

                        if (javascript) {
                            out.append('\\');

                            // 设置改变标志
                            needToChange = true;
                        }

                        out.append(ch);

                        break;

                    case '"':
                        out.append('\\');
                        out.append('"');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case '\\':
                        out.append('\\');
                        out.append('\\');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    default:
                        out.append(ch);
                        break;
                }
            }
        }

        return needToChange;
    }

    /**
     * 按Java的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 不包含转义字符的字符串
     * @return 恢复成未转义的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String unescapeJava(String str) {
        return unescapeJavaStyleString(str);
    }

    /**
     * 按Java的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 包含转义字符的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void unescapeJava(String str, Appendable out) throws IOException {
        unescapeJavaStyleString(str, out);
    }

    /**
     * 按JavaScript的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 包含转义字符的字符串
     * @return 恢复成未转义的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String unescapeJavaScript(String str) {
        return unescapeJavaStyleString(str);
    }

    /**
     * 按Java的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 包含转义字符的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void unescapeJavaScript(String str, Appendable out) throws IOException {
        unescapeJavaStyleString(str, out);
    }

    /**
     * 按Java的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 包含转义字符的字符串
     * @return 不包含转义字符的字符串
     */
    private static String unescapeJavaStyleString(String str) {
        if (str == null) {
            return null;
        }

        try {
            StringBuilder out = new StringBuilder(str.length());

            if (unescapeJavaStyleString(str, out)) {
                return out.toString();
            }

            return str;
        } catch (IOException e) {
            return str; // StringBuilder不可能发生这个异常
        }
    }

    /**
     * 按Java的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 包含转义字符的字符串
     * @param out 输出流
     * @return 如果字符串没有变化，则返回<code>false</code>
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    private static boolean unescapeJavaStyleString(String str, Appendable out) throws IOException {
        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        int length = str.length();
        StringBuilder unicode = new StringBuilder(4);
        boolean hadSlash = false;
        boolean inUnicode = false;

        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);

            if (inUnicode) {
                unicode.append(ch);

                if (unicode.length() == 4) {
                    String unicodeStr = unicode.toString();

                    try {
                        int value = Integer.parseInt(unicodeStr, 16);

                        out.append((char) value);
                        unicode.setLength(0);
                        inUnicode = false;
                        hadSlash = false;

                        // 设置改变标志
                        needToChange = true;
                    } catch (NumberFormatException e) {
                        out.append("\\u" + unicodeStr);
                    }
                }

                continue;
            }

            if (hadSlash) {
                hadSlash = false;

                switch (ch) {
                    case '\\':
                        out.append('\\');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case '\'':
                        out.append('\'');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case '\"':
                        out.append('"');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case 'r':
                        out.append('\r');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case 'f':
                        out.append('\f');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case 't':
                        out.append('\t');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case 'n':
                        out.append('\n');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case 'b':
                        out.append('\b');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case 'u': {
                        inUnicode = true;
                        break;
                    }

                    default:
                        out.append(ch);
                        break;
                }

                continue;
            } else if (ch == '\\') {
                hadSlash = true;
                continue;
            }

            out.append(ch);
        }

        if (hadSlash) {
            out.append('\\');
        }

        return needToChange;
    }

    // ==========================================================================
    // HTML和XML。
    // ==========================================================================

    /**
     * 根据HTML的规则，将字符串中的部分字符转换成实体编码。
     * <p>
     * 例如：<code>"bread" & "butter"</code>将被转换成 <tt>&amp;quot;bread&amp;quot; &amp;amp;
     * &amp;quot;butter&amp;quot;</tt>.
     * </p>
     * <p>
     * 支持所有HTML 4.0 entities。
     * </p>
     * 
     * @param str 要转义的字符串
     * @return 用实体编码转义的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     * @see <a href="http://hotwired.lycos.com/webmonkey/reference/special_characters/">ISO Entities</a>
     * @see <a href="http://www.w3.org/TR/REC-html32#latin1">HTML 3.2 Character Entities for ISO Latin-1</a>
     * @see <a href="http://www.w3.org/TR/REC-html40/sgml/entities.html">HTML 4.0 Character entity references</a>
     * @see <a href="http://www.w3.org/TR/html401/charset.html#h-5.3">HTML 4.01 Character References</a>
     * @see <a href="http://www.w3.org/TR/html401/charset.html#code-position">HTML 4.01 Code positions</a>
     */
    public static String escapeHtml(String str) {
        return escapeEntities(Entities.HTML40_MODIFIED, str);
    }

    /**
     * 根据HTML的规则，将字符串中的部分字符转换成实体编码。
     * <p>
     * 例如：<code>"bread" & "butter"</code>将被转换成 <tt>&amp;quot;bread&amp;quot; &amp;amp;
     * &amp;quot;butter&amp;quot;</tt>.
     * </p>
     * <p>
     * 支持所有HTML 4.0 entities。
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     * @see <a href="http://hotwired.lycos.com/webmonkey/reference/special_characters/">ISO Entities</a>
     * @see <a href="http://www.w3.org/TR/REC-html32#latin1">HTML 3.2 Character Entities for ISO Latin-1</a>
     * @see <a href="http://www.w3.org/TR/REC-html40/sgml/entities.html">HTML 4.0 Character entity references</a>
     * @see <a href="http://www.w3.org/TR/html401/charset.html#h-5.3">HTML 4.01 Character References</a>
     * @see <a href="http://www.w3.org/TR/html401/charset.html#code-position">HTML 4.01 Code positions</a>
     */
    public static void escapeHtml(String str, Appendable out) throws IOException {
        escapeEntities(Entities.HTML40_MODIFIED, str, out);
    }

    /**
     * 根据XML的规则，将字符串中的部分字符转换成实体编码。
     * <p>
     * 例如：<code>"bread" & "butter"</code>将被转换成 <tt>&amp;quot;bread&amp;quot; &amp;amp;
     * &amp;quot;butter&amp;quot;</tt>.
     * </p>
     * <p>
     * 只转换4种基本的XML实体：<code>gt</code>、<code>lt</code>、<code>quot</code>和 <code>amp</code>。 不支持DTD或外部实体。
     * </p>
     * 
     * @param str 要转义的字符串
     * @return 用实体编码转义的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeXml(String str) {
        return escapeEntities(Entities.XML, str);
    }

    /**
     * 根据XML的规则，将字符串中的部分字符转换成实体编码。
     * <p>
     * 例如：<code>"bread" & "butter"</code>将被转换成 <tt>&amp;quot;bread&amp;quot; &amp;amp;
     * &amp;quot;butter&amp;quot;</tt>.
     * </p>
     * <p>
     * 只转换4种基本的XML实体：<code>gt</code>、<code>lt</code>、<code>quot</code>和 <code>amp</code>。 不支持DTD或外部实体。
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void escapeXml(String str, Appendable out) throws IOException {
        escapeEntities(Entities.XML, str, out);
    }

    /**
     * 根据指定的规则，将字符串中的部分字符转换成实体编码。
     * 
     * @param entities 实体集合
     * @param str 要转义的字符串
     * @return 用实体编码转义的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeEntities(Entities entities, String str) {
        if (str == null) {
            return null;
        }

        try {
            StringBuilder out = new StringBuilder(str.length());

            if (escapeEntitiesInternal(entities, str, out)) {
                return out.toString();
            }

            return str;
        } catch (IOException e) {
            return str; // StringBuilder不可能发生这个异常
        }
    }

    /**
     * 根据指定的规则，将字符串中的部分字符转换成实体编码。
     * 
     * @param entities 实体集合
     * @param str 要转义的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void escapeEntities(Entities entities, String str, Appendable out) throws IOException {
        escapeEntitiesInternal(entities, str, out);
    }

    /**
     * 按HTML的规则对字符串进行反向转义，支持HTML 4.0中的所有实体，以及unicode实体如<code>&amp;#12345;</code> 。
     * <p>
     * 例如："&amp;lt;Fran&amp;ccedil;ais&amp;gt;"将被转换成"&lt;Fran&ccedil;ais&gt;"
     * </p>
     * <p>
     * 如果实体不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 不包含转义字符的字符串
     * @return 恢复成未转义的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String unescapeHtml(String str) {
        return unescapeEntities(Entities.HTML40, str);
    }

    /**
     * 按HTML的规则对字符串进行反向转义，支持HTML 4.0中的所有实体，以及unicode实体如<code>&amp;#12345;</code> 。
     * <p>
     * 例如："&amp;lt;Fran&amp;ccedil;ais&amp;gt;"将被转换成"&lt;Fran&ccedil;ais&gt;"
     * </p>
     * <p>
     * 如果实体不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 包含转义字符的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void unescapeHtml(String str, Appendable out) throws IOException {
        unescapeEntities(Entities.HTML40, str, out);
    }

    /**
     * 按XML的规则对字符串进行反向转义，支持unicode实体如<code>&amp;#12345;</code>。
     * <p>
     * 例如："&amp;lt;Fran&amp;ccedil;ais&amp;gt;"将被转换成"&lt;Fran&ccedil;ais&gt;"
     * </p>
     * <p>
     * 如果实体不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 不包含转义字符的字符串
     * @return 恢复成未转义的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String unescapeXml(String str) {
        return unescapeEntities(Entities.XML, str);
    }

    /**
     * 按XML的规则对字符串进行反向转义，支持unicode实体如<code>&amp;#12345;</code>。
     * <p>
     * 例如："&amp;lt;Fran&amp;ccedil;ais&amp;gt;"将被转换成"&lt;Fran&ccedil;ais&gt;"
     * </p>
     * <p>
     * 如果实体不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 不包含转义字符的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void unescapeXml(String str, Appendable out) throws IOException {
        unescapeEntities(Entities.XML, str, out);
    }

    /**
     * 按指定的规则对字符串进行反向转义。
     * 
     * @param entities 实体集合
     * @param str 不包含转义字符的字符串
     * @return 恢复成未转义的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String unescapeEntities(Entities entities, String str) {
        if (str == null) {
            return null;
        }

        try {
            StringBuilder out = new StringBuilder(str.length());

            if (unescapeEntitiesInternal(entities, str, out)) {
                return out.toString();
            }

            return str;
        } catch (IOException e) {
            return str; // StringBuilder不可能发生这个异常
        }
    }

    /**
     * 按指定的规则对字符串进行反向转义。
     * <p>
     * 如果实体不能被识别，它将被保留不变。
     * </p>
     * 
     * @param entities 实体集合
     * @param str 不包含转义字符的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void unescapeEntities(Entities entities, String str, Appendable out) throws IOException {
        unescapeEntitiesInternal(entities, str, out);
    }

    /**
     * 将字符串中的部分字符转换成实体编码。
     * 
     * @param entities 实体集合
     * @param str 要转义的字符串
     * @param out 字符输出流，不能为<code>null</code>
     * @return 如果字符串没有变化，则返回<code>false</code>
     * @throws IllegalArgumentException 如果<code>entities</code>或输出流为 <code>null</code>
     * @throws IOException 如果输出失败
     */
    private static boolean escapeEntitiesInternal(Entities entities, String str, Appendable out) throws IOException {
        boolean needToChange = false;

        if (entities == null) {
            throw new IllegalArgumentException("The Entities must not be null");
        }

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            String entityName = entities.getEntityName(ch);

            if (entityName == null) {
                out.append(ch);
            } else {
                out.append('&');
                out.append(entityName);
                out.append(';');

                // 设置改变标志
                needToChange = true;
            }
        }

        return needToChange;
    }

    /**
     * 将字符串中的已定义实体和unicode实体如<code>&amp;#12345;</code>转换成相应的unicode字符。
     * <p>
     * 未定义的实体将保留不变。
     * </p>
     * 
     * @param entities 实体集合，如果为<code>null</code>，则只转换<code>&amp;#number</code> 实体。
     * @param str 包含转义字符的字符串
     * @param out 字符输出流，不能为<code>null</code>
     * @return 如果字符串没有变化，则返回<code>false</code>
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    private static boolean unescapeEntitiesInternal(Entities entities, String str, Appendable out) throws IOException {
        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);

            if (ch == '&') {
                // 查找&xxxx;
                int semi = str.indexOf(';', i + 1);

                if (semi == -1 || i + 1 >= semi - 1) {
                    out.append(ch);
                    continue;
                }

                // 如果是&#xxxxx;
                if (str.charAt(i + 1) == '#') {
                    int firstCharIndex = i + 2;
                    int radix = 10;

                    if (firstCharIndex >= semi - 1) {
                        out.append(ch);
                        out.append('#');
                        i++;
                        continue;
                    }

                    char firstChar = str.charAt(firstCharIndex);

                    if (firstChar == 'x' || firstChar == 'X') {
                        firstCharIndex++;
                        radix = 16;

                        if (firstCharIndex >= semi - 1) {
                            out.append(ch);
                            out.append('#');
                            i++;
                            continue;
                        }
                    }

                    try {
                        int entityValue = Integer.parseInt(str.substring(firstCharIndex, semi), radix);

                        out.append((char) entityValue);

                        // 设置改变标志
                        needToChange = true;
                    } catch (NumberFormatException e) {
                        out.append(ch);
                        out.append('#');
                        i++;
                        continue;
                    }
                } else {
                    String entityName = str.substring(i + 1, semi);
                    int entityValue = -1;

                    if (entities != null) {
                        entityValue = entities.getEntityValue(entityName);
                    }

                    if (entityValue == -1) {
                        out.append('&');
                        out.append(entityName);
                        out.append(';');
                    } else {
                        out.append((char) entityValue);

                        // 设置改变标志
                        needToChange = true;
                    }
                }

                i = semi;
            } else {
                out.append(ch);
            }
        }

        return needToChange;
    }

    // ==========================================================================
    // SQL语句。
    // ==========================================================================

    /**
     * 按SQL语句的规则对字符串进行转义。
     * <p>
     * 例如：
     * <p/>
     * 
     * <pre>
     * statement.executeQuery(&quot;SELECT * FROM MOVIES WHERE TITLE='&quot; + StringEscapeUtil.escapeSql(&quot;McHale's Navy&quot;) + &quot;'&quot;);
     * </pre>
     * <p/>
     * </p>
     * <p>
     * 目前，此方法只将单引号转换成两个单引号：<code>"McHale's Navy"</code>转换成<code>"McHale''s
     * Navy"</code>。不处理字符串中包含的<code>%</code>和<code>_</code>字符。
     * </p>
     * 
     * @param str 要转义的字符串
     * @return 转义后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     * @see <a href="http://www.jguru.com/faq/view.jsp?EID=8881">faq</a>
     */
    public static String escapeSql(String str) {
        return StringUtil.replace(str, "'", "''");
    }

    /**
     * 按SQL语句的规则对字符串进行转义。
     * <p>
     * 例如：
     * <p/>
     * 
     * <pre>
     * statement.executeQuery(&quot;SELECT * FROM MOVIES WHERE TITLE='&quot; + StringEscapeUtil.escapeSql(&quot;McHale's Navy&quot;) + &quot;'&quot;);
     * </pre>
     * <p/>
     * </p>
     * <p>
     * 目前，此方法只将单引号转换成两个单引号：<code>"McHale's Navy"</code>转换成<code>"McHale''s
     * Navy"</code>。不处理字符串中包含的<code>%</code>和<code>_</code>字符。
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     * @see <a href="http://www.jguru.com/faq/view.jsp?EID=8881">faq</a>
     */
    public static void escapeSql(String str, Appendable out) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        String result = StringUtil.replace(str, "'", "''");

        if (result != null) {
            out.append(result);
        }
    }

    // ==========================================================================
    // URL/URI encoding/decoding。
    // 根据RFC2396：http://www.ietf.org/rfc/rfc2396.txt
    // ==========================================================================

    /** "Alpha" characters from RFC 2396. */
    private static final BitSet ALPHA = new BitSet(256);

    static {
        for (int i = 'a'; i <= 'z'; i++) {
            ALPHA.set(i);
        }

        for (int i = 'A'; i <= 'Z'; i++) {
            ALPHA.set(i);
        }
    }

    /** "Alphanum" characters from RFC 2396. */
    private static final BitSet ALPHANUM = new BitSet(256);

    static {
        ALPHANUM.or(ALPHA);

        for (int i = '0'; i <= '9'; i++) {
            ALPHANUM.set(i);
        }
    }

    /** "Mark" characters from RFC 2396. */
    private static final BitSet MARK = new BitSet(256);

    static {
        MARK.set('-');
        MARK.set('_');
        MARK.set('.');
        MARK.set('!');
        MARK.set('~');
        MARK.set('*');
        MARK.set('\'');
        MARK.set('(');
        MARK.set(')');
    }

    /** "Reserved" characters from RFC 2396. */
    private static final BitSet RESERVED = new BitSet(256);

    static {
        RESERVED.set(';');
        RESERVED.set('/');
        RESERVED.set('?');
        RESERVED.set(':');
        RESERVED.set('@');
        RESERVED.set('&');
        RESERVED.set('=');
        RESERVED.set('+');
        RESERVED.set('$');
        RESERVED.set(',');
    }

    /** "Unreserved" characters from RFC 2396. */
    private static final BitSet UNRESERVED = new BitSet(256);

    static {
        UNRESERVED.or(ALPHANUM);
        UNRESERVED.or(MARK);
    }

    /** 将一个数字转换成16进制的转换表。 */
    private static char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F' };

    /**
     * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
     * <p>
     * 除了RFC2396中的<code>unreserved</code>字符之外的所有字符，都将被转换成URL编码<code>%xx</code>。 根据RFC2396，<code>unreserved</code>的定义如下：
     * <p/>
     * 
     * <pre>
     * &lt;![CDATA
     *  unreserved  = alphanum | mark
     *  alphanum    = 大小写英文字母 | 数字
     *  mark        = &quot;-&quot; | &quot;_&quot; | &quot;.&quot; | &quot;!&quot; | &quot;&tilde;&quot; | &quot;*&quot; | &quot;'&quot; | &quot;(&quot; | &quot;)&quot;
     * ]]&gt;
     * </pre>
     * <p/>
     * </p>
     * <p>
     * 警告：该方法使用当前线程默认的字符编码来编码URL，因此该方法在不同的上下文中可能会产生不同的结果。
     * </p>
     * 
     * @param str 要编码的字符串，可以是<code>null</code>
     * @return URL编码后的字符串
     */
    public static String escapeURL(String str) {
        try {
            return escapeURLInternal(str, null, true);
        } catch (UnsupportedEncodingException e) {
            return str; // 不可能发生这个异常
        }
    }

    /**
     * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
     * <p>
     * 除了RFC2396中的<code>unreserved</code>字符之外的所有字符，都将被转换成URL编码<code>%xx</code>。 根据RFC2396，<code>unreserved</code>的定义如下：
     * <p/>
     * 
     * <pre>
     * &lt;![CDATA
     *  unreserved  = alphanum | mark
     *  alphanum    = 大小写英文字母 | 数字
     *  mark        = &quot;-&quot; | &quot;_&quot; | &quot;.&quot; | &quot;!&quot; | &quot;&tilde;&quot; | &quot;*&quot; | &quot;'&quot; | &quot;(&quot; | &quot;)&quot;
     * ]]&gt;
     * </pre>
     * <p/>
     * </p>
     * <p>
     * 该方法使用指定的字符编码来编码URL。
     * </p>
     * 
     * @param str 要编码的字符串，可以是<code>null</code>
     * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
     * @return URL编码后的字符串
     * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
     */
    public static String escapeURL(String str, String encoding) throws UnsupportedEncodingException {
        return escapeURLInternal(str, encoding, true);
    }

    /**
     * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
     * <p>
     * 如果指定参数<code>strict</code>为<code>true</code>，则按严格的方式编码URL。 除了RFC2396中的 <code>unreserved</code>
     * 字符之外的所有字符，都将被转换成URL编码<code>%xx</code>。 根据RFC2396， <code>unreserved</code>的定义如下：
     * <p/>
     * 
     * <pre>
     * &lt;![CDATA
     *  unreserved  = alphanum | mark
     *  alphanum    = 大小写英文字母 | 数字
     *  mark        = &quot;-&quot; | &quot;_&quot; | &quot;.&quot; | &quot;!&quot; | &quot;&tilde;&quot; | &quot;*&quot; | &quot;'&quot; | &quot;(&quot; | &quot;)&quot;
     * ]]&gt;
     * </pre>
     * <p/>
     * </p>
     * <p>
     * 如果指定参数<code>strict</code>为<code>false</code>，则使用宽松的方式编码URL。 除了控制字符、空白字符以及RFC2396中的<code>reserved</code>
     * 字符之外的所有字符，都将被保留不变。 根据RFC2396，只有控制字符、空白字符以及符合下列<code>reserved</code>定义的字符才被转换成 <code>%xx</code>格式：
     * <p/>
     * 
     * <pre>
     * &lt;![CDATA
     *  reserved      = &quot;;&quot; | &quot;/&quot; | &quot;?&quot; | &quot;:&quot; | &quot;@&quot; | &quot;&amp;&quot; | &quot;=&quot; | &quot;+&quot; | &quot;$&quot; | &quot;,&quot;
     * ]]&gt;
     * </pre>
     * <p/>
     * </p>
     * <p>
     * 该方法使用指定的字符编码来编码URL。
     * </p>
     * 
     * @param str 要编码的字符串，可以是<code>null</code>
     * @param encoding 输出字符编码，如果为<code>null</code>，则使用当前线程默认的编码
     * @param strict 是否以严格的方式编码URL
     * @return URL编码后的字符串
     * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
     */
    public static String escapeURL(String str, String encoding, boolean strict) throws UnsupportedEncodingException {
        return escapeURLInternal(str, encoding, strict);
    }

    /**
     * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
     * <p>
     * 除了RFC2396中的<code>unreserved</code>字符之外的所有字符，都将被转换成URL编码<code>%xx</code>。 根据RFC2396，<code>unreserved</code>的定义如下：
     * <p/>
     * 
     * <pre>
     * &lt;![CDATA
     *  unreserved  = alphanum | mark
     *  alphanum    = 大小写英文字母 | 数字
     *  mark        = &quot;-&quot; | &quot;_&quot; | &quot;.&quot; | &quot;!&quot; | &quot;&tilde;&quot; | &quot;*&quot; | &quot;'&quot; | &quot;(&quot; | &quot;)&quot;
     * ]]&gt;
     * </pre>
     * <p/>
     * </p>
     * <p>
     * 该方法使用指定的字符编码来编码URL。
     * </p>
     * 
     * @param str 要编码的字符串，可以是<code>null</code>
     * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
     * @param out 输出到指定字符流
     * @throws IOException 如果输出到<code>out</code>失败
     * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
     * @throws IllegalArgumentException <code>out</code>为<code>null</code>
     */
    public static void escapeURL(String str, String encoding, Appendable out) throws IOException {
        escapeURLInternal(str, encoding, out, true);
    }

    /**
     * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
     * <p>
     * 如果指定参数<code>strict</code>为<code>true</code>，则按严格的方式编码URL。 除了RFC2396中的 <code>unreserved</code>
     * 字符之外的所有字符，都将被转换成URL编码<code>%xx</code>。 根据RFC2396， <code>unreserved</code>的定义如下：
     * <p/>
     * 
     * <pre>
     * &lt;![CDATA
     *  unreserved  = alphanum | mark
     *  alphanum    = 大小写英文字母 | 数字
     *  mark        = &quot;-&quot; | &quot;_&quot; | &quot;.&quot; | &quot;!&quot; | &quot;&tilde;&quot; | &quot;*&quot; | &quot;'&quot; | &quot;(&quot; | &quot;)&quot;
     * ]]&gt;
     * </pre>
     * <p/>
     * </p>
     * <p>
     * 如果指定参数<code>strict</code>为<code>false</code>，则使用宽松的方式编码URL。 除了控制字符、空白字符以及RFC2396中的<code>reserved</code>
     * 字符之外的所有字符，都将被保留不变。 根据RFC2396，只有控制字符、空白字符以及符合下列<code>reserved</code>定义的字符才被转换成 <code>%xx</code>格式：
     * <p/>
     * 
     * <pre>
     * &lt;![CDATA
     *  reserved      = &quot;;&quot; | &quot;/&quot; | &quot;?&quot; | &quot;:&quot; | &quot;@&quot; | &quot;&amp;&quot; | &quot;=&quot; | &quot;+&quot; | &quot;$&quot; | &quot;,&quot;
     * ]]&gt;
     * </pre>
     * <p/>
     * </p>
     * <p>
     * 该方法使用指定的字符编码来编码URL。
     * </p>
     * 
     * @param str 要编码的字符串，可以是<code>null</code>
     * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
     * @param out 输出到指定字符流
     * @param strict 是否以严格的方式编码URL
     * @throws IOException 如果输出到<code>out</code>失败
     * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
     * @throws IllegalArgumentException <code>out</code>为<code>null</code>
     */
    public static void escapeURL(String str, String encoding, Appendable out, boolean strict) throws IOException {
        escapeURLInternal(str, encoding, out, strict);
    }

    /**
     * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
     * 
     * @param str 要编码的字符串，可以是<code>null</code>
     * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
     * @param strict 是否以严格的方式编码URL
     * @return URL编码后的字符串
     * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
     */
    private static String escapeURLInternal(String str, String encoding, boolean strict)
            throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }

        try {
            StringBuilder out = new StringBuilder(64);

            if (escapeURLInternal(str, encoding, out, strict)) {
                return out.toString();
            }

            return str;
        } catch (UnsupportedEncodingException e) {
            throw e;
        } catch (IOException e) {
            return str; // StringBuilder不可能发生这个异常
        }
    }

    /**
     * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
     * 
     * @param str 要编码的字符串，可以是<code>null</code>
     * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
     * @param strict 是否以严格的方式编码URL
     * @param out 输出流
     * @return 如果字符串被改变，则返回<code>true</code>
     * @throws IOException 如果输出到<code>out</code>失败
     * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
     * @throws IllegalArgumentException <code>out</code>为<code>null</code>
     */
    private static boolean escapeURLInternal(String str, String encoding, Appendable out, boolean strict)
            throws IOException {
        if (encoding == null) {
            encoding = LocaleUtil.getContext().getCharset().name();
        }

        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        char[] charArray = str.toCharArray();
        int length = charArray.length;

        for (int i = 0; i < length; i++) {
            int ch = charArray[i];

            if (isSafeCharacter(ch, strict)) {
                // “安全”的字符，直接输出
                out.append((char) ch);
            } else if (ch == ' ') {
                // 特殊情况：空格（0x20）转换成'+'
                out.append('+');

                // 设置改变标志
                needToChange = true;
            } else {
                // 对ch进行URL编码。
                // 首先按指定encoding取得该字符的字节码。
                byte[] bytes = String.valueOf((char) ch).getBytes(encoding);

                for (byte toEscape : bytes) {
                    out.append('%');

                    int low = toEscape & 0x0F;
                    int high = (toEscape & 0xF0) >> 4;

                    out.append(HEXADECIMAL[high]);
                    out.append(HEXADECIMAL[low]);
                }

                // 设置改变标志
                needToChange = true;
            }
        }

        return needToChange;
    }

    /**
     * 判断指定字符是否是“安全”的，这个字符将不被转换成URL编码。
     * 
     * @param ch 要判断的字符
     * @param strict 是否以严格的方式编码
     * @return 如果是“安全”的，则返回<code>true</code>
     */
    private static boolean isSafeCharacter(int ch, boolean strict) {
        if (strict) {
            return UNRESERVED.get(ch);
        }
        return ch > ' ' && !RESERVED.get(ch) && !Character.isWhitespace((char) ch);
    }

    /**
     * 解码<code>application/x-www-form-urlencoded</code>格式的字符串。
     * <p>
     * 警告：该方法使用系统字符编码来解码URL，因此该方法在不同的系统中可能会产生不同的结果。
     * </p>
     * 
     * @param str 要解码的字符串，可以是<code>null</code>
     * @return URL解码后的字符串
     */
    public static String unescapeURL(String str) {
        try {
            return unescapeURLInternal(str, null);
        } catch (UnsupportedEncodingException e) {
            return str; // 不可能发生这个异常
        }
    }

    /**
     * 解码<code>application/x-www-form-urlencoded</code>格式的字符串。
     * 
     * @param str 要解码的字符串，可以是<code>null</code>
     * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
     * @return URL解码后的字符串
     * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
     */
    public static String unescapeURL(String str, String encoding) throws UnsupportedEncodingException {
        return unescapeURLInternal(str, encoding);
    }

    /**
     * 解码<code>application/x-www-form-urlencoded</code>格式的字符串。
     * 
     * @param str 要解码的字符串，可以是<code>null</code>
     * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
     * @param out 输出流
     * @throws IOException 如果输出到<code>out</code>失败
     * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
     * @throws IllegalArgumentException <code>out</code>为<code>null</code>
     */
    public static void unescapeURL(String str, String encoding, Appendable out) throws IOException {
        unescapeURLInternal(str, encoding, out);
    }

    /**
     * 解码<code>application/x-www-form-urlencoded</code>格式的字符串。
     * 
     * @param str 要解码的字符串，可以是<code>null</code>
     * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
     * @return URL解码后的字符串
     * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
     */
    private static String unescapeURLInternal(String str, String encoding) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }

        try {
            StringBuilder out = new StringBuilder(str.length());

            if (unescapeURLInternal(str, encoding, out)) {
                return out.toString();
            }

            return str;
        } catch (UnsupportedEncodingException e) {
            throw e;
        } catch (IOException e) {
            return str; // StringBuilder不可能发生这个异常
        }
    }

    /**
     * 解码<code>application/x-www-form-urlencoded</code>格式的字符串。FIXME，此方法恶心
     * 
     * @param str 要解码的字符串，可以是<code>null</code>
     * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
     * @param out 输出流
     * @return 如果字符串被改变，则返回<code>true</code>
     * @throws IOException 如果输出到<code>out</code>失败
     * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
     * @throws IllegalArgumentException <code>out</code>为<code>null</code>
     */
    private static boolean unescapeURLInternal(String str, String encoding, Appendable out) throws IOException {
        if (encoding == null) {
            encoding = LocaleUtil.getContext().getCharset().name();
        }

        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        byte[] buffer = null;
        int pos = 0;
        int startIndex = 0;

        char[] charArray = str.toCharArray();
        int length = charArray.length;

        for (int i = 0; i < length; i++) {
            int ch = charArray[i];

            if (ch < 256) {
                // 读取连续的字节，并将它按指定编码转换成字符。
                if (buffer == null) {
                    buffer = new byte[length - i]; // 最长只需要length - i
                }

                if (pos == 0) {
                    startIndex = i;
                }

                switch (ch) {
                    case '+':

                        // 将'+'转换成' '
                        buffer[pos++] = ' ';

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case '%':

                        if (i + 2 < length) {
                            try {
                                byte b = (byte) Integer.parseInt(str.substring(i + 1, i + 3), 16);

                                buffer[pos++] = b;
                                i += 2;

                                // 设置改变标志
                                needToChange = true;
                            } catch (NumberFormatException e) {
                                // 如果%xx不是合法的16进制数，则原样输出
                                buffer[pos++] = (byte) ch;
                            }
                        } else {
                            buffer[pos++] = (byte) ch;
                        }

                        break;

                    default:

                        // 写到bytes中，到时一起输出。
                        buffer[pos++] = (byte) ch;
                        break;
                }
                continue;
            }
            // 先将buffer中的字节串转换成字符串。
            if (pos > 0) {
                String s = new String(buffer, 0, pos, encoding);

                out.append(s);

                if (!needToChange && !s.equals(new String(charArray, startIndex, pos))) {
                    needToChange = true;
                }

                pos = 0;
            }

            // 如果ch是ISO-8859-1以外的字符，直接输出即可
            out.append((char) ch);
        }

        // 先将buffer中的字节串转换成字符串。
        if (pos > 0) {
            String s = new String(buffer, 0, pos, encoding);

            out.append(s);

            if (!needToChange && !s.equals(new String(charArray, startIndex, pos))) {
                needToChange = true;
            }

            pos = 0;
        }

        return needToChange;
    }
}
