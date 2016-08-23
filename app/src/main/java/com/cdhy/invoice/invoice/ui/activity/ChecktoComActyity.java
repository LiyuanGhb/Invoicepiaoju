package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.databinding.ChecktocomBinding;
import com.cdhy.invoice.invoice.model.CheckingBean;
import com.cdhy.invoice.invoice.nohttp.CallServer;
import com.cdhy.invoice.invoice.nohttp.HttpListener;
import com.cdhy.invoice.invoice.ui.BaseActivity;
import com.cdhy.invoice.invoice.ui.adapter.CheckedAdapter;
import com.cdhy.invoice.invoice.ui.adapter.CheckingAdapter;
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
 * Created by Administrator on 2016/8/16.
 */

public class ChecktoComActyity extends BaseActivity implements View.OnClickListener {
    ChecktocomBinding checktocomBinding;
    String comid;
    Intent intent;
    public static Activity activity;
    List<CheckingBean> checkingBeen;
    PopupMenu popupMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        activity = this;
        checktocomBinding = DataBindingUtil.setContentView(this, R.layout.checktocom);
        checktocomBinding.setOnclick(this);
        intent = getIntent();
        comid = intent.getStringExtra("comid");
        getingData("0");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back_checktocom:
                finish();
                break;
            case R.id.checktocom_set_rl:
                showPopminu(view);
                break;
            case R.id.checktocom_btn_ing:
                checktocomBinding.checktocomBtnEd.setBackgroundResource(R.drawable.logins2);
                checktocomBinding.checktocomBtnIng.setBackgroundResource(R.drawable.loginsleft_click);
                getingData("0");
                break;
            case R.id.checktocom_btn_ed:
                checktocomBinding.checktocomBtnEd.setBackgroundResource(R.drawable.logins);
                checktocomBinding.checktocomBtnIng.setBackgroundResource(R.drawable.loginsleft);
                getingData("1");
                break;
        }
    }

    private void getingData(final String status) {
        Request<JSONObject> jsonObjectRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        jsonObjectRequest.setDefineRequestBodyForJson(listUserToCom(status));
        CallServer.getRequestInstance().add(0, ChecktoComActyity.this, jsonObjectRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                Log.e("data", response.get().toString() + "=");
                checkingBeen = rtnEcuInfoBean(response.get().toString());
                if (status.equals("0")) {
                    CheckingAdapter checkingAdapter = new CheckingAdapter(ChecktoComActyity.this, checkingBeen, comid);
                    checktocomBinding.checktocomListview.setAdapter(checkingAdapter);
                } else {
                    CheckedAdapter checkingAdapter = new CheckedAdapter(ChecktoComActyity.this, checkingBeen, comid);
                    checktocomBinding.checktocomListview.setAdapter(checkingAdapter);
                }
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
                        ecuInfoBean.setADMINID(obj.optString("ADMINID"));
                        checkingBeen.add(ecuInfoBean);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return checkingBeen;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void showPopminu(View button) {
        //创建PopupMenu对象
        popupMenu = new PopupMenu(this, button);
        getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.makeover_menu:
                        Intent intent = new Intent(ChecktoComActyity.this, MakeOverActivity.class);
                        intent.putExtra("comid", comid);
                        startActivity(intent);

                        break;
                    case R.id.setchose_menu:
                        Intent intent2 = new Intent(ChecktoComActyity.this, IsChekedActivity.class);
                        intent2.putExtra("comid", comid);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }


}
