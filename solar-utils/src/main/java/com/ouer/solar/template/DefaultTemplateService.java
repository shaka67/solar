/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.reflect.FieldUtils;

import com.google.common.collect.Maps;
import com.samskivert.mustache.Mustache;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DefaultTemplateService implements FullTemplateService {

	private final TemplateLoader tplLoader;
	private final TemplateContextLoader cxtLoader;
	private final TemplateClassRegister regsiter;
	private final Map<String, String> templates;
	private final Map<String, Map<String, Object>> ctxs;

	public DefaultTemplateService(TemplateLoader tplLoader,
							 TemplateContextLoader cxtLoader,
							 TemplateClassRegister regsiter,
							 Set<TemplateResource> resources) {
		this.tplLoader = tplLoader;
		this.cxtLoader = cxtLoader;
		this.regsiter = regsiter;
		this.templates = Maps.newHashMap();
		ctxs = Maps.newHashMap();
		initializeTemplate(resources);
	}

	private void initializeTemplate(Set<TemplateResource> resources) {
		String content;
		Map<String, Object> ctx;
		for (final TemplateResource resource : resources) {
			content = tplLoader.loadTemplate(resource.getTplPath());
			if (content == null) {
	            continue;
	        }

			if (resource.getCtxPath() != null) {
				ctx = cxtLoader.getContext(resource.getCtxPath());
		        if (ctx != null) {
		        	ctxs.put(resource.getTplPath(), ctx);
		        }
			}

	        templates.put(resource.getTplPath(), content);
		}
	}

	@Override
    public String loadTemplate(String tplPath) {
        return tplLoader.loadTemplate(tplPath);
    }

    @Override
    public String getContent(String tplPath) {
        return Mustache.compiler().compile(templates.get(tplPath)).execute(ctxs.get(tplPath));
    }

	@Override
    public String getDynamicContent(String tplPath, Object... objects) throws IllegalAccessException {
		if (objects == null || objects.length == 0) {
		    return templates.get(tplPath);
        }

		final Map<String, Object> ctx = Maps.newHashMap();
		if (ctxs.containsKey(tplPath)) {
			ctx.putAll(ctxs.get(tplPath));
		}

        Collection<Field> fields;
        String key;
        Object value;
		for (final Object object : objects) {
		    if (!regsiter.isTemplateClass(object.getClass())) {
	            continue;
	        }

		    fields = regsiter.getTemplateFields(object.getClass());
		    for (final Field field : fields) {
	            key = regsiter.getFieldVariable(field);
	            value = FieldUtils.readField(field, object, true);
	            ctx.put(key, value);
	        }
		}

		return Mustache.compiler().compile(templates.get(tplPath)).execute(ctx);
	}

}
