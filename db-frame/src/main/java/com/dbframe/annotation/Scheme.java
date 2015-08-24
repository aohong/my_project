package com.dbframe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 声明domain类的scheme
 * 
 * @author leyuanren
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scheme {

	/**
	 * 映射的scheme名
	 * @return
	 */
	String name();
}
