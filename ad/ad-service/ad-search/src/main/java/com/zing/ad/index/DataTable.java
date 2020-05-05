package com.zing.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Zing
 * @date 2019-11-25
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;

    public static final Map<Class, Object> dataTableMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    public static <T> T of(Class<T> clazz) {
        T instance = (T) dataTableMap.get(clazz);
        if (instance != null) {
            return instance;
        }

        dataTableMap.put(clazz, bean(clazz));

        return (T) dataTableMap.get(clazz);
    }

    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    private static <T> T bean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}