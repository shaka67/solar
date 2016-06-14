/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.ouer.solar.i18n.LocaleUtil;

/**
 * 取得有关系统信息的工具类。
 * 
 * <p>
 * 如果因为Java安全的原因而不能取得<code>System.getProperty</code>，则相应的字段将被设置成 <code>null</code>， 并且这个消息将被输出到<code>System.err</code>中。
 * </p>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月15日 上午5:32:28
 */
public abstract class SystemUtil {
    private static final JvmSpecInfo JVM_SPEC_INFO = new JvmSpecInfo();
    private static final JvmInfo JVM_INFO = new JvmInfo();
    private static final JavaSpecInfo JAVA_SPEC_INFO = new JavaSpecInfo();
    private static final JavaInfo JAVA_INFO = new JavaInfo();
    private static final OsInfo OS_INFO = new OsInfo();
    private static final UserInfo USER_INFO = new UserInfo();
    private static final HostInfo HOST_INFO = new HostInfo();
    private static final JavaRuntimeInfo JAVA_RUNTIME_INFO = new JavaRuntimeInfo();

    private SystemUtil() {

    }

    /**
     * 取得Java Virtual Machine Specification的信息。
     * 
     * @return <code>JvmSpecInfo</code>对象
     */
    public static final JvmSpecInfo getJvmSpecInfo() {
        return JVM_SPEC_INFO;
    }

    /**
     * 取得Java Virtual Machine Implementation的信息。
     * 
     * @return <code>JvmInfo</code>对象
     */
    public static final JvmInfo getJvmInfo() {
        return JVM_INFO;
    }

    /**
     * 取得Java Specification的信息。
     * 
     * @return <code>JavaSpecInfo</code>对象
     */
    public static final JavaSpecInfo getJavaSpecInfo() {
        return JAVA_SPEC_INFO;
    }

    /**
     * 取得Java Implementation的信息。
     * 
     * @return <code>JavaInfo</code>对象
     */
    public static final JavaInfo getJavaInfo() {
        return JAVA_INFO;
    }

    /**
     * 取得当前运行的JRE的信息。
     * 
     * @return <code>JreInfo</code>对象
     */
    public static final JavaRuntimeInfo getJavaRuntimeInfo() {
        return JAVA_RUNTIME_INFO;
    }

    /**
     * 取得OS的信息。
     * 
     * @return <code>OsInfo</code>对象
     */
    public static final OsInfo getOsInfo() {
        return OS_INFO;
    }

    /**
     * 取得User的信息。
     * 
     * @return <code>UserInfo</code>对象
     */
    public static final UserInfo getUserInfo() {
        return USER_INFO;
    }

    /**
     * 取得Host的信息。
     * 
     * @return <code>HostInfo</code>对象
     */
    public static final HostInfo getHostInfo() {
        return HOST_INFO;
    }

    /**
     * 代表Java Virutal Machine Specification的信息。
     */
    public static final class JvmSpecInfo {
        private final String JAVA_VM_SPECIFICATION_NAME = getSystemProperty("java.vm.specification.name", false);
        private final String JAVA_VM_SPECIFICATION_VERSION = getSystemProperty("java.vm.specification.version", false);
        private final String JAVA_VM_SPECIFICATION_VENDOR = getSystemProperty("java.vm.specification.vendor", false);

        /**
         * 防止从外界创建此对象。
         */
        private JvmSpecInfo() {
        }

        /**
         * 取得当前JVM spec.的名称（取自系统属性：<code>java.vm.specification.name</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"Java Virtual Machine Specification"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getName() {
            return JAVA_VM_SPECIFICATION_NAME;
        }

        /**
         * 取得当前JVM spec.的版本（取自系统属性：<code>java.vm.specification.version</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"1.0"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getVersion() {
            return JAVA_VM_SPECIFICATION_VERSION;
        }

        /**
         * 取得当前JVM spec.的厂商（取自系统属性：<code>java.vm.specification.vendor</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"Sun Microsystems Inc."</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getVendor() {
            return JAVA_VM_SPECIFICATION_VENDOR;
        }

        /**
         * 将Java Virutal Machine Specification的信息转换成字符串。
         * 
         * @return JVM spec.的字符串表示
         */
        @Override
		public final String toString() {
            StringBuilder builder = new StringBuilder();

            append(builder, "JavaVM Spec. Name:    ", getName());
            append(builder, "JavaVM Spec. Version: ", getVersion());
            append(builder, "JavaVM Spec. Vendor:  ", getVendor());

            return builder.toString();
        }
    }

    /**
     * 代表Java Virtual Machine Implementation的信息。
     */
    public static final class JvmInfo {
        private final String JAVA_VM_NAME = getSystemProperty("java.vm.name", false);
        private final String JAVA_VM_VERSION = getSystemProperty("java.vm.version", false);
        private final String JAVA_VM_VENDOR = getSystemProperty("java.vm.vendor", false);
        private final String JAVA_VM_INFO = getSystemProperty("java.vm.info", false);

