package com.dbframe.compiler;

import java.util.Map;

/**
 * sql语句编译器接口
 * @author leyuanren 2013-7-3 上午11:01:51
 */
public interface SqlCompiler {
    
	String apply(String sql,Map<String, Object> parametersAsMap);
	
}
