package com.dbframe.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbframe.core.SqlTemplateContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 在spring配置中注册，初始化SqlTemplateContext
 * 
 * @author leyuanren 2013-7-5 下午3:45:00
 */
public class SqlTemplateRegisterBean implements InitializingBean,
        BeanPostProcessor, ApplicationContextAware {

    /**
     * 每个spring context环境下拥有一个SqlTemplateContext
     */
    private static Map<ApplicationContext, SqlTemplateContext> sqlTemplateContextCache = new HashMap<ApplicationContext, SqlTemplateContext>();

    /**
     * 配置需要绑定的Domain类
     */
    private List<DomainBinding> domainBingings = new ArrayList<DomainBinding>();

    private SqlTemplateContext sqlTemplateContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sqlTemplateContext.addDomainBindings(domainBingings);
    }

    /**
     * 把sqlTemplateContext注入实现了SqlTemplateContextAware的bean
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof SqlTemplateContextAware) {
            SqlTemplateContextAware aware = (SqlTemplateContextAware) bean;
            aware.setSqlTemplateContext(sqlTemplateContext);
        }
        return bean;
    }

    public void setDomainBingings(List<DomainBinding> domainBingings) {
        this.domainBingings = domainBingings;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        sqlTemplateContext = sqlTemplateContextCache.get(applicationContext);
        if (sqlTemplateContext == null) {
            sqlTemplateContext = new SqlTemplateContext();
            sqlTemplateContextCache.put(applicationContext, sqlTemplateContext);
        }
    }
}
