package com.dbframe.executor;


public class GeneratedKey {

    private String statment = null;
    
    private Object key = null;
    
    public GeneratedKey() {
    }
    
    public GeneratedKey(String statment) {
        this.statment = statment;
    }
    
    public String getStatment() {
        return statment;
    }


    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }


    
}
