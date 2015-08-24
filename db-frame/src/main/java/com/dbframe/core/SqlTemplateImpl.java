package com.dbframe.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbframe.exception.UnsupportedOperationException;
import com.dbframe.basepo.ShardTable;
import com.dbframe.executor.GeneratedKey;
import com.dbframe.executor.MoreGeneratedKeys;
import com.dbframe.executor.SqlDefinition;
import com.dbframe.executor.SqlExecutor;
import com.dbframe.model.PageModel;
import com.dbframe.model.SqlFilter;
import com.dbframe.model.SqlSorter;
import com.dbframe.script.SqlBuilder;

public class SqlTemplateImpl<T> implements SqlTemplate<T> {

    private SqlExecutor sqlExecutor;
    private Class<T> domainClass;
    private DomainMeta meta;
    private SqlBuilder sqlBuilder;

    public SqlTemplateImpl(Class<T> domainClass, SqlExecutor sqlExecutor,
            SqlBuilder sqlBuilder) {
        this.sqlExecutor = sqlExecutor;
        this.domainClass = domainClass;
        this.meta = DomainMeta.getMeta(domainClass);
        this.sqlBuilder = sqlBuilder;
    }

    @Override
    public Object getId(T entity) {
        if (entity == null) {
            return null;
        }
        return meta.getValue(entity, meta.getIdName());
    }

    @Override
    public int insert(T entity) {
        return doInsert(entity, false);
    }

    @Override
    public int ignoreInsert(T entity) {
        return doInsert(entity, true);
    }

    @Override
    public int batchInsert(List<T> entities) {
        return doBatchInsert(entities, false);
    }

    @Override
    public int batchIgnoreInsert(List<T> entities) {
        return doBatchInsert(entities, true);
    }

    @Override
    public int update(T entity, String... updates) {
        if (entity == null) {
            return 0;
        }

        // 如果分表，表名添加后缀
        if (entity instanceof ShardTable) {
            ShardTable splitTable = (ShardTable) entity;
            meta.setShardTableSuffix(splitTable.shardTableSuffix());
        }

        SqlDefinition sqlDefinition = sqlBuilder.getUpdateSql(meta, entity,
                updates);

        // 清掉分表后缀
        if (entity instanceof ShardTable) {
            meta.setShardTableSuffix(null);
        }

        return sqlExecutor.update(sqlDefinition);
    }

    @Override
    public int delete(T entity) {
        if (entity == null) {
            return 0;
        }

        // 如果分表，表名添加后缀
        if (entity instanceof ShardTable) {
            ShardTable splitTable = (ShardTable) entity;
            meta.setShardTableSuffix(splitTable.shardTableSuffix());
        }

        Object idValue = meta.getValue(entity, meta.getIdName());

        return deleteById(idValue);
    }

    @Override
    public int deleteById(Object id) {
        SqlDefinition sqlDefinition = sqlBuilder.getDeleteSql(meta, id);

        // 清掉分表后缀
        meta.setShardTableSuffix(null);

        return sqlExecutor.delete(sqlDefinition);
    }

