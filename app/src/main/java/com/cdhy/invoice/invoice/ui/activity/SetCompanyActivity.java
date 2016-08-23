package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.model.EcuInfoBean;
import com.cdhy.invoice.invoice.ui.BaseActivity;
import com.cdhy.invoice.invoice.ui.adapter.MyecuItemAdapter;
import com.cdhy.invoice.invoice.ui.util.MineUtil;
import com.cdhy.invoice.invoice.ui.util.UrlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */

public class    SetCompanyActivity extends BaseActivity {
    ListView listView;
    public static Activity activity;
    RelativeLayout back_rl;
    TextView textView;
    public static int what;
    List<EcuInfoBean> ecuInfoBeans;
    MyecuItemAdapter myecuItemAdapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ecuInfoBeans = (List<EcuInfoBean>) msg.obj;
            Log.i("SetComActivityHandler", ecuInfoBeans.size() + "Count");
            myecuItemAdapter = new MyecuItemAdapter(ecuInfoBeans, SetCompanyActivity.this);
            listView.setAdapter(myecuItemAdapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myecucom);
        init();
    }

    private void init() {
        activity = this;
        listView = (ListView) findViewById(R.id.myecu_list);
        back_rl = (RelativeLayout) findViewById(R.id.rl_back_myecu);
        textView = (TextView) findViewById(R.id.tx_gscm_myecu);
        textView.setText("我的企业");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String resutl = MineUtil.postHttp(UrlUtils.mainUrl, fincomList());
                Log.i("SetComActivity", resutl);
                ecuInfoBeans = rtnEcuInfoBean(resutl);
                Log.i("SetComActivity", ecuInfoBeans.size() + "Count");
                Message message = new Message();
                message.obj = ecuInfoBeans;
                handler.sendMessage(message);
            }
        }).start();
        back_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent(SetCompanyActivity.this, ChecktoComActyity.class);
                intent.putExtra("comid", ecuInfoBeans.get(position).getCustinfo());
                startActivity(intent);
            }
        });
    }


    public String fincomList() {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "getMyCom");
            JSONObject js = new JSONObject();
            js.put("type", "Android");
            js.put("athID", CustomApplication.athID);
            js.put("admin", "1");
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("phone", url);
        return url;
    }

    private List<EcuInfoBean> rtnEcuInfoBean(String resutl) {
        ecuInfoBeans = new ArrayList<>();
        if (resutl != null) {
            try {
                JSONObject jsonObject = new JSONObject(resutl);
                if (jsonObject.getString("resultType").equals("SUCCESS")) {
                    JSONObject object = new JSONObject(resutl);
                    JSONArray array = object.optJSONArray("data");
                    Log.i("ListArray", array.length() + "Count");
                    if (null != array) {
                        for (int i = 0; i < array.length(); i++) {
                            EcuInfoBean ecuInfoBean = new EcuInfoBean();
                            JSONObject obj = array.optJSONObject(i);
                            ecuInfoBean.setEcuName(obj.optString("COMNAME"));
                            ecuInfoBean.setCustinfo(obj.optString("COMID"));
                            ecuInfoBean.setUPLOAD(obj.optString("UPLOAD"));
                            ecuInfoBeans.add(ecuInfoBean);
                        }
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ecuInfoBeans;
    }

}
