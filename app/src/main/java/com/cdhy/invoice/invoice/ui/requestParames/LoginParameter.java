package com.cdhy.invoice.invoice.ui.requestParames;

import android.util.Log;

import com.cdhy.invoice.invoice.CustomApplication;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginParameter {
    /**
     * 登陆
     *
     * @param userName 用户名
     * @param passWord 密码
     * @return
     */
    public String loginByPwd(String userName, String passWord) {
        String url;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "loginByPwd");
            JSONObject js = new JSONObject();
            js.put("type", "Android");
            js.put("username", userName);
            js.put("password", passWord);
            js.put("client", CustomApplication.DEVICE_ID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("url", url);
        return url;
    }

    /**
     * 获取验证码
     *
     * @param userName 用户名
     * @return
     */
    public String getSmsCode(String userName) {
        String url;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "getSmsCode");
            JSONObject js = new JSONObject();
            js.put("type", "Android");
            js.put("username", userName);
            js.put("client", CustomApplication.DEVICE_ID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("url", url);
        return url;
    }

    /**
     * 注册
     *
     * @param userName 用户名
     * @param passWord 密码
     * @param zsxm 真实姓名
     * @param code 验证码
     * @return
     */
    public String userRegist(String userName, String passWord,String zsxm,String code) {
        String url;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "userRegist");
            JSONObject js = new JSONObject();
            js.put("type", "Android");
            js.put("username", userName);
            js.put("password", passWord);
            js.put("zsxm", zsxm);
            js.put("code", code);
            js.put("client", CustomApplication.DEVICE_ID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("url", url);
        return url;
    }

    /**
     * 修改密码
     *
     * @param userName 用户名
     * @param passWord 密码
     * @param code 验证码
     * @return
     */
    public String resetPwd(String userName, String passWord,String code) {
        String url;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "resetPwd");
            JSONObject js = new JSONObject();
            js.put("type", "Android");
            js.put("username", userName);
            js.put("password", passWord);
            js.put("code", code);
            js.put("client", CustomApplication.DEVICE_ID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("url", url);
        return url;
    }

    /**
     * 验证码登陆
     *
     * @param userName 用户名
     * @param code 验证码
     * @return
     */
    public String loginBySms(String userName,String code) {
        String url;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "loginBySms");
            JSONObject js = new JSONObject();
            js.put("type", "Android");
            js.put("username", userName);
            js.put("code", code);
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