        /**
         * 防止从外界创建此对象。
         */
        private JvmInfo() {
        }

        /**
         * 取得当前JVM impl.的名称（取自系统属性：<code>java.vm.name</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"Java HotSpot(TM) Client VM"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getName() {
            return JAVA_VM_NAME;
        }

        /**
         * 取得当前JVM impl.的版本（取自系统属性：<code>java.vm.version</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"1.4.2-b28"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getVersion() {
            return JAVA_VM_VERSION;
        }

        /**
         * 取得当前JVM impl.的厂商（取自系统属性：<code>java.vm.vendor</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"Sun Microsystems Inc."</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getVendor() {
            return JAVA_VM_VENDOR;
        }

        /**
         * 取得当前JVM impl.的信息（取自系统属性：<code>java.vm.info</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"mixed mode"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getInfo() {
            return JAVA_VM_INFO;
        }

        /**
         * 将Java Virutal Machine Implementation的信息转换成字符串。
         * 
         * @return JVM impl.的字符串表示
         */
        @Override
		public final String toString() {
            StringBuilder builder = new StringBuilder();

            append(builder, "JavaVM Name:    ", getName());
            append(builder, "JavaVM Version: ", getVersion());
            append(builder, "JavaVM Vendor:  ", getVendor());
            append(builder, "JavaVM Info:    ", getInfo());

            return builder.toString();
        }
    }

    /**
     * 代表Java Specification的信息。
     */
    public static final class JavaSpecInfo {
        private final String JAVA_SPECIFICATION_NAME = getSystemProperty("java.specification.name", false);
        private final String JAVA_SPECIFICATION_VERSION = getSystemProperty("java.specification.version", false);
        private final String JAVA_SPECIFICATION_VENDOR = getSystemProperty("java.specification.vendor", false);

        /**
         * 防止从外界创建此对象。
         */
        private JavaSpecInfo() {
        }

        /**
         * 取得当前Java Spec.的名称（取自系统属性：<code>java.specification.name</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"Java Platform API Specification"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getName() {
            return JAVA_SPECIFICATION_NAME;
        }

        /**
         * 取得当前Java Spec.的版本（取自系统属性：<code>java.specification.version</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"1.4"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.3
         */
        public final String getVersion() {
            return JAVA_SPECIFICATION_VERSION;
        }

        /**
         * 取得当前Java Spec.的厂商（取自系统属性：<code>java.specification.vendor</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"Sun Microsystems Inc."</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getVendor() {
            return JAVA_SPECIFICATION_VENDOR;
        }

        /**
         * 将Java Specification的信息转换成字符串。
         * 
         * @return JVM spec.的字符串表示
         */
        @Override
		public final String toString() {
            StringBuilder builder = new StringBuilder();

            append(builder, "Java Spec. Name:    ", getName());
            append(builder, "Java Spec. Version: ", getVersion());
            append(builder, "Java Spec. Vendor:  ", getVendor());

            return builder.toString();
        }
    }

    /**
     * 代表Java Implementation的信息。
     */
    public static final class JavaInfo {
        private final String JAVA_VERSION = getSystemProperty("java.version", false);
        private final float JAVA_VERSION_FLOAT = getJavaVersionAsFloat();
        private final int JAVA_VERSION_INT = getJavaVersionAsInt();
        private final String JAVA_VENDOR = getSystemProperty("java.vendor", false);
        private final String JAVA_VENDOR_URL = getSystemProperty("java.vendor.url", false);

        // 1.1--1.3能否识别?
        private final boolean IS_JAVA_1_1 = getJavaVersionMatches("1.1");
        private final boolean IS_JAVA_1_2 = getJavaVersionMatches("1.2");
        private final boolean IS_JAVA_1_3 = getJavaVersionMatches("1.3");
        private final boolean IS_JAVA_1_4 = getJavaVersionMatches("1.4");
        private final boolean IS_JAVA_1_5 = getJavaVersionMatches("1.5");
        private final boolean IS_JAVA_1_6 = getJavaVersionMatches("1.6");
        private final boolean IS_JAVA_1_7 = getJavaVersionMatches("1.7");

        /**
         * 防止从外界创建此对象。
         */
        private JavaInfo() {
        }

        /**
         * 取得当前Java impl.的版本（取自系统属性：<code>java.version</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"1.4.2"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getVersion() {
            return JAVA_VERSION;
        }

        /**
         * 取得当前Java impl.的版本（取自系统属性：<code>java.version</code>）。
         * 
         * <p>
         * 例如：
         * 
         * <ul>
         * <li>
         * JDK 1.2：<code>1.2f</code>。</li>
         * <li>
         * JDK 1.3.1：<code>1.31f</code></li>
         * </ul>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>0</code>。
         * 
         * @since Java 1.1
         */
        public final float getVersionFloat() {
            return JAVA_VERSION_FLOAT;
        }

