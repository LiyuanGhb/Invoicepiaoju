package com.cdhy.invoice.invoice.ui.requestParames;

import android.util.Log;

import com.cdhy.invoice.invoice.CustomApplication;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanyParameter {
    /**
     * 查询名片列表
     *
     * @return
     */
    public String fincomList() {
        String url;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "queryCard");
            JSONObject js = new JSONObject();
            js.put("type", "Android");
            js.put("athID", CustomApplication.athID);
            js.put("client", CustomApplication.DEVICE_ID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        return url;
    }


    /**
     * @param custinfo id
     * @return
     */
    public String getComById(String custinfo) {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "getCardById");
            JSONObject js = new JSONObject();
            js.put("id", custinfo);
            js.put("type", "Android");
            js.put("athID", CustomApplication.athID);
            js.put("client", CustomApplication.DEVICE_ID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        return url;
    }

    public String addCom(String name, String sbh, String dz, String dh, String yh, String yhzh) {
        String url;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "addCard");
            JSONObject js = new JSONObject();
            js.put("name", name);
            js.put("nsrsbh", sbh);
            js.put("address", dz);
            js.put("phone", dh);
            js.put("khh", yh);
            js.put("yhzh", yhzh);
            js.put("type", "Android");
            js.put("athID", CustomApplication.athID);
            js.put("client", CustomApplication.DEVICE_ID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("url", url);
        return url;
    }

    public String updatecom(String companyId, String userId,
                            String name, String sbh, String dz, String dh, String yh, String yhzh) {
        String url;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "updateCard");
            JSONObject js = new JSONObject();
            js.put("id", companyId);
            js.put("userid", userId);
            js.put("name", name);
            js.put("nsrsbh", sbh);
            js.put("address", dz);
            js.put("phone", dh);
            js.put("khh", yh);
            js.put("yhzh", yhzh);
            js.put("type", "Android");
            js.put("athID", CustomApplication.athID);
            js.put("client", CustomApplication.DEVICE_ID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("url", url);
        return url;
    }

    public String deletecom(String custinfo) {
        String url;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "deleteCardById");
            JSONObject js = new JSONObject();
            js.put("type", "Android");
            js.put("id", custinfo);
            js.put("athID", CustomApplication.athID);
            js.put("client", CustomApplication.DEVICE_ID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("url", url);
        return url;
    }


}
