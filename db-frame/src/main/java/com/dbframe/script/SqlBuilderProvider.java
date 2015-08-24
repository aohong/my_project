package com.dbframe.script;

import com.dbframe.support.DialectHelper;
import org.apache.ibatis.session.SqlSessionFactory;

import com.dbframe.enums.DBType;

public class SqlBuilderProvider{

    public SqlBuilder getSqlBuilder(SqlSessionFactory factory) {
        String dbName = factory.getConfiguration().getDatabaseId();
        DBType type = DialectHelper.getDBType(dbName);
        if(type!=null){
            if(type==DBType.MySql){
                return new MysqlBuilder();
            }else if(type==DBType.Oracle){
                return new OracleBuilder();
            }
        }
        SqlBuilder sqlBuilder = provideSqlBuilder();
        if(sqlBuilder == null){
            sqlBuilder = new SimpleSqlBuilder();
        }
        return sqlBuilder;
    }
    
    protected SqlBuilder provideSqlBuilder(){
        return null;
    }
}
