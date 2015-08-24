package com.dbframe.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * 声明domain类属性映射到数据库字段时是否转换成小写下划线风格，
 * 默认为true
 * @author leyuanren 2013-7-5 下午3:54:13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MapToUnderscore {
    
    boolean value() default true;
}
