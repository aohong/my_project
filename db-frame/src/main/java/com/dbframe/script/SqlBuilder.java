package com.dbframe.script;

import java.util.List;

import com.dbframe.core.DomainMeta;
import com.dbframe.model.SqlFilter;
import com.dbframe.executor.SqlDefinition;
import com.dbframe.model.PageModel;
import com.dbframe.model.SqlSorter;

public interface SqlBuilder {
    
    SqlDefinition getInsertSql(DomainMeta meta,Object entity);
    
    SqlDefinition getUpdateSql(DomainMeta meta,Object entity,String[] updates);
    
    SqlDefinition getDeleteSql(DomainMeta meta,Object entity);
    
    SqlDefinition getCountSql(DomainMeta meta,SqlFilter filter);
    
    SqlDefinition getSelectSql(DomainMeta meta,SqlFilter filter,SqlSorter sorter,String[] selects);

    boolean isSupportIgnoreInsert();

    boolean isSupportBatchInsert();

    boolean isSupportIgnoreBatchInsert();

    boolean isSupportPageSelect();

    // 插入同时忽略主键重复
    SqlDefinition getIgnoreInsertSql(DomainMeta meta,Object entity);

    // 批量插入
    SqlDefinition getBatchInsertSql(DomainMeta meta,List<?> entities);

    // 批量插入同时忽略主键重复
    SqlDefinition getIgnoreBatchInsertSql(DomainMeta meta,List<?> entities);

    // 分页查询
    SqlDefinition getSelectPageSql(DomainMeta meta,SqlFilter filter, SqlSorter sorter,
            PageModel pageModel, String[] selects);

}
