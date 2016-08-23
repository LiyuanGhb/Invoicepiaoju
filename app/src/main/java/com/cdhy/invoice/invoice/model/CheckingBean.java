package com.cdhy.invoice.invoice.model;

/**
 * Created by Administrator on 2016/8/16.
 */

public class CheckingBean {
    String name;
    String username;
    String ADMINID;

    public String getADMINID() {
        return ADMINID;
    }

    public void setADMINID(String ADMINID) {
        this.ADMINID = ADMINID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    String userid;
}
