package com.cdhy.invoice.invoice.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.model.CheckingBean;
import com.cdhy.invoice.invoice.nohttp.CallServer;
import com.cdhy.invoice.invoice.nohttp.HttpListener;
import com.cdhy.invoice.invoice.ui.activity.ChecktoComActyity;
import com.cdhy.invoice.invoice.ui.activity.SetCompanyActivity;
import com.cdhy.invoice.invoice.ui.util.DialogHb;
import com.cdhy.invoice.invoice.ui.util.UrlUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */

public class MakeOverAdapter extends BaseAdapter {
    Activity context;
    List<CheckingBean> list;
    String comid;

    public MakeOverAdapter(Activity context, List<CheckingBean> list, String comid) {
        this.context = context;
        this.list = list;
        this.comid = comid;
    }

    @Override
    public int getCount() {
        if (list.size() == 0) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.makeoveritem, null);
            viewHolder.name = (TextView) view.findViewById(R.id.makeover_item_name);
            viewHolder.phone = (TextView) view.findViewById(R.id.makeover_item_phone);
            viewHolder.set = (Button) view.findViewById(R.id.makeover_bing_btn);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final CheckingBean bean = list.get(i);
        viewHolder.name.setText(bean.getName());
        viewHolder.phone.setText(bean.getUsername());
        viewHolder.set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                mBuilder.setTitle("请确认删除信息");
                mBuilder.setMessage("您确定要转让管理员吗？");
                mBuilder.setNegativeButton("取消", null);
                mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getingData(bean.getUserid());
                    }
                });
                mBuilder.create();
                mBuilder.show();

            }
        });
        return view;
    }

    private void getingData(final String newadmin) {
        Request<JSONObject> jsonObjectRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        jsonObjectRequest.setDefineRequestBodyForJson(changeAdmin(newadmin));
        CallServer.getRequestInstance().add(0, context, jsonObjectRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                Log.e("data", response.get().toString() + "=");
                String data = rtnData(response.get().toString());
                if ("成功".equals(data)) {
                    SetCompanyActivity.activity.finish();
                    ChecktoComActyity.activity.finish();
                    context.finish();
                } else {
                    DialogHb.showdialog(context, data);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
            }
        });
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


    public String changeAdmin(String newadmin) {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "changeAdmin");
            JSONObject js = new JSONObject();
            js.put("comid", comid);
            js.put("newadmin", newadmin);
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

    public class ViewHolder {
        TextView name, phone;
        Button set;
    }


}
