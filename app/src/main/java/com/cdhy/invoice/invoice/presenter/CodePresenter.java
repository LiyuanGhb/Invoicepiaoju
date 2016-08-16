package com.cdhy.invoice.invoice.presenter;

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

public class CodePresenter implements CodeControl.Presenter {
    private CodeControl.View mView;

    public CodePresenter(CodeControl.View mView) {
        this.mView = mView;
    }

    @Override
    public void getCode() {
        String phone = mView.getPhone();
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        String requestParameter = new LoginParameter().getSmsCode(phone);
        mRequest.setDefineRequestBodyForJson(requestParameter);
        CallServer.getRequestInstance().add(0, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                mView.hintMessage("验证码发送成功");
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
