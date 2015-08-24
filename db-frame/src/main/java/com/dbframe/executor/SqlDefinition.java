package com.dbframe.executor;

import java.util.HashMap;

/**
 * 
 * 封装SQL脚本和运行时参数
 * @author leyuanren 2013-7-5 上午10:43:50
 */
public class SqlDefinition {

    private String sql;
    private HashMap<String, Object> params = new HashMap<String, Object>();

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void addParam(String key,Object value){
        params.put(key, value);
    }
    
    public HashMap<String, Object> getParams() {
        return params;
    }
    
}
