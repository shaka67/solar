/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

/**
 * 有关资源文件的工具类。FIXME TODO("classpath:/")
 * <p>
 * 这个类中的每个方法都可以“安全”地处理 <code>null</code> ，而不会抛出 <code>NullPointerException</code>。
 * </p>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-11-13 下午7:43:01
 */
public abstract class ResourceUtil {

    /**
     * classpath前缀
     */
    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    // ==========================================================================
    // 取得类名和package名的resource名的方法。
    //
    // 和类名、package名不同的是，resource名符合文件名命名规范，例如：
    // java/lang/String.class
    // etc.
    // ==========================================================================

    /**
     * 根据对象<code>object</code>所属的类得位置获取资源文件<code>resource</code>名。<br>
     * 对于数组，此方法返回的是数组元素类型所属的类得位置获取资源文件<code>resource</code>名。<br>
     * <code>object</code>或资源文件<code>resource</code>为<code>null</code>，则返回<code>null</code>
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getResourcePath((Object)null, null) = null;
     * ResourceUtil.getResourcePath((Object)null, &quot;test.resource&quot;) = null;
     * ResourceUtil.getResourcePath(new Object(), null) = null;
     * ResourceUtil.getResourcePath(new Object(), &quot;test.resource&quot;) = &quot;java/lang/test.resource&quot;
     * ResourceUtil.getResourcePath(new int[0], &quot;test.resource&quot;) = &quot;/test.resource&quot;
     * </pre>
     * 
     * @param object 要显示类名的对象
     * @param resource 资源文件名
     * @return 指定对象所属类位置的资源名
     */
    public static String getResourcePath(Object object, String resource) {
        if (ObjectUtil.isAnyNull(object, resource)) {
            return null;
        }

        String path = PackageUtil.getPackageName(object).replace('.', '/');

        return path + '/' + resource;
    }

    /**
     * 根据类<code>clazz</code>得位置获取资源文件<code>resource</code>名。<br>
     * 对于数组，此方法返回的是数组元素类型所属的类得位置获取资源文件<code>resource</code>名。<br>
     * 如果类<code>clazz</code>或资源文件<code>resource</code>为<code>null</code>，则返回<code>null</code>
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getResourcePath((Class<?>)null, null) = null;
     * ResourceUtil.getResourcePath((Class<?>)null, &quot;test.resource&quot;) = null;
     * ResourceUtil.getResourcePath(String.class, null) = null;
     * ResourceUtil.getResourcePathForClass(String.class, &quot;test.resource&quot;) = &quot;java/lang/test.resource&quot;
     * ResourceUtil.getResourcePath(int[].class, &quot;test.resource&quot;) = &quot;/test.resource&quot;
     * </pre>
     * 
     * @param className 要显示的类名
     * @param resource 资源文件名
     * @return 指定指定类位置的资源名
     */
    public static String getResourcePath(Class<?> clazz, String resource) {
        if (ObjectUtil.isAnyNull(clazz, resource)) {
            return null;
        }

        String path = PackageUtil.getPackageName(clazz).replace('.', '/');

        return path + '/' + resource;
    }

    /**
     * 根据类名<code>className</code>所在位置获取资源文件<code>resource</code>名。<br>
     * 对于数组，此方法返回的是数组元素类型所属的类得位置获取资源文件<code>resource</code>名。<br>
     * 如果类<code>className</code>名或资源文件<code>resource</code>为<code>null</code>，则返回<code>null</code>
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getResourcePath((String)null, null) = null;
     * ResourceUtil.getResourcePath((String)null, &quot;test.resource&quot;) = null;
     * ResourceUtil.getResourcePath(&quot;This is a string&quot;, null) = null;
     * ResourceUtil.getResourcePath(&quot;java.lang.String&quot;, null) = null;
     * ResourceUtil.getResourcePath(&quot;This is a string&quot;, &quot;test.resource&quot;) = &quot;java/lang/test.resource&quot;
     * ResourceUtil.getResourcePath(&quot;java.lang.String&quot;, &quot;test.resource&quot;) = &quot;java/lang/test.resource&quot;
     * ResourceUtil.getResourcePath(&quot;int&quot;, &quot;test.resource&quot;) = &quot;/test.resource&quot;
     * ResourceUtil.getResourcePath(&quot;[I&quot;, &quot;test.resource&quot;) = &quot;/test.resource&quot;
     * ResourceUtil.getResourcePath(&quot;[Ljava.lang.Integer;&quot;, &quot;test.resource&quot;) = &quot;/test.resource&quot;
     * </pre>
     * 
     * @param className 要查看的类名
     * @param resource 资源文件名
     * @return 指定类名对应的资源文件名
     */
    public static String getResourcePath(String className, String resource) {
        if (ObjectUtil.isAnyNull(className, resource)) {
            return null;
        }

        String path = PackageUtil.getPackageName(className).replace('.', '/');

        return path + '/' + resource;
    }

    /**
     * 根据对象<code>object</code>所属的类得位置获取资源文件<code>property</code>名。<br>
     * 对于数组，此方法返回的是数组元素类型所属的类得位置获取资源文件<code>resource</code>名。<br>
     * 如果对象<code>object</code>或资源文件<code>resource</code>为<code>null</code>，则返回<code>null</code>
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getPropertyPath((Object)null, null) = null;
     * ResourceUtil.getPropertyPath((Object)null, &quot;test&quot;) = null;
     * ResourceUtil.getPropertyPath(new Object(), null) = null;
     * ResourceUtil.getPropertyPath(new Object(), &quot;test&quot;) = &quot;java/lang/test.properties&quot;
     * ResourceUtil.getResourcePath(new int[0], &quot;test&quot;) = &quot;/test.properties&quot;
     * </pre>
     * 
     * @param object 要显示类名的对象
     * @param property 资源文件名
     * @return 指定对象所属类位置的资源名
     */
    public static String getPropertyPath(Object object, String property) {
        if (ObjectUtil.isAnyNull(object, property)) {
            return null;
        }

        String path = PackageUtil.getPackageName(object).replace('.', '/');

        return path + '/' + property + ".properties";
    }

    /**
     * 根据类<code>clazz</code>得位置获取资源文件<code>property</code>名。<br>
     * 对于数组，此方法返回的是数组元素类型所属的类得位置获取资源文件<code>resource</code>名。<br>
     * 如果类<code>clazz</code>或资源文件<code>resource</code>为<code>null</code>，则返回<code>null</code>
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getPropertyPath((Class<?>)null, null) = null;
     * ResourceUtil.getPropertyPath((Class<?>)null, &quot;test&quot;) = null;
     * ResourceUtil.getPropertyPath(String.class, null) = null;
     * ResourceUtil.getPropertyPath(String.class, &quot;test&quot;) = &quot;java/lang/test.properties&quot;
     * ResourceUtil.getResourcePath(int.class, &quot;test&quot;) = &quot;/test.properties&quot;
     * </pre>
     * 
     * @param className 要显示的类名
     * @param property 资源文件名
     * @return 指定指定类位置的资源名
     */
    public static String getPropertyPath(Class<?> clazz, String property) {
        if (ObjectUtil.isAnyNull(clazz, property)) {
            return null;
        }

        String path = PackageUtil.getPackageName(clazz).replace('.', '/');

        return path + '/' + property + ".properties";
    }

    /**
     * 根据类名<code>className</code>所在位置获取资源文件<code>property</code>名。<br>
     * 对于数组，此方法返回的是数组元素类型所属的类得位置获取资源文件<code>resource</code>名。<br>
     * 如果类<code>className</code>名或资源文件<code>property</code>为<code>null</code>，则返回<code>null</code>
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getPropertyPath((String)null, null) = null;
     * ResourceUtil.getPropertyPath((String)null, test.resource) = null;
     * ResourceUtil.getPropertyPath(&quot;This is a string&quot;, null) = null;
     * ResourceUtil.getPropertyPath(&quot;This is a string&quot;, &quot;test&quot;) = &quot;java/lang/test.properties&quot;
     * ResourceUtil.getPropertyPath(&quot;int&quot;, &quot;test&quot;) = &quot;/test.resource&quot;
     * ResourceUtil.getPropertyPath(&quot;[I&quot;, &quot;test&quot;) = &quot;/test.resource&quot;
     * ResourceUtil.getPropertyPath(&quot;[Ljava.lang.Integer;&quot;, &quot;test&quot;) = &quot;/test.resource&quot;
     * </pre>
     * 
     * @param className 要查看的类名
     * @param property 资源文件名
     * @return 指定类名对应的资源文件名
     */
    public static String getPropertyPath(String className, String property) {
        if (ObjectUtil.isAnyNull(className, property)) {
            return null;
        }

        String path = PackageUtil.getPackageName(className).replace('.', '/');

        return path + '/' + property + ".properties";
    }

    /**
     * 取得对象所属的类的资源名。
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getResourceNameForObject(null) = null;
     * ResourceUtil.getResourceNameForObject(&quot;This is a string&quot;) = &quot;java/lang/String.class&quot;
     * ResourceUtil.getResourceNameForObject(new int[0]) = &quot;[I.class&quot;
     * ResourceUtil.getResourceNameForObject(new Integer[0]) = &quot;[Ljava/lang/Integer;.class&quot;
     * </pre>
     * 
     * @param object 要显示类名的对象
     * @return 指定对象所属类的资源名，如果对象为空，则返回<code>null</code>
     */
    public static String getResourceNameForObject(Object object) {
        if (object == null) {
            return null;
        }

        return object.getClass().getName().replace('.', '/') + ".class";
    }

    /**
     * 取得指定类的资源名。
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getResourceNameForClass((Class<?>)null) = null;
     * ResourceUtil.getResourceNameForClass(String.class) = &quot;java/lang/String.class&quot;
     * ResourceUtil.getResourceNameForClass(int.class) = &quot;int.class&quot;
     * ResourceUtil.getResourceNameForClass(int[].class) = &quot;[I.class&quot;
     * ResourceUtil.getResourceNameForClass(Integer[].class) = &quot;[Ljava/lang/Integer;.class&quot;
     * </pre>
     * 
     * @param clazz 要显示类名的类
     * @return 指定类的资源名，如果指定类为空，则返回<code>null</code>
     */
    public static String getResourceNameForClass(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return clazz.getName().replace('.', '/') + ".class";
    }

    /**
     * 取得对象所属的类名对应的资源名称，如果对象为<code>null</code>，则返回<code>null</code>。
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getResourceNameFor(null) = null;
     * ResourceUtil.getResourceNameFor(&quot;This is a string&quot;) = &quot;java/lang/String&quot;
     * ResourceUtil.getResourceNameFor(new int[0]) = &quot;[I&quot;
     * ResourceUtil.getResourceNameFor(new Integer[0]) = &quot;[Ljava/lang/Integer;&quot;
     * </pre>
     * 
     * @param object 要显示类名的对象
     * @return 对象所属的类名对应的资源名称，如果对象为<code>null</code>，则返回<code>null</code>
     */
    public static String getResourceNameFor(Object object) {
        if (object == null) {
            return null;
        }

        return object.getClass().getName().replace('.', '/');
    }

    /**
     * 取得指定类名对应的资源名称，如果对象为<code>null</code>，则返回<code>null</code>。
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getResourceNameFor((Class<?>)null) = null;
     * ResourceUtil.getResourceNameFor(String.class) = &quot;java/lang/String&quot;
     * ResourceUtil.getResourceNameFor(int.class) = &quot;int&quot;
     * ResourceUtil.getResourceNameFor(int[].class) = &quot;[I&quot;
     * ResourceUtil.getResourceNameFor(Integer[].class) = &quot;[Ljava/lang/Integer;&quot;
     * </pre>
     * 
     * @param clazz 要显示类名的类
     * @return 对象所属的类名对应的资源名称，如果指定类为<code>null</code>，则返回<code>null</code>
     */
    public static String getResourceNameFor(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return clazz.getName().replace('.', '/');
    }

    /**
     * 取得指定对象所属的类的package名的资源名。 <br>
     * 对于数组，此方法返回的是数组元素类型的package名。
     * 
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getResourceNameForPackage((Object)null) = null;
     * ResourceUtil.getResourceNameForClass(&quot;java.lang.String&quot;) = &quot;java/lang&quot;
     * ResourceUtil.getResourceNameForClass(new int[0]) = &quot;java/lang&quot;
     * ResourceUtil.getResourceNameForClass(new Integer[0]) = &quot;java/lang&quot;
     * </pre>
     * 
     * @param object 要查看的对象
     * @return package名，如果对象为 <code>null</code> ，则返回 <code>null</code>
     */
    public static String getResourceNameForPackage(Object object) {
        if (object == null) {
            return null;
        }

        return PackageUtil.getPackageName(object).replace('.', '/');
    }

    /**
     * 取得指定类的package名的资源名。 <br>
     * 对于数组，此方法返回的是数组元素类型的package名。
     * 
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getResourceNameForPackage((Class<?>)null) = null;
     * ResourceUtil.getResourceNameForClass(String.class) = &quot;java/lang&quot;
     * ResourceUtil.getResourceNameForClass(int[].class) = &quot;java/lang&quot;
     * ResourceUtil.getResourceNameForClass(Integer[].class) = &quot;java/lang&quot;
     * </pre>
     * 
     * @param clazz 要查看的类
     * @return package名，如果类为 <code>null</code> ，则返回 <code>null</code>
     */
    public static String getResourceNameForPackage(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return PackageUtil.getPackageName(clazz).replace('.', '/');
    }

    /**
     * 取得指定类名的package名的资源名。
     * <p>
     * 对于数组，此方法返回的是数组元素类型的package名。
     * </p>
     * 
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * ResourceUtil.getResourceNameForPackage((String)null) = null;
     * ResourceUtil.getResourceNameForClass(&quot;java.lang.String&quot;) = &quot;java/lang&quot;
     * ResourceUtil.getResourceNameForClass(&quot;[I&quot;) = &quot;&quot;
     * ResourceUtil.getResourceNameForClass(&quot;[Ljava.lang.Integer;&quot;) = &quot;java/lang&quot;
     * </pre>
     * 
     * @param className 要查看的类名
     * @return package名，如果类名为空，则返回 <code>null</code>
     */
    public static String getResourceNameForPackage(String className) {
        if (className == null) {
            return null;
        }

        return PackageUtil.getPackageName(className).replace('.', '/');
    }

}
