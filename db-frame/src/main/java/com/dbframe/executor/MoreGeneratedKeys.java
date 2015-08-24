package com.dbframe.executor;

import java.util.ArrayList;
import java.util.List;


public class MoreGeneratedKeys extends GeneratedKey{

    private int size;
    
    private List<Object> keys = new ArrayList<Object>();
    
    public MoreGeneratedKeys(int size) {
        super();
        this.size = size;
    }

    public List<Object> getKeys() {
        return keys;
    }

    public int getSize() {
        return size;
    }
    
}
