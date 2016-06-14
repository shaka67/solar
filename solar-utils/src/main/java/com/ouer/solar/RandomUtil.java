/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.util.Random;
import java.util.concurrent.Callable;

import com.ouer.solar.able.Computable;
import com.ouer.solar.cache.ConcurrentCache;

/**
 * 有关随机数的工具类，依赖于<code>Random</code>实现
 * <p>
 * 因为<code>Random</code>内部实现为伪随机算法；注意：<br>
 * 如果用相同的种子创建两个 Random 实例，则对每个实例进行相同的方法调用序列，它们将生成并返回相同的数字序列；<br>
 * 且内部使<code>synchronized</code>，JDK1.5以后使用<code>java.util.concurrent.atomic<code>包中的自旋锁；<br>
 * 当并发需要时，如使用相同的<code>Random</code>对象该类提供的方法会有性能问题。可使用 {@link #next(Class, int, int)}或 {@link #next(Random, int, int)}
 * 来提升性能
 * 
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月15日 下午3:10:04
 */
public abstract class RandomUtil {

    private static final Computable<Long, Random> seedMap = ConcurrentCache.createComputable();

    private static final Computable<Class<?>, Random> classBinds = ConcurrentCache.createComputable();

    private static final Random random = new Random();

    public static int next(Random random, int begin, int end) {
        if (end < begin) {
            throw new IllegalArgumentException("end must not smaller than begin");
        }

        int minus = random.nextInt(end - begin + 1);
        return (begin + minus);
    }

    /**
     * 获取begin和end之间的整数 [begin,end]
     * 
     * @param begin 起始值
     * @param end 终止值
     * @return begin和end之间的整数
     */
    public static int next(int begin, int end) {
        if (end < begin) {
            throw new IllegalArgumentException("end must not smaller than begin");
        }

        int minus = random.nextInt(end - begin + 1);
        return (begin + minus);
    }

    /**
     * 获取begin和end之间的整数 [begin,end]
     * 
     * @param begin 起始值
     * @param end 终止值
     * @return begin和end之间的整数
     */
    public static long next(long begin, long end) {
        if (end < begin) {
            throw new IllegalArgumentException("end must not smaller than begin");
        }

        long minus = random.nextInt((int) (end - begin + 1));
        return (begin + minus);
    }

    private static Random getRandom(final long seed) {
        return seedMap.get(seed, new Callable<Random>() {
            @Override
            public Random call() throws Exception {
                return new Random(seed);
            }
        });
    }

    /**
     * 获取begin和end之间的整数 [begin,end]
     * 
     * @param seed 随机数种子
     * @param begin 起始值
     * @param end 终止值
     * @return begin和end之间的整数
     */
    public static int next(final long seed, int begin, int end) {
        if (end < begin) {
            throw new IllegalArgumentException("end must not smaller than begin");
        }

        Random random = getRandom(seed);

        int minus = random.nextInt(end - begin + 1);
        return (begin + minus);
    }

    /**
     * 获取begin和end之间的整数 [begin,end]
     * 
     * @param seed 随机数种子
     * @param begin 起始值
     * @param end 终止值
     * @return begin和end之间的整数
     */
    public static long next(final long seed, long begin, long end) {
        if (end < begin) {
            throw new IllegalArgumentException("end must not smaller than begin");
        }

        Random random = getRandom(seed);

        long minus = random.nextInt((int) (end - begin + 1));
        return (begin + minus);
    }

    /**
     * 获取pSngBegin和pSngEnd之间的数值 [pSngBegin,pSngEnd)
     * 
     * @param pSngBegin 起始值
     * @param pSngEnd 终止值
     * @return pSngBegin和pSngEnd之间的数值
     */
    public static double getRandomNum(double pSngBegin, double pSngEnd) {
        if (pSngEnd < pSngBegin) {
            throw new IllegalArgumentException("pSngEnd must not smaller than pSngBegin");
        }

        return (pSngEnd - pSngBegin) * Math.random() + pSngBegin;
    }

