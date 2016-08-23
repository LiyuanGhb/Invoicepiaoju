package com.cdhy.invoice.invoice.presenter;

import android.content.Context;
import android.util.Log;

import com.cdhy.invoice.invoice.control.RegisterControl;
import com.cdhy.invoice.invoice.model.ResultRoot;
import com.cdhy.invoice.invoice.nohttp.CallServer;
import com.cdhy.invoice.invoice.nohttp.HttpListener;
import com.cdhy.invoice.invoice.ui.util.UrlUtils;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class RegisterPresenter implements RegisterControl.Presenter {
    private RegisterControl.View mView;
    private Context mContext;

    public RegisterPresenter(RegisterControl.View mView) {
        this.mView = mView;
        mContext = mView.getViewContext();
    }

    @Override
    public void register(String requestParameter) {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        mRequest.setDefineRequestBodyForJson(requestParameter);
        CallServer.getRequestInstance().add(0,mContext, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                ResultRoot mRoot = new Gson().fromJson(response.get().toString(), ResultRoot.class);
                mView.hintMessage(mRoot.getMsg());
                if (mRoot.getResultType().equals("SUCCESS")) {
                    mView.registerSuccess();
                }else{
                    mView.OnHttpListenerFailed("");
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                Log.e(TAG, "RegisterPresenter_register_onFailed_url: " + url);
                Log.e(TAG, "RegisterPresenter_register_onFailed_tag: " + tag);
                Log.e(TAG, "RegisterPresenter_register_onFailed_error: " + error);
                Log.e(TAG, "RegisterPresenter_register_onFailed_resCode: " + resCode);
                Log.e(TAG, "RegisterPresenter_register_onFailed_ms: " + ms);
                mView.OnHttpListenerFailed(error);
            }
        });
    }

    @Override
    public void start() {

    }
}
