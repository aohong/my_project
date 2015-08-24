package com.dbframe.spring;

import com.dbframe.script.SqlBuilder;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 
 * 封装domain绑定信息 
 * @author leyuanren 2013-7-12 下午3:20:13
 */
public class DomainBinding {

    /**
     * 要绑定的domain类的包路径，也可以是具体的domain类名,多个用','分隔
     * 如 com.wanda.trade.po,com.wanda.settle.po,com.wanda.test.po.TestPO
     */
    private String domainPackages;
    
    /**
     * 绑定的Mybatis SqlSessionFactory实例
     */
    private SqlSessionFactory sqlSessionFactory;
    
    /**
     * 绑定的SqlBuilder（可选）
     */
    private SqlBuilder sqlBuilder;
    
    public String getDomainPackages() {
        return domainPackages;
    }
    public void setDomainPackages(String domainPackages) {
        this.domainPackages = domainPackages;
    }
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    public SqlBuilder getSqlBuilder() {
        return sqlBuilder;
    }
    public void setSqlBuilder(SqlBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }
}
