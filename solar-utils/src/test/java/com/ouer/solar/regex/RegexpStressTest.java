/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.regex;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.ouer.solar.CollectionUtil;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 上午2:42:07
 */
@RunWith(Parameterized.class)
public class RegexpStressTest {
    private String[] exps;
    private String[] data;
    private int matchedCount;

    public RegexpStressTest(String[] exps, String[] data, int matchedCount) {
        this.exps = exps;
        this.data = data;
        this.matchedCount = matchedCount;
    }

    @Parameters
    public static List<Object[]> data() {
        List<Object[]> list = CollectionUtil.createLinkedList();

        String[] data =
                new String[] { "control.setTemplate(aaa)", "screen_placeholder", "stringEscapeUtil.escape($abc)",
                        "csrfToken.getHiddenField()",
                        "sdfasdfasldkfjqwpoieruqpweiorjalkfjasd;klfjasdpoifuqwpioerjkjdsfsladfjpqoieadsfkasdfdaksfj;asdkfa;sdwr" };

        add(list, new String[] { "^control\\.", "^screen_placeholder", "^stringEscapeUtil\\.escape",
                "^csrfToken\\.(get)?hiddenField", "dsfsladf\\w+qo.*ds" }, data, 5);

        add(list, new String[] { "(^control\\.)" + //
                "|(^screen_placeholder)" + //
                "|(^stringEscapeUtil\\.escape)" + //
                "|(^csrfToken\\.(get)?hiddenField)" + //
                "|(dsfsladf\\w+qo.*ds)" }, data, 5);

        return list;
    }

    private static void add(List<Object[]> list, String[] exps, String[] data, int matchedCount) {
        list.add(new Object[] { exps, data, matchedCount });
    }

    @Test
    public void oro() throws Exception {
        test(new OroTester(), 10000, 10);
    }

    @Test
    public void jdk() throws Exception {
        test(new JdkRegexTester(), 10000, 10);
    }

    private void test(final Tester test, final int loop, final int concurrency) throws Exception {
        test.warmUp();

        Thread[] ts1 = new Thread[concurrency];

        long start = System.currentTimeMillis();

        for (int i = 0; i < ts1.length; i++) {
            ts1[i] = new Thread(new Runnable() {
                @Override
				public void run() {
                    test.run(loop);
                }
            });
        }

        for (Thread element : ts1) {
            element.start();
        }

        for (Thread element : ts1) {
            element.join();
        }

        long duration = System.currentTimeMillis() - start;
        int matches = loop * exps.length * data.length * concurrency;

        System.out.printf("%s - %,d ms for %,d matches%n", test, duration, matches);
        System.out.printf("%s \u03BCs%n", (double) duration / (matches / 1000));
    }

    interface Tester {
        void run(int loop);

        void warmUp();
    }

    class OroTester implements Tester {
        private Pattern[] patterns;

        public OroTester() throws Exception {
            patterns = new Pattern[exps.length];

            for (int i = 0; i < patterns.length; i++) {
                patterns[i] =
                        new Perl5Compiler().compile(exps[i], Perl5Compiler.CASE_INSENSITIVE_MASK
                                | Perl5Compiler.READ_ONLY_MASK);
            }
        }

        @Override
		public void run(int loop) {
            PatternMatcher matcher = new Perl5Matcher();

            for (int i = 0; i < loop; i++) {
                for (Pattern pattern : patterns) {
                    for (String value : data) {
                        matcher.contains(value, pattern);
                    }
                }
            }
        }

        @Override
		public void warmUp() {
            PatternMatcher matcher = new Perl5Matcher();
            int matchedCount = 0;

            for (Pattern pattern : patterns) {
                for (String value : data) {
                    if (matcher.contains(value, pattern)) {
                        matchedCount++;
                    }
                }
            }

            assertEquals(RegexpStressTest.this.matchedCount, matchedCount);
        }

        @Override
        public String toString() {
            return "ORO regex";
        }
    }

    class JdkRegexTester implements Tester {
        private java.util.regex.Pattern[] patterns;

        public JdkRegexTester() throws Exception {
            patterns = new java.util.regex.Pattern[exps.length];

            for (int i = 0; i < patterns.length; i++) {
                patterns[i] = java.util.regex.Pattern.compile(exps[i], java.util.regex.Pattern.CASE_INSENSITIVE);
            }
        }

        @Override
		public void run(int loop) {
            for (int i = 0; i < loop; i++) {
                for (java.util.regex.Pattern pattern : patterns) {
                    for (String value : data) {
                        pattern.matcher(value).find();
                    }
                }
            }
        }

        @Override
		public void warmUp() {
            int matchedCount = 0;

            for (java.util.regex.Pattern pattern : patterns) {
                for (String value : data) {
                    if (pattern.matcher(value).find()) {
                        matchedCount++;
                    }
                }
            }

            assertEquals(RegexpStressTest.this.matchedCount, matchedCount);
        }

        @Override
        public String toString() {
            return "JDK regex";
        }
    }
}
