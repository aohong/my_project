package com.dbframe.script;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dbframe.core.DomainMeta;
import com.dbframe.executor.SqlDefinition;
import com.dbframe.model.FilterProperty;
import com.dbframe.model.SortProperty;
import com.dbframe.model.SqlFilter;
import com.dbframe.model.SqlSorter;
import com.dbframe.support.Constants;

/**
 * 基本SQL生成器，不支持批量插入、忽略重复插入、分页查询等跟具体数据库语法相关的操作
 * 
 * @author leyuanren 2013-7-4 下午4:00:36
 */
public abstract class BaseSqlBuilder implements SqlBuilder {

    private final static Pattern sumPat = Pattern
            .compile("^sum\\(([_a-zA-Z0-9]+)\\)$");

    private final static Pattern countPat = Pattern
            .compile("^count\\((\\s*distinct\\s*)?([_a-zA-Z0-9]+)\\)$");

    private final static String INSERT_VARS_NAME = "_insert_var_";
    private final static String FILTER_VAR_NAME = "filter";
    private final static String SORTER_VAR_NAME = "sorter";
    private final static String ID_VAR_NAME = "idValue";
    private final static String VERSION_VAR_NAME = "versionValue";

    private final String insertSqlFormat = "insert into %s (%s) values (#wrap{:"
            + INSERT_VARS_NAME + "})";

    private final String updateSqlFormat = "update %s set %s where %s=#{"
            + ID_VAR_NAME + "}";
    private final String updateVersionSqlFormat = "update %s set %s where %s=#{"
            + ID_VAR_NAME + "} and %s=#{" + VERSION_VAR_NAME + "}";

    private final String deleteSqlFormat = "delete from %s where %s=#{"
            + ID_VAR_NAME + "}";
    private final String countSqlFormat = "select count(*) AS total from %s #wrap{where :"
            + FILTER_VAR_NAME + "}";
    private final String selectSqlFormat = "select %s from %s #wrap{where :"
            + FILTER_VAR_NAME + "} #wrap{order by :" + SORTER_VAR_NAME + "}";

    public SqlDefinition getInsertSql(DomainMeta meta, Object entity) {
        List<Object> values = new ArrayList<Object>();
        String columns = "";
        int i = 0;
        for (String property : meta.getAllProperties()) {
            Object value = meta.getValue(entity, property);
            if (value == null && meta.isIgnoreNull(property)) {
                continue;
            }
            if (i > 0) {
                columns += ",";
            }
            i++;
            columns += meta.propertyToColumn(property);
            values.add(covertValue(value));
        }
        String sql = String.format(insertSqlFormat, meta.getFullTableName(),
                columns);
        SqlDefinition sqlDefinition = new SqlDefinition();
        sqlDefinition.setSql(sql);
        sqlDefinition.addParam(INSERT_VARS_NAME, values);
        return sqlDefinition;
    }

    public SqlDefinition getUpdateSql(DomainMeta meta, Object entity,
            String[] updates) {
        Object idValue = meta.getValue(entity, meta.getIdName());
        if (idValue == null) {
            throw new IllegalArgumentException("主鍵值为NULL,不能生成Update语句");
        }
        if (updates == null || updates.length == 0) {
            updates = meta.getAllProperties();
        }
        String versionName = meta.getVersionName();
        StringBuilder updateStrBuffer = new StringBuilder();
        for (String property : updates) {
            String column = meta.propertyToColumn(property);
            if (column == null) {
                throw new RuntimeException("属性" + property
                        + "在Domain类中未定义! Domain class:"
                        + meta.getDomainClass().getName());
            } else if (column != versionName) {
                if (updateStrBuffer.length() > 0) {
                    updateStrBuffer.append(",");
                }
                Object value = meta.getValue(entity, property);
                updateStrBuffer
                        .append(column)
                        .append("=")
                        .append(meta.getSingleUpdateExpr(property,
                                covertValue(value)));
            }
        }
        String updateStr = updateStrBuffer.toString();

        String sql;
        SqlDefinition sqlDefinition = new SqlDefinition();

        if (versionName != null) {
            Object oldVersion = meta.getValue(entity,
                    meta.columnToProperty(versionName));
            updateStr = updateStr + "," + versionName + "=" + versionName
                    + "+1";
            sql = String.format(updateVersionSqlFormat,
                    meta.getFullTableName(), updateStr, meta.getIdColumn(),
                    meta.getVersionName());
            sqlDefinition.addParam(VERSION_VAR_NAME, oldVersion);
        } else {
            sql = String.format(updateSqlFormat, meta.getFullTableName(),
                    updateStr, meta.getIdColumn());
        }

        sqlDefinition.setSql(sql);
        sqlDefinition.addParam(ID_VAR_NAME, idValue);
        sqlDefinition.addParam(Constants.UPDATE_BEAN_NAME, entity);
        return sqlDefinition;
    }

    public SqlDefinition getDeleteSql(DomainMeta meta, Object idValue) {
        if (idValue == null) {
            throw new IllegalArgumentException("主鍵值为NULL,不能生成Delete语句");
        }
        SqlDefinition sqlDefinition = new SqlDefinition();
        String sql = String.format(deleteSqlFormat, meta.getFullTableName(),
                meta.getIdColumn());
        sqlDefinition.setSql(sql);
        sqlDefinition.addParam(ID_VAR_NAME, idValue);
        return sqlDefinition;
    }

    public SqlDefinition getCountSql(DomainMeta meta, SqlFilter filter) {
        this.handleFilter(meta, filter);
        SqlDefinition sqlDefinition = new SqlDefinition();
        String sql = String.format(countSqlFormat, meta.getFullTableName());
        sqlDefinition.setSql(sql);
        sqlDefinition.addParam(FILTER_VAR_NAME, filter);
        return sqlDefinition;
    }

    public SqlDefinition getSelectSql(DomainMeta meta, SqlFilter filter,
            SqlSorter sorter, String[] selects) {
        this.handleFilter(meta, filter);
        this.handleSorter(meta, sorter);
        String selectStr;
        if (selects != null && selects.length > 0) {
            int i = 0;
            StringBuilder buffer = new StringBuilder();
            for (String select : selects) {
                if (i > 0) {
                    buffer.append(",");
                }
                buffer.append(getSelectColumn(meta, select));
                i++;
            }
            selectStr = buffer.toString();
        } else {
            selectStr = meta.getFullColumnStr();
        }
        String sql = String.format(selectSqlFormat, selectStr,
                meta.getFullTableName());
        SqlDefinition sqlDefinition = new SqlDefinition();
        sqlDefinition.setSql(sql);
        sqlDefinition.addParam(FILTER_VAR_NAME, filter);
        sqlDefinition.addParam(SORTER_VAR_NAME, sorter);
        return sqlDefinition;
    }

    protected String getSelectColumn(DomainMeta meta, String select) {
        StringBuilder sb = new StringBuilder();
        String property = select;
        String column;
        String aliasPost = "";
        int start = -1;
        int end = -1;
        Matcher matcher = sumPat.matcher(select);
        if (matcher.find()) {
            property = matcher.group(1);
            start = matcher.start(1);
            end = matcher.end(1);
            aliasPost = "_sum";
        } else if ((matcher = countPat.matcher(select)).find()) {
            property = matcher.group(2);
            start = matcher.start(2);
            end = matcher.end(2);
            if (matcher.group(1) == null) {
                aliasPost = "_count";
            } else {
                aliasPost = "_count_distinct";
            }
        }

        column = meta.propertyToColumn(property);
        if (column != null) {
            if (start != -1) {
                sb.append(select.substring(0, start)).append(column)
                        .append(select.substring(end)).append(" AS ")
                        .append(property).append(aliasPost);
                ;
            } else {
                sb.append(column);
            }
        } else {
            throw new IllegalArgumentException(select
                    + "不是一个有效的属性字段。Domain Class Type:"
                    + meta.getDomainClass().getName());
        }
        return sb.toString();
    }

    private Object covertValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value.getClass() == DbInnerValue.DbTime.class) {
            return covertSystime((DbInnerValue.DbTime) value);
        }
        return value;
    }

    protected Object covertSystime(DbInnerValue.DbTime value) {
        return value;
    }

    protected final void handleFilter(DomainMeta meta, SqlFilter filter) {
        if (filter == null) {
            return;
        }
        for (FilterProperty fp : filter.getList()) {
            String column = meta.propertyToColumn(fp.getPropertyName());
            if (column != null) {
                fp.setColumnName(column);
            }
            Object val = fp.getPropertyValue();
            fp.setPropertyValue(covertValue(val));
        }
        // 递归处理
        for (SqlFilter fp : filter.getGroupList()) {
            handleFilter(meta, fp);
        }
    }

    protected final void handleSorter(DomainMeta meta, SqlSorter sortProperties) {
        if (sortProperties == null) {
            return;
        }
        for (SortProperty sp : sortProperties.getList()) {
            String column = meta.propertyToColumn(sp.getPropertyName());
            if (column != null) {
                sp.setColumnName(column);
            }
        }
    }
}
