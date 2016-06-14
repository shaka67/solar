package com.ouer.solar.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext contex) throws BeansException {
		context = contex;
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static Object getBean(String beanName){
		return context.getBean(beanName);
	}

	public static <T> T getBean(Class<T> requiredType){
		return context.getBean(requiredType);
	}

	public static <T> T getBean(String beanName, Class<T> requiredType){
		return context.getBean(beanName, requiredType);
	}

	public static String getProfile() {
		return context.getEnvironment().getActiveProfiles()[0];
	}
}

