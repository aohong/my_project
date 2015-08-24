package com.dbframe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 默认domain类的所有getXxx()方法都会被认为是domain的一个名为xxx的属性， 
 * 通过该注解忽略被标记的getXxx()方法。
 * @author yuanren.le
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignore {
	
}
