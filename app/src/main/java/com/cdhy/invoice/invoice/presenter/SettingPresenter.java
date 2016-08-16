package com.cdhy.invoice.invoice.presenter;

import android.util.Log;

import com.cdhy.invoice.invoice.control.SettingFragmentControl;
import com.cdhy.invoice.invoice.nohttp.CallServer;
import com.cdhy.invoice.invoice.nohttp.HttpListener;
import com.cdhy.invoice.invoice.ui.requestParames.UserParameter;
import com.cdhy.invoice.invoice.ui.util.UrlUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class SettingPresenter implements SettingFragmentControl.Presenter {
    private SettingFragmentControl.View mView;

    public SettingPresenter(SettingFragmentControl.View mView) {
        this.mView = mView;
    }

    @Override
    public void getUserDescribe(String userId) {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        Log.i(TAG, "getUserDescribe: " + userId);
        String requestParameter = new UserParameter().getUserInfo(userId);
        mRequest.setDefineRequestBodyForJson(requestParameter);
        CallServer.getRequestInstance().add(0, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                // FIXME: 2016/8/15  接口下午更新暂时放在这里
                Log.i(TAG, "getUserDescribe: " + response.get().toString());
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
