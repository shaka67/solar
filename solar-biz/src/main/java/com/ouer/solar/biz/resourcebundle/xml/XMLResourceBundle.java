/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.resourcebundle.xml;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.dom4j.Document;
import org.dom4j.Node;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.StringUtil;
import com.ouer.solar.biz.resourcebundle.AbstractResourceBundle;
import com.ouer.solar.biz.resourcebundle.ResourceBundleConstant;
import com.ouer.solar.biz.resourcebundle.ResourceBundleCreateException;
import com.ouer.solar.biz.resourcebundle.ResourceBundleEnumeration;
import com.ouer.solar.collection.ListMap;

/**
 * XML格式的<code>ResourceBundle</code>.
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午3:22:28
 */

public class XMLResourceBundle extends AbstractResourceBundle {

    protected static final String CODE_SUFFIX = StringUtil.substring(
            ResourceBundleConstant.XPATH_RESOURCE_MESSAGE_CODE,
            ResourceBundleConstant.XPATH_RESOURCE_MESSAGE_SPACE.length());

    protected ListMap<String, Object> values = CollectionUtil.createArrayHashMap();

    /**
     * 从XML文档中创建<code>ResourceBundle</code>.
     * 
     * @param doc XML文档
     * 
     * @throws ResourceBundleCreateException 解析错误
     */
    public XMLResourceBundle(Document doc) throws ResourceBundleCreateException {
        // 解析group.
        for (Iterator<?> i = doc.selectNodes(ResourceBundleConstant.XPATH_GROUPS).iterator(); i.hasNext();) {
            Node groupNode = (Node) i.next();

            initGroup(groupNode);
        }

        // 解析没有group的resource.
        for (Iterator<?> i = doc.selectNodes(ResourceBundleConstant.XPATH_UNGROUPED_RESOURCES).iterator(); i.hasNext();) {
            Node resourceNode = (Node) i.next();

            initResource(resourceNode);
        }
    }

    /**
     * 根据XML Node初始化一个resource项.
     * 
     * @param groupNode 代表resource信息的XML Node
     * 
     * @throws ResourceBundleCreateException 解析错误
     */
    protected void initGroup(Node groupNode) throws ResourceBundleCreateException {
        for (Iterator<?> i = groupNode.selectNodes(ResourceBundleConstant.XPATH_RESOURCES).iterator(); i.hasNext();) {
            Node resourceNode = (Node) i.next();

            initResource(resourceNode);
        }
    }

    /**
     * 根据XML Node初始化一个resource项.
     * 
     * @param resourceNode 代表resource信息的XML Node
     * 
     * @throws ResourceBundleCreateException 解析错误
     */
    protected void initResource(Node resourceNode) throws ResourceBundleCreateException {
        String id = (String) resourceNode.selectObject(ResourceBundleConstant.XPATH_RESOURCE_ID);
        Object value = null;
        Object code = null;
        String type = resourceNode.getName();
        if (ResourceBundleConstant.RB_RESOURCE_TYPE_MESSAGE.equals(type)) {
            value = getMessageResource(id, resourceNode);
            code = getCodeResource(id, resourceNode);
            values.put(id + CODE_SUFFIX, code);
        } else if (ResourceBundleConstant.RB_RESOURCE_TYPE_MAP.equals(type)) {
            value = getMapResource(id, resourceNode);
        } else if (ResourceBundleConstant.RB_RESOURCE_TYPE_LIST.equals(type)) {
            value = getListResource(id, resourceNode);
        }

        if (values.containsKey(id)) {
            throw new ResourceBundleCreateException(ResourceBundleConstant.RB_DUPLICATED_RESOURCE_KEY,
                    new Object[] { id }, null);
        }
        values.put(id, value);
    }

    /**
     * 根据XML Node创建message resource项.
     * 
     * @param id resource ID
     * @param resourceNode 代表resource信息的XML Node
     * 
     * @return resource的值
     * 
     * @throws ResourceBundleCreateException 解析错误
     */
    protected Object getMessageResource(String id, Node resourceNode) throws ResourceBundleCreateException {
        return resourceNode.selectObject(ResourceBundleConstant.XPATH_RESOURCE_MESSAGE_DATA);
    }

