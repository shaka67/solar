/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.io;

import static com.ouer.solar.StringPool.Charset.UTF_8;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.FileUtil;
import com.ouer.solar.ObjectUtil;

/**
 * FIXME 有关<code>Writer</code>的工具类。
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月27日 下午12:34:00
 */
public abstract class WriterUtil {

    // public static ByteArray

    /**
     * 写入数据并关闭流，如果<code>filePath</code>为<code>null</code>或者<code>datsa</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param filePath 文件路径
     * @param datas 数据集合
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, Collection<?> datas) throws IOException {
        return writeLinesAndClose(filePath, datas, false);
    }

    /**
     * 写入数据并关闭流，如果<code>filePath</code>为<code>null</code>或者<code>datas</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param filePath 文件路径
     * @param charsetName 编码格式
     * @param datas 数据集合
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, Collection<?> datas, String charsetName)
            throws IOException {
        return writeLinesAndClose(filePath, datas, false, charsetName);
    }

    /**
     * 写入数据并关闭流，如果<code>filePath</code>为<code>null</code>或者<code>datas</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param filePath 文件路径
     * @param datas 数据数组
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, Object...datas) throws IOException {
        return writeLinesAndClose(filePath, false, datas);
    }

    /**
     * 写入数据并关闭流，如果<code>filePath</code>为<code>null</code>或者<code>data</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param filePath 文件路径
     * @param data 数据
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, Object data) throws IOException {
        return writeLinesAndClose(filePath, data, false);
    }

    /**
     * 写入数据并关闭流，如果<code>file</code>为<code>null</code>或者<code>data</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param file 文件
     * @param data 数据
     * @return 是否成功
     * @throws IOException
     */
    public static boolean writeLinesAndClose(File file, Object data) throws IOException {
        return writeLinesAndClose(file, data, false);
    }

    /**
     * 写入数据并关闭流，如果<code>filePath</code>为<code>null</code>或者<code>datas</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param filePath 文件路径
     * @param datas 数据集合
     * @param append 是否追加
     * @return 是否成功
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, Collection<?> datas, boolean append) throws IOException {
        return writeLinesAndClose(filePath, datas, append, UTF_8);

    }

    /**
     * 写入数据并关闭流，如果<code>filePath</code>为<code>null</code>或者<code>datas</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param filePath 文件路径
     * @param datas 数据集合
     * @param append 是否追加
     * @param charsetName 编码格式
     * 
     * @return 是否成功
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, Collection<?> datas, boolean append, String charsetName)
            throws IOException {
        if (filePath == null || CollectionUtil.isEmpty(datas)) {
            return false;
        }
        if (!FileUtil.exist(filePath)) {
            FileUtil.createParentDir(filePath);
        }

        Writer writer = null;
        try {
            writer = getWriter(filePath, append, charsetName);
            for (Object data : datas) {
                writer.append(data.toString()).append("\n");
            }
            return true;
        } finally {
            StreamUtil.close(writer);
        }

    }

    /**
     * 写入文件并关闭，如果<code>filePath</code>为<code>null</code>或者<code>datas</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param filePath 文件路径
     * @param append 是否追加
     * @param datas 数据数组
     * 
     * @return 是否成功
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, boolean append, Object...datas) throws IOException {
        return writeLinesAndClose(filePath, append, UTF_8, datas);
    }

    /**
     * 写入文件并关闭，如果<code>filePath</code>为<code>null</code>或者<code>datas</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param filePath 文件路径
     * @param append 是否追加
     * @param charsetName 编码格式
     * @param datas 数据数组
     * 
     * @return 是否成功
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, boolean append, String charsetName, Object...datas)
            throws IOException {
        if (filePath == null || ObjectUtil.isEmpty(datas)) {
            return false;
        }
        
        if (!FileUtil.exist(filePath)) {
            FileUtil.createParentDir(filePath);
        }

        Writer writer = null;
        try {
            writer = getWriter(filePath, append, charsetName);
            for (Object data : datas) {
                writer.append(data.toString()).append("\n");
            }
            return true;
        } finally {
            StreamUtil.close(writer);
        }
    }

    /**
     * 写入文件并关闭，如果<code>filePath</code>为<code>null</code>或者<code>datas</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param filePath 文件路径
     * @param data 数据
     * @param append 是否追加
     * @return 是否成功
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, Object data, boolean append) throws IOException {
        return writeLinesAndClose(filePath, data, append, UTF_8);
    }

    /**
     * 写入文件并关闭，如果<code>file</code>为<code>null</code>或者<code>datas</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param file 文件
     * @param data 数据
     * @param append 是否追加
     * @return 是否成功
     * @throws IOException
     */
    public static boolean writeLinesAndClose(File file, Object data, boolean append) throws IOException {
        return writeLinesAndClose(file, data, append, UTF_8);
    }

