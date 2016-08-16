package com.cdhy.invoice.invoice.model.Card;

import java.util.List;

public class CompanyRoot {
    private List<Data> data ;

    private String resultType;

    private String msg;

    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
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
