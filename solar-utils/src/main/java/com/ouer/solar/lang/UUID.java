/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

import com.ouer.solar.ExceptionUtil;
import com.ouer.solar.StringUtil;
import com.ouer.solar.io.ByteArrayOutputStream;
import com.ouer.solar.io.StreamUtil;

/**
 * 生成唯一ID。
 * <p>
 * 唯一ID由以下元素构成：<code>machineId-jvmId-timestamp-counter</code>。
 * </p>
 * <p>
 * 默认情况下，UUID由数字和大写字母构成。如果在构造函数时，指定<code>noCase=false</code> ，那么所生成的ID将包含小写字母，这样ID的长度会较短。
 * </p>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 下午2:42:17
 */
public class UUID {
    private boolean noCase;
    private String instanceId;
    private AtomicInteger counter;

    public UUID() {
        this(true);
    }

    public UUID(boolean noCase) {
        // 1. Machine ID - 根据IP/MAC区分
        byte[] machineId = getLocalHostAddress();

        // 2. JVM ID - 根据启动时间区分 + 随机数
        byte[] jvmId = getRandomizedTime();

        this.instanceId = StringUtil.bytesToString(machineId, noCase) + "-" + StringUtil.bytesToString(jvmId, noCase);

        // counter
        this.counter = new AtomicInteger();

        this.noCase = noCase;
    }

    /** 取得local host的地址，如果有可能，取得物理MAC地址。 */
    private static byte[] getLocalHostAddress() {
        Method getHardwareAddress;

        try {
            getHardwareAddress = NetworkInterface.class.getMethod("getHardwareAddress");
        } catch (Exception e) {
            getHardwareAddress = null;
        }

        byte[] addr;

        try {
            InetAddress localHost = InetAddress.getLocalHost();

            if (getHardwareAddress != null) {
                addr = (byte[]) getHardwareAddress.invoke(NetworkInterface.getByInetAddress(localHost)); // maybe null
            } else {
                addr = localHost.getAddress();
            }
        } catch (Exception e) {
            addr = null;
        }

        if (addr == null) {
            addr = new byte[] { 127, 0, 0, 1 };
        }

        return addr;
    }

    /** 取得当前时间，加上随机数。 */
    private byte[] getRandomizedTime() {
        long jvmId = System.currentTimeMillis();
        long random = new SecureRandom().nextLong();

        // 取得上述ID的bytes，并转化成字符串
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeLong(jvmId);
            dos.writeLong(random);
            return baos.toByteArray().toByteArray();
        } catch (Exception e) {
            throw ExceptionUtil.toRuntimeException(e);
        } finally {
            StreamUtil.close(dos);
        }

    }

    /**
     * <code>System.nanoTime()</code>虽能避免使用自旋锁提供唯一ID，但自身非常耗性能<br>
     * 不如<code>System.currentTimeMillis()</code>与自旋锁配合性能高
     * 
     * @return 下一个唯一ID
     */
    public String nextID() {
        // MACHINE_ID + JVM_ID + 当前时间 + counter
        return instanceId + "-" + StringUtil.longToString(System.currentTimeMillis(), noCase) + "-"
                + StringUtil.longToString(counter.getAndIncrement(), noCase);
    }

}