    /**
     * 写入文件并关闭，如果<code>filePath</code>为<code>null</code>或者<code>data</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param filePath 文件路径
     * @param data 数据
     * @param append 是否追加
     * @param charsetName 编码格式
     * @return 是否成功
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, Object data, boolean append, String charsetName)
            throws IOException {
        if (filePath == null || data == null) {
            return false;
        }
        if (!FileUtil.exist(filePath)) {
            FileUtil.createParentDir(filePath);
        }

        Writer writer = null;
        try {
            writer = getWriter(filePath, append, charsetName);
            writer.append(data.toString()).append("\n");
            return true;
        } finally {
            StreamUtil.close(writer);
        }
    }

    /**
     * 写入文件并关闭，如果<code>file</code>为<code>null</code>或者<code>data</code>为<code>null</code>，则返回<code>false</code>。
     * 
     * @param file 文件路径
     * @param data 数据
     * @param append 是否追加
     * @param charsetName 编码格式
     * @return 是否成功
     * @throws IOException
     */
    public static boolean writeLinesAndClose(File file, Object data, boolean append, String charsetName)
            throws IOException {
        if (file == null || data == null) {
            return false;
        }
        if (!FileUtil.exist(file)) {
            FileUtil.createParentDir(file);
        }

        Writer writer = null;
        try {
            writer = getWriter(file, append, charsetName);
            writer.append(data.toString()).append("\n");
            return true;
        } finally {
            StreamUtil.close(writer);
        }
    }

    /**
     * 写入流并关闭
     * 
     * @param datas 数据
     * @param out 输出流
     * @param charsetName 编码格式
     * @throws IOException
     */
    public static void writeLinesAndClose(Collection<?> datas, OutputStream out, String charsetName) throws IOException {
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(out, charsetName);
            for (Object data : datas) {
                writer.append(data.toString()).append("\n");
            }

        } finally {
            StreamUtil.close(writer);
        }
    }

    /**
     * 写入流并关闭
     * 
     * @param out 输出流
     * @param charsetName 编码格式
     * @param datas 数据
     * @throws IOException
     */
    public static void writeLinesAndClose(OutputStream out, String charsetName, Object...datas) throws IOException {
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(out, charsetName);
            for (Object data : datas) {
                writer.append(data.toString()).append("\n");
            }

        } finally {
            StreamUtil.close(writer);
        }
    }

    /**
     * 获取<code>Writer</code>
     * 
     * @param filePath 文件路径
     * @param append 是否追加
     * @param charsetName 编码格式
     * @return <code>Writer</code>
     * @throws IOException
     */
    private static Writer getWriter(String filePath, boolean append, String charsetName) throws IOException {
        OutputStream output = new FileOutputStream(filePath, append);

        Writer writer = new OutputStreamWriter(output, charsetName);
        return new BufferedWriter(writer);
    }

    /**
     * 获取<code>Writer</code>
     * 
     * @param file 文件
     * @param append 是否追加
     * @param charsetName 编码格式
     * @return <code>Writer</code>
     * @throws IOException
     */
    private static Writer getWriter(File file, boolean append, String charsetName) throws IOException {
        OutputStream output = new FileOutputStream(file, append);

        Writer writer = new OutputStreamWriter(output, charsetName);
        return new BufferedWriter(writer);
    }

    // private static Writer getWriter(String filePath, boolean append)
    // throws IOException {
    // return getWriter(filePath, append, UTF_8);
    // }

}