        /**
         * 取得当前Java impl.的版本（取自系统属性：<code>java.version</code>）。
         * 
         * <p>
         * 例如：
         * 
         * <ul>
         * <li>
         * JDK 1.2：<code>120</code>。</li>
         * <li>
         * JDK 1.3.1：<code>131</code></li>
         * </ul>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>0</code>。
         * 
         * @since Java 1.1
         */
        public final int getVersionInt() {
            return JAVA_VERSION_INT;
        }

        /**
         * 取得当前Java impl.的版本的<code>float</code>值。
         * 
         * @return Java版本的<code>float</code>值或<code>0</code>
         */
        private final float getJavaVersionAsFloat() {
            if (JAVA_VERSION == null) {
                return 0f;
            }

            String str = JAVA_VERSION.substring(0, 3);

            if (JAVA_VERSION.length() >= 5) {
                str = str + JAVA_VERSION.substring(4, 5);
            }

            return Float.parseFloat(str);
        }

        /**
         * 取得当前Java impl.的版本的<code>int</code>值。
         * 
         * @return Java版本的<code>int</code>值或<code>0</code>
         */
        private final int getJavaVersionAsInt() {
            if (JAVA_VERSION == null) {
                return 0;
            }

            String str = JAVA_VERSION.substring(0, 1);

            str = str + JAVA_VERSION.substring(2, 3);

            if (JAVA_VERSION.length() >= 5) {
                str = str + JAVA_VERSION.substring(4, 5);
            } else {
                str = str + "0";
            }

            return Integer.parseInt(str);
        }

        /**
         * 取得当前Java impl.的厂商（取自系统属性：<code>java.vendor</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"Sun Microsystems Inc."</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getVendor() {
            return JAVA_VENDOR;
        }

        /**
         * 取得当前Java impl.的厂商网站的URL（取自系统属性：<code>java.vendor.url</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"http://java.sun.com/"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getVendorURL() {
            return JAVA_VENDOR_URL;
        }

        /**
         * 判断当前Java的版本。
         * 
         * <p>
         * 如果不能取得系统属性<code>java.version</code>（因为Java安全限制），则总是返回 <code>false</code>
         * </p>
         * 
         * @return 如果当前Java版本为1.1，则返回<code>true</code>
         */
        public final boolean isJava11() {
            return IS_JAVA_1_1;
        }

        /**
         * 判断当前Java的版本。
         * 
         * <p>
         * 如果不能取得系统属性<code>java.version</code>（因为Java安全限制），则总是返回 <code>false</code>
         * </p>
         * 
         * @return 如果当前Java版本为1.2，则返回<code>true</code>
         */
        public final boolean isJava12() {
            return IS_JAVA_1_2;
        }

        /**
         * 判断当前Java的版本。
         * 
         * <p>
         * 如果不能取得系统属性<code>java.version</code>（因为Java安全限制），则总是返回 <code>false</code>
         * </p>
         * 
         * @return 如果当前Java版本为1.3，则返回<code>true</code>
         */
        public final boolean isJava13() {
            return IS_JAVA_1_3;
        }

        /**
         * 判断当前Java的版本。
         * 
         * <p>
         * 如果不能取得系统属性<code>java.version</code>（因为Java安全限制），则总是返回 <code>false</code>
         * </p>
         * 
         * @return 如果当前Java版本为1.4，则返回<code>true</code>
         */
        public final boolean isJava14() {
            return IS_JAVA_1_4;
        }

        /**
         * 判断当前Java的版本。
         * 
         * <p>
         * 如果不能取得系统属性<code>java.version</code>（因为Java安全限制），则总是返回 <code>false</code>
         * </p>
         * 
         * @return 如果当前Java版本为1.5，则返回<code>true</code>
         */
        public final boolean isJava15() {
            return IS_JAVA_1_5;
        }

        /**
         * 判断当前Java的版本。
         * 
         * <p>
         * 如果不能取得系统属性<code>java.version</code>（因为Java安全限制），则总是返回 <code>false</code>
         * </p>
         * 
         * @return 如果当前Java版本为1.6，则返回<code>true</code>
         */
        public final boolean isJava16() {
            return IS_JAVA_1_6;
        }

        /**
         * 判断当前Java的版本。
         * 
         * <p>
         * 如果不能取得系统属性<code>java.version</code>（因为Java安全限制），则总是返回 <code>false</code>
         * </p>
         * 
         * @return 如果当前Java版本为1.7，则返回<code>true</code>
         */
        public final boolean isJava17() {
            return IS_JAVA_1_7;
        }

