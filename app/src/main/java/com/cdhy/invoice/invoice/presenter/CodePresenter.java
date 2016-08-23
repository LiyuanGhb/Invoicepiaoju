package com.cdhy.invoice.invoice.presenter;

import android.content.Context;
import android.util.Log;

import com.cdhy.invoice.invoice.control.CodeControl;
import com.cdhy.invoice.invoice.nohttp.CallServer;
import com.cdhy.invoice.invoice.nohttp.HttpListener;
import com.cdhy.invoice.invoice.ui.requestParames.LoginParameter;
import com.cdhy.invoice.invoice.ui.util.UrlUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class CodePresenter implements CodeControl.Presenter {
    private CodeControl.View mView;
    private Context mContext;

    public CodePresenter(CodeControl.View mView) {
        this.mView = mView;
        mContext = mView.getViewContext();
    }

    @Override
    public void getCode() {
        String phone = mView.getPhone();
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        String requestParameter = new LoginParameter().getSmsCode(phone);
        mRequest.setDefineRequestBodyForJson(requestParameter);
        CallServer.getRequestInstance().add(0, mContext, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                mView.hintMessage("验证码发送成功");
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                Log.e(TAG, "CodePresenter_onFailed_url: " + url);
                Log.e(TAG, "CodePresenter_onFailed_tag: " + tag);
                Log.e(TAG, "CodePresenter_onFailed_error: " + error);
                Log.e(TAG, "CodePresenter_onFailed_resCode: " + resCode);
                Log.e(TAG, "CodePresenter_onFailed_ms: " + ms);
            }
        });
    }

    @Override
    public void start() {

    }
}
