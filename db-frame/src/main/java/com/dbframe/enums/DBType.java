package com.dbframe.enums;

public enum DBType {
    MySql("mysql"),
    Oracle("oracle");
    
    private final String key;//关键字
    
    DBType(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
