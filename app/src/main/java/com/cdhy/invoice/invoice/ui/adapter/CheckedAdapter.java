package com.cdhy.invoice.invoice.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
 * Created by Administrator on 2016/8/16.
 */

public class CheckedAdapter extends BaseAdapter {
    Context context;
    List<CheckingBean> list;
    String comid;

    public CheckedAdapter(Context context, List<CheckingBean> list, String comid) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.checked, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.checked_name);
            viewHolder.delete = (Button) view.findViewById(R.id.checked_delete_btn);
            viewHolder.phone = (TextView) view.findViewById(R.id.checked_phone);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final CheckingBean bean = list.get(i);
        Log.e("test", bean.getName() + "ma,e");
        viewHolder.name.setText(bean.getName());
        viewHolder.phone.setText(bean.getUsername());
        if (bean.getUserid().equals(bean.getADMINID())) {
            viewHolder.delete.setText("管理员");
            viewHolder.delete.setEnabled(false);
        } else {
            viewHolder.delete.setText("删除");
            viewHolder.delete.setEnabled(true);
        }
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                mBuilder.setTitle("请确认删除信息");
                mBuilder.setMessage("您确定要删除该员工吗？");
                mBuilder.setNegativeButton("取消", null);
                mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getingData(bean.getUserid(), "2", i);

                    }
                });
                mBuilder.create();
                mBuilder.show();

            }
        });
        return view;
    }

    private void getingData(String userid, String status, final int i) {
        Request<JSONObject> jsonObjectRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        jsonObjectRequest.setDefineRequestBodyForJson(checkUserToCom(userid, status));
        CallServer.getRequestInstance().add(0, context, jsonObjectRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                Log.e("data", response.get().toString() + "=================");
                String data = rtnData(response.get().toString());
                DialogHb.showdialog(context, data);
                if ("成功".equals(data)) {
                    remove(i);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {

            }
        });
    }

    private String checkUserToCom(String userid, String status) {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "checkUserToCom");
            JSONObject js = new JSONObject();
            js.put("userid", userid);
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

    public class ViewHolder {
        TextView name, phone;
        Button delete;
    }

    public void remove(int index) {
        if (index < 0) {
            return;
        } else {
            list.remove(index);
        }
        notifyDataSetChanged();
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
}
