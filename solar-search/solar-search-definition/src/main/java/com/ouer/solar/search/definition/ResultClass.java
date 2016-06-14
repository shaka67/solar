/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.definition;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ResultClass {

	private final int id;
	private final int indexId;
    private final String name;
    private final Map<String, ResultField> fields = Maps.newHashMap();

    public ResultClass(int id, int indexId, String name) {
    	this.id = id;
    	this.indexId = indexId;
    	this.name = name;
    }

	public int getId() {
		return id;
	}

	public int getIndexId() {
		return indexId;
	}

	public String getName() {
		return name;
	}

	public Map<String, ResultField> getFields() {
        return fields;
    }

    public ResultField getField(String name) {
        return fields.get(name);
    }

    void addField(ResultField field) throws DefinitionException {
        if (fields.containsKey(field.getName())) {
            throw new DefinitionException("duplicated field : " + field.getName() + " in result class: [" + id + "]");
        }
        fields.put(field.getName(), field);
    }
}
