package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.model.ComContentBean;
import com.cdhy.invoice.invoice.model.VagueCompanyBean;
import com.cdhy.invoice.invoice.nohttp.CallServer;
import com.cdhy.invoice.invoice.nohttp.HttpListener;
import com.cdhy.invoice.invoice.ui.adapter.ComListAdapter;
import com.cdhy.invoice.invoice.ui.util.DialogHb;
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
 * Created by Administrator on 2016/8/15.
 */

public class AuthComActivity extends Activity {
    Button findcom, findsh;
    TextView title;
    EditText kaihuhang, phone, name, yhzh, dz, shuihao;
    RelativeLayout back;
    String comid;
    Button bing;
    PopupWindow popupWindow;
    List<VagueCompanyBean.DataBean> ecuInfoBeans;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ecuInfoBeans = (List<VagueCompanyBean.DataBean>) msg.obj;
            findsh.setEnabled(true);
            findsh.setBackgroundResource(R.drawable.buttonblue);
            findsh.setText("查询");
            findcom.setText("查询");
            findcom.setEnabled(true);
            findcom.setBackgroundResource(R.drawable.buttonblue);
            if (ecuInfoBeans.size() > 0) {
                getPopupWindow(ecuInfoBeans);
                popupWindow.showAtLocation(bing, Gravity.CENTER, 0, 0);
            } else {
                DialogHb.showdialog(AuthComActivity.this, "没有查到相应的数据");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_layout);
        init();
    }

    private void init() {
        bing = (Button) findViewById(R.id.bt_bing_current);
        findcom = (Button) findViewById(R.id.findcom_btn);
        findsh = (Button) findViewById(R.id.findsh_btn);
        kaihuhang = (EditText) findViewById(R.id.et_kaihuyinghang_current);
        phone = (EditText) findViewById(R.id.et_number_current);
        name = (EditText) findViewById(R.id.et_taitou_current);
        back = (RelativeLayout) findViewById(R.id.rl_back_current);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shuihao = (EditText) findViewById(R.id.et_shuihao_current);
        yhzh = (EditText) findViewById(R.id.et_yhzh_current);
        dz = (EditText) findViewById(R.id.et_dizhi_current);
        title = (TextView) findViewById(R.id.current_title);
        title.setText("申请加入企业");
        bing.setText("申请加入企业");
        findcom.setVisibility(View.VISIBLE);
        findsh.setVisibility(View.VISIBLE);
        bing.setOnClickListener(onClickListener);
        findsh.setOnClickListener(onClickListener);
        findcom.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_bing_current:
                    Request<JSONObject> jsonObjectRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
                    jsonObjectRequest.setDefineRequestBodyForJson(addCom(comid));
                    CallServer.getRequestInstance().add(0, AuthComActivity.this, jsonObjectRequest, new HttpListener<JSONObject>() {
                        @Override
                        public void onSucceed(int what, Response<JSONObject> response) {
                            String s = response.get().toString();
                            Log.i("tah", "onSucceed: " + response.get().toString());
                            DialogHb.showdialog(AuthComActivity.this, rtnData(s));
                        }

                        @Override
                        public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {

                        }
                    });
                    break;
                case R.id.findcom_btn:
                    findcom.setText("查询中");
                    findcom.setBackgroundResource(R.drawable.buttonblue_0);
                    Request<JSONObject> jsonObjectRequest2 = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
                    jsonObjectRequest2.setDefineRequestBodyForJson(insert(name.getText().toString(), shuihao.getText().toString()));
                    CallServer.getRequestInstance().add(0, AuthComActivity.this, jsonObjectRequest2, new HttpListener<JSONObject>() {
                        @Override
                        public void onSucceed(int what, Response<JSONObject> response) {
                            Log.i("tah", "onSucceed: " + response.get().toString());
                            ecuInfoBeans = rtnEcuInfoBean(response.get().toString());
                            findcom.setText("查询");
                            findcom.setBackgroundResource(R.drawable.buttonblue);
                            if (ecuInfoBeans.size() > 0) {
                                getPopupWindow(ecuInfoBeans);
                                popupWindow.showAtLocation(bing, Gravity.CENTER, 0, 0);
                            } else {
                                DialogHb.showdialog(AuthComActivity.this, "没有查到相应的数据");

                            }
                        }

                        @Override
                        public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                            findcom.setText("查询");
                            findcom.setBackgroundResource(R.drawable.buttonblue);
                        }
                    });
                    break;
                case R.id.findsh_btn:
                    findsh.setText("查询中");
                    findsh.setBackgroundResource(R.drawable.buttonblue_0);
                    Request<JSONObject> jsonObjectRequest3 = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
                    jsonObjectRequest3.setDefineRequestBodyForJson(insert(name.getText().toString(), shuihao.getText().toString()));
                    CallServer.getRequestInstance().add(0, AuthComActivity.this, jsonObjectRequest3, new HttpListener<JSONObject>() {
                        @Override
                        public void onSucceed(int what, Response<JSONObject> response) {
                            findsh.setText("查询");
                            findsh.setBackgroundResource(R.drawable.buttonblue);
                            ecuInfoBeans = rtnEcuInfoBean(response.get().toString());
                            Log.i("tah", "onSucceed: " + response.get().toString());
                            if (ecuInfoBeans.size() > 0) {
                                getPopupWindow(ecuInfoBeans);
                                popupWindow.showAtLocation(bing, Gravity.CENTER, 0, 0);
                            } else {
                                DialogHb.showdialog(AuthComActivity.this, "没有查到相应的数据");
                            }
                        }

                        @Override
                        public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                            findsh.setText("查询");
                            findsh.setBackgroundResource(R.drawable.buttonblue);
                        }
                    });
                    break;
            }
        }
    };

    public String insert(String name, String sbh) {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "authCom");
            JSONObject js = new JSONObject();
            js.put("comname", name);
            js.put("nsrsbh", sbh);
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

    private String rtnData(String resutl) {
        String rtndata = null;
        if (resutl != null) {
            try {
                JSONObject jsonObject = new JSONObject(resutl);
                rtndata = jsonObject.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rtndata;
    }

    public String addCom(String comid) {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "userToCom");
            JSONObject js = new JSONObject();
            js.put("type", "Android");
            js.put("athID", CustomApplication.athID);
            js.put("client", CustomApplication.DEVICE_ID);
            js.put("comid", comid);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("url", url);
        return url;
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow(List<VagueCompanyBean.DataBean> list) {
        // TODO Auto-generated method stub
        Log.e("ss", list.size() + "x");
        View popupWindow_view = getLayoutInflater().inflate(R.layout.popcomlist, null,
                false);
        ListView listView = (ListView) popupWindow_view.findViewById(R.id.pop_list);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        ComListAdapter ecuAdapter = new ComListAdapter(AuthComActivity.this, list, true);
        listView.setAdapter(ecuAdapter);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        System.out.println("heigth : " + dm.heightPixels);
        int hight = (int) (dm.heightPixels * (0.75));
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.WRAP_CONTENT, hight, true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                name.setText(ecuInfoBeans.get(i).getGsmc());
                shuihao.setText(ecuInfoBeans.get(i).getNsrsbh());
                kaihuhang.setText(ecuInfoBeans.get(i).getKhh());
                dz.setText(ecuInfoBeans.get(i).getGsdz());
                phone.setText(ecuInfoBeans.get(i).getDh());
                yhzh.setText(ecuInfoBeans.get(i).getYhzh());
                popupWindow.dismiss();
                comid = ecuInfoBeans.get(i).getComid();
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
                popupWindow = null;
            }
        });
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    WindowManager.LayoutParams params = getWindow().getAttributes();
                    params.alpha = 1f;
                    getWindow().setAttributes(params);
                    popupWindow = null;
                }
                return false;
            }
        })
        ;
    }

    ;

    /***
     * 获取PopupWindow实例
     */

    private void getPopupWindow(List<VagueCompanyBean.DataBean> list) {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow(list);
        }
    }

    private List<VagueCompanyBean.DataBean> rtnEcuInfoBean(String resutl) {
        ecuInfoBeans = new ArrayList<>();
        String rtndata = null;
        if (resutl != null) {
            try {
                JSONObject jsonObject = new JSONObject(resutl);
                rtndata = jsonObject.getString("resultType");
                if (jsonObject.getString("resultType").equals("SUCCESS")) {
                    JSONObject object = new JSONObject(resutl);
                    JSONArray array = object.optJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        VagueCompanyBean.DataBean ecuInfoBean = new VagueCompanyBean.DataBean();
                        JSONObject obj = array.optJSONObject(i);
                        ecuInfoBean.setGsdz(obj.optString("ADDRESS"));
                        ecuInfoBean.setDh(obj.optString("PHONE"));
                        ecuInfoBean.setKhh(obj.optString("KHH"));
                        ecuInfoBean.setGsmc(obj.optString("NAME"));
                        ecuInfoBean.setYhzh(obj.optString("YHZH"));
                        ecuInfoBean.setNsrsbh(obj.optString("NSRSBH"));
                        ecuInfoBean.setComid(obj.optString("ID"));
                        ecuInfoBeans.add(ecuInfoBean);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ecuInfoBeans;
    }
}
