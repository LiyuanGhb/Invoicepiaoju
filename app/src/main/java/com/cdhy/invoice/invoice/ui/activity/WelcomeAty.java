package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.ui.util.SharedPreferencesHelper;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by ly on 2016/7/13. 闪屏
 */
public class WelcomeAty extends Activity {
    private SharedPreferencesHelper mSharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welecome_aty);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        CustomApplication.DEVICE_ID = tm.getDeviceId();

        mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        boolean loginState = mSharedPreferencesHelper.getBooleanByShared(SharedPreferencesHelper.LOGIN_STATE, false);
        CustomApplication.LOGIN_STATE = loginState;
        if (loginState) {
            CustomApplication.athID = mSharedPreferencesHelper.getStringByShared(SharedPreferencesHelper.ATHID, "");
            CustomApplication.userId = mSharedPreferencesHelper.getStringByShared(SharedPreferencesHelper.USER_ID, "");
            CustomApplication.ZSXM = mSharedPreferencesHelper.getStringByShared(SharedPreferencesHelper.ZSXM, "");
            CustomApplication.userName = mSharedPreferencesHelper.getStringByShared(SharedPreferencesHelper.USERNAME, "");
            CustomApplication.ip = mSharedPreferencesHelper.getStringByShared(SharedPreferencesHelper.IP, "");
            CustomApplication.saveIp = mSharedPreferencesHelper.getBooleanByShared(SharedPreferencesHelper.SAVE_IP, false);
        }


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeAty.this, MainAty.class));
                finish();
            }
        }, 2000);
    }
}
