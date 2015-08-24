package com.dbframe.support;

import java.util.Map;

import org.apache.commons.lang.ArrayUtils;


public class CommonUtils {
    
    /**
     * mybatis实现的参数map中，如果get操作中的key不存在，会抛异常，所以必须先用containsKey验证
     * @param parameterMap
     * @param key
     * @return
     */
    public static Object getFromMybatisParameter(Map<?,?> parameterMap,String key){
        return parameterMap.containsKey(key) ?  parameterMap.get(key) : null;
    }
    
    public static Object[] toObjectArray(Object array){
        if(array instanceof Object[]){
            return (Object[])array;
        }else if (array instanceof int[]) {
            return ArrayUtils.toObject((int[])array);
        } else if (array instanceof long[]) {
            return ArrayUtils.toObject((long[])array);
        } else if (array instanceof char[]) {
            return ArrayUtils.toObject((char[])array);
        } else if (array instanceof boolean[]) {
            return ArrayUtils.toObject((boolean[])array);
        } else if (array instanceof byte[]) {
            return ArrayUtils.toObject((byte[])array);
        } else if (array instanceof double[]) {
            return ArrayUtils.toObject((double[])array);
        } else if (array instanceof float[]) {
            return ArrayUtils.toObject((float[])array);
        } else if (array instanceof short[]) {
            return ArrayUtils.toObject((short[])array);
        } else{
            return null;
        }
    }
    
    public static int getArrayLength(Object array){
        if(array instanceof Object[]){
            return ((Object[])array).length;
        }else if (array instanceof int[]) {
            return ((int[])array).length;
        } else if (array instanceof long[]) {
            return ((long[])array).length;
        } else if (array instanceof char[]) {
            return ((char[])array).length;
        } else if (array instanceof boolean[]) {
            return ((boolean[])array).length;
        } else if (array instanceof byte[]) {
            return ((byte[])array).length;
        } else if (array instanceof double[]) {
            return ((double[])array).length;
        } else if (array instanceof float[]) {
            return ((float[])array).length;
        } else if (array instanceof short[]) {
            return ((short[])array).length;
        } else{
            return 0;
        }
    }
}
