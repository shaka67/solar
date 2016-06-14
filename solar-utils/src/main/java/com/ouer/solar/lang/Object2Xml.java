/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-1-13 下午7:06:10
 */
public abstract class Object2Xml {

    /**
     * 对象输出到XML文件
     * 
     * @param obj 待输出的对象
     * @param outFileName 目标XML文件的文件名
     * @return 返回输出XML文件的路径
     * @throws FileNotFoundException
     */
    public static String object2XML(Object obj, String outFileName) throws FileNotFoundException {
        XMLEncoder xmlEncoder = null;
        try {
            // 构造输出XML文件的字节输出流
            File outFile = new File(outFileName);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));

            // 构造一个XML编码器
            xmlEncoder = new XMLEncoder(bos);
            // 使用XML编码器写对象
            xmlEncoder.writeObject(obj);

            return outFile.getAbsolutePath();
        } finally {
            if (xmlEncoder != null) {
                // 关闭编码器
                xmlEncoder.close();
            }
        }
    }

    /**
     * 把XML文件解码成对象
     * 
     * @param inFileName 输入的XML文件
     * @return 返回生成的对象
     * @throws FileNotFoundException
     */
    public static <T> T xml2Object(String inFileName) throws FileNotFoundException {
        XMLDecoder xmlDecoder = null;
        try {
            // 构造输入的XML文件的字节输入流
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inFileName));
            // 构造一个XML解码器
            xmlDecoder = new XMLDecoder(bis);
            // 使用XML解码器读对象
            @SuppressWarnings("unchecked")
            T obj = (T) xmlDecoder.readObject();

            return obj;
        } finally {
            // 关闭解码器
            xmlDecoder.close();
        }

    }
}
