/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

import java.io.Serializable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ScoreField implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final String name;
	private final float factor;
	private final String modifier;

	public ScoreField(String name, float factor, String modifier) {
		this.name = name;
		this.factor = factor;
		this.modifier = modifier;
	}

	public String getName() {
		return name;
	}

	public float getFactor() {
		return factor;
	}

	public String getModifier() {
		return modifier;
	}

}
