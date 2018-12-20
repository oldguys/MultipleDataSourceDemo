package com.oldguy.example.modules.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author ren
 * @date 2018/12/20
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public static String[] getBeanNamesForType(Class<?> clazz ){
		return applicationContext.getBeanNamesForType(clazz);
	}

	public static <T> T getBean(String beanName,Class<T> clazz) {
		return applicationContext.getBean(beanName,clazz);
	}

	public static  <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

}
