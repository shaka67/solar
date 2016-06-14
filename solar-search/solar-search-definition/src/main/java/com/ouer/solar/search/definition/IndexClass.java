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

public class IndexClass {

	private final int id;
	private final String index; // like db
    private final String type;  // like table
    private final String name;
    private final boolean preIndex;
    private String esId;

    private final Map<String, IndexField> fields = Maps.newHashMap();

    public IndexClass(int id, String index, String type, String name, boolean preIndex) {
        this.id = id;
        this.index = index;
        this.type = type;
        this.name = name;
        this.preIndex = preIndex;
    }

    public int getId() {
		return id;
	}

	public String getIndex() {
		return index;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public boolean isPreIndex() {
		return preIndex;
	}

	public String getEsId() {
		return esId;
	}

	public void setEsId(String esId) {
		this.esId = esId;
	}

	public Map<String, IndexField> getFields() {
        return fields;
    }

    public IndexField getField(String name) {
        return fields.get(name);
    }

    void addField(IndexField field) throws DefinitionException {
        if (fields.containsKey(field.getName())) {
            throw new DefinitionException("duplicated field : " + field.getName() + " in index class: [" + id + "]");
        }
        fields.put(field.getName(), field);
    }
}
