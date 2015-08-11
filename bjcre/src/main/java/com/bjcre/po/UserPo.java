package com.bjcre.po;

import com.dbframe.annotation.Id;
import com.dbframe.annotation.Table;

/**
 * Created by aohong on 15/5/24.
 */
@Table("user")
public class UserPo {

    private Integer id;

    private String loginName;

    private String password;
    private Integer type;

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "id:"+id+" ,login_name:"+loginName+" ,password:" +
                password + " ,type" +
                type;
    }
}
