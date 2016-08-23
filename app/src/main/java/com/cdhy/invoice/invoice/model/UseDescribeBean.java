package com.cdhy.invoice.invoice.model;

/**
 * Created by Administrator on 2016/8/17.
 */

public class UseDescribeBean {

    /**
     * ZSXM : zby,
     * ID : F1B948B8D3C34331BCFF0AB6B96982EA
     * EMAIL :
     * USERNAME : 18981443691
     * PASSWORD : b79070b38e661ba782b6b59328d98ddb
     * BZ :
     */

    private DataBean data;
    /**
     * data : {"ZSXM":"zby,","ID":"F1B948B8D3C34331BCFF0AB6B96982EA","EMAIL":"","USERNAME":"18981443691","PASSWORD":"b79070b38e661ba782b6b59328d98ddb","BZ":""}
     * resultType : SUCCESS
     * msg : 成功
     */

    private String resultType;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private String ZSXM;
        private String ID;
        private String EMAIL;
        private String USERNAME;
        private String PASSWORD;
        private String BZ;

        public String getZSXM() {
            return ZSXM;
        }

        public void setZSXM(String ZSXM) {
            this.ZSXM = ZSXM;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getEMAIL() {
            return EMAIL;
        }

        public void setEMAIL(String EMAIL) {
            this.EMAIL = EMAIL;
        }

        public String getUSERNAME() {
            return USERNAME;
        }

        public void setUSERNAME(String USERNAME) {
            this.USERNAME = USERNAME;
        }

        public String getPASSWORD() {
            return PASSWORD;
        }

        public void setPASSWORD(String PASSWORD) {
            this.PASSWORD = PASSWORD;
        }

        public String getBZ() {
            return BZ;
        }

        public void setBZ(String BZ) {
            this.BZ = BZ;
        }
    }
}