    @Override
    public int count(SqlFilter filter) {
        // 如果分表，表名添加后缀
        if (ShardTable.class.isAssignableFrom(domainClass)) {
            try {
                ShardTable splitTable = (ShardTable) domainClass.newInstance();
                meta.setShardTableSuffix(splitTable.shardTableSuffix(filter));
			} catch (InstantiationException e) {
				throw new RuntimeException("domainClass.newInstance() fail", e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("domainClass.newInstance() fail", e);
            }
        }

        SqlDefinition sqlDefinition = sqlBuilder.getCountSql(meta, filter);

        // 清掉分表后缀
        if (ShardTable.class.isAssignableFrom(domainClass)) {
            meta.setShardTableSuffix(null);
        }
        return sqlExecutor.queryCount(sqlDefinition);
    }

    @Override
    public T getUniqueById(Object id) {
        return getUnique(SqlFilter.init(meta.getIdName(), id));
    }

    @Override
    public T getUnique(SqlFilter filter) {
        List<Map<String, Object>> list = doQueryMap(filter, null, null, null);
        if (list.isEmpty()) {
            return null;
        } else {
            return this.coverToBean((Map<String, Object>) list.get(0));
        }
    }

    @Override
    public List<T> queryEntities(SqlFilter filter, SqlSorter sorter) {
        List<Map<String, Object>> list = queryMaps(filter, sorter);
        List<T> resultList = new ArrayList<T>(list.size());
        for (Map<String, Object> resultMap : list) {
            resultList.add(coverToBean(resultMap));
        }
        return resultList;
    }

    @Override
    public List<T> pageEntities(SqlFilter filter, SqlSorter sorter, int offset,
            int limit) {
        List<Map<String, Object>> list = pageMaps(filter, sorter, offset, limit);
        List<T> resultList = new ArrayList<T>(list.size());
        for (Map<String, Object> resultMap : list) {
            resultList.add(coverToBean(resultMap));
        }
        return resultList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> querySingleColumn(SqlFilter filter, SqlSorter sorter,
            String select, Class<E> type) {
        List<Map<String, Object>> result = queryMaps(filter, sorter, select);
        List<E> list = new ArrayList<E>();
        for (Map<String, Object> map : result) {
            E val = null;
            if (map != null) {
                val = (E) map.get(select);
            }
            list.add(val);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryMaps(SqlFilter filter,
            SqlSorter sorter, String... selects) {
        return doQueryMap(filter, sorter, null, selects);
    }

    @Override
    public List<Map<String, Object>> pageMaps(SqlFilter filter,
            SqlSorter sorter, int offset, int limit, String... selects) {
        PageModel pm = new PageModel(offset, limit);
        return doQueryMap(filter, sorter, pm, selects);
    }

    private List<Map<String, Object>> doQueryMap(SqlFilter filter,
            SqlSorter sorter, PageModel pageModel, String[] selects) {
        if (pageModel != null && !sqlBuilder.isSupportPageSelect()) {
            throw new com.dbframe.exception.UnsupportedOperationException(
                    "page select operation is unsupported!");
        }

        // 如果分表，表名添加后缀
        if (ShardTable.class.isAssignableFrom(domainClass)) {
            try {
                ShardTable splitTable = (ShardTable) domainClass.newInstance();
                meta.setShardTableSuffix(splitTable.shardTableSuffix(filter));
            } catch (InstantiationException e) {
                throw new RuntimeException("domainClass.newInstance() fail", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("domainClass.newInstance() fail", e);
            }
        }

        SqlDefinition sqlDefinition;
        if (pageModel != null) {
            sqlDefinition = sqlBuilder.getSelectPageSql(meta, filter, sorter,
                    pageModel, selects);
        } else {
            sqlDefinition = sqlBuilder.getSelectSql(meta, filter, sorter,
                    selects);
        }

        // 清掉分表后缀
        if (ShardTable.class.isAssignableFrom(domainClass)) {
            meta.setShardTableSuffix(null);
        }

        List<Map<String, Object>> resultList = sqlExecutor
                .queryEntities(sqlDefinition);
        String[] resultProps;
        if (selects == null || selects.length == 0) {
            resultProps = meta.getAllProperties();
        } else {
            resultProps = selects;
        }
        for (Map<String, Object> result : resultList) {
            if (result == null) continue;

            Map<String, String> lowerKeys = new HashMap<String, String>();
            for (String key : result.keySet()) {
                lowerKeys.put(key.toLowerCase(), key);
            }
            for (String prop : resultProps) {
                String column = meta.propertyToColumn(prop);
                if (column != null) {
                    String key = lowerKeys.get(column);
                    if (key != null) {
                        result.put(prop, result.remove(key));
                    }
                }
            }
        }
        return resultList;
    }

    private int doInsert(T entity, boolean ignore) {
        if (entity == null) {
            return 0;
        }
        if (ignore && !sqlBuilder.isSupportIgnoreInsert()) {
            throw new UnsupportedOperationException(
                    "ignore insert operation is unsupported!");
        }

        GeneratedKey generateKey = null;
        if (getId(entity) == null) {
            if (meta.getSelectStatement() != null && meta.isSelectBefore()) {
                Object returnKey = sqlExecutor.queryKey(meta
                        .getSelectStatement());
                meta.setValue(entity, meta.getIdName(), returnKey);
            } else {
                generateKey = new GeneratedKey(meta.getSelectStatement());
            }
        }

        // 如果分表，表名添加后缀
        if (entity instanceof ShardTable) {
            ShardTable splitTable = (ShardTable) entity;
            meta.setShardTableSuffix(splitTable.shardTableSuffix());
        }

        SqlDefinition sqlDefinition = null;
        if (ignore) {
            sqlDefinition = sqlBuilder.getIgnoreInsertSql(meta, entity);
        } else {
            sqlDefinition = sqlBuilder.getInsertSql(meta, entity);
        }

        // 清掉分表后缀
        if (entity instanceof ShardTable) {
            meta.setShardTableSuffix(null);
        }

        int n = sqlExecutor.insert(sqlDefinition, generateKey);
        if (n > 0 && generateKey != null) {
            Object returnKey = generateKey.getKey();
            meta.setValue(entity, meta.getIdName(), returnKey);
        }
        return n;
    }

    private int doBatchInsert(List<T> entities, boolean ignore) {
        if (entities == null || entities.isEmpty()) {
            return 0;
        }
        if (ignore && !sqlBuilder.isSupportIgnoreBatchInsert()) {
            throw new UnsupportedOperationException(
                    "ignore batch insert operation is unsupported!");
        }
        if (!ignore && !sqlBuilder.isSupportBatchInsert()) {
            throw new UnsupportedOperationException(
                    "batch insert operation is unsupported!");
        }
        if (meta.getSelectStatement() != null && meta.isSelectBefore()) {
            for (T entity : entities) {
                if (getId(entity) == null) {
                    Object returnKey = sqlExecutor.queryKey(meta
                            .getSelectStatement());
                    meta.setValue(entity, meta.getIdName(), returnKey);
                }
            }
        }

        MoreGeneratedKeys generatedKeys = null;
        List<T> noIdEntities = new ArrayList<T>();
        if (meta.getSelectStatement() == null) {
            for (T entity : entities) {
                if (getId(entity) == null) {
                    noIdEntities.add(entity);
                }
            }
            generatedKeys = new MoreGeneratedKeys(noIdEntities.size());
        }
        // 如果分表，表名添加后缀
        T entity = entities.get(0);
        if (entity instanceof ShardTable) {
            ShardTable splitTable = (ShardTable) entity;
            meta.setShardTableSuffix(splitTable.shardTableSuffix());
        }

        SqlDefinition sqlDefinition = null;
        if (ignore) {
            sqlDefinition = sqlBuilder.getIgnoreBatchInsertSql(meta, entities);
        } else {
            sqlDefinition = sqlBuilder.getBatchInsertSql(meta, entities);
        }

        // 清掉分表后缀
        if (entity instanceof ShardTable) {
            meta.setShardTableSuffix(null);
        }

        int total = sqlExecutor.insert(sqlDefinition, generatedKeys);
        if (generatedKeys != null) {
            for (int i = 0; i < generatedKeys.getKeys().size(); i++) {
                Object key = generatedKeys.getKeys().get(i);
                if (key != null) {
                    meta.setValue(noIdEntities.get(i), meta.getIdName(), key);
                }
            }
        }
        return total;
    }

    private T coverToBean(Map<String, Object> resultMap) {
        try {
            T bean = domainClass.newInstance();
            for (String property : resultMap.keySet()) {
                meta.setValue(bean, property, resultMap.get(property));
            }
            return bean;
        } catch (Exception e) {
            throw new RuntimeException("不能创建Domain对象! Domain class:"
                    + domainClass.getName(), e);
        }
    }

}
