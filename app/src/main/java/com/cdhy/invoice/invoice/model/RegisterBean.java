package com.cdhy.invoice.invoice.model;

/**
 * Created by Administrator on 2016/8/12.
 */

public class RegisterBean {
    private String userName;//用户名
    private String userZsxm;//真实姓名
    private String code;
    private String passWord;
    private String rePassWord;

    public RegisterBean(String mUserName, String mUserZsxm, String mCode, String mPassWord, String mRePassWord) {
        userName = mUserName;
        userZsxm = mUserZsxm;
        code = mCode;
        passWord = mPassWord;
        rePassWord = mRePassWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String mUserName) {
        userName = mUserName;
    }

    public String getUserZsxm() {
        return userZsxm;
    }

    public void setUserZsxm(String mUserZsxm) {
        userZsxm = mUserZsxm;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String mCode) {
        code = mCode;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String mPassWord) {
        passWord = mPassWord;
    }

    public String getRePassWord() {
        return rePassWord;
    }

    public void setRePassWord(String mRePassWord) {
        rePassWord = mRePassWord;
    }
}
