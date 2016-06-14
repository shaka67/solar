/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import com.ouer.solar.ClassUtil;
import com.ouer.solar.able.Valuable;

/**
 * Primitive Type
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-4-6 下午2:23:15
 */
public enum PrimitiveType implements Valuable<Class<?>> {

    BOOLEAN {
        @Override
        public Class<?> value() {
            return boolean.class;
        }

        @Override
        public Class<?> wrappedClass() {
            return Boolean.class;
        }

        @Override
        public int byteSize() {
            return 1;
        }
    },

    BYTE {
        @Override
        public Class<?> value() {
            return byte.class;
        }

        @Override
        public Class<?> wrappedClass() {
            return Byte.class;
        }

        @Override
        public int byteSize() {
            return 1;
        }
    },
    CHAR {
        @Override
        public Class<?> value() {
            return char.class;
        }

        @Override
        public Class<?> wrappedClass() {
            return Character.class;
        }

        @Override
        public int byteSize() {
            return 2;
        }

    },
    SHORT {
        @Override
        public Class<?> value() {
            return short.class;
        }

        @Override
        public Class<?> wrappedClass() {
            return Short.class;
        }

        @Override
        public int byteSize() {
            return 2;
        }
    },
    INT {
        @Override
        public Class<?> value() {
            return int.class;
        }

        @Override
        public Class<?> wrappedClass() {
            return Integer.class;
        }

        @Override
        public int byteSize() {
            return 4;
        }
    },
    LONG {
        @Override
        public Class<?> value() {
            return long.class;
        }

        @Override
        public Class<?> wrappedClass() {
            return Long.class;
        }

        @Override
        public int byteSize() {
            return 8;
        }
    },
    FLOAT {
        @Override
        public Class<?> value() {
            return float.class;
        }

        @Override
        public Class<?> wrappedClass() {
            return Float.class;
        }

        @Override
        public int byteSize() {
            return 4;
        }
    },
    DOUBLE {
        @Override
        public Class<?> value() {
            return double.class;
        }

        @Override
        public Class<?> wrappedClass() {
            return Double.class;
        }

        @Override
        public int byteSize() {
            return 8;
        }
    };

    static final PrimitiveType[] CHOICES = new PrimitiveType[] { BYTE, SHORT, INT, LONG };

    public abstract Class<?> wrappedClass();

    public abstract int byteSize();

    public static PrimitiveType find(Class<?> type) {
        for (PrimitiveType pt : PrimitiveType.values()) {
            if (pt.value() == type || pt.wrappedClass() == type) {
                return pt;
            }
        }

        return null;
    }

    public static PrimitiveType chooseSuitable(Number number) {
        for (PrimitiveType pt : CHOICES) {
            @SuppressWarnings("unchecked")
            Comparable<Number> value = (Comparable<Number>) ClassUtil.getPrimitiveMaxValue(pt.value());
            if (value.compareTo(number) >= 0) {
                return pt;
            }
        }

        return null;
    }
}
