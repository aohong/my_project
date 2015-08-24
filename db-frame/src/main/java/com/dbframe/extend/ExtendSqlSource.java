package com.dbframe.extend;

import java.lang.reflect.Field;
import java.util.Map;

import com.dbframe.support.Constants;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;

import com.dbframe.compiler.SqlCompileEngine;
import com.dbframe.executor.SqlDefinition;
import com.dbframe.support.CommonUtils;

public class ExtendSqlSource implements SqlSource {

    private Configuration configuration;
    private SqlCompileEngine sqlCompileEngine;
    private SqlNode rootSqlNode;

    public ExtendSqlSource(DynamicSqlSource dynamicSqlSource,SqlCompileEngine sqlCompileEngine) {
        //通过反射获得dynamicSqlSource对象的私有属性
        try {
            Field rootSqlNodeField = DynamicSqlSource.class.getDeclaredField("rootSqlNode");
            rootSqlNodeField.setAccessible(true);
            rootSqlNode = (SqlNode)rootSqlNodeField.get(dynamicSqlSource);
            
            Field configurationField = DynamicSqlSource.class.getDeclaredField("configuration");
            configurationField.setAccessible(true);
            configuration = (Configuration)configurationField.get(dynamicSqlSource);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        this.sqlCompileEngine = sqlCompileEngine;
    }

    @SuppressWarnings("unchecked")
    public BoundSql getBoundSql(Object parameterObject) {
        DynamicContext context = new DynamicContext(configuration, parameterObject);
        rootSqlNode.apply(context);
        String sql = context.getSql();
        
        if(parameterObject instanceof Map){
            Map<String,Object> params = (Map<String,Object>)parameterObject;
            //对SqlDefinition对象处理
            SqlDefinition sqlDefinition = (SqlDefinition) CommonUtils
                    .getFromMybatisParameter(params, Constants.SQLDEFINITION_NAME);
            if(sqlDefinition!=null){
                params.putAll(sqlDefinition.getParams());
            }
            //这里插入对sql语句的扩展处理逻辑
            sql = sqlCompileEngine.apply(sql, params);
        }
        
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
        SqlSource sqlSource = sqlSourceParser.parse(sql, parameterType, context.getBindings());
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
          boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
        }
        return boundSql;
    }

}
