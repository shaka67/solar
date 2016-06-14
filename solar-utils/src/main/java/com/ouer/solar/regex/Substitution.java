/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.regex;

import com.ouer.solar.Assert;
import com.ouer.solar.StringUtil;

/**
 * 代表一个替换。替换字符串中的变量。通常变量以<code>'$'</code>开始，例如： <code>$1</code>，<code>$2</code> 等，但<code>Substitution</code>
 * 类可支持对多种不同前缀的变量进行替换。
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 上午2:40:34
 */
public abstract class Substitution {
    protected final String replacementPrefixes;

    /** 创建一个替换，以<code>'$'</code>为变量前缀。 */
    public Substitution() {
        this("$");
    }

    /** 创建一个替换，以指定字符为变量前缀。 */
    public Substitution(String replacementPrefixes) {
        this.replacementPrefixes =
                Assert.assertNotNull(StringUtil.trimToNull(replacementPrefixes), "replacementPrefixes");
    }

    /** 替换字符串中的变量。 */
    public final String substitute(String input) {
        if (input == null) {
            return null;
        }

        StringBuilder buf = new StringBuilder();

        substitute(buf, input);

        return buf.toString();
    }

    /** 替换字符串中的变量。 */
    public final void substitute(StringBuilder buf, String input) {
        int length = input.length();
        int index;

        for (int i = 0; i < length;) {
            char ch = input.charAt(i);

            if (ch == '\\') {
                i++;

                if (i < length) {
                    buf.append(input.charAt(i++));
                } else {
                    buf.append(ch);
                }
            } else if ((index = replacementPrefixes.indexOf(ch)) >= 0) {
                i++;

                int num = -1;
                int numStartIndex = i; // 保存index

                while (i < length) {
                    int digit = input.charAt(i) - '0';

                    if (digit < 0 || digit > 9) {
                        break;
                    }

                    i++;

                    if (num == -1) {
                        num = digit;
                    } else {
                        num = num * 10 + digit;
                    }
                }

                String groupValue;

                if (num == -1) { // not a number
                    buf.append(ch);
                } else if ((groupValue = group(index, num)) != null) {
                    buf.append(groupValue);
                } else { // out of range
                    buf.append(ch);
                    buf.append(input, numStartIndex, i);
                }
            } else {
                buf.append(ch);
                i++;
            }
        }
    }

    /** 子类覆盖此方法，以提供指定类型、指定group序号的的replacement结果。 */
    protected abstract String group(int index, int groupNumber);
}