        /**
         * 匹配当前Java的版本。
         * 
         * @param versionPrefix Java版本前缀
         * 
         * @return 如果版本匹配，则返回<code>true</code>
         */
        private final boolean getJavaVersionMatches(String versionPrefix) {
            if (JAVA_VERSION == null) {
                return false;
            }

            return JAVA_VERSION.startsWith(versionPrefix);
        }

        /**
         * 判定当前Java的版本是否大于等于指定的版本号。
         * 
         * <p>
         * 例如：
         * </p>
         * 
         * <ul>
         * <li>
         * 测试JDK 1.2：<code>isJavaVersionAtLeast(1.2f)</code></li>
         * <li>
         * 测试JDK 1.2.1：<code>isJavaVersionAtLeast(1.31f)</code></li>
         * </ul>
         * 
         * 
         * @param requiredVersion 需要的版本
         * 
         * @return 如果当前Java版本大于或等于指定的版本，则返回<code>true</code>
         */
        public final boolean isJavaVersionAtLeast(float requiredVersion) {
            return getVersionFloat() >= requiredVersion;
        }

        /**
         * 判定当前Java的版本是否大于等于指定的版本号。
         * 
         * <p>
         * 例如：
         * </p>
         * 
         * <ul>
         * <li>
         * 测试JDK 1.2：<code>isJavaVersionAtLeast(120)</code></li>
         * <li>
         * 测试JDK 1.2.1：<code>isJavaVersionAtLeast(131)</code></li>
         * </ul>
         * 
         * 
         * @param requiredVersion 需要的版本
         * 
         * @return 如果当前Java版本大于或等于指定的版本，则返回<code>true</code>
         */
        public final boolean isJavaVersionAtLeast(int requiredVersion) {
            return getVersionInt() >= requiredVersion;
        }

        /**
         * 将Java Implementation的信息转换成字符串。
         * 
         * @return JVM impl.的字符串表示
         */
        @Override
		public final String toString() {
            StringBuilder builder = new StringBuilder();

            append(builder, "Java Version:    ", getVersion());
            append(builder, "Java Vendor:     ", getVendor());
            append(builder, "Java Vendor URL: ", getVendorURL());

            return builder.toString();
        }
    }

    /**
     * 代表当前运行的JRE的信息。
     */
    public static final class JavaRuntimeInfo {
        private final String JAVA_RUNTIME_NAME = getSystemProperty("java.runtime.name", false);
        private final String JAVA_RUNTIME_VERSION = getSystemProperty("java.runtime.version", false);
        private final String JAVA_HOME = getSystemProperty("java.home", false);
        private final String JAVA_EXT_DIRS = getSystemProperty("java.ext.dirs", false);
        private final String JAVA_ENDORSED_DIRS = getSystemProperty("java.endorsed.dirs", false);
        private final String JAVA_CLASS_PATH = getSystemProperty("java.class.path", false);
        private final String JAVA_CLASS_VERSION = getSystemProperty("java.class.version", false);
        private final String JAVA_LIBRARY_PATH = getSystemProperty("java.library.path", false);

        private final String SUN_BOOT_CLASS_PATH = getSystemProperty("sun.boot.class.path", false);

        private final String SUN_ARCH_DATA_MODEL = getSystemProperty("sun.arch.data.model", false);

        /**
         * 防止从外界创建此对象。
         */
        private JavaRuntimeInfo() {
        }

        public final String getSunBoothClassPath() {
            return SUN_BOOT_CLASS_PATH;
        }

        /**
         * JVM is 32M <code>or</code> 64M
         * 
         * @return 32 <code>or</code> 64
         */
        public final String getSunArchDataModel() {
            return SUN_ARCH_DATA_MODEL;
        }

        /**
         * 取得当前JRE的名称（取自系统属性：<code>java.runtime.name</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2： <code>"Java(TM) 2 Runtime Environment, Standard Edition"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.3
         */
        public final String getName() {
            return JAVA_RUNTIME_NAME;
        }

        /**
         * 取得当前JRE的版本（取自系统属性：<code>java.runtime.version</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"1.4.2-b28"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.3
         */
        public final String getVersion() {
            return JAVA_RUNTIME_VERSION;
        }

        /**
         * 取得当前JRE的安装目录（取自系统属性：<code>java.home</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"/opt/jdk1.4.2/jre"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getHomeDir() {
            return JAVA_HOME;
        }

        /**
         * 取得当前JRE的扩展目录列表（取自系统属性：<code>java.ext.dirs</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"/opt/jdk1.4.2/jre/lib/ext:..."</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.3
         */
        public final String getExtDirs() {
            return JAVA_EXT_DIRS;
        }

        /**
         * 取得当前JRE的endorsed目录列表（取自系统属性：<code>java.endorsed.dirs</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"/opt/jdk1.4.2/jre/lib/endorsed:..."</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.4
         */
        public final String getEndorsedDirs() {
            return JAVA_ENDORSED_DIRS;
        }

