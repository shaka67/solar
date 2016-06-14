/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.rabbitmq.nnd;

import java.io.IOException;
import java.util.Collection;

import org.springframework.util.ClassUtils;

import com.ouer.solar.protocol.JsonObjectConverter;
import com.ouer.solar.protocol.ObjectConverter;

/**
 * A Jackson base {@link NoDirectDependentCarrierConverter} on convert a {@link NoDirectDependentCarrier} 
 * or reverse a NDDM in {@link NoDirectDependentCarrier}, please refer to {@link NoDirectDependentCarrier} for more information
 * 
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JsonNDDCarrierConverter implements NoDirectDependentCarrierConverter {

	private final ObjectConverter nddCarrierConverter;
	
	public JsonNDDCarrierConverter() {
		nddCarrierConverter = new JsonObjectConverter();
	}
	
	@Override
	public NoDirectDependentCarrier convert(NoDirectDependentCarrier source) throws IOException {
		final Object[] args = source.getNoDirectDependentMsgs();
		if (args == null) {
			return source;
		}
		boolean needTransform = false;
		Class<?> clazz;
		for (final Object arg : args) {
			clazz = arg.getClass();
			if (clazz.isPrimitive()
					|| ClassUtils.isPrimitiveArray(clazz)) {
				continue;
			}
			if (ClassUtils.isAssignable(Collection.class, clazz)) {
				final Collection<?> collection = (Collection<?>) arg;
				if (collection.size() == 0) {
					continue;
				}
				if (collection.getClass().getPackage().getName().startsWith("java.") 
						|| collection.getClass().getPackage().getName().startsWith("javax.")) {
					continue;
				} 
				needTransform = true;
				break;
			}
			if (clazz.getPackage().getName().startsWith("java.") 
					|| clazz.getPackage().getName().startsWith("javax.")) {
				continue;
			}
			needTransform = true;
			break;
		}
		if (!needTransform) {
			return source;
		}
		
		final NoDirectDependentCarrier copy = source.clone();
		final Object[] transformArgs = new Object[args.length * 2 + 1];
		transformArgs[0] = NoDirectDependentCarrier.class;
		for (int i = 0; i < args.length; i++) {
			transformArgs[i * 2 + 1] =  args[i].getClass().getName();
			transformArgs[i * 2 + 2] = nddCarrierConverter.toString(args[i]);
		}
		copy.setNoDirectDependentMsgs(transformArgs);
		return copy;
	}

	@Override
	public Object[] reverse(Object[] source) throws Exception {
		final Object[] arguments = new Object[(source.length - 1) / 2];
		int j = 0;
		for (int i = 1; i < source.length; i = i + 2) {
			arguments[j] = nddCarrierConverter.fromString((String) source[i + 1], 
						Class.forName((String) source[i]));
			j++;
		}
		return arguments;
	}

}
