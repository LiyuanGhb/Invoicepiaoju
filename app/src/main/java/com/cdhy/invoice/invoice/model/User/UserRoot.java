package com.cdhy.invoice.invoice.model.User;

/**
 * Created by Administrator on 2016/8/12.
 */

public class UserRoot {
    private Data data;

    private String resultType;

    private String msg;

    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }
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
