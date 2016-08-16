package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.databinding.SetipBinding;
import com.cdhy.invoice.invoice.ui.util.DialogHb;
import com.cdhy.invoice.invoice.ui.util.SharedPreferencesHelper;

import scanner.CaptureActivity;

public class SetIpActivity extends Activity {
    private SetipBinding mbinding;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = DataBindingUtil.setContentView(this, R.layout.setip);
        init();
    }

    private void init() {
        if (CustomApplication.saveIp) {
            mbinding.ipeditSetip.setText(CustomApplication.ip);
        }
        listern();
    }

    private void listern() {
        mbinding.rlBackSetip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mbinding.btBingSetip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip = mbinding.ipeditSetip.getText().toString();
                CustomApplication.ip = ip;
                CustomApplication.saveIp = true;
                SharedPreferencesHelper mSharedPreferencesHelper = new SharedPreferencesHelper(SetIpActivity.this);
                mSharedPreferencesHelper.setParameterToShared(SharedPreferencesHelper.IP, ip);
                mSharedPreferencesHelper.setBooleanToShared(SharedPreferencesHelper.SAVE_IP, true);
                DialogHb.showdialog(SetIpActivity.this, "保存成功");
            }
        });
        mbinding.btSaomaSetip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetIpActivity.this, CaptureActivity.class);
                intent.putExtra("what", CaptureActivity.ScanIp);
                startActivityForResult(intent, 33);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 33 && resultCode == RESULT_OK) {
            String datas = data.getStringExtra("content");
            String s = datas.substring(0, 4);
            if (s.equals("CDHY")) {
                ip = datas.substring(4, datas.length());
                mbinding.ipeditSetip.setText(ip);
            } else {
                DialogHb.showdialog(SetIpActivity.this, "请扫描正确的二维码");
            }
        }
    }
}
