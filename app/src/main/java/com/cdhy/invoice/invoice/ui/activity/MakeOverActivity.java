package com.cdhy.invoice.invoice.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.databinding.MakeoverBinding;
import com.cdhy.invoice.invoice.model.CheckingBean;
import com.cdhy.invoice.invoice.nohttp.CallServer;
import com.cdhy.invoice.invoice.nohttp.HttpListener;
import com.cdhy.invoice.invoice.ui.BaseActivity;
import com.cdhy.invoice.invoice.ui.adapter.MakeOverAdapter;
import com.cdhy.invoice.invoice.ui.util.UrlUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 * 转让管理员
 */

public class MakeOverActivity extends BaseActivity implements View.OnClickListener {
    MakeoverBinding makeoverBinding;
    List<CheckingBean> checkingBeen;
    String comid;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getingData("1");
    }

    private void init() {
        makeoverBinding = DataBindingUtil.setContentView(this, R.layout.makeover);
        makeoverBinding.setOnclick(this);
        intent = getIntent();
        comid = intent.getStringExtra("comid");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back_makeover:
                finish();
                break;
        }
    }

    private void getingData(final String status) {
        Request<JSONObject> jsonObjectRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        jsonObjectRequest.setDefineRequestBodyForJson(listUserToCom(status));
        CallServer.getRequestInstance().add(0,MakeOverActivity.this,jsonObjectRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                Log.e("data", response.get().toString() + "=");
                checkingBeen = rtnEcuInfoBean(response.get().toString());
                MakeOverAdapter checkingAdapter = new MakeOverAdapter(MakeOverActivity.this, checkingBeen, comid);
                makeoverBinding.listviewMakeover.setAdapter(checkingAdapter);
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {

            }
        });
    }

    public String listUserToCom(String status) {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "listUserToCom");
            JSONObject js = new JSONObject();
            js.put("comid", comid);
            js.put("status", status);
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

    private List<CheckingBean> rtnEcuInfoBean(String resutl) {
        checkingBeen = new ArrayList<>();
        String rtndata = null;
        if (resutl != null) {
            try {
                JSONObject jsonObject = new JSONObject(resutl);
                rtndata = jsonObject.getString("resultType");
                if (jsonObject.getString("resultType").equals("SUCCESS")) {
                    JSONObject object = new JSONObject(resutl);
                    JSONArray array = object.optJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        CheckingBean ecuInfoBean = new CheckingBean();
                        JSONObject obj = array.optJSONObject(i);
                        ecuInfoBean.setName(obj.optString("ZSXM"));
                        ecuInfoBean.setUserid(obj.optString("USER_ID"));
                        ecuInfoBean.setUsername(obj.optString("USERNAME"));
                        checkingBeen.add(ecuInfoBean);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return checkingBeen;
    }


}
