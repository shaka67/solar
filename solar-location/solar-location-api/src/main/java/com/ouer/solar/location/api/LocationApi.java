/**
 * Ouer.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.ouer.solar.location.api;

import java.util.List;

/**
 * 
 * @author tonghu
 * @version $Id: LocationApi.java, v 0.1 2015年9月17日 下午7:42:52 tonghu Exp $
 */
public interface LocationApi {
    
	/**
	 * 根据ID获取地理位置
	 * @param id
	 * @return
	 */
    ZoneBO load(Long id);
    
    /***
     * 获取根级地理位置列表
     * @return
     */
    List<ZoneBO> listRoots();
    
    /***
     * 获取子节点地理位置列表
     * @param zoneId
     * @return
     */
    List<ZoneBO> listChildren(Long zoneId);
    
    /**
     * 获取同级别地理位置列表
     * @param zoneId
     * @return
     */
    List<ZoneBO> listSiblings(Long zoneId);
    
    /**
     * 获取父级地理位置
     * @param zoneId
     * @return
     */
    ZoneBO findParent(Long zoneId);

    /**
     * 向上递归父级地理位置列表
     * @param zoneId
     * @return
     */
    List<ZoneBO> listParents(Long zoneId);

   /**
    * 是否存在级联关系
    * @param srcId  父级
    * @param destId 子级
    * @return
    */
    Boolean isAncestor(Long srcId,Long destId);
}
