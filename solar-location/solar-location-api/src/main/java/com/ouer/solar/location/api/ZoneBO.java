/**
 * Ouer.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.ouer.solar.location.api;

import java.io.Serializable;

/**
 * 
 * @author tonghu
 * @version $Id: Zone.java, v 0.1 2015年9月17日 下午7:49:34 tonghu Exp $
 */
public class ZoneBO implements Serializable {
    
    private static final long serialVersionUID = -4790655267449767582L;

    private Long id;
    
    private String name; // 名称

    private String zipCode; 

    private String parentId; // 父Id
    
    private Boolean archive; // 是否逻辑删除

    /**
     * Getter method for property <tt>id</tt>.
     * Zone
     * @return property value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>zipCode</tt>.
     * 
     * @return property value of zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Setter method for property <tt>zipCode</tt>.
     * 
     * @param zipCode value to be assigned to property zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Getter method for property <tt>parentId</tt>.
     * 
     * @return property value of parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Setter method for property <tt>parentId</tt>.
     * 
     * @param parentId value to be assigned to property parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * Getter method for property <tt>archive</tt>.
     * 
     * @return property value of archive
     */
    public Boolean getArchive() {
        return archive;
    }

    /**
     * Setter method for property <tt>archive</tt>.
     * 
     * @param archive value to be assigned to property archive
     */
    public void setArchive(Boolean archive) {
        this.archive = archive;
    }
    
}