    /**
     * 根据XML Node创建message resource项.
     * 
     * @param id resource ID
     * @param resourceNode 代表resource信息的XML Node
     * 
     * @return resource的值
     * 
     * @throws ResourceBundleCreateException 解析错误
     */
    protected Object getCodeResource(String id, Node resourceNode) throws ResourceBundleCreateException {
        return resourceNode.selectObject(ResourceBundleConstant.XPATH_RESOURCE_MESSAGE_CODE);
    }

    /**
     * 根据XML Node创建map resource项.
     * 
     * @param id resource ID
     * @param resourceNode 代表resource信息的XML Node
     * 
     * @return resource的值
     * 
     * @throws ResourceBundleCreateException 解析错误
     */

    protected Object getMapResource(String id, Node resourceNode) throws ResourceBundleCreateException {
        ListMap<Object, Object> map = CollectionUtil.createArrayHashMap();

        for (Iterator<?> i = resourceNode.selectNodes(ResourceBundleConstant.XPATH_RESOURCES).iterator(); i.hasNext();) {
            Node mapItemNode = (Node) i.next();
            Object mapKey = mapItemNode.selectObject(ResourceBundleConstant.XPATH_RESOURCE_ID);

            if (map.containsKey(id)) {
                throw new ResourceBundleCreateException(ResourceBundleConstant.RB_DUPLICATED_MAP_RESOURCE_KEY,
                        new Object[] { mapKey, id }, null);
            }

            String mapItemType = mapItemNode.getName();
            Object value = null;

            if (ResourceBundleConstant.RB_RESOURCE_TYPE_MESSAGE.equals(mapItemType)) {
                value = getMessageResource(id, mapItemNode);
            } else if (ResourceBundleConstant.RB_RESOURCE_TYPE_MAP.equals(mapItemType)) {
                value = getMapResource(id, mapItemNode);
            } else if (ResourceBundleConstant.RB_RESOURCE_TYPE_LIST.equals(mapItemType)) {
                value = getListResource(id, mapItemNode);
            }

            map.put(mapKey, value);
        }

        return Collections.unmodifiableMap(map);
    }

    /**
     * 根据XML Node创建list resource项.
     * 
     * @param id resource ID
     * @param resourceNode 代表resource信息的XML Node
     * 
     * @return resource的值
     * 
     * @throws ResourceBundleCreateException 解析错误
     */
    protected Object getListResource(String id, Node resourceNode) throws ResourceBundleCreateException {
        List<Object> list = CollectionUtil.createArrayList();

        for (Iterator<?> i = resourceNode.selectNodes(ResourceBundleConstant.XPATH_RESOURCES).iterator(); i.hasNext();) {
            Node listItemNode = (Node) i.next();
            String listItemType = listItemNode.getName();
            Object value = null;

            if (ResourceBundleConstant.RB_RESOURCE_TYPE_MESSAGE.equals(listItemType)) {
                value = getMessageResource(id, listItemNode);
            } else if (ResourceBundleConstant.RB_RESOURCE_TYPE_MAP.equals(listItemType)) {
                value = getMapResource(id, listItemNode);
            } else if (ResourceBundleConstant.RB_RESOURCE_TYPE_LIST.equals(listItemType)) {
                value = getListResource(id, listItemNode);
            }

            list.add(value);
        }

        return Collections.unmodifiableList(list);
    }

    /**
     * 根据指定的键, 从resource bundle中取得相应的对象. 如果返回<code>null</code>表示对应的对象不存在.
     * 
     * @param key 要查找的键
     * 
     * @return key对应的对象, 或<code>null</code>表示不存在该对象
     */
    @Override
	protected Object handleGetObject(String key) {
        return values.get(key);
    }

    /**
     * 取得所有keys.
     * 
     * @return 所有keys
     */
    @Override
	public Enumeration<String> getKeys() {
        ResourceBundle parent = getParent();

        return new ResourceBundleEnumeration(values.keySet(), (parent != null) ? parent.getKeys() : null);
    }
}
