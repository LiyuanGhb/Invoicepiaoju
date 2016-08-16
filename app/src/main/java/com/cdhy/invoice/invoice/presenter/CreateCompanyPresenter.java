package com.cdhy.invoice.invoice.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.cdhy.invoice.invoice.control.CreateCompanyControl;
import com.cdhy.invoice.invoice.model.Card.Data;
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


public class CreateCompanyPresenter implements CreateCompanyControl.Presenter {
    private CreateCompanyControl.View mView;
    private Data companyBean;

    public CreateCompanyPresenter(CreateCompanyControl.View mView) {
        this.mView = mView;
    }

    @Override
    public void queryByCompanyName(String companyName) {

    }

    @Override
    public void queryByCompanySH(String sh) {

    }

    @Override
    public void createCompany() {
        companyBean = mView.getCreateCompanyDescribe();
        if (TextUtils.isEmpty(companyBean.getNAME()) || companyBean.getNAME().equals("")) {
            mView.hintMessage("公司名称不能为空");
        } else {
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
            CallServer.getRequestInstance().add(0, mRequest, new HttpListener<JSONObject>() {
                @Override
                public void onSucceed(int what, Response<JSONObject> response) {
                    ResultRoot mRoot = new Gson().fromJson(response.get().toString(), ResultRoot.class);
                    if (mRoot.getResultType().equals("SUCCESS")) {
                        mView.createCompanySuccess();
                    }
                    mView.hintMessage(mRoot.getMsg());
                }

                @Override
                public void onFailed(int what, String url, Object tag, CharSequence error, int resCode, long ms) {
                    mView.hintMessage(error.toString());
                }
            });
        }
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
            CallServer.getRequestInstance().add(0, mRequest, new HttpListener<JSONObject>() {
                @Override
                public void onSucceed(int what, Response<JSONObject> response) {
                    Log.i(TAG, "onSucceed: " + response.get());
                    ResultRoot mRoot = new Gson().fromJson(response.get().toString(), ResultRoot.class);
                    Log.i(TAG, "onSucceed: " + response.get());
                    if (mRoot.getResultType().equals("SUCCESS")) {
                        mView.updateCompanySuccess();
                    }
                    mView.hintMessage(mRoot.getMsg());
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
