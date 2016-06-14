/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

import java.util.Date;

/**
 * 代表一个主键为<code>long</code>型的通用型的数据对象
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月23日 下午10:42:56
 */

public class LongData extends LongID {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1972647768021901067L;

    /** 创建日期 对应数据表的创建日期 */
    protected Date createdAt;

    /** 最后修改日期 对应数据表的修改日期 */
    protected Date updatedAt;

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
