/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import com.ouer.solar.FileUtil;

/**
 * Serialization Util
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-3-9 下午11:13:27
 */
public abstract class SerializationUtil {

    private static final int BYTE_ARRAY_BUFFER = 1024;

    /**
     * 将对象序列化后保存到指定文件
     * 
     * @param object 要序列化的对象
     * @param filePath 保存的路径
     * @return 如果成功返回<code>true</code>
     * @throws IOException
     */
    public static <T> boolean serialize(T object, String filePath) throws IOException {
        if (object == null) {
            return false;
        }

        ObjectOutputStream oos = null;
        try {
            OutputStream fos = new FileOutputStream(filePath);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            return true;
        } finally {
            StreamUtil.close(oos);
        }

    }

    /**
     * 将指定对象序列化成字节数组
     * 
     * @param object 要序列化的对象
     * @return 字节数组
     * @throws IOException
     */
    public static <T> byte[] serialize(T object) throws IOException {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(BYTE_ARRAY_BUFFER);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
            return baos.toByteArray();
        } finally {
            StreamUtil.close(oos);
        }
    }

    /**
     * 采用随机读写将对象序列化后保存到指定文件
     * 
     * @param object 要序列化的对象
     * @param filePath 保存的路径
     * @return 如果成功返回<code>true</code>
     * @throws IOException
     */
    public static <T> boolean serializeWithRA(T object, String filePath) throws IOException {
        if (object == null) {
            return false;
        }

        ObjectOutputStream oos = null;
        try {
            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
            OutputStream fos = new FileOutputStream(raf.getFD());
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            return true;
        } finally {
            StreamUtil.close(oos);
        }
    }

    /**
     * 将字节数组反序列化为对象
     * 
     * @param 要反序列化的数组
     * @return 反序列化的对象
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T deserialize(String filePath) throws IOException, ClassNotFoundException {
        if (filePath == null || !FileUtil.exist(filePath)) {
            return null;
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filePath));
            @SuppressWarnings("unchecked")
            T ret = (T) ois.readObject();
            return ret;
        } finally {
            StreamUtil.close(ois);
        }
    }
    
    /**
     * 将字节数组反序列化为对象
     * 
     * @param 要反序列化的数组
     * @return 反序列化的对象
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        if (bytes == null) {
            return null;
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            @SuppressWarnings("unchecked")
            T ret = (T) ois.readObject();
            return ret;
        } finally {
            StreamUtil.close(ois);
        }
    }

    /**
     * 采用随机读写将字节数组反序列化为对象 FIXME，此处应能提升性能
     * 
     * @param 要反序列化的数组
     * @return 反序列化的对象
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T deserializeWithRA(String filePath) throws IOException, ClassNotFoundException {
        if (filePath == null || !FileUtil.exist(filePath)) {
            return null;
        }
        ObjectInputStream ois = null;
        try {
            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
            InputStream fis = new FileInputStream(raf.getFD());
            ois = new ObjectInputStream(fis);
            @SuppressWarnings("unchecked")
            T ret = (T) ois.readObject();
            return ret;
        } finally {
            StreamUtil.close(ois);
        }
    }

    /**
     * 获取对象序列化字节大小
     * 
     * @param o 要序列化的对象
     * @return 对象序列化字节大小
     * @throws IOException
     */
    public static int size(final Object o) throws IOException {
        if (o == null) {
            return 0;
        }
        ByteArrayOutputStream buf = new ByteArrayOutputStream(BYTE_ARRAY_BUFFER);
        ObjectOutputStream out = new ObjectOutputStream(buf);
        out.writeObject(o);
        out.flush();
        buf.close();

        return buf.size();
    }

    /**
     * 拷贝对象
     * 
     * @param o 要拷贝的对象
     * @return 对象的副本
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T copy(final T o) throws IOException, ClassNotFoundException {
        if (o == null) {
            return null;
        }

        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(BYTE_ARRAY_BUFFER);
        ObjectOutput out = new ObjectOutputStream(outbuf);
        out.writeObject(o);
        out.flush();
        outbuf.close();

        ByteArrayInputStream inbuf = new ByteArrayInputStream(outbuf.toByteArray());
        ObjectInput in = new ObjectInputStream(inbuf);
        @SuppressWarnings("unchecked")
        T ret = (T) in.readObject();
        return ret;
    }

}
