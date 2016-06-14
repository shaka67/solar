/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.collection;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 有序的<code>java.util.Map</code>.
 * </p>
 * <p>
 * 除了拥有<code>java.util.Map</code>的所有特性以外, <code>ListMap</code>中的 项( <code>Map.Entry</code>)是有序的. 也就是说, 它既能以键值(key)来访问,
 * 也可以用索引(index)来访问. 例如,
 * </p>
 * <p>
 * 通过key访问:
 * </p>
 * <p/>
 * 
 * <pre>
 * Object value1 = listMap.get(&quot;key1&quot;);
 * </pre>
 * <p>
 * 通过整数index, 取得key和value:
 * </p>
 * <p/>
 * 
 * <pre>
 * Object value2 = listMap.get(2);
 * Object key2 = listMap.getKey(2);
 * </pre>
 * <p>
 * 通过整数index, 删除一项, 并返回被删除的项:
 * </p>
 * <p/>
 * 
 * <pre>
 * Map.Entry removedEntry = listMap.remove(3);
 * </pre>
 * <p>
 * 此外, 它还提供了三个方法: <code>keyList()</code>, <code>valueList()</code>和 <code>entryList()</code>, 用来取得key, value和entry的
 * <code>List</code>. 相对于 <code>Map.keySet()</code>, <code>Map.values()</code> 以及 <code>Map.entrySet()</code>,
 * 后者只提供了取得无序的key和entry的<code>Set</code>, 以及取得value的<code>Collection</code>的方法.
 * </p>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午3:27:22
 * @param <K>
 * @param <V>
 */
public interface ListMap<K, V> extends Map<K, V> {
    /**
     * 返回指定index处的value. 如果index超出范围, 则掷出<code>IndexOutOfBoundsException</code>.
     * 
     * @param index 要返回的value的索引值.
     * @return 指定index处的value对象
     */
    V get(int index);

    /**
     * 返回指定index处的key. 如果index超出范围, 则返回<code>IndexOutOfBoundsException</code>.
     * 
     * @param index 要返回的key的索引值.
     * @return 指定index处的key对象
     */
    K getKey(int index);

    /**
     * 删除指定index处的项. 如果index超出范围, 则返回<code>IndexOutOfBoundsException</code>.
     * 
     * @param index 要删除的项的索引值.
     * @return 被删除的<code>Map.Entry</code>项.
     */
    Map.Entry<K, V> removeEntry(int index);

    /**
     * 返回所有key的<code>List</code>.
     * 
     * @return 所有key的<code>List</code>.
     */
    List<K> keyList();

    /**
     * 返回所有value的<code>List</code>.
     * 
     * @return 所有value的<code>List</code>.
     */
    List<V> valueList();

    /**
     * 返回所有entry的<code>List</code>.
     * 
     * @return 所有entry的<code>List</code>.
     */
    List<Map.Entry<K, V>> entryList();
}
