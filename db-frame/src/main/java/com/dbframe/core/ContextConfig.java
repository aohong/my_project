package com.dbframe.core;

import java.util.HashMap;
import java.util.Map;

import com.dbframe.script.SqlBuilder;
import com.dbframe.script.SqlBuilderProvider;
import com.dbframe.spring.DomainBinding;

/**
 * 环境配置类,已废弃不用
 * 
 * @author leyuanren 2013-7-5 上午10:41:49
 */
@Deprecated
public class ContextConfig {

    /**
     * domain类和mybatis SqlSessionFactory 实例的映射关系
     */
    private Map<String, DomainBinding> domainBindings = new HashMap<String, DomainBinding>();

    /**
     * SqlBuilder生成器
     * 
     * @see SqlBuilder
     */
    private SqlBuilderProvider sqlBuilderProvider;

    public Map<String, DomainBinding> getDomainBindings() {
        return domainBindings;
    }

    public void setDomainBindings(Map<String, DomainBinding> domainBindings) {
        this.domainBindings = domainBindings;
    }

    public SqlBuilderProvider getSqlBuilderProvider() {
        if (sqlBuilderProvider == null) {
            sqlBuilderProvider = new SqlBuilderProvider();
        }
        return sqlBuilderProvider;
    }

    public void setSqlBuilderProvider(SqlBuilderProvider sqlBuilderProvider) {
        this.sqlBuilderProvider = sqlBuilderProvider;
    }

}
