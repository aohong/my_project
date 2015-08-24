package com.dbframe.extend;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import com.dbframe.compiler.SqlCompileEngine;

/**
 * 
 * 整合mybatis的注入点，在mybatis的config文件中添加以下配置以启用该驱动类 
 *  <setting name="defaultScriptingLanguage" value="com.wanda.dbframe.mybatis.ExtendLanguageDriver" />
 * @author leyuanren 2013-7-8 下午7:35:54
 */
public class ExtendLanguageDriver extends XMLLanguageDriver {

    private  SqlCompileEngine sqlCompileEngine;
    
    public ExtendLanguageDriver() {
        sqlCompileEngine = createSqlCompileEngine();
    }
    
    @Override
    public SqlSource createSqlSource(Configuration configuration, XNode script,
            Class<?> parameterType) {
        DynamicSqlSource sqlSource = (DynamicSqlSource) super.createSqlSource(
                configuration, script, parameterType);
        return new ExtendSqlSource(sqlSource,sqlCompileEngine);
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration,
            String script, Class<?> parameterType) {
        DynamicSqlSource sqlSource = (DynamicSqlSource) super.createSqlSource(
                configuration, script, parameterType);
        return new ExtendSqlSource(sqlSource,sqlCompileEngine);
    }
    
    /**
     * 将来可以扩展
     * @return
     */
    protected SqlCompileEngine createSqlCompileEngine(){
        return new SqlCompileEngine();
    }

}
