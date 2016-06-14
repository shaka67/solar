/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public final class Require {

    /**
     * Assert that an object is <code>null</code> .
     * <pre class="code">
     * Require.isNull(value);
     * </pre>
     * @param object the object to check
     * @throws IllegalArgumentException if the object is not <code>null</code>
     */
    public static void isNull(final Object object) {
        isNull(object, "[Require failed] - the given object argument must be null");
    }

    /**
     * Assert that an object is <code>null</code> .
     * <pre class="code">
     * Require.isNull(value, "The value must be null");
     * </pre>
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is not <code>null</code>
     */
    public static void isNull(final Object object, final String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an object is not <code>null</code> .
     * <pre class="code">
     * Require.notNull(value);
     * </pre>
     * @param object the object to check
     * @throws IllegalArgumentException if the object is <code>null</code>
     */
    public static void isNotNull(final Object object) {
        isNotNull(object, "[Require failed] - the given object argument must not be null");
    }

    /**
     * Assert that an object is not <code>null</code> .
     * <pre class="code">
     * Require.notNull(value, "The value must not be null");
     * </pre>
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is <code>null</code>
     */
    public static void isNotNull(final Object object, final String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code>
     * if the test result is <code>false</code>.
     * <pre class="code">
     * Require.isTrue(i &gt; 0);
     * </pre>
     * @param expression a boolean expression
     * @throws IllegalArgumentException if expression is <code>false</code>
     */
    public static void isTrue(final boolean expression) {
        isTrue(expression, "[Require failed] - the given expression must be true");
    }

    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code>
     * if the test result is <code>false</code>.
     * <pre class="code">
     * Require.isTrue(i &gt; 0, "The value must be greater than zero");
     * </pre>
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if expression is <code>false</code>
     */
    public static void isTrue(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code>
     * if the test result is <code>true</code>.
     * <pre class="code">
     * Require.isFalse(i &gt; 0);
     * </pre>
     * @param expression a boolean expression
     * @throws IllegalArgumentException if expression is <code>true</code>
     */
    public static void isFalse(final boolean expression) {
        isFalse(expression, "[Require failed] - the given expression must be false");
    }

    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code>
     * if the test result is <code>false</code>.
     * <pre class="code">
     * Require.isFalse(i &gt; 0, "The value must be less than zero");
     * </pre>
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if expression is <code>true</code>
     */
    public static void isFalse(final boolean expression, final String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

}
