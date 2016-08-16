package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.control.CreateCompanyControl;
import com.cdhy.invoice.invoice.databinding.UpdateCompanyBinding;
import com.cdhy.invoice.invoice.model.Card.Data;
import com.cdhy.invoice.invoice.presenter.CreateCompanyPresenter;
import com.cdhy.invoice.invoice.ui.fragment.HomeFragment;

import static android.content.ContentValues.TAG;


public class UpdateCompanyAty extends Activity implements CreateCompanyControl.View, View.OnClickListener {
    private UpdateCompanyBinding mBinding;
    private Data companyBean;
    private CreateCompanyPresenter mCompanyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.update_company);
        init();
    }

    private void init() {
        companyBean = (Data) getIntent().getSerializableExtra("companyBean");
        mBinding.updateQymc.setDetails(companyBean.getNAME());
        mBinding.updateQysh.setDetails(companyBean.getNSRSBH());
        mBinding.updateQydz.setDetails(companyBean.getADDRESS());
        mBinding.updateQydh.setDetails(companyBean.getPHONE());
        mBinding.updateKhh.setDetails(companyBean.getKHH());
        mBinding.updateYhzh.setDetails(companyBean.getYHZH());


        mBinding.setOnCliclistener(this);

        mCompanyPresenter = new CreateCompanyPresenter(this);
    }

    @Override
    public void createCompanySuccess() {
        /*创建公司和修改公司用的同一个借口 修改公司不做创建工作*/
    }

    @Override
    public void updateCompanySuccess() {
        setResult(HomeFragment.UPDATE_COMPANY);
        finish();
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
        String companyName = mBinding.updateQymc.getDetails();
        String companySH = mBinding.updateQysh.getDetails();
        String companyAddress = mBinding.updateQydz.getDetails();
        String companyPhone = mBinding.updateQydh.getDetails();
        String companyKhh = mBinding.updateKhh.getDetails();
        String companyYhzh = mBinding.updateYhzh.getDetails();
        Log.i(TAG, "companyName: " + companyName);
        Log.i(TAG, "companySH: " + companySH);
        Log.i(TAG, "companyAddress: " + companyAddress);
        Log.i(TAG, "companyPhone: " + companyPhone);
        Log.i(TAG, "companyKhh: " + companyKhh);
        Log.i(TAG, "companyYhzh: " + companyYhzh);
        companyBean.setNAME(companyName);
        companyBean.setNSRSBH(companySH);
        companyBean.setADDRESS(companyAddress);
        companyBean.setPHONE(companyPhone);
        companyBean.setKHH(companyKhh);
        companyBean.setYHZH(companyYhzh);
        return companyBean;
    }

    @Override
    public void onClick(View v) {
        mCompanyPresenter.updateCompany();
    }
}
