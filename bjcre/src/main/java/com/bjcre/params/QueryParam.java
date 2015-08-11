package com.bjcre.params;

import org.hibernate.validator.constraints.NotEmpty;

public class QueryParam {

    @NotEmpty
    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "param:"+param;
    }
}