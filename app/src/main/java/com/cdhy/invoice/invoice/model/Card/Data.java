package com.cdhy.invoice.invoice.model.Card;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/11.
 */

public class Data implements Serializable {
    private String YHZH;//银行账户

    private String PHONE;//企业电话

    private String NSRSBH;//企业税号

    private String NAME;//企业名称

    private String ORDERBY;//排序标识

    private String KHH;//开户银行

    private String CLIENT;//手机唯一标识符

    private String ADDRESS;//企业地址

    private String ID;//企业id

    // FIXME: 2016/8/11 暂时不知道的参数
    private String UPLOAD;//暂时不知道

    private String USERID;//用户id

    public void setYHZH(String YHZH){
        this.YHZH = YHZH;
    }
    public String getYHZH(){
        return this.YHZH;
    }
    public void setPHONE(String PHONE){
        this.PHONE = PHONE;
    }
    public String getPHONE(){
        return this.PHONE;
    }
    public void setNSRSBH(String NSRSBH){
        this.NSRSBH = NSRSBH;
    }
    public String getNSRSBH(){
        return this.NSRSBH;
    }
    public void setNAME(String NAME){
        this.NAME = NAME;
    }
    public String getNAME(){
        return this.NAME;
    }
    public void setORDERBY(String ORDERBY){
        this.ORDERBY = ORDERBY;
    }
    public String getORDERBY(){
        return this.ORDERBY;
    }
    public void setKHH(String KHH){
        this.KHH = KHH;
    }
    public String getKHH(){
        return this.KHH;
    }
    public void setCLIENT(String CLIENT){
        this.CLIENT = CLIENT;
    }
    public String getCLIENT(){
        return this.CLIENT;
    }
    public void setADDRESS(String ADDRESS){
        this.ADDRESS = ADDRESS;
    }
    public String getADDRESS(){
        return this.ADDRESS;
    }
    public void setID(String ID){
        this.ID = ID;
    }
    public String getID(){
        return this.ID;
    }
    public void setUPLOAD(String UPLOAD){
        this.UPLOAD = UPLOAD;
    }
    public String getUPLOAD(){
        return this.UPLOAD;
    }
    public void setUSERID(String USERID){
        this.USERID = USERID;
    }
    public String getUSERID(){
        return this.USERID;
    }
}
