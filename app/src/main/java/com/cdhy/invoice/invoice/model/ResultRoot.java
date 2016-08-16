package com.cdhy.invoice.invoice.model;

/**
 * Created by Administrator on 2016/8/11.
 */

public class ResultRoot {
    private String resultType;

    private String msg;

    public void setResultType(String resultType){
        this.resultType = resultType;
    }
    public String getResultType(){
        return this.resultType;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
}