    /**
     * 按照一定概率进行随机<br>
     * 该方法参数太多，不做合法检测<br>
     * FIXME
     * 
     * @param pSngBegin 随机数范围的开始数字
     * @param pSngEnd 随机数范围结束数字
     * @param pSngPB 要随机的数字的开始数字
     * @param pSngPE 要随机的数字的结束数字
     * @param pBytP 要随机的数字随机概率
     * @return 按照一定概率随机的数字
     */
    public static double getRndNumP(double pSngBegin, double pSngEnd, double pSngPB, double pSngPE, double pBytP) {
        double sngPLen = pSngPE - pSngPB;
        // total length
        double sngTLen = pSngEnd - pSngBegin;
        // FIXME may throw java.lang.ArithmeticException : / by zero
        if ((sngPLen / sngTLen) * 100 == pBytP) {
            return getRandomNum(pSngBegin, pSngEnd);
        }

        // ((sngPLen + sngIncreased) / (sngTLen + sngIncreased)) * 100 =
        // bytP
        double sngIncreased = ((pBytP / 100) * sngTLen - sngPLen) / (1 - (pBytP / 100));
        // 缩放回原来区间
        double sngResult = getRandomNum(pSngBegin, pSngEnd + sngIncreased);
        if (pSngBegin <= sngResult && sngResult <= pSngPB) {
            return sngResult;
        }

        if (pSngPB <= sngResult && sngResult <= (pSngPE + sngIncreased)) {
            return pSngPB + (sngResult - pSngPB) * sngPLen / (sngPLen + sngIncreased);
        }

        if ((pSngPE + sngIncreased) <= sngResult && sngResult <= (pSngEnd + sngIncreased)) {
            return sngResult - sngIncreased;
        }

        return 0d;
    }

    /**
     * 获取pSngBegin和pSngEnd之间的数值 [pSngBegin,pSngEnd)
     * 
     * @param seed 随机数种子
     * @param pSngBegin 起始值
     * @param pSngEnd 终止值
     * @return pSngBegin和pSngEnd之间的数值
     */
    public static double getRandomNum(final long seed, double pSngBegin, double pSngEnd) {
        if (pSngEnd < pSngBegin) {
            throw new IllegalArgumentException("pSngEnd must not smaller than pSngBegin");
        }

        return (pSngEnd - pSngBegin) * Math.random() + pSngBegin;
    }

    /**
     * 按照一定概率进行随机<br>
     * 该方法参数太多，不做合法检测<br>
     * FIXME
     * 
     * @param pSngBegin 随机数范围的开始数字
     * @param pSngEnd 随机数范围结束数字
     * @param pSngPB 要随机的数字的开始数字
     * @param pSngPE 要随机的数字的结束数字
     * @param pBytP 要随机的数字随机概率
     * @return 按照一定概率随机的数字
     */
    public static double getRndNumP(final long seed, double pSngBegin, double pSngEnd, double pSngPB, double pSngPE,
            double pBytP) {
        double sngPLen = pSngPE - pSngPB;
        // total length
        double sngTLen = pSngEnd - pSngBegin;
        // FIXME may throw java.lang.ArithmeticException : / by zero
        if ((sngPLen / sngTLen) * 100 == pBytP) {
            return getRandomNum(pSngBegin, pSngEnd);
        }

        // ((sngPLen + sngIncreased) / (sngTLen + sngIncreased)) * 100 =
        // bytP
        double sngIncreased = ((pBytP / 100) * sngTLen - sngPLen) / (1 - (pBytP / 100));
        // 缩放回原来区间
        double sngResult = getRandomNum(pSngBegin, pSngEnd + sngIncreased);
        if (pSngBegin <= sngResult && sngResult <= pSngPB) {
            return sngResult;
        }

        if (pSngPB <= sngResult && sngResult <= (pSngPE + sngIncreased)) {
            return pSngPB + (sngResult - pSngPB) * sngPLen / (sngPLen + sngIncreased);
        }

        if ((pSngPE + sngIncreased) <= sngResult && sngResult <= (pSngEnd + sngIncreased)) {
            return sngResult - sngIncreased;
        }

        return 0d;
    }

    private static Random getRandom(final Class<?> clazz) {
        return classBinds.get(clazz, new Callable<Random>() {
            @Override
            public Random call() throws Exception {
                return new Random();
            }
        });
    }

    /**
     * 获取begin和end之间的整数 [begin,end]
     * 
     * @param clazz 绑定的Class，一个类绑定一个Random，提高性能
     * @param begin 起始值
     * @param end 终止值
     * @return begin和end之间的整数
     */
    public static int next(final Class<?> clazz, int begin, int end) {
        if (end < begin) {
            throw new IllegalArgumentException("end must not smaller than begin");
        }

        Random random = getRandom(clazz);

        int minus = random.nextInt(end - begin + 1);
        return (begin + minus);
    }

    /**
     * 获取begin和end之间的整数 [begin,end]
     * 
     * @param clazz 绑定的Class，一个类绑定一个Random，提高性能
     * @param begin 起始值
     * @param end 终止值
     * @return begin和end之间的整数
     */
    public static long next(final Class<?> clazz, long begin, long end) {
        if (end < begin) {
            throw new IllegalArgumentException("end must not smaller than begin");
        }

        Random random = getRandom(clazz);

        long minus = random.nextInt((int) (end - begin + 1));
        return (begin + minus);
    }

}
