package com.dbframe.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SqlCompileEngine  {

	private List<SqlCompiler> compilerChain;
	
	public SqlCompileEngine() {
	    compilerChain = new ArrayList<SqlCompiler>();
	    compilerChain.add(new WrapTokenCompiler());
    }
	
	public String apply(String sql,Map<String, Object> parametersAsMap){
	    for(SqlCompiler compiler : compilerChain){
	        sql =  compiler.apply(sql, parametersAsMap);
	    }
	    return sql;
	}
	
	public void addCompiler(SqlCompiler c) {
         compilerChain.add(c);
    }
}
