package com.cdhy.invoice.invoice.presenter;

import android.util.Log;

import com.cdhy.invoice.invoice.control.UpdateDescribeControl;
import com.cdhy.invoice.invoice.model.ResultRoot;
import com.cdhy.invoice.invoice.nohttp.CallServer;
import com.cdhy.invoice.invoice.nohttp.HttpListener;
import com.cdhy.invoice.invoice.ui.requestParames.LoginParameter;
import com.cdhy.invoice.invoice.ui.requestParames.UserParameter;
import com.cdhy.invoice.invoice.ui.util.UrlUtils;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2016/8/15.
 */

public class UpdateDescribePresenter implements UpdateDescribeControl.Presenter {
    private UpdateDescribeControl.View mView;

    public UpdateDescribePresenter(UpdateDescribeControl.View mView) {
        this.mView = mView;
    }

    @Override
    public void updateUserDescribe(String zsxm) {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        mRequest.setDefineRequestBodyForJson(new UserParameter().updateUserInfo(zsxm));
        CallServer.getRequestInstance().add(0, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                ResultRoot mRoot = new Gson().fromJson(response.get().toString(), ResultRoot.class);
                mView.hintMessage(mRoot.getMsg());
                if (mRoot.getResultType().equals("SUCCESS")) {
                    mView.updateUserDescribeSuccess();
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
