package com.cdhy.invoice.invoice.model;

import java.util.List;

public class VagueCompanyBean {
    /**
     * data : [{"gsyhzh":"北京交通银行慧忠北里支行110060878012015008756","gsmc":"北京科伟达电力设备有限公司","nsrsbh":"110105801733634","gsdzdh":"北京市朝阳区慧忠北里101号 010-82929595","gsdz":"北京市朝阳区慧忠北里101号 010","dh":"82929595","khh":"北京交通银行慧忠北里支","yhzh":"110060878012015008756"}]
     * resultType : SUCCESS
     * msg : 成功
     */

    private String resultType;
    private String msg;
    /**
     * gsyhzh : 北京交通银行慧忠北里支行110060878012015008756
     * gsmc : 北京科伟达电力设备有限公司
     * nsrsbh : 110105801733634
     * gsdzdh : 北京市朝阳区慧忠北里101号 010-82929595
     * gsdz : 北京市朝阳区慧忠北里101号 010
     * dh : 82929595
     * khh : 北京交通银行慧忠北里支
     * yhzh : 110060878012015008756
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String gsyhzh;
        private String gsmc;
        private String nsrsbh;
        private String gsdzdh;
        private String gsdz;
        private String dh;
        private String khh;
        private String yhzh;
        private String comid;

        public String getComid() {
            return comid;
        }

        public void setComid(String comid) {
            this.comid = comid;
        }

        public String getGsyhzh() {
            return gsyhzh;
        }

        public void setGsyhzh(String gsyhzh) {
            this.gsyhzh = gsyhzh;
        }

        public String getGsmc() {
            return gsmc;
        }

        public void setGsmc(String gsmc) {
            this.gsmc = gsmc;
        }

        public String getNsrsbh() {
            return nsrsbh;
        }

        public void setNsrsbh(String nsrsbh) {
            this.nsrsbh = nsrsbh;
        }

        public String getGsdzdh() {
            return gsdzdh;
        }

        public void setGsdzdh(String gsdzdh) {
            this.gsdzdh = gsdzdh;
        }

        public String getGsdz() {
            return gsdz;
        }

        public void setGsdz(String gsdz) {
            this.gsdz = gsdz;
        }

        public String getDh() {
            return dh;
        }

        public void setDh(String dh) {
            this.dh = dh;
        }

        public String getKhh() {
            return khh;
        }

        public void setKhh(String khh) {
            this.khh = khh;
        }

        public String getYhzh() {
            return yhzh;
        }

        public void setYhzh(String yhzh) {
            this.yhzh = yhzh;
        }
    }
}
