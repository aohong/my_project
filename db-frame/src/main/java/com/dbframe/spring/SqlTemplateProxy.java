package com.dbframe.spring;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dbframe.core.SqlTemplateContext;
import org.apache.ibatis.binding.BindingException;

import com.dbframe.core.SqlTemplate;
import com.dbframe.model.SqlFilter;
import com.dbframe.model.SqlSorter;

/**
 * SqlTemplate代理类，spring环境下继承并参数化该类，可以直接DB操作
 * 
 * @author leyuanren 2013-7-3 下午8:50:14
 */
public abstract class SqlTemplateProxy<T> implements SqlTemplateContextAware {

    private SqlTemplate<T> sqlTemplate;

    private Class<T> domainClass;

    public SqlTemplateProxy() {
        domainClass = deduceType();
        if (domainClass == null) {
            throw new RuntimeException("不能从类的继承结构中推断出Domain类的类型！");
        }
    }

    @Override
    public void setSqlTemplateContext(SqlTemplateContext sqlTemplateContext) {
        sqlTemplate = sqlTemplateContext.getSqlTemplate(domainClass);
        if (sqlTemplate == null) {
            throw new BindingException("无法初始化SqlTemplate！ 映射Domain类型："
                    + domainClass.getName());
        }
    }

    /**
     * 默认以ID为空作为新记录的标志
     * 
     * @param entity
     * @return
     */
    public void save(T entity) {
        if (entity != null) {
            if (isNewEntity(entity)) {
                insert(entity);
            } else {
                update(entity);
            }
        }
    }

    protected boolean isNewEntity(T entity) {
        return sqlTemplate.getId(entity) == null;
    }

    public int insert(T entity) {
        return sqlTemplate.insert(entity);
    }

    public int ignoreInsert(T entity) {
        return sqlTemplate.ignoreInsert(entity);
    }

    /**
     * 批量插入数据，mysql支持，oracle不支持
     * 
     * @author aohong 2014-03-07 add
     * @param entities
     * @return
     */
    public int batchInsert(List<T> entities) {
        return sqlTemplate.batchInsert(entities);
    }

    /**
     * 批量插入数据，mysql支持，oracle不支持
     * 
     * @author aohong 2014-03-07 add
     * @param entities
     * @return
     */
    public int batchIgnoreInsert(List<T> entities) {
        return sqlTemplate.batchIgnoreInsert(entities);
    }

    public int update(T entity, String... properties) {
        return sqlTemplate.update(entity, properties);
    }

    public int delete(T entity) {
        return sqlTemplate.delete(entity);
    }

    public int deleteById(Object id) {
        return sqlTemplate.deleteById(id);
    }

    public int count(SqlFilter sqlFilter) {
        return sqlTemplate.count(sqlFilter);
    }

    public T getUniqueById(Object id) {
        return sqlTemplate.getUniqueById(id);
    }

    public T getUnique(String property, Object value) {
        return sqlTemplate.getUnique(SqlFilter.init(property, value));
    }

    public T getUnique(SqlFilter sqlFilter) {
        return sqlTemplate.getUnique(sqlFilter);
    }

    public List<T> queryEntities() {
        return sqlTemplate.queryEntities(null, null);
    }

    public List<T> queryEntities(SqlFilter sqlFilter) {
        return sqlTemplate.queryEntities(sqlFilter, null);
    }

    public List<T> queryEntities(SqlFilter sqlFilter, SqlSorter sqlSorter) {
        return sqlTemplate.queryEntities(sqlFilter, sqlSorter);
    }

    public List<T> pageEntities(SqlFilter sqlFilter, int offset, int limit) {
        return pageEntities(sqlFilter, null, offset, limit);
    }

    public List<T> pageEntities(SqlFilter sqlFilter, SqlSorter sqlSorter,
            int offset, int limit) {
        return sqlTemplate.pageEntities(sqlFilter, sqlSorter, offset, limit);
    }

    public List<Map<String, Object>> queryMaps(SqlFilter sqlFilter,
            SqlSorter sqlSorter, String... selects) {
        return sqlTemplate.queryMaps(sqlFilter, sqlSorter, selects);
    }

    public <E> List<E> querySingleColumn(SqlFilter filter, SqlSorter sorter,
            String select, Class<E> type) {
        return sqlTemplate.querySingleColumn(filter, sorter, select, type);
    }

    public List<Map<String, Object>> pageMaps(SqlFilter sqlFilter,
            SqlSorter sqlSorter, int offset, int limit, String... selects) {
        return sqlTemplate.pageMaps(sqlFilter, sqlSorter, offset, limit,
                selects);
    }

    @SuppressWarnings("unchecked")
    private Class<T> deduceType() {
        Class<?> clazz = this.getClass();
        List<Class<?>> classList = new ArrayList<Class<?>>();
        boolean found = false;
        while (clazz != SqlTemplateProxy.class && !found) {
            classList.add(0, clazz);
            Type type = clazz.getGenericSuperclass();
            if (type instanceof ParameterizedType
                    && ((ParameterizedType) type).getRawType() == SqlTemplateProxy.class) {
                found = true;
            }
            clazz = clazz.getSuperclass();
        }
        if (found) {
            int index = 0;
            for (Class<?> clz : classList) {
                Type type = ((ParameterizedType) clz.getGenericSuperclass())
                        .getActualTypeArguments()[index];
                if (type instanceof Class) {
                    return (Class<T>) type;
                } else {
                    String typeName = ((TypeVariable<?>) type).getName();
                    TypeVariable<?>[] vars = clz.getTypeParameters();
                    for (int i = 0; i < vars.length; i++) {
                        if (vars[i].getName().equals(typeName)) {
                            index = i;
                            break;
                        }
                    }
                }
            }
        }
        return null;
    }

}
