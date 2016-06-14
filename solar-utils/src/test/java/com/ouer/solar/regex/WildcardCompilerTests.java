/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.regex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.ouer.solar.ArrayUtil;
import com.ouer.solar.regex.ClassNameWildcardCompiler;
import com.ouer.solar.regex.PathNameWildcardCompiler;

public class WildcardCompilerTests {
    @Test
    public void pathNameWildcard() throws Exception {
        Pattern pattern = PathNameWildcardCompiler.compilePathName("/ab?/def/**/ghi/*.jsp");

        assertTrue(contains("/abc/def/ghi/test.jsp", pattern, "c", "", "test"));
        assertTrue(contains("/abd/def/xxx/ghi/test.jsp", pattern, "d", "xxx", "test"));
        assertTrue(contains("/abe/def/xxx/yyy/ghi/test.jsp", pattern, "e", "xxx/yyy", "test"));
        assertTrue(contains("/abf/def/ghi/.jsp", pattern, "f", "", ""));
        assertTrue(contains("/abg/def/ghi/.jsp", pattern, "g", "", ""));

        assertFalse(contains("/ab/def/ghi/test.jsp", pattern));
        assertFalse(contains("/abcd/def/ghi/test.jsp", pattern));
        assertFalse(contains("/abc/def/xxxghi/test.jsp", pattern));
        assertFalse(contains("/abc/defxxx/ghi/test.jsp", pattern));
        assertFalse(contains("/abc/def/ghi/jsp", pattern));

        pattern = PathNameWildcardCompiler.compilePathName("/xxx/yyy/**");

        assertTrue(contains("/xxx/yyy/", pattern, ""));
        assertTrue(contains("/xxx/yyy/zzz", pattern, "zzz"));
        assertTrue(contains("/xxx/yyy/zzz/aaa", pattern, "zzz/aaa"));
        assertTrue(contains("/xxx/yyy/zzz/aaa/", pattern, "zzz/aaa/"));

        assertFalse(contains("/xxx/yyy", pattern));
        assertFalse(contains("xxx/yyy", pattern));
        assertFalse(contains("xxx/yyy/zzz", pattern));
        assertFalse(contains("xxx/yyy/zzz/aaa", pattern));
        assertFalse(contains("xxx/yyy/zzz/aaa/", pattern));

        pattern = PathNameWildcardCompiler.compilePathName("/xxx/yyy");

        assertTrue(contains("/xxx/yyy", pattern));
        assertFalse(contains("/xxx/yyyzzz", pattern));

        pattern = PathNameWildcardCompiler.compilePathName("/xxx/yyy*");

        assertTrue(contains("/xxx/yyy", pattern, ""));
        assertTrue(contains("/xxx/yyyzzz", pattern, "zzz"));

        // 以?结束
        pattern = PathNameWildcardCompiler.compilePathName("/xxx/yyy?");

        assertTrue(contains("/xxx/yyyz", pattern, "z"));
        assertTrue(contains("/xxx/yyyz/", pattern, "z"));
        assertFalse(contains("/xxx/yyyzz", pattern));

        // 以?开始
        pattern = PathNameWildcardCompiler.compilePathName("?xxx/yyy");

        assertTrue(contains("/zxxx/yyy", pattern, "z"));
        assertFalse(contains("/zzxxx/yyy", pattern));

        // 特殊处理
        pattern = PathNameWildcardCompiler.compilePathName("/");

        assertTrue(contains("", pattern));
        assertTrue(contains("/xxx/yyy", pattern));

        pattern = PathNameWildcardCompiler.compilePathName("");

        assertTrue(contains("", pattern));
        assertTrue(contains("/xxx/yyy", pattern));
    }

    @Test
    public void pathNameRelevant() {
        assertEquals(0, PathNameWildcardCompiler.getPathNameRelevancy(null));
        assertEquals(0, PathNameWildcardCompiler.getPathNameRelevancy("  "));
        assertEquals(0, PathNameWildcardCompiler.getPathNameRelevancy(""));

        assertEquals(0, PathNameWildcardCompiler.getPathNameRelevancy("/*/**"));
        assertEquals(1, PathNameWildcardCompiler.getPathNameRelevancy("/a?/**"));
        assertEquals(3, PathNameWildcardCompiler.getPathNameRelevancy("/a?/**/bc"));
    }

    @Test
    public void classNameWildcard() throws Exception {
        Pattern pattern = ClassNameWildcardCompiler.compileClassName("ab?.def.**.ghi.*.jsp");

        assertTrue(contains("abc.def.ghi.test.jsp", pattern, "c", "", "test"));
        assertTrue(contains("abd.def.xxx.ghi.test.jsp", pattern, "d", "xxx", "test"));
        assertTrue(contains("abe.def.xxx.yyy.ghi.test.jsp", pattern, "e", "xxx.yyy", "test"));

        assertFalse(contains("abf.def.ghi..jsp", pattern));
        assertFalse(contains("abg.def.ghi..jsp", pattern));

        assertFalse(contains("ab.def.ghi.test.jsp", pattern));
        assertFalse(contains("abcd.def.ghi.test.jsp", pattern));
        assertFalse(contains("abc.def.xxxghi.test.jsp", pattern));
        assertFalse(contains("abc.defxxx.ghi.test.jsp", pattern));
        assertFalse(contains("abc.def.ghi.jsp", pattern));

        pattern = ClassNameWildcardCompiler.compileClassName("xxx.yyy.**");

        assertTrue(contains("xxx.yyy.", pattern, ""));
        assertTrue(contains("xxx.yyy.zzz", pattern, "zzz"));
        assertTrue(contains("xxx.yyy.zzz.aaa", pattern, "zzz.aaa"));
        assertTrue(contains("xxx.yyy.zzz.aaa.", pattern, "zzz.aaa."));

        assertFalse(contains("xxx.yyy", pattern));
        assertFalse(contains("xxx.yyy", pattern));

        pattern = ClassNameWildcardCompiler.compileClassName("xxx.yyy");

        assertTrue(contains("xxx.yyy", pattern));
        assertFalse(contains("xxx.yyyzzz", pattern));

        pattern = ClassNameWildcardCompiler.compileClassName("xxx.yyy*");

        assertTrue(contains("xxx.yyy", pattern, ""));
        assertTrue(contains("xxx.yyyzzz", pattern, "zzz"));

        // 以?结尾
        pattern = ClassNameWildcardCompiler.compileClassName("xxx.yyy?");

        assertTrue(contains("xxx.yyyz", pattern, "z"));
        assertFalse(contains("xxx.yyyzz", pattern));

        // 以?开始
        pattern = ClassNameWildcardCompiler.compileClassName("?xxx.yyy");

        assertTrue(contains("zxxx.yyy", pattern, "z"));
        assertFalse(contains("zzxxx.yyy", pattern));

        pattern = ClassNameWildcardCompiler.compileClassName("");

        assertTrue(contains("", pattern));
        assertTrue(contains("xxx.yyy", pattern));
    }

