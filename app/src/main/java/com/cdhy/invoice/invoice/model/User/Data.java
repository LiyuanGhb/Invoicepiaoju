package com.cdhy.invoice.invoice.model.User;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/12.
 */

public class Data implements Serializable {
    private String athID;

    private String ZSXM;

    private String ID;

    private String EMAIL;

    private String USERNAME;

    private String BZ;

    public void setAthID(String athID) {
        this.athID = athID;
    }

    public String getAthID() {
        return this.athID;
    }

    public void setZSXM(String ZSXM) {
        this.ZSXM = ZSXM;
    }

    public String getZSXM() {
        return this.ZSXM;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return this.ID;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getEMAIL() {
        return this.EMAIL;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getUSERNAME() {
        return this.USERNAME;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getBZ() {
        return this.BZ;
    }
}
