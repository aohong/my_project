package com.dbframe.script;

import java.util.List;

import com.dbframe.core.DomainMeta;
import com.dbframe.model.SqlFilter;
import org.apache.commons.lang.StringUtils;

import com.dbframe.executor.SqlDefinition;
import com.dbframe.model.PageModel;
import com.dbframe.model.SqlSorter;

public class OracleBuilder extends BaseSqlBuilder {

    private final String pageSelectSqlFormat = "select %s from (select t.*,rownum as rn from (%s) t where rownum<=%d) where rn>%d";

    private final DbInnerValue systimeValue = new DbInnerValue("systime");

    @Override
    public SqlDefinition getSelectPageSql(DomainMeta meta, SqlFilter filter, SqlSorter sorter,
            PageModel pageModel, String[] selects) {
        SqlDefinition sqlDefinition = getSelectSql(meta, filter, sorter, selects);
        String baseSql = sqlDefinition.getSql();
        String selectStr = StringUtils.substringBetween(baseSql, "select", "from");
        String sql = String.format(pageSelectSqlFormat, selectStr, baseSql, pageModel.getLimit(),
                pageModel.getOffset());
        sqlDefinition.setSql(sql);
        return sqlDefinition;
    }

    @Override
    public SqlDefinition getBatchInsertSql(DomainMeta meta, List<?> entities) {
        return null;
    }

    @Override
    public SqlDefinition getIgnoreBatchInsertSql(DomainMeta meta, List<?> entities) {
        return null;
    }

    @Override
    public SqlDefinition getIgnoreInsertSql(DomainMeta meta, Object entity) {
        return null;
    }

    @Override
    public boolean isSupportIgnoreInsert() {
        return false;
    }

    @Override
    public boolean isSupportBatchInsert() {
        return false;
    }

    @Override
    public boolean isSupportPageSelect() {
        return true;
    }

    @Override
    public boolean isSupportIgnoreBatchInsert() {
        return false;
    }

    @Override
    protected Object covertSystime(DbInnerValue.DbTime value) {
        return systimeValue;
    }

}
