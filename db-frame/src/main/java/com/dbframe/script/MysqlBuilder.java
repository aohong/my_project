package com.dbframe.script;

import java.util.List;

import com.dbframe.core.DomainMeta;
import com.dbframe.executor.SqlDefinition;
import com.dbframe.model.PageModel;
import com.dbframe.model.SqlFilter;
import com.dbframe.model.SqlSorter;
import com.dbframe.script.DbInnerValue.DbTime;

public class MysqlBuilder extends BaseSqlBuilder {

    private final static String BATCH_VAR = "batchVar";

    private final String batchInsertSqlFormat = "insert %s into %s (%s) values %s";

    private final String pageSelectSqlFormat = "%s limit %d,%d";

    private final DbInnerValue systimeValue = new DbInnerValue("now()");

    @Override
    public SqlDefinition getSelectPageSql(DomainMeta meta, SqlFilter filter,
            SqlSorter sorter, PageModel pageModel, String[] selects) {
        SqlDefinition sqlDefinition = getSelectSql(meta, filter, sorter,
                selects);
        String sql = String.format(pageSelectSqlFormat, sqlDefinition.getSql(),
                pageModel.getOffset(), pageModel.getLimit());
        sqlDefinition.setSql(sql);
        return sqlDefinition;
    }

    @Override
    public SqlDefinition getBatchInsertSql(DomainMeta meta, List<?> entities) {
        return buildBatchInsertSql(meta, entities, false);
    }

    @Override
    public SqlDefinition getIgnoreBatchInsertSql(DomainMeta meta,
            List<?> entities) {
        return buildBatchInsertSql(meta, entities, true);
    }

    private SqlDefinition buildBatchInsertSql(DomainMeta meta,
            List<?> entities, boolean ignoreDuplicate) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entities.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("(");
            boolean isBegin = true;
            for (String property : meta.getAllProperties()) {
                if (!isBegin) {
                    sb.append(",");
                }
                sb.append("#{").append(BATCH_VAR).append("[").append(i)
                        .append("].").append(property).append("}");
                isBegin = false;
            }
            sb.append(")");
        }
        String ignoreToken = ignoreDuplicate ? "ignore" : "";
        String sql = String
                .format(batchInsertSqlFormat, ignoreToken,
                        meta.getFullTableName(), meta.getFullColumnStr(),
                        sb.toString());
        SqlDefinition sqlDefinition = new SqlDefinition();
        sqlDefinition.setSql(sql);
        sqlDefinition.addParam(BATCH_VAR, entities);
        return sqlDefinition;
    }

    @Override
    public SqlDefinition getIgnoreInsertSql(DomainMeta meta, Object entity) {
        SqlDefinition sqlDefinition = getInsertSql(meta, entity);
        String sql = sqlDefinition.getSql();
        int i = sql.indexOf("insert");
        if (i != -1) {
            StringBuilder buffer = new StringBuilder(sql);
            buffer.insert(i + "insert".length(), " ignore ");
            sqlDefinition.setSql(buffer.toString());
        }
        return sqlDefinition;
    }

    @Override
    public boolean isSupportIgnoreInsert() {
        return true;
    }

    @Override
    public boolean isSupportBatchInsert() {
        return true;
    }

    @Override
    public boolean isSupportPageSelect() {
        return true;
    }

    @Override
    public boolean isSupportIgnoreBatchInsert() {
        return true;
    }

    @Override
    protected Object covertSystime(DbTime value) {
        return systimeValue;
    }
}
