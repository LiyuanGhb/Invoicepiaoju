package com.cdhy.invoice.invoice;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;

import com.yolanda.nohttp.NoHttp;

/**
 * Created by ly on 2016/7/15. 自定义全局location
 */
public class CustomApplication extends Application {
    public static String DEVICE_ID;//手机唯一标识
    public static boolean LOGIN_STATE;//登陆状态是否登陆
    public static String athID;//用户唯一标识
    public static String userId;
    public static int isIntent=0;
    public static String ip;
    public static boolean saveIp;
    public static String ZSXM;
    public static String userName;

    public static boolean mustUp = false;//

    public static boolean index = false;//判断数据是否更新

    /*测试字段用于判断是否登陆*/
    public static boolean isLogin = false;
    /*测试字段用于区分用户类型 0:普通用户 1：审核员 2：管理员*/
    public static int userType = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        //获取手机唯一标识
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        DEVICE_ID = tm.getDeviceId();
        NoHttp.initialize(this);
    }

    public static void connectionProgressBar() {
    }
}
