package com.cdhy.invoice.invoice.ui.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by ly on 2016/7/14.  d
 */
public class SharedPreferencesHelper {
    private static final String SHARED_NAME = "shared";
    public static final String RUN_STATEMENT = "RunStatement";
    public static final String LOGIN_STATE = "LoginState";
    public static final String ATHID = "athID";
    public static final String USER_ID = "userId";
    public static final String IP = "ip";
    public static final String SAVE_IP = "saveIp";
    public static final String ZSXM = "zsxm";
    public static final String USERNAME = "userName";


    public static SharedPreferencesHelper mSharedPreferencesHelper;

    private SharedPreferences mSharedPreferences;

    public SharedPreferencesHelper(Context mContext) {
        this.mSharedPreferences = mContext.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }

    public boolean getBooleanByShared(String key, boolean devValue) {
        return mSharedPreferences.getBoolean(key, devValue);
    }

    public String getStringByShared(String key, String devValue) {
        return mSharedPreferences.getString(key, devValue);
    }

    public void setBooleanToShared(String key, boolean value) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public void setParameterToShared(String key, Object object) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        if (object instanceof String) {
            mEditor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            mEditor.putInt(key, (Integer) object);
        } else if (object instanceof Long) {
            mEditor.putLong(key, (Long) object);
        } else if (object instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            mEditor.putFloat(key, (Float) object);
        }
        mEditor.apply();
    }
}