    @Test
    public void classNameRelevant() {
        assertEquals(0, ClassNameWildcardCompiler.getClassNameRelevancy(null));
        assertEquals(0, ClassNameWildcardCompiler.getClassNameRelevancy("  "));
        assertEquals(0, ClassNameWildcardCompiler.getClassNameRelevancy(""));

        assertEquals(0, ClassNameWildcardCompiler.getClassNameRelevancy("*.**"));
        assertEquals(1, ClassNameWildcardCompiler.getClassNameRelevancy("a?.**"));
        assertEquals(3, ClassNameWildcardCompiler.getClassNameRelevancy("a?.**.bc"));
    }

    @Test
    public void normalizePathName() {
        assertEquals(null, PathNameWildcardCompiler.normalizePathName(null));
        assertEquals("", PathNameWildcardCompiler.normalizePathName(" "));

        assertEquals("/a/b/c/", PathNameWildcardCompiler.normalizePathName(" /a\\\\b//c// "));
        assertEquals("a/b/c", PathNameWildcardCompiler.normalizePathName(" a\\\\b\\/c "));

        assertEquals("/*/**/?/", PathNameWildcardCompiler.normalizePathName(" /*\\\\**//?// "));
        assertEquals("*/**/?", PathNameWildcardCompiler.normalizePathName(" *\\\\**\\/? "));
    }

    @Test
    public void normalizeClassName() {
        assertEquals(null, ClassNameWildcardCompiler.normalizeClassName(null));
        assertEquals("", ClassNameWildcardCompiler.normalizeClassName(" "));

        assertEquals("a.b.c", ClassNameWildcardCompiler.normalizeClassName(" .a..b//c.. "));
        assertEquals("a.b.c", ClassNameWildcardCompiler.normalizeClassName(" .a..b\\/c.. "));

        assertEquals("*.**.?", ClassNameWildcardCompiler.normalizeClassName(" .*..**//?.. "));
        assertEquals("*.**.?", ClassNameWildcardCompiler.normalizeClassName(" .*..**\\/?.. "));
    }

    @Test
    public void classNameToPathName() {
        assertEquals(null, ClassNameWildcardCompiler.classNameToPathName(null));
        assertEquals("", ClassNameWildcardCompiler.classNameToPathName(" "));

        assertEquals("a/b/c", ClassNameWildcardCompiler.classNameToPathName(" .a..b//c.. "));
        assertEquals("a/b/c", ClassNameWildcardCompiler.classNameToPathName(" .a..b\\/c.. "));

        assertEquals("*/**/?", ClassNameWildcardCompiler.classNameToPathName(" .*..**//?.. "));
        assertEquals("*/**/?", ClassNameWildcardCompiler.classNameToPathName(" .*..**\\/?.. "));
    }

    @Test
    public void stress() throws Exception {
        final int concurrency = 10;
        final int loops = 10000; // 100000;
        final Pattern pattern = PathNameWildcardCompiler.compilePathName("/abc/def/**/ghi/*.jsp");

        Runnable runnable = new Runnable() {
            @Override
			public void run() {
                long start = System.currentTimeMillis();

                for (int i = 0; i < loops; i++) {
                    assertTrue(contains("/abc/def/xyz/uvw/ghi/test.jsp", pattern, "xyz/uvw", "test"));
                }

                long duration = System.currentTimeMillis() - start;

                System.out.println(Thread.currentThread().getName() + " takes " + getDuration(duration));
            }
        };

        Thread[] threads = new Thread[concurrency];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(runnable, "Thread_" + i);
        }

        long start = System.currentTimeMillis();

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long duration = System.currentTimeMillis() - start;

        System.out.println("Total time: " + getDuration(duration));
    }

    private static String getDuration(long duration) {
        long ms = duration % 1000;
        long secs = duration / 1000 % 60;
        long min = duration / 1000 / 60;

        return MessageFormat.format("{0,choice,0#|.1#{0,number,integer}m}" + " {1,choice,0#|.1#{1,number,integer}s}"
                + " {2,number,integer}ms", min, secs, ms);
    }

    private boolean contains(String input, Pattern pattern, String...matches) {
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            assertEquals(matches.length, matcher.groupCount());

            for (int i = 0; i < matches.length; i++) {
                assertEquals(matches[i], matcher.group(i + 1));
            }

            return true;
        }

        assertTrue(ArrayUtil.isEmpty(matches));
        return false;
    }
}
