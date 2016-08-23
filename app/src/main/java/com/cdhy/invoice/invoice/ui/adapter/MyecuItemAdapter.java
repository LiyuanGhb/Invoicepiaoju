package com.cdhy.invoice.invoice.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.model.EcuInfoBean;

import java.util.List;


/**
 * Created by Administrator on 2016/6/22.
 */
public class MyecuItemAdapter extends BaseAdapter {
    List<EcuInfoBean> list;
    Context context;

    public MyecuItemAdapter(List<EcuInfoBean> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        Log.i("MyecuItemAdapter",list.size()+"Count");
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.myecuitem, null);
            viewHolder = new ViewHolder();
            viewHolder.guanli = (TextView) convertView.findViewById(R.id.myecuitem_guanli);
            viewHolder.name = (TextView) convertView.findViewById(R.id.myecuitem_gsmc);
            viewHolder.gongsi = (ImageView) convertView.findViewById(R.id.myecu_img);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.myecu_rl);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final EcuInfoBean ecuInfoBean = list.get(position);
        viewHolder.name.setText(ecuInfoBean.getEcuName());
        if ("1".equals(ecuInfoBean.getUPLOAD())) {
          viewHolder.guanli.setText("正在认证中");
          viewHolder.guanli.setTextColor(context.getResources().getColor( R.color.appColor));
        } else if("0".equals(ecuInfoBean.getUPLOAD())){
            viewHolder.guanli.setText("未认证");
            viewHolder.guanli.setTextColor(context.getResources().getColor(R.color.red));
        }else{
            //viewHolder.guanli.setVisibility(View.GONE);
            viewHolder.guanli.setText("已经认证");
            viewHolder.guanli.setTextColor(context.getResources().getColor( R.color.green));
        }
        viewHolder.gongsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(context, MyMainActivity.class);
                intent.putExtra("gsid", ecuInfoBean.getCustinfo());
                context.startActivity(intent);*/
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView name, guanli;
        RelativeLayout relativeLayout;
        ImageView gongsi;
    }
    public void remove(int index) {
        if (index < 0) {
            return;
        } else {
            list.remove(index);
        }
        notifyDataSetChanged();
    }

}
