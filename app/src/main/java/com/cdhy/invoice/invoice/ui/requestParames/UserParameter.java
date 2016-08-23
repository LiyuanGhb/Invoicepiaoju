package com.cdhy.invoice.invoice.ui.requestParames;

import android.util.Log;

import com.cdhy.invoice.invoice.CustomApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/12.
 */

public class UserParameter {
    /**
     * 查询用户信息
     *
     * @param userId 用户id
     * @return
     */
    public String getUserInfo(String userId) {
        String url;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "getUserInfo");
            JSONObject js = new JSONObject();
            js.put("type", "Android");
            js.put("athID", CustomApplication.athID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("url", url);
        return url;
    }


    /**
     * 修改用户信息
     *
     * @param zsxm 真实姓名
     * @return
     */
    public String updateUserInfo(String zsxm) {
        String url;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "updateUserInfo");
            JSONObject js = new JSONObject();
            js.put("zsxm", zsxm);
            js.put("type", "Android");
            js.put("athID", CustomApplication.athID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        return url;
    }


}
