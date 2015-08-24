package com.dbframe.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.dbframe.extend.ExtendStatementHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import com.dbframe.executor.SqlExecutor;
import com.dbframe.extend.ExtendKeyGenerator;
import com.dbframe.script.SqlBuilder;
import com.dbframe.script.SqlBuilderProvider;
import com.dbframe.spring.DomainBinding;

/**
 * SqlTemplate上下文环境类，从这里获取SqlTemplate
 * 
 * @author leyuanren 2013-7-3 下午8:40:59
 */
public class SqlTemplateContext {

    private Map<Class<?>, SqlTemplate<?>> templateCache = new HashMap<Class<?>, SqlTemplate<?>>();

    private final static Class<SqlExecutor> executorClass = SqlExecutor.class;

    private final static String INSERT_STATEMENT_ID = executorClass.getName()
            + ".insert";

    /**
     * domain类和mybatis SqlSessionFactory 实例的映射关系
     */
    private Map<String, DomainBinding> domainBindings = new HashMap<String, DomainBinding>();

    /**
     * SqlBuilder生成器
     * 
     * @see SqlBuilder
     */
    private SqlBuilderProvider sqlBuilderProvider = new SqlBuilderProvider();

    private static Set<Configuration> pluginedConfigurationSet = new HashSet<Configuration>();

    public void addDomainBindings(List<DomainBinding> bindings) {
        // 防止同一个Configuration实例被多次plug
        for (DomainBinding domainBinding : bindings) {
            final Configuration configuration = domainBinding
                    .getSqlSessionFactory().getConfiguration();
            if (!pluginedConfigurationSet.contains(configuration)) {
                plugMybatisConfiguration(configuration);
                pluginedConfigurationSet.add(configuration);
            }
            String domainPackages = domainBinding.getDomainPackages();
            if (StringUtils.isNotBlank(domainPackages)
                    && domainBinding.getSqlSessionFactory() != null) {
                String[] packageArray = domainPackages.split(",");
                for (String domainPackage : packageArray) {
                    if (StringUtils.isNotBlank(domainPackage)) {
                        domainBindings.put(domainPackage, domainBinding);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> SqlTemplate<T> getSqlTemplate(Class<T> domainClass) {
        SqlTemplate<T> sqlTemplate = (SqlTemplate<T>) templateCache
                .get(domainClass);
        if (sqlTemplate == null) {
            String name = domainClass.getName();
            DomainBinding binding = getDomainBindingByDirectory(name);
            if (binding == null || binding.getSqlSessionFactory() == null) {
                // domain未绑定factory
                return null;
            }
            SqlSessionFactory factory = binding.getSqlSessionFactory();
            Configuration configuration = factory.getConfiguration();
            SqlExecutor dao = configuration.getMapper(executorClass,
                    new SqlSessionTemplate(factory));

            SqlBuilder sqlBuilder = binding.getSqlBuilder();
            if (sqlBuilder == null) {
                sqlBuilder = sqlBuilderProvider.getSqlBuilder(factory);
            }
            sqlTemplate = new SqlTemplateImpl<T>(domainClass, dao, sqlBuilder);
            templateCache.put(domainClass, sqlTemplate);
        }
        return sqlTemplate;
    }

    private void plugMybatisConfiguration(final Configuration configuration) {
        // 添加拦截器扩展;
        configuration.addInterceptor(new Interceptor() {
            @Override
            public void setProperties(Properties properties) {
            }

            @Override
            public Object plugin(Object target) {
                if (!(target instanceof ExtendStatementHandler)
                        && (target instanceof StatementHandler)) {
                    return new ExtendStatementHandler(
                            (StatementHandler) target, configuration);
                }
                return target;
            }

            @Override
            public Object intercept(Invocation invocation) throws Throwable {
                return null;
            }
        });
        // 注册SqlExecutor
        if (!configuration.hasMapper(executorClass)) {
            configuration.addMapper(executorClass);

            // 替换insert语句默认的KeyGenerator
            MappedStatement ms = configuration
                    .getMappedStatement(INSERT_STATEMENT_ID);
            Field keyGeneratorField;
            try {
                ExtendKeyGenerator newKeyGen = new ExtendKeyGenerator(
                        (SelectKeyGenerator) ms.getKeyGenerator());
                keyGeneratorField = MappedStatement.class
                        .getDeclaredField("keyGenerator");
                keyGeneratorField.setAccessible(true);
                keyGeneratorField.set(ms, newKeyGen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private DomainBinding getDomainBindingByDirectory(String catalog) {
        String tempCatalog = catalog;
        DomainBinding binding;
        while (tempCatalog != null && tempCatalog.length() > 0) {
            binding = domainBindings.get(tempCatalog);
            if (binding != null) {
                return binding;
            }
            int index = tempCatalog.lastIndexOf('.');
            if (index == -1) {
                tempCatalog = null;
            } else {
                tempCatalog = tempCatalog.substring(0, index);
            }
        }
        return null;
    }
}
