package com.cdhy.invoice.invoice.presenter;

import android.content.Context;
import android.util.Log;

import com.cdhy.invoice.invoice.control.HomeFragmentControl;
import com.cdhy.invoice.invoice.model.Card.CompanyRoot;
import com.cdhy.invoice.invoice.model.ResultRoot;
import com.cdhy.invoice.invoice.nohttp.CallServer;
import com.cdhy.invoice.invoice.nohttp.HttpListener;
import com.cdhy.invoice.invoice.ui.requestParames.CompanyParameter;
import com.cdhy.invoice.invoice.ui.util.UrlUtils;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class HomeFragmentPresenter implements HomeFragmentControl.Presenter {
    private HomeFragmentControl.HomeFragmentView mView;
    private CompanyParameter mParameter;
    private Context mContext;

    public HomeFragmentPresenter(HomeFragmentControl.HomeFragmentView mView) {
        this.mView = mView;
        mParameter = new CompanyParameter();
        mContext = mView.getViewContext();
    }

    @Override
    public void deleteCompany(String params) {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        mRequest.setDefineRequestBodyForJson(mParameter.deletecom(params));
        CallServer.getRequestInstance().add(0, mContext, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                ResultRoot mRoot = new Gson().fromJson(response.get().toString(), ResultRoot.class);
                if (mRoot.getResultType().equals("SUCCESS")) {
                    mView.deleteCompanySuccess();
                }
                mView.hintMessage(mRoot.getMsg());
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                Log.e(TAG, "HomeFragmentPresenter_deleteCompany_onFailed_url: " + url);
                Log.e(TAG, "HomeFragmentPresenter_deleteCompany_onFailed_tag: " + tag.toString());
                Log.e(TAG, "HomeFragmentPresenter_deleteCompany_onFailed_error: " + error);
                Log.e(TAG, "HomeFragmentPresenter_deleteCompany_onFailed_resCode: " + resCode);
                Log.e(TAG, "HomeFragmentPresenter_deleteCompany_onFailed_ms: " + ms);
            }
        });
    }

    @Override
    public void queryCompany() {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        mRequest.setDefineRequestBodyForJson(mParameter.fincomList());
        CallServer.getRequestInstance().add(0, mContext, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                Log.e(TAG, "queryCompanySucceed: " + response.get());
                CompanyRoot mCompanyRoot = new Gson().fromJson(response.get().toString(), CompanyRoot.class);
                if (mCompanyRoot.getResultType().equals("SUCCESS")) {
                    mView.queryCompanySuccess(mCompanyRoot);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                Log.e(TAG, "HomeFragmentPresenter_queryCompany_onFailed_url: " + url);
                Log.e(TAG, "HomeFragmentPresenter_queryCompany_onFailed_tag: " + tag);
                Log.e(TAG, "HomeFragmentPresenter_queryCompany_onFailed_error: " + error);
                Log.e(TAG, "HomeFragmentPresenter_queryCompany_onFailed_resCode: " + resCode);
                Log.e(TAG, "HomeFragmentPresenter_queryCompany_onFailed_ms: " + ms);
            }
        });
    }

    @Override
    public void start() {

    }
}
