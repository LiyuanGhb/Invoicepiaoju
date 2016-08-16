package com.cdhy.invoice.invoice.presenter;

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

public class RegisterPresenter implements RegisterControl.Presenter {
    private RegisterControl.View mView;

    public RegisterPresenter(RegisterControl.View mView) {
        this.mView = mView;
    }

    @Override
    public void register(String requestParameter) {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        mRequest.setDefineRequestBodyForJson(requestParameter);
        CallServer.getRequestInstance().add(0, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                ResultRoot mRoot = new Gson().fromJson(response.get().toString(), ResultRoot.class);
                mView.hintMessage(mRoot.getMsg());
                if (mRoot.getResultType().equals("SUCCESS")) {
                    mView.registerSuccess();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, CharSequence error, int resCode, long ms) {
                mView.hintMessage(error.toString());
            }
        });
    }

    @Override
    public void start() {

    }
}
