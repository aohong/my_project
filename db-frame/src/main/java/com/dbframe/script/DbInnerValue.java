package com.dbframe.script;

import java.util.Date;

public class DbInnerValue {

    private String valueSql = "";

    public static Date getDbTime() {
        return new DbTime();
    }

    public DbInnerValue(String sql) {
        if (sql != null) {
            valueSql = sql;
        }
    }

    public String getValueSql() {
        return valueSql;
    }

    static class DbTime extends Date {

        private static final long serialVersionUID = -8888796949289409135L;

    }
}