        /**
         * 取得当前JRE的系统classpath（取自系统属性：<code>java.class.path</code>）。
         * 
         * <p>
         * 例如：<code>"/home/admin/myclasses:/home/admin/..."</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getClassPath() {
            return JAVA_CLASS_PATH;
        }

        /**
         * 取得当前JRE的系统classpath（取自系统属性：<code>java.class.path</code>）。
         * 
         * <p>
         * 例如：<code>"/home/admin/myclasses:/home/admin/..."</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String[] getClassPathArray() {
            return StringUtil.split(getClassPath(), getOsInfo().getPathSeparator());
        }

        /**
         * 取得当前JRE的class文件格式的版本（取自系统属性：<code>java.class.version</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"48.0"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getClassVersion() {
            return JAVA_CLASS_VERSION;
        }

        /**
         * 取得当前JRE的library搜索路径（取自系统属性：<code>java.library.path</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"/opt/jdk1.4.2/bin:..."</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getLibraryPath() {
            return JAVA_LIBRARY_PATH;
        }

        /**
         * 取得当前JRE的library搜索路径（取自系统属性：<code>java.library.path</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"/opt/jdk1.4.2/bin:..."</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String[] getLibraryPathArray() {
            return StringUtil.split(getLibraryPath(), getOsInfo().getPathSeparator());
        }

        /**
         * 取得当前JRE的URL协议packages列表（取自系统属性：<code>java.library.path</code>）。
         * 
         * <p>
         * 例如Sun JDK 1.4.2：<code>"sun.net.www.protocol|..."</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getProtocolPackages() {
            return getSystemProperty("java.protocol.handler.pkgs", true);
        }

        /**
         * 将当前运行的JRE信息转换成字符串。
         * 
         * @return JRE信息的字符串表示
         */
        @Override
		public final String toString() {
            StringBuilder builder = new StringBuilder();

            append(builder, "Java Runtime Name:      ", getName());
            append(builder, "Java Runtime Version:   ", getVersion());
            append(builder, "Java Home Dir:          ", getHomeDir());
            append(builder, "Java Extension Dirs:    ", getExtDirs());
            append(builder, "Java Endorsed Dirs:     ", getEndorsedDirs());
            append(builder, "Java Class Path:        ", getClassPath());
            append(builder, "Java Class Version:     ", getClassVersion());
            append(builder, "Java Library Path:      ", getLibraryPath());
            append(builder, "Java Protocol Packages: ", getProtocolPackages());

            return builder.toString();
        }
    }

    /**
     * 代表当前OS的信息。
     */
    public static final class OsInfo {
        private final String OS_VERSION = getSystemProperty("os.version", false);
        private final String OS_ARCH = getSystemProperty("sun.arch.data.model", false);
        private final String OS_NAME = getSystemProperty("os.name", false);
        private final boolean IS_64 = getArch(64);
        private final boolean IS_32 = getArch(32);
        private final boolean IS_OS_AIX = getOSMatches("AIX");
        private final boolean IS_OS_HP_UX = getOSMatches("HP-UX");
        private final boolean IS_OS_IRIX = getOSMatches("Irix");
        private final boolean IS_OS_LINUX = getOSMatches("Linux") || getOSMatches("LINUX");
        // FIXME add others
        private final boolean IS_OS_UNIX = IS_OS_LINUX || IS_OS_AIX;
        private final boolean IS_OS_MAC = getOSMatches("Mac");
        private final boolean IS_OS_MAC_OSX = getOSMatches("Mac OS X");
        private final boolean IS_OS_OS2 = getOSMatches("OS/2");
        private final boolean IS_OS_SOLARIS = getOSMatches("Solaris");
        private final boolean IS_OS_SUN_OS = getOSMatches("SunOS");
        private final boolean IS_OS_WINDOWS = getOSMatches("Windows");
        private final boolean IS_OS_WINDOWS_2000 = getOSMatches("Windows", "5.0");
        private final boolean IS_OS_WINDOWS_95 = getOSMatches("Windows 9", "4.0");
        private final boolean IS_OS_WINDOWS_98 = getOSMatches("Windows 9", "4.1");
        private final boolean IS_OS_WINDOWS_ME = getOSMatches("Windows", "4.9");
        private final boolean IS_OS_WINDOWS_NT = getOSMatches("Windows NT");
        private final boolean IS_OS_WINDOWS_XP = getOSMatches("Windows", "5.1");
        private final boolean IS_OS_WINDOWS_7 = getOSMatches("Windows", "7");

        // 由于改变file.encoding属性并不会改变系统字符编码，为了保持一致，通过LocaleUtil取系统默认编码。
        // FIXME
        private final String FILE_ENCODING = LocaleUtil.getSystem().getCharset().name();
        private final String FILE_SEPARATOR = getSystemProperty("file.separator", false);
        private final String LINE_SEPARATOR = getSystemProperty("line.separator", false);
        private final String PATH_SEPARATOR = getSystemProperty("path.separator", false);

        /**
         * 防止从外界创建此对象。
         */
        private OsInfo() {
        }

        /**
         * 取得当前OS的架构（取自系统属性：<code>os.arch</code>）。
         * 
         * <p>
         * 例如：<code>"x86"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getArch() {
            return OS_ARCH;
        }

        /**
         * 取得当前OS的名称（取自系统属性：<code>os.name</code>）。
         * 
         * <p>
         * 例如：<code>"Windows XP"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getName() {
            return OS_NAME;
        }

        /**
         * 取得当前OS的版本（取自系统属性：<code>os.version</code>）。
         * 
         * <p>
         * 例如：<code>"5.1"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getVersion() {
            return OS_VERSION;
        }

        /**
         * 判断是否为64位机。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.arch</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为AIX，则返回<code>true</code>
         */
        public final boolean is64() {
            return IS_64;
        }

        /**
         * 判断是否为32位机。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.arch</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为AIX，则返回<code>true</code>
         */
        public final boolean is32() {
            return IS_32;
        }

        /**
         * 判断当前OS是否为AIX。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为AIX，则返回<code>true</code>
         */
        public final boolean isAix() {
            return IS_OS_AIX;
        }

        /**
         * 判断当前OS的类型。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为HP-UX，则返回<code>true</code>
         */
        public final boolean isHpUx() {
            return IS_OS_HP_UX;
        }

        /**
         * 判断当前OS是否为IRIX。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为IRIX，则返回<code>true</code>
         */
        public final boolean isIrix() {
            return IS_OS_IRIX;
        }

        /**
         * 判断当前OS是否为Linux。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Linux，则返回<code>true</code>
         */
        public final boolean isLinux() {
            return IS_OS_LINUX;
        }

        /**
         * 判断当前OS是否为Linux。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Linux，则返回<code>true</code>
         */
        public final boolean isUNIX() {
            return IS_OS_UNIX;
        }

        /**
         * 判断当前OS是否为Mac。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Mac，则返回<code>true</code>
         */
        public final boolean isMac() {
            return IS_OS_MAC;
        }

        /**
         * 判断当前OS是否为MacOS X。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为MacOS X，则返回<code>true</code>
         */
        public final boolean isMacOsX() {
            return IS_OS_MAC_OSX;
        }

        /**
         * 判断当前OS是否为OS2。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为OS2，则返回<code>true</code>
         */
        public final boolean isOs2() {
            return IS_OS_OS2;
        }

        /**
         * 判断当前OS是否为Solaris。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Solaris，则返回<code>true</code>
         */
        public final boolean isSolaris() {
            return IS_OS_SOLARIS;
        }

        /**
         * 判断当前OS是否为Sun OS。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Sun OS，则返回<code>true</code>
         */
        public final boolean isSunOS() {
            return IS_OS_SUN_OS;
        }

        /**
         * 判断当前OS是否为Windows。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Windows，则返回<code>true</code>
         */
        public final boolean isWindows() {
            return IS_OS_WINDOWS;
        }

        /**
         * 判断当前OS是否为Windows 2000。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Windows 2000，则返回<code>true</code>
         */
        public final boolean isWindows2000() {
            return IS_OS_WINDOWS_2000;
        }

        /**
         * 判断当前OS是否为Windows 95。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Windows 95，则返回<code>true</code>
         */
        public final boolean isWindows95() {
            return IS_OS_WINDOWS_95;
        }

        /**
         * 判断当前OS是否为Windows 98。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Windows 98，则返回<code>true</code>
         */
        public final boolean isWindows98() {
            return IS_OS_WINDOWS_98;
        }

        /**
         * 判断当前OS是否为Windows ME。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Windows ME，则返回<code>true</code>
         */
        public final boolean isWindowsME() {
            return IS_OS_WINDOWS_ME;
        }

        /**
         * 判断当前OS是否为Windows NT。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Windows NT，则返回<code>true</code>
         */
        public final boolean isWindowsNT() {
            return IS_OS_WINDOWS_NT;
        }

        /**
         * 判断当前OS是否为Windows XP。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Windows XP，则返回<code>true</code>
         */
        public final boolean isWindowsXP() {
            return IS_OS_WINDOWS_XP;
        }

        /**
         * 判断当前OS是否为Windows 7。
         * 
         * <p>
         * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
         * </p>
         * 
         * @return 如果当前OS类型为Windows 7，则返回<code>true</code>
         */
        public final boolean isWindows7() {
            return IS_OS_WINDOWS_7;
        }

        /**
         * 匹配Arch。
         * 
         * @param arch OS arch
         * 
         * @return 如果匹配，则返回<code>true</code>
         */
        private final boolean getArch(int arch) {
            if (OS_ARCH == null) {
                return false;
            }

            return OS_ARCH.contains(String.valueOf(arch));
        }

        /**
         * 匹配OS名称。
         * 
         * @param osNamePrefix OS名称前缀
         * 
         * @return 如果匹配，则返回<code>true</code>
         */
        private final boolean getOSMatches(String osNamePrefix) {
            if (OS_NAME == null) {
                return false;
            }

            return OS_NAME.startsWith(osNamePrefix);
        }

        /**
         * 匹配OS名称。
         * 
         * @param osNamePrefix OS名称前缀
         * @param osVersionPrefix OS版本前缀
         * 
         * @return 如果匹配，则返回<code>true</code>
         */
        private final boolean getOSMatches(String osNamePrefix, String osVersionPrefix) {
            if ((OS_NAME == null) || (OS_VERSION == null)) {
                return false;
            }

            return OS_NAME.startsWith(osNamePrefix) && OS_VERSION.startsWith(osVersionPrefix);
        }

        /**
         * 取得OS的默认字符编码集（取自系统属性：<code>file.encoding</code>）。
         * 
         * <p>
         * 这个编码字符集将被作为当前JVM转换字节/字符的默认方式。 例如：<code>GBK</code>。
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getFileEncoding() {
            return FILE_ENCODING;
        }

        /**
         * 取得OS的文件路径的分隔符（取自系统属性：<code>file.separator</code>）。
         * 
         * <p>
         * 例如：Unix为<code>"/"</code>，Windows为<code>"\\"</code>。
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getFileSeparator() {
            return FILE_SEPARATOR;
        }

        /**
         * 取得OS的文本文件换行符（取自系统属性：<code>line.separator</code>）。
         * 
         * <p>
         * 例如：Unix为<code>"\n"</code>，Windows为<code>"\r\n"</code>。
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getLineSeparator() {
            return LINE_SEPARATOR;
        }

        /**
         * 取得OS的搜索路径分隔符（取自系统属性：<code>path.separator</code>）。
         * 
         * <p>
         * 例如：Unix为<code>":"</code>，Windows为<code>";"</code>。
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getPathSeparator() {
            return PATH_SEPARATOR;
        }

        /**
         * 将OS的信息转换成字符串。
         * 
         * @return OS的字符串表示
         */
        @Override
		public final String toString() {
            StringBuilder builder = new StringBuilder();

            append(builder, "OS Arch:        ", getArch());
            append(builder, "OS Name:        ", getName());
            append(builder, "OS Version:     ", getVersion());
            append(builder, "File Encoding:  ", getFileEncoding());
            append(builder, "File Separator: ", getFileSeparator());
            append(builder, "Line Separator: ", getLineSeparator());
            append(builder, "Path Separator: ", getPathSeparator());

            return builder.toString();
        }
    }

    /**
     * 代表当前用户的信息。
     */
    public static final class UserInfo {
        private final String USER_NAME = getSystemProperty("user.name", false);
        private final String USER_HOME = getSystemProperty("user.home", false);
        private final String USER_DIR = getSystemProperty("user.dir", false);
        private final String USER_LANGUAGE = getSystemProperty("user.language", false);
        private final String USER_COUNTRY = ((getSystemProperty("user.country", false) == null) ? getSystemProperty(
                "user.region", false) : getSystemProperty("user.country", false));
        private final String JAVA_IO_TMPDIR = getSystemProperty("java.io.tmpdir", false);

        /**
         * 防止从外界创建此对象。
         */
        private UserInfo() {
        }

        /**
         * 取得当前登录用户的名字（取自系统属性：<code>user.name</code>）。
         * 
         * <p>
         * 例如：<code>"admin"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getName() {
            return USER_NAME;
        }

        /**
         * 取得当前登录用户的home目录（取自系统属性：<code>user.home</code>）。
         * 
         * <p>
         * 例如：<code>"/home/admin"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getHomeDir() {
            return USER_HOME;
        }

        /**
         * 取得当前目录（取自系统属性：<code>user.dir</code>）。
         * 
         * <p>
         * 例如：<code>"/home/admin/working"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.1
         */
        public final String getCurrentDir() {
            return USER_DIR;
        }

        /**
         * 取得临时目录（取自系统属性：<code>java.io.tmpdir</code>）。
         * 
         * <p>
         * 例如：<code>"/tmp"</code>
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getTempDir() {
            return JAVA_IO_TMPDIR;
        }

        /**
         * 取得当前登录用户的语言设置（取自系统属性：<code>user.language</code>）。
         * 
         * <p>
         * 例如：<code>"zh"</code>、<code>"en"</code>等
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getLanguage() {
            return USER_LANGUAGE;
        }

        /**
         * 取得当前登录用户的国家或区域设置（取自系统属性：JDK1.4 <code>user.country</code>或JDK1.2 <code>user.region</code>）。
         * 
         * <p>
         * 例如：<code>"CN"</code>、<code>"US"</code>等
         * </p>
         * 
         * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
         * 
         * @since Java 1.2
         */
        public final String getCountry() {
            return USER_COUNTRY;
        }

