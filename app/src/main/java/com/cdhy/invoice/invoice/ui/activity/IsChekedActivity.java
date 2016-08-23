package com.cdhy.invoice.invoice.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.databinding.IschekdBinding;
import com.cdhy.invoice.invoice.nohttp.CallServer;
import com.cdhy.invoice.invoice.nohttp.HttpListener;
import com.cdhy.invoice.invoice.ui.BaseActivity;
import com.cdhy.invoice.invoice.ui.util.UrlUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/17.
 */

public class IsChekedActivity extends BaseActivity implements View.OnClickListener {
    Intent intent;
    String comid;
    IschekdBinding ischekdBinding;
    String ISCHECK = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        ischekdBinding = DataBindingUtil.setContentView(this, R.layout.ischekd);
        ischekdBinding.setOnclick(this);
        intent = getIntent();
        comid = intent.getStringExtra("comid");
        getingData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back_ischekd:
                finish();
                break;
            case R.id.chebox_yes:
                if (ischekdBinding.cheboxYes.isChecked()) {
                    setISCHECK("1");
                    ischekdBinding.chexboxNo.setChecked(false);
                } else {
                    setISCHECK("0");
                    ischekdBinding.chexboxNo.setChecked(true);
                }

                break;
            case R.id.chexbox_no:
                if (ischekdBinding.chexboxNo.isChecked()) {
                    setISCHECK("0");
                    ischekdBinding.cheboxYes.setChecked(false);
                } else {
                    setISCHECK("1");
                    ischekdBinding.cheboxYes.setChecked(true);
                }
                break;
        }
    }

    private void getingData() {
        Request<JSONObject> jsonObjectRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        jsonObjectRequest.setDefineRequestBodyForJson(getComInfo());
        CallServer.getRequestInstance().add(0,IsChekedActivity.this,jsonObjectRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                Log.e("data", response.get().toString() + "=");
                rtnEcuInfoBean(response.get().toString());
                if ("1".equals(ISCHECK)) {
                    ischekdBinding.chexboxNo.setChecked(false);
                    ischekdBinding.cheboxYes.setChecked(true);
                } else {
                    ischekdBinding.chexboxNo.setChecked(true);
                    ischekdBinding.cheboxYes.setChecked(false);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                // Log.e("data", error.toString() + "=");
            }
        });
    }

    private void setISCHECK(String ischeck) {
        Request<JSONObject> jsonObjectRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        jsonObjectRequest.setDefineRequestBodyForJson(setIsCheck(ischeck));
        CallServer.getRequestInstance().add(0, IsChekedActivity.this, jsonObjectRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                Log.e("data", response.get().toString() + "=");
                response.get();
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
            }
        });
    }

    public String getComInfo() {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "getComInfo");
            JSONObject js = new JSONObject();
            js.put("comid", comid);
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

    public String setIsCheck(String ischeck) {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "setIsCheck");
            JSONObject js = new JSONObject();
            js.put("comid", comid);
            js.put("ischeck", ischeck);
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

    private void rtnEcuInfoBean(String resutl) {
        String rtndata = null;
        if (resutl != null) {
            try {
                JSONObject jsonObject = new JSONObject(resutl);
                rtndata = jsonObject.getString("resultType");
                if (jsonObject.getString("resultType").equals("SUCCESS")) {
                    String data = jsonObject.optString("data");
                    JSONObject jsonObject2 = new JSONObject(data);
                    ISCHECK = jsonObject2.getString("ISCHECK");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
