package com.cdhy.invoice.invoice.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.control.PassWordLoginControl;
import com.cdhy.invoice.invoice.model.ResultRoot;
import com.cdhy.invoice.invoice.model.User.UserMessage;
import com.cdhy.invoice.invoice.model.User.UserRoot;
import com.cdhy.invoice.invoice.nohttp.CallServer;
import com.cdhy.invoice.invoice.nohttp.HttpListener;
import com.cdhy.invoice.invoice.ui.MineUrl;
import com.cdhy.invoice.invoice.ui.requestParames.LoginParameter;
import com.cdhy.invoice.invoice.ui.util.MineUtil;
import com.cdhy.invoice.invoice.ui.util.SharedPreferencesHelper;
import com.cdhy.invoice.invoice.ui.util.UrlUtils;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class PassWordLoginPresenter implements PassWordLoginControl.Presenter {
    private PassWordLoginControl.View mView;

    public PassWordLoginPresenter(PassWordLoginControl.View mView) {
        this.mView = mView;
    }

    @Override
    public void PassWordLogin(String userName, String passWord) {
        if (TextUtils.isEmpty(userName)) {
            mView.hintMessage("用户名不能为空");
        } else if (TextUtils.isEmpty(passWord)) {
            mView.hintMessage("密码不能为空");
        } else {
            Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
            mRequest.setDefineRequestBodyForJson(new LoginParameter().loginByPwd(userName, passWord));
            CallServer.getRequestInstance().add(0, mRequest, new HttpListener<JSONObject>() {
                @Override
                public void onSucceed(int what, Response<JSONObject> response) {
                    Log.i(TAG, "onSucceed: " + response.get().toString());
                    UserRoot mRoot = new Gson().fromJson(response.get().toString(), UserRoot.class);
                    mView.hintMessage(mRoot.getMsg());
                    if (mRoot.getResultType().equals("SUCCESS")) {
                        UserMessage.newInstance().setUserBean(mRoot.getData());
                        mView.PassWordForSuccessSuccess();
                    }
                }

                @Override
                public void onFailed(int what, String url, Object tag, CharSequence error, int resCode, long ms) {
                    Log.i(TAG, "onFailed: " + error.toString());
                    mView.hintMessage(error.toString());
                }
            });
        }
    }

    @Override
    public void start() {

    }

}
