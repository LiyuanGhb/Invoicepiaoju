package com.cdhy.invoice.invoice.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.cdhy.invoice.invoice.control.CreateCompanyControl;
import com.cdhy.invoice.invoice.model.Card.Data;
import com.cdhy.invoice.invoice.model.ResultRoot;
import com.cdhy.invoice.invoice.model.VagueCompanyBean;
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

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class CreateCompanyPresenter implements CreateCompanyControl.Presenter {
    private CreateCompanyControl.View mView;
    private Data companyBean;
    private Context mContext;

    public CreateCompanyPresenter(CreateCompanyControl.View mView) {
        this.mView = mView;
        mContext = mView.getViewContext();
    }

    @Override
    public void queryByCompanyName() {
        String companyName = mView.getCompanyName();
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.getcardinfo, RequestMethod.POST);
        Map<String, String> mMap = new HashMap<>();
        mMap.put("name", companyName);
        mRequest.set(mMap);
        CallServer.getRequestInstance().add(0, mContext, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                Log.i(TAG, "queryByCompanyNameSucceed: " + response.get());
                VagueCompanyBean vMBean = new Gson().fromJson(response.get().toString(), VagueCompanyBean.class);
                if (vMBean.getResultType().equals("SUCCESS")) {
                    mView.queryByCompanyNameSuccess(vMBean);
                }
                if (vMBean.getMsg().equals("连接超时")) {
                    mView.hintMessage("连接超时");
                    mView.OnHttpListenerFailed("");
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                Log.e(TAG, "CreateCompanyPresenter_queryByCompanyName_onFailed_url: " + url);
                Log.e(TAG, "CreateCompanyPresenter_queryByCompanyName_onFailed_tag: " + tag);
                Log.e(TAG, "CreateCompanyPresenter_queryByCompanyName_onFailed_error: " + error);
                Log.e(TAG, "CreateCompanyPresenter_queryByCompanyName_onFailed_resCode: " + resCode);
                Log.e(TAG, "CreateCompanyPresenter_queryByCompanyName_onFailed_ms: " + ms);
                mView.OnHttpListenerFailed(error);
            }
        });
    }

    @Override
    public void queryByCompanySH() {
        String companySH = mView.getCompanySH();
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.getcardinfo, RequestMethod.POST);
        Map<String, String> mMap = new HashMap<>();
        mMap.put("nsrsbh", companySH);
        mRequest.set(mMap);
        CallServer.getRequestInstance().add(0, mContext, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                Log.i(TAG, "queryByCompanySHSucceed: " + response.get());
                VagueCompanyBean vMBean = new Gson().fromJson(response.get().toString(), VagueCompanyBean.class);
                if (vMBean.getResultType().equals("SUCCESS")) {
                    mView.queryByCompanySHSuccess(vMBean);
                }
                if (vMBean.getMsg().equals("连接超时")) {
                    mView.hintMessage("连接超时");
                    mView.OnHttpListenerFailed("");
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                Log.e(TAG, "CreateCompanyPresenter_queryByCompanySH_onFailed_url: " + url);
                Log.e(TAG, "CreateCompanyPresenter_queryByCompanySH_onFailed_tag: " + tag);
                Log.e(TAG, "CreateCompanyPresenter_queryByCompanyName_onFailed_error: " + error);
                Log.e(TAG, "CreateCompanyPresenter_queryByCompanySH_onFailed_resCode: " + resCode);
                Log.e(TAG, "CreateCompanyPresenter_queryByCompanySH_onFailed_ms: " + ms);
                mView.OnHttpListenerFailed(error);
            }
        });
    }

    @Override
    public void createCompany() {
        companyBean = mView.getCreateCompanyDescribe();
        final Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        final String resultData = new CompanyParameter().addCom(
                companyBean.getNAME(),
                companyBean.getNSRSBH(),
                companyBean.getADDRESS(),
                companyBean.getPHONE(),
                companyBean.getKHH(),
                companyBean.getYHZH()
        );
        mRequest.setDefineRequestBodyForJson(resultData);
        CallServer.getRequestInstance().add(0, mContext, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                ResultRoot mRoot = new Gson().fromJson(response.get().toString(), ResultRoot.class);
                if (mRoot.getResultType().equals("SUCCESS")) {
                    mView.createCompanySuccess();
                }
                if (mRoot.getMsg().equals("连接超时")) {
                    mView.OnHttpListenerFailed("");
                }
                mView.hintMessage(mRoot.getMsg());
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                Log.e(TAG, "CreateCompanyPresenter_CreateCompany_onFailed_url: " + url);
                Log.e(TAG, "CreateCompanyPresenter_CreateCompany_onFailed_tag: " + tag);
                Log.e(TAG, "CreateCompanyPresenter_CreateCompany_onFailed_error: " + error);
                Log.e(TAG, "CreateCompanyPresenter_CreateCompany_onFailed_resCode: " + resCode);
                Log.e(TAG, "CreateCompanyPresenter_CreateCompany_onFailed_ms: " + ms);
                mView.OnHttpListenerFailed(error);
            }
        });

    }

    @Override
    public void updateCompany() {
        companyBean = mView.getCreateCompanyDescribe();
        if (TextUtils.isEmpty(companyBean.getNAME()) || companyBean.getNAME().equals("")) {
            mView.hintMessage("公司名称不能为空");
        } else {
            final Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
            final String resultData = new CompanyParameter().updatecom(
                    companyBean.getID(),
                    companyBean.getUSERID(),
                    companyBean.getNAME(),
                    companyBean.getNSRSBH(),
                    companyBean.getADDRESS(),
                    companyBean.getPHONE(),
                    companyBean.getKHH(),
                    companyBean.getYHZH()
            );
            mRequest.setDefineRequestBodyForJson(resultData);
            CallServer.getRequestInstance().add(0, mContext, mRequest, new HttpListener<JSONObject>() {
                @Override
                public void onSucceed(int what, Response<JSONObject> response) {
                    Log.i(TAG, "onSucceed: " + response.get());
                    ResultRoot mRoot = new Gson().fromJson(response.get().toString(), ResultRoot.class);
                    Log.i(TAG, "onSucceed: " + response.get());
                    if (mRoot.getResultType().equals("SUCCESS")) {
                        mView.updateCompanySuccess();
                    }
                    if (mRoot.getMsg().equals("连接超时")) {
                        mView.OnHttpListenerFailed("");
                    }
                    mView.hintMessage(mRoot.getMsg());
                }

                @Override
                public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                    Log.e(TAG, "CreateCompanyPresenter_UpdateCompany_onFailed_url: " + url);
                    Log.e(TAG, "CreateCompanyPresenter_UpdateCompany_onFailed_tag: " + tag);
                    Log.e(TAG, "CreateCompanyPresenter_UpdateCompany_onFailed_error: " + error);
                    Log.e(TAG, "CreateCompanyPresenter_UpdateCompany_onFailed_resCode: " + resCode);
                    Log.e(TAG, "CreateCompanyPresenter_UpdateCompany_onFailed_ms: " + ms);
                    mView.OnHttpListenerFailed(error);
                }
            });
        }
    }

    @Override
    public void start() {

    }
}
