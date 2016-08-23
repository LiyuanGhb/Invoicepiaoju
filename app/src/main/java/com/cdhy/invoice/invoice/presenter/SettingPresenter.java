package com.cdhy.invoice.invoice.presenter;

import android.content.Context;
import android.util.Log;

import com.cdhy.invoice.invoice.control.SettingFragmentControl;
import com.cdhy.invoice.invoice.model.UseDescribeBean;
import com.cdhy.invoice.invoice.nohttp.CallServer;
import com.cdhy.invoice.invoice.nohttp.HttpListener;
import com.cdhy.invoice.invoice.ui.requestParames.UserParameter;
import com.cdhy.invoice.invoice.ui.util.UrlUtils;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class SettingPresenter implements SettingFragmentControl.Presenter {
    private SettingFragmentControl.View mView;
    private Context mContext;

    public SettingPresenter(SettingFragmentControl.View mView) {
        this.mView = mView;
        mContext = mView.getViewContext();
    }

    @Override
    public void getUserDescribe(String userId) {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        Log.i(TAG, "getUserDescribe: " + userId);
        final String requestParameter = new UserParameter().getUserInfo(userId);
        mRequest.setDefineRequestBodyForJson(requestParameter);
        CallServer.getRequestInstance().add(0,mContext, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                UseDescribeBean mUseDescribeBean = new Gson().fromJson(response.get().toString(),UseDescribeBean.class);
                if(mUseDescribeBean.getResultType().equals("SUCCESS")){
                    mView.setUserDescribe(
                            mUseDescribeBean.getData().getZSXM(),
                            mUseDescribeBean.getData().getUSERNAME()

                            );
                }
                Log.i(TAG, "getUserDescribe: " + response.get().toString());
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                Log.e(TAG, "SettingPresenter_getUserDescribe_onFailed_url: " + url);
                Log.e(TAG, "SettingPresenter_getUserDescribe_onFailed_tag: " + tag);
                Log.e(TAG, "SettingPresenter_getUserDescribe_onFailed_error: " + error);
                Log.e(TAG, "SettingPresenter_getUserDescribe_onFailed_resCode: " + resCode);
                Log.e(TAG, "SettingPresenter_getUserDescribe_onFailed_ms: " + ms);
            }
        });
    }

    @Override
    public void start() {

    }
}
