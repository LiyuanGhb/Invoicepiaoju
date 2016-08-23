package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.model.EcuInfoBean;
import com.cdhy.invoice.invoice.ui.MineUrl;
import com.cdhy.invoice.invoice.ui.adapter.MyecuItemAdapter;
import com.cdhy.invoice.invoice.ui.util.DialogHb;
import com.cdhy.invoice.invoice.ui.util.MineUtil;
import com.cdhy.invoice.invoice.ui.util.UrlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class SetComActivity extends Activity {
    ListView listView;
    RelativeLayout back_rl;
    public static int what;
    List<EcuInfoBean> ecuInfoBeans;
    MyecuItemAdapter myecuItemAdapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ecuInfoBeans = (List<EcuInfoBean>) msg.obj;
            Log.i("SetComActivityHandler",ecuInfoBeans.size()+"Count");
            myecuItemAdapter = new MyecuItemAdapter(ecuInfoBeans, SetComActivity.this);
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

        listView = (ListView) findViewById(R.id.myecu_list);
        back_rl = (RelativeLayout) findViewById(R.id.rl_back_myecu);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String resutl = MineUtil.postHttp(UrlUtils.mainUrl, fincomList());
                ecuInfoBeans = rtnEcuInfoBean(resutl);
                Log.i("SetComActivity",ecuInfoBeans.size()+"Count");
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
                if (what == 1) {
                    showdialog(SetComActivity.this, "确定将" + ecuInfoBeans.get(position).getEcuName() + "设为默认公司吗？", ecuInfoBeans.get(position).getCustinfo());
                } else if (what == 2) {
                    if(ecuInfoBeans.get(position).getUPLOAD().equals("2")){
                        DialogHb.showdialog(SetComActivity.this,"该企业已认证");
                    }else{
                        Intent intent = new Intent(SetComActivity.this, AccreditationActivity.class);
                        intent.putExtra("id", ecuInfoBeans.get(position).getCustinfo());
                        startActivity(intent);
                    }

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(CustomApplication.index){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String resutl = MineUtil.postHttp(MineUrl.newHeads, fincomList());
                    ecuInfoBeans = rtnEcuInfoBean(resutl);
                    Message message = new Message();
                    message.obj = ecuInfoBeans;
                    handler.sendMessage(message);
                }
            }).start();
        }
    }

    public String fincomList() {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "queryCard");
            JSONObject js = new JSONObject();
            //   js.put("userid", MyApplication.gryhid);
            js.put("type", "Android");
            js.put("userid", CustomApplication.userId);
            js.put("athID", CustomApplication.athID);
            js.put("client", CustomApplication.DEVICE_ID);
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
                    Log.i("ListArray",array.length()+"Count");
                    if(null!=array){
                        for (int i = 0; i < array.length(); i++) {
                            EcuInfoBean ecuInfoBean = new EcuInfoBean();
                            JSONObject obj = array.optJSONObject(i);
                            ecuInfoBean.setEcuName(obj.optString("NAME"));
                            ecuInfoBean.setCustinfo(obj.optString("ID"));
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

    public String SetCardCommon(String custinfo) {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "setCardCommon");
            JSONObject js = new JSONObject();
            js.put("id", custinfo);
            js.put("type", "Android");
            js.put("userid", CustomApplication.userId);
            js.put("client", CustomApplication.DEVICE_ID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("phone", url);
        return url;
    }

    public void showdialog(Context context, String msg, final String comid) {
        CustomApplication.index = true;
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String result = MineUtil.postHttp(UrlUtils.mainUrl, SetCardCommon(comid));
                            }
                        }).start();

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

}
