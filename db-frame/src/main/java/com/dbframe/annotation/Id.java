package com.dbframe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否是主键，不支持联合主键。请保证只有一个属性是主键,如果一个domain类里设置了多个主键，只取第一个。
 * @author leyuanren
 * @return
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
	
    //插入操作中获得记录主键值的SQL语句
    String select() default "";

    boolean before() default true;
    
}
