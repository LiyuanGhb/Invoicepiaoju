package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.control.CreateCompanyControl;
import com.cdhy.invoice.invoice.databinding.CurrentBinding;
import com.cdhy.invoice.invoice.model.Card.Data;
import com.cdhy.invoice.invoice.presenter.CreateCompanyPresenter;
import com.cdhy.invoice.invoice.ui.fragment.HomeFragment;

/**
 * Created by ly on 2016/6/13. createCompany
 */
public class CurrentActivity extends Activity implements CreateCompanyControl.View, View.OnClickListener {
    private CurrentBinding mBinding;
    private CreateCompanyPresenter mCompanyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.current);
        initParams();
    }

    private void initParams() {
        mBinding.setOnCliclistener(this);
        mBinding.currentQymc.setFunctionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mBinding.currentQysh.setFunctionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCompanyPresenter = new CreateCompanyPresenter(this);
    }

    @Override
    public void onClick(View v) {
        mCompanyPresenter.createCompany();
    }


    @Override
    public void createCompanySuccess() {
        setResult(HomeFragment.ADD_COMPANY);
        finish();
    }

    @Override
    public void updateCompanySuccess() {
        /*创建公司和修改公司用的同一个借口 创建公司不做修改工作*/
    }

    @Override
    public void queryByCompanyNameSuccess() {

    }

    @Override
    public void queryByCompanySHSuccess() {

    }

    @Override
    public void hintMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Data getCreateCompanyDescribe() {
        Data companyBean = new Data();
        String companyName = mBinding.currentQymc.getDetails();
        String companySH = mBinding.currentQysh.getDetails();
        String companyAddress = mBinding.currentQydz.getDetails();
        String companyPhone = mBinding.currentQydh.getDetails();
        String companyKhh = mBinding.currentKhh.getDetails();
        String companyYhzh = mBinding.currentYhzh.getDetails();
        companyBean.setNAME(companyName);
        companyBean.setNSRSBH(companySH);
        companyBean.setADDRESS(companyAddress);
        companyBean.setPHONE(companyPhone);
        companyBean.setKHH(companyKhh);
        companyBean.setYHZH(companyYhzh);
        return companyBean;
    }


}