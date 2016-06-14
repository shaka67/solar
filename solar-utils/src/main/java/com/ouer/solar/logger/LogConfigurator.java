/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * 初始化日志，但不引入对特定日志系统的依赖。FIXME
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年11月20日 上午3:58:11
 */
public abstract class LogConfigurator {
    private static final String PROVIDERS_PATTERN = "META-INF/logconfig.providers";
    private static final String LOGGING_LEVEL = "loggingLevel";
    private static final String LOGGING_CHARSET = "loggingCharset";
    private static final String LOGGING_ROOT = "loggingRoot";
    private static final String LOCAL_HOST = "localHost";
    private static final String LOCAL_ADDRESS = "localAddress";
    private String logSystem;

    /**
     * 用指定的配置文件来配置log system。
     * <p/>
     * 注意，即使配置过程失败，也不会抛出任何异常，只是打印错误信息。日志系统的失败不应该影响应用系统。
     * <p/>
     */
    public final void configure(URL configFile) {
        configure(configFile, null);
    }

    /**
     * 用指定的配置文件和properties来配置log system。
     * <p/>
     * 注意，即使配置过程失败，也不会抛出任何异常，只是打印错误信息。日志系统的失败不应该影响应用系统。
     * <p/>
     */
    public final void configure(URL configFile, Map<String, String> props) {
        StringBuilder buf = new StringBuilder();

        buf.append("INFO: configuring \"").append(logSystem).append("\" using ").append(configFile).append("\n");

        if (props == null) {
            props = new HashMap<String, String>();
        }

        for (String key : new TreeSet<String>(props.keySet())) {
            String value = props.get(key);

            // 以log开头的property，或者值与system properties不同的，打印出来。
            if (key.startsWith("log") || value != null && !value.equals(System.getProperty(key))) {
                buf.append(" - with property ").append(key).append(" = ").append(value).append("\n");
            }
        }

        log(buf.toString());

        try {
            doConfigure(configFile, props);
        } catch (Exception e) {
            log("WARN: Failed to configure " + logSystem + " using " + configFile, e);
        }
    }

    /**
     * 用默认的配置文件和默认的properties来配置log system。
     * <p>
     * 相当于： <code>configure(getDefaultConfigFile(), getDefaultProperties());</code> 。
     * </p>
     */
    public final void configureDefault() {
        configureDefault(null);
    }

    /**
     * 用默认的配置文件和默认的properties来配置log system。
     * <p>
     * 相当于： <code>configure(getDefaultConfigFile(), getDefaultProperties(debug));</code> 。
     * </p>
     */
    public final void configureDefault(Boolean debug) {
        URL configFile = getDefaultConfigFile();

        if (configFile == null) {
            log("ERROR: could not find default config file for \"" + logSystem + "\"");
            return;
        }

        configure(configFile, getDefaultProperties(debug));
    }

    /** 取得当前configurator对应的log system的名称，例如：<code>logback</code>。 */
    public final String getLogSystem() {
        return logSystem;
    }

    /** 取得默认的配置文件URL。 */
    public final URL getDefaultConfigFile() {
        return getClass().getClassLoader().getResource(
                getClass().getPackage().getName().replace('.', '/') + "/" + getDefaultConfigFileName());
    }

    /**
     * 取得默认的配置文件名。
     * <p>
     * 子类可以覆盖此方法，以取得特定的配置文件。如不覆盖，默认返回<code>logsystem.xml</code>。
     * </p>
     */
    protected String getDefaultConfigFileName() {
        return logSystem + "-default.xml";
    }

    /**
     * 取得用于配置log system的默认的properties，包含以下内容：
     * <ul>
     * <li>system properties。</li>
     * <li><code>loggingCharset</code> - 输出charset，取决于系统默认值。</li>
     * <li><code>loggingLevel</code> - 日志level，取决于debug参数。</li>
     * <li><code>loggingRoot</code> - 日志根目录，默认为<code>$HOME/logs</code>。</li>
     * <li><code>localHost</code> - 当前机器名。</li>
     * <li><code>localAddress</code> - 当前网址。</li>
     * <li>子类可覆盖<code>setDefaultProperties()</code>方法，以便修改所设置的值。</li>
     * </ul>
     */
    public final Map<String, String> getDefaultProperties() {
        return getDefaultProperties(null);
    }

    /**
     * 取得用于配置log system的默认的properties，包含以下内容：
     * <ul>
     * <li>system properties。</li>
     * <li><code>loggingCharset</code> - 输出charset，取决于系统默认值。</li>
     * <li><code>loggingLevel</code> - 日志level，取决于debug参数。</li>
     * <li><code>loggingRoot</code> - 日志根目录，默认为<code>$HOME/logs</code>。</li>
     * <li><code>localHost</code> - 当前机器名。</li>
     * <li><code>localAddress</code> - 当前网址。</li>
     * <li>子类可覆盖<code>setDefaultProperties()</code>方法，以便修改所设置的值。</li>
     * </ul>
     */
    public final Map<String, String> getDefaultProperties(Boolean debug) {
        Map<String, String> props = new HashMap<String, String>();

        // system properties
        for (Map.Entry<?, ?> entry : System.getProperties().entrySet()) {
            props.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }

        // charset
        if (!isPropertyExist(props, LOGGING_CHARSET)) {
            props.put(LOGGING_CHARSET, getDefaultCharset());
        }

        // level
        if (!isPropertyExist(props, LOGGING_LEVEL)) {
            if (debug == null) {
                debug = false;
            }

            props.put(LOGGING_LEVEL, getDefaultLevel(debug));
        }

        // default logging root
        if (!isPropertyExist(props, LOGGING_ROOT)) {
            props.put(LOGGING_ROOT, getDefaultLoggingRoot());
        }

        // host info
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

        props.put(LOCAL_HOST, hostName);
        props.put(LOCAL_ADDRESS, hostAddress);

        // 给子类一个机会设置properties
        setDefaultProperties(props);

        return props;
    }

    private boolean isPropertyExist(Map<String, String> props, String name) {
        return trimToNull(props.get(name)) != null;
    }

    private String getDefaultCharset() {
        return Charset.defaultCharset().name();
    }

    private String getDefaultLevel(boolean debug) {
        return debug ? "TRACE" : "INFO";
    }

    private String getDefaultLoggingRoot() {
        return new File(System.getProperty("user.home") + "/logs").getAbsolutePath();
    }

    /**
     * 设置默认的properties。
     * <p>
     * 子类可以覆盖它，以设置自己的默认值。
     * </p>
     */
    protected void setDefaultProperties(Map<String, String> props) {
    }

    /** 配置对应的log system，由子类实现。 */
    protected abstract void doConfigure(URL configFile, Map<String, String> props) throws Exception;

    /** 关闭和清理log system，由子类实现。 */
    public abstract void shutdown();

    /** 取得指定的日志系统配置器。假如未指定日志系统的名称，则试着从slf4j中自动取得当前可用的日志系统。 */
    public static LogConfigurator getConfigurator() {
        return getConfigurator((String) null);
    }

    /** 取得指定的日志系统配置器。假如未指定日志系统的名称，则试着从slf4j中自动取得当前可用的日志系统。 */
    public static LogConfigurator getConfigurator(String logSystem) {
        LogConfigurator[] configurators = getConfigurators(logSystem);

        if (configurators == null || configurators.length == 0) {
            return null;
        } else {
            return configurators[0];
        }
    }

    /** 取得指定的日志系统配置器。假如未指定日志系统的名称，则试着从slf4j中自动取得当前可用的日志系统。 */
    public static LogConfigurator[] getConfigurators(String...logSystems) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Map<String, String> providers = getProviders(PROVIDERS_PATTERN, cl);

        // 检查SLF4J所选择的日志系统
        String slf4jLogSystem = guessSlf4jLogSystem(providers);

        // 规格化所有logSystems
        Set<String> logSystemToBeConfigured = new LinkedHashSet<String>();

        if (logSystems == null || logSystems.length == 0) {
            logSystems = new String[1];
        }

        boolean containsNull = false;

        for (String logSystem : logSystems) {
            if (logSystem != null) {
                logSystem = trimToNull(logSystem.toLowerCase());
            }

            if (logSystem == null) {
                logSystem = slf4jLogSystem;
            }

            if (logSystem == null) {
                containsNull = true;
            } else {
                logSystemToBeConfigured.add(logSystem);
            }
        }

        // 假如logSystems列表中未包含slf4jLogSystem，则报警。
        if (slf4jLogSystem != null && !logSystemToBeConfigured.contains(slf4jLogSystem)) {
            log("WARN: The current logging system [" + slf4jLogSystem + "] used by SLF4J may not be configured, "
                    + "because it is not in the configuration list: " + logSystemToBeConfigured + ".");
        }

        // 如果系统中不存在默认的logSystems，则报错
        if (containsNull) {
            log("WARN: No log system bound with SLF4J");
        }

        // 取得log configurators
        LogConfigurator[] configurators = new LogConfigurator[logSystemToBeConfigured.size()];
        int i = 0;

        for (String logSystem : logSystemToBeConfigured) {
            String providerClassName = providers.get(logSystem);

            if (providerClassName == null) {
                throw new IllegalArgumentException("Could not find LogConfigurator for \"" + logSystem
                        + "\" by searching in " + PROVIDERS_PATTERN);
            }

            Class<?> providerClass;

            try {
                providerClass = cl.loadClass(providerClassName);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Could not find LogConfigurator for " + logSystem, e);
            }

            if (!LogConfigurator.class.isAssignableFrom(providerClass)) {
                throw new IllegalArgumentException(logSystem + " class " + providerClassName
                        + " is not a sub-class of " + LogConfigurator.class.getName());
            }

            LogConfigurator configurator;

            try {
                configurator = (LogConfigurator) providerClass.newInstance();
            } catch (Throwable e) {
                throw new IllegalArgumentException("Could not create instance of class " + providerClassName + " for "
                        + logSystem, e);
            }

            configurator.logSystem = logSystem;

            configurators[i++] = configurator;
        }

        return configurators;
    }

    private static Map<String, String> getProviders(String location, ClassLoader cl) {
        Properties props = new Properties();
        Enumeration<?> i = null;

        try {
            i = cl.getResources(location);
        } catch (IOException e) {
            log("ERROR: Failed to read " + location, e);
        }

        while (i != null && i.hasMoreElements()) {
            URL url = (URL) i.nextElement();
            InputStream is = null;

            try {
                is = url.openStream();
                props.load(is);
            } catch (Exception e) {
                log("ERROR: Failed to read " + url, e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        Map<String, String> propsMap = new HashMap<String, String>();

        for (Map.Entry<?, ?> entry : props.entrySet()) {
            String key = trimToNull(entry.getKey());
            String className = trimToNull(entry.getValue());

            if (key != null && className != null) {
                propsMap.put(key.toLowerCase(), className);
            }
        }

        return propsMap;
    }

    private static String guessSlf4jLogSystem(Map<String, String> providers) {
        String s;

        try {
            s = org.slf4j.LoggerFactory.getILoggerFactory().getClass().getName().toLowerCase();
        } catch (Throwable e) {
            s = null;
        }

        if (s != null) {
            for (String name : providers.keySet()) {
                if (s.contains(name)) {
                    return name;
                }
            }
        }

        return null;
    }

    protected static String trimToNull(Object str) {
        if (!(str instanceof String)) {
            return null;
        }

        String result = ((String) str).trim();

        if (result == null || result.length() == 0) {
            return null;
        }

        return result;
    }

    protected static void log(String msg) {
        log(msg, null);
    }

    protected static void log(String msg, Throwable e) {
        System.out.flush(); // 防止控制台输出乱序
        System.err.println(msg);

        if (e != null) {
            e.printStackTrace();
        }

        System.err.flush();
    }
}
