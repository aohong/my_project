package com.dbframe.support;

import com.dbframe.enums.DBType;

/**
 * 根据数据库名称、关键字返回不同的数据库类型枚举
 * @author leyuanren 2013-7-3 下午2:48:27
 */
public class DialectHelper {

    public static DBType getDBType(String databaseName){
        if(databaseName!=null){
            String name= databaseName.toLowerCase();
            for(DBType dbType : DBType.values()){
                String key = dbType.key().toLowerCase();
                if(name.contains(key)){
                    return dbType;
                }
            }
            
        }
        return null;
    }
    
}
