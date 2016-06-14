/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

import java.util.Date;

/**
 * 代表一个主键为<code>T</code>型的通用型的数据对象
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月23日 下午10:48:28
 */

public class GenericData<T extends Comparable<T>> extends GenericID<T> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2661977423252114412L;

    /** 创建日期 对应数据表的创建日期 */
    protected Date addTime;

    /** 最后修改日期 对应数据表的修改日期 */
    protected Date modTime;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

}
