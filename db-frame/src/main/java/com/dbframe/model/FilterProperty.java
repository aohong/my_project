package com.dbframe.model;

import com.dbframe.enums.JoinType;
import com.dbframe.enums.MatchType;

public class FilterProperty {

    private String propertyName;
    private Object propertyValue;

    private String columnName;

    private MatchType matchType = null;

    private JoinType joinType;

    public FilterProperty(String propertyName, Object propertyValue,
            MatchType matchType) {
        this(propertyName, propertyValue, matchType, JoinType.AND);
    }

    public FilterProperty(String propertyName, Object propertyValue,
            MatchType matchType, JoinType joinType) {
        this.propertyName = propertyName;
        this.columnName = propertyName;
        this.propertyValue = propertyValue;
        this.matchType = matchType;
        this.joinType = joinType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getPropertyValue() {
        return propertyValue;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setPropertyValue(Object propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String toString() {
        return "{matchType=" + matchType + ", propertyName=" + propertyName
                + ", propertyValue=" + propertyValue + "}";
    }
}
