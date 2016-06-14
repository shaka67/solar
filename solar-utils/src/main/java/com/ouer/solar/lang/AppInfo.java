/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.StringUtil;
import com.ouer.solar.able.Processable;
import com.ouer.solar.logger.Logger;
import com.ouer.solar.logger.LoggerFactory;

/**
 * 代表一个应用
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月31日 下午8:28:11
 */
public class AppInfo {

    private static final Logger logger = LoggerFactory.getLogger(AppInfo.class);

    private String appVersion;

    private String specificationTitle;
    private String specificationVersion;
    private String specificationVendor;
    private String implementationTitle;
    private String implementationVersion;
    private String implementationVendor;

    private Processable processable;

    public AppInfo() {

    }

    public String executeCommand(String command) {
        processable.execute(command);
        return processable.getInfo();
    }

    public String executeCommands(String commands) {
        return this.executeCommands(commands, " ,");
    }

    public String executeCommands(String commands, String separator) {
        StringBuilder builder = new StringBuilder();
        String[] cmds = StringUtil.split(commands, separator);

        for (String cmd : cmds) {
            builder.append(cmd).append(":\n");
            builder.append(this.executeCommand(cmd));
        }

        return builder.toString();
    }

    public void setLocation(String location) {

        if (location != null && location.endsWith(".jar")) {
            setJarLocation(location);
        } else {
            setPackage(location);
        }

        logger.info("Specification-Title=[{}]", this.specificationTitle);
        logger.info("Specification-Version=[{}]", this.specificationVersion);
        logger.info("Specification-Vendor=[{}]", this.specificationVendor);
        logger.info("Implementation-Title=[{}]", this.implementationTitle);
        logger.info("Implementation-Version=[{}]", this.implementationVersion);
        logger.info("Implementation-Vendor=[{}]", this.implementationVendor);

    }

    private void setJarLocation(String location) {
        JarFile jar;
        try {
            jar = new JarFile(location);
            Manifest man = jar.getManifest();
            Attributes attrs = man.getMainAttributes();
            // appVersion =
            // attrs.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
            appVersion = attrs.getValue(Attributes.Name.SPECIFICATION_VERSION);
            this.specificationTitle = attrs.getValue(Attributes.Name.SPECIFICATION_TITLE);
            this.specificationVersion = attrs.getValue(Attributes.Name.SPECIFICATION_VERSION);
            this.specificationVendor = attrs.getValue(Attributes.Name.SPECIFICATION_VENDOR);
            this.implementationTitle = attrs.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
            this.implementationVersion = attrs.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
            this.implementationVendor = attrs.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);

        } catch (IOException e) {
            logger.error("setJarLocation:", e);
        }
    }

    private void setPackage(String location) {

        Package pack = Package.getPackage(location);
        if (pack == null) {
            logger.warn("can,t find AppInfo");
            return;
        }

        appVersion = pack.getImplementationVersion();
        this.specificationTitle = pack.getSpecificationTitle();
        this.specificationVersion = pack.getSpecificationVersion();
        this.specificationVendor = pack.getSpecificationVendor();

        this.implementationTitle = pack.getImplementationTitle();
        this.implementationVersion = pack.getImplementationVersion();
        this.implementationVendor = pack.getImplementationVendor();
    }

    /**
     * @return the appVersion
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * @return the specificationTitle
     */
    public String getSpecificationTitle() {
        return specificationTitle;
    }

    /**
     * @return the specificationVersion
     */
    public String getSpecificationVersion() {
        return specificationVersion;
    }

    /**
     * @return the specificationVendor
     */
    public String getSpecificationVendor() {
        return specificationVendor;
    }

    /**
     * @return the implementationTitle
     */
    public String getImplementationTitle() {
        return implementationTitle;
    }

    /**
     * @return the implementationVersion
     */
    public String getImplementationVersion() {
        return implementationVersion;
    }

    /**
     * @return the implementationVendor
     */
    public String getImplementationVendor() {
        return implementationVendor;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
	public String toString() {
        return ReflectionUtil.dump(this);
    }

    public void setProcessable(Processable processable) {
        this.processable = processable;
    }

}
