package com.cdhy.invoice.invoice.model;

/**
 * Created by Administrator on 2016/6/21.
 */
public class EcuInfoBean {
    String EcuName;
    String qyyhid;
    String userid;
    String custinfo;
    String UPLOAD;

    public String getUPLOAD() {
        return UPLOAD;
    }

    public void setUPLOAD(String UPLOAD) {
        this.UPLOAD = UPLOAD;
    }

    public String getEcuName() {
        return EcuName;
    }

    public void setEcuName(String ecuName) {
        EcuName = ecuName;
    }

    public String getCustinfo() {
        return custinfo;
    }

    public void setCustinfo(String custinfo) {
        this.custinfo = custinfo;
    }

    public String getQyyhid() {
        return qyyhid;
    }

    public void setQyyhid(String qyyhid) {
        this.qyyhid = qyyhid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
