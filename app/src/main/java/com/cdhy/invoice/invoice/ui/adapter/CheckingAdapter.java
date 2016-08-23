package com.cdhy.invoice.invoice.ui.adapter;

import android.content.Context;
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
import com.cdhy.invoice.invoice.ui.util.UrlUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/8/16.
 */

public class CheckingAdapter extends BaseAdapter {
    Context context;
    List<CheckingBean> list;
    String comid;

    public CheckingAdapter(Context context, List<CheckingBean> list, String comid) {
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
        final ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.checking, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.checking_name);
            viewHolder.no = (Button) view.findViewById(R.id.checking_no_btn);
            viewHolder.ok = (Button) view.findViewById(R.id.checking_ok_btn);
            viewHolder.status = (TextView) view.findViewById(R.id.checking_stute);
            viewHolder.phone = (TextView) view.findViewById(R.id.checking_phone);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final CheckingBean bean = list.get(i);
        Log.e("test", bean.getName() + "ma,e");
        viewHolder.name.setText(bean.getName());
        viewHolder.phone.setText(bean.getUsername());
        viewHolder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getingData(bean.getUserid(), "2");
                viewHolder.no.setVisibility(View.GONE);
                viewHolder.ok.setVisibility(View.GONE);
                viewHolder.status.setText("已拒绝审核");
                viewHolder.status.setVisibility(View.VISIBLE);
            }
        });
        viewHolder.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getingData(bean.getUserid(), "1");
                viewHolder.no.setVisibility(View.GONE);
                viewHolder.ok.setVisibility(View.GONE);
                viewHolder.status.setVisibility(View.VISIBLE);
                viewHolder.status.setText("已通过审核");

            }
        });
        return view;
    }

    private void getingData(String userid, String status) {
        Request<JSONObject> jsonObjectRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        jsonObjectRequest.setDefineRequestBodyForJson(checkUserToCom(userid, status));
        CallServer.getRequestInstance().add(0,context, jsonObjectRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                Log.e("data", response.get().toString() + "=================");
                response.get().toString();
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
        TextView name, status,phone;
        Button ok, no;
    }
}
