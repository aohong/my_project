package com.dbframe.script;

import java.util.List;

import com.dbframe.core.DomainMeta;
import com.dbframe.model.SqlFilter;
import com.dbframe.executor.SqlDefinition;
import com.dbframe.model.PageModel;
import com.dbframe.model.SqlSorter;

public class SimpleSqlBuilder extends BaseSqlBuilder{
    

    @Override
    public boolean isSupportIgnoreInsert() {
        return false;
    }

    @Override
    public boolean isSupportBatchInsert() {
        return false;
    }

    @Override
    public boolean isSupportIgnoreBatchInsert() {
        return false;
    }

    @Override
    public boolean isSupportPageSelect() {
        return false;
    }

    @Override
    public SqlDefinition getIgnoreInsertSql(DomainMeta meta, Object entity) {
        return null;
    }

    @Override
    public SqlDefinition getBatchInsertSql(DomainMeta meta,
            List<?> entities) {
        return null;
    }

    @Override
    public SqlDefinition getIgnoreBatchInsertSql(DomainMeta meta,
            List<?> entities) {
        return null;
    }

    @Override
    public SqlDefinition getSelectPageSql(DomainMeta meta, SqlFilter filter,
            SqlSorter sorter, PageModel pageModel, String[] selects) {
        return null;
    }
    
    

}
