package com.cdhy.invoice.invoice.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.model.VagueCompanyBean;

import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by Administrator on 2016/8/5.
 */
public class ComListAdapter extends BaseAdapter {
    Context context;
    List<VagueCompanyBean.DataBean> list;
    boolean check;

    public ComListAdapter(Context context, List<VagueCompanyBean.DataBean> list, boolean check) {
        this.context = context;
        this.list = list;
        this.check= check;
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount: " + list.size());
        if(list.size()==0){
            return  0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.poplistitem,null);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.pop_list_item_tx);
            viewHolder.nsrsbh  = (TextView) convertView.findViewById(R.id.pop_list_item_nsrsbh);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).getGsmc());
        if(check){
            viewHolder.nsrsbh.setText(list.get(position).getNsrsbh());
        }else {
            viewHolder.nsrsbh.setVisibility(View.GONE);
        }
        return convertView;
    }

    public class ViewHolder{
        TextView textView,nsrsbh;
    }
}
