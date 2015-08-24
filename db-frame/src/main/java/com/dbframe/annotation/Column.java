package com.dbframe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于domain类的get方法上，指定domain属性与数据库字段的映射关系
 * 不加该注解则使用自动映射规则
 * @author leyuanren
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

	/**
	 * domain属性对应的数据库字段名
	 * @return
	 */
	String name() default "";
	
	/**
	 * insert操作时如果值为null是否忽略
	 * @return
	 */
	boolean ignoreNull() default true;
	
	
}
