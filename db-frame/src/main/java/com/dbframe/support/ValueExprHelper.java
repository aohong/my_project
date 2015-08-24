package com.dbframe.support;

import com.dbframe.script.DbInnerValue;

public class ValueExprHelper {

    public static String getValueExpr(String varName, Object value) {
        if (value != null && value instanceof DbInnerValue) {
            DbInnerValue dv = (DbInnerValue) value;
            return dv.getValueSql();
        }
        StringBuilder expr = new StringBuilder();
        expr.append("#{").append(varName).append("}");
        return expr.toString();
    }

    public static String getValueExpr(String varName, int offset, int arraySize) {
        StringBuilder expr = new StringBuilder();
        // 编译成#{varName[offset]},#{varName[offset+1]}...#{varName[offset+arraySize]}格式
        for (int i = offset; i < offset + arraySize; i++) {
            if (i > offset) {
                expr.append(",");
            }
            expr.append("#{").append(varName).append("[").append(i)
                    .append("]}");
        }
        return expr.toString();
    }

    public static String getValueExpr(String varName, int offset,
            Object[] values) {
        StringBuilder expr = new StringBuilder();
        int arraySize = values.length;
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                expr.append(",");
            }
            if (values[i] != null && values[i] instanceof DbInnerValue) {
                DbInnerValue dv = (DbInnerValue) values[i];
                expr.append(dv.getValueSql());
            } else {
                expr.append("#{").append(varName).append("[")
                        .append(i + offset).append("]}");
            }
        }
        return expr.toString();
    }
}