        /**
         * 将当前用户的信息转换成字符串。
         * 
         * @return 用户信息的字符串表示
         */
        @Override
		public final String toString() {
            StringBuilder builder = new StringBuilder();

            append(builder, "User Name:        ", getName());
            append(builder, "User Home Dir:    ", getHomeDir());
            append(builder, "User Current Dir: ", getCurrentDir());
            append(builder, "User Temp Dir:    ", getTempDir());
            append(builder, "User Language:    ", getLanguage());
            append(builder, "User Country:     ", getCountry());

            return builder.toString();
        }
    }

    /**
     * 代表当前主机的信息。
     */
    public static final class HostInfo {
        private final String HOST_NAME;
        private final String HOST_ADDRESS;

        /**
         * 防止从外界创建此对象。
         */
        private HostInfo() {
            String hostName;
            String hostAddress;

            try {
                InetAddress localhost = InetAddress.getLocalHost();

                hostName = localhost.getHostName();
                hostAddress = localhost.getHostAddress();
            } catch (UnknownHostException e) {
                hostName = "localhost";
                hostAddress = "127.0.0.1";
            }

            HOST_NAME = hostName;
            HOST_ADDRESS = hostAddress;
        }

        /**
         * 取得当前主机的名称。
         * 
         * <p>
         * 例如：<code>"webserver1"</code>
         * </p>
         * 
         * @return 主机名
         */
        public final String getName() {
            return HOST_NAME;
        }

        /**
         * 取得当前主机的地址。
         * 
         * <p>
         * 例如：<code>"192.168.0.1"</code>
         * </p>
         * 
         * @return 主机地址
         */
        public final String getAddress() {
            return HOST_ADDRESS;
        }

        /**
         * 将当前主机的信息转换成字符串。
         * 
         * @return 主机信息的字符串表示
         */
        @Override
		public final String toString() {
            StringBuilder builder = new StringBuilder();

            append(builder, "Host Name:    ", getName());
            append(builder, "Host Address: ", getAddress());

            return builder.toString();
        }
    }

    /**
     * 将系统信息输出到指定<code>PrintWriter</code>中。
     */
    public static final void dumpSystemInfo() {
        dumpSystemInfo(new PrintWriter(System.out));
    }

    /**
     * 将系统信息输出到指定<code>PrintWriter</code>中。
     * 
     * @param out <code>PrintWriter</code>输出流
     */
    public static final void dumpSystemInfo(PrintWriter out) {
        out.println("--------------");
        out.println(getJvmSpecInfo());
        out.println("--------------");
        out.println(getJvmInfo());
        out.println("--------------");
        out.println(getJavaSpecInfo());
        out.println("--------------");
        out.println(getJavaInfo());
        out.println("--------------");
        out.println(getJavaRuntimeInfo());
        out.println("--------------");
        out.println(getOsInfo());
        out.println("--------------");
        out.println(getUserInfo());
        out.println("--------------");
        out.println(getHostInfo());
        out.println("--------------");
        out.flush();
    }

    /**
     * 取得系统属性，如果因为Java安全的限制而失败，则将错误打在<code>System.err</code>中，然后返回 <code>null</code>。
     * 
     * @param name 属性名
     * @param quiet 安静模式，不将出错信息打在<code>System.err</code>中
     * 
     * @return 属性值或<code>null</code>
     */
    private static String getSystemProperty(String name, boolean quiet) {
        try {
            return System.getProperty(name);
        } catch (SecurityException e) {
            if (!quiet) {
                System.err.println("Caught a SecurityException reading the system property '" + name
                        + "'; the SystemUtil property value will default to null.");
            }

            return null;
        }
    }

    /**
     * 输出到<code>StringBuilder</code>。
     * 
     * @param builder <code>StringBuilder</code>对象
     * @param caption 标题
     * @param value 值
     */
    private static void append(StringBuilder builder, String caption, String value) {
        builder.append(caption).append(StringUtil.defaultIfNull(StringEscapeUtil.escapeJava(value), "[n/a]"))
                .append("\n");
    }

    public static void dump() {
        dumpSystemInfo();

        Set<?> keys = System.getProperties().keySet();

        @SuppressWarnings("unchecked")
        List<String> list = CollectionUtil.createArrayList((Set<String>) keys);

        Collections.sort(list);

        for (String key : list) {
            String value = System.getProperty(key);

            System.out.println(key + " = " + ObjectUtil.defaultIfNull(StringEscapeUtil.escapeJava(value), "[n/a]"));
        }
    }

}
