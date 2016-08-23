package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.control.UpdateDescribeControl;
import com.cdhy.invoice.invoice.databinding.EcusercontentBinding;
import com.cdhy.invoice.invoice.presenter.UpdateDescribePresenter;
import com.cdhy.invoice.invoice.ui.fragment.SettingFragment;
import com.cdhy.invoice.invoice.ui.util.SharedPreferencesHelper;

public class UserMessageAty extends Activity implements View.OnClickListener, UpdateDescribeControl.View {
    private EcusercontentBinding mBinding;
    private UpdateDescribeControl.Presenter mPresenter;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.ecusercontent);
        initParameter();
    }

    private void initParameter() {
        mBinding.setOnClickListener(this);
        mPresenter = new UpdateDescribePresenter(this);
        mIntent = new Intent();
        Intent mIntent = getIntent();
        String zsxm = mIntent.getStringExtra(SettingFragment.PARAMETER_ZSXM);
        String phone = mIntent.getStringExtra(SettingFragment.PARAMETER_PHONE);
        mBinding.setZsxm(zsxm);
        mBinding.setPhone(phone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_rl_content:
                mIntent.putExtra("type", 0);
                setResult(SettingFragment.REQUEST_UPDATE_DESCRIBE, mIntent);
                finish();
                break;
            case R.id.name_updata_content:
                mBinding.nameContent.setEnabled(true);
                mBinding.nameContent.setFocusable(true);
                mBinding.nameContent.setFocusableInTouchMode(true);
                mBinding.nameContent.requestFocus();
                mBinding.nameContent.setSelection(mBinding.nameContent.getText().toString().length());
                InputMethodManager inputManager =
                        (InputMethodManager) mBinding.nameContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mBinding.nameContent, 0);
                mBinding.nameUpdataContent.setEnabled(false);
                mBinding.nameUpdataContent.setText("修改中");
                mBinding.nameUpdataContent.setBackground(getResources().getDrawable(R.drawable.buttonblue_0));
                mBinding.resetpasswdContent.setVisibility(View.VISIBLE);
                break;
            case R.id.resetpasswd_content:
                mPresenter.updateUserDescribe(mBinding.nameContent.getText().toString());
                break;
            case R.id.message_back:
                CustomApplication.athID = "";
                CustomApplication.LOGIN_STATE = false;
                SharedPreferencesHelper mSharedPreferencesHelper = new SharedPreferencesHelper(this);
                mSharedPreferencesHelper.setBooleanToShared(SharedPreferencesHelper.LOGIN_STATE, false);
                mSharedPreferencesHelper.setParameterToShared(SharedPreferencesHelper.ATHID, "");
                mSharedPreferencesHelper.setParameterToShared(SharedPreferencesHelper.USERNAME, "");
                mSharedPreferencesHelper.setParameterToShared(SharedPreferencesHelper.ZSXM, "");
                mIntent.putExtra("type", 2);
                setResult(SettingFragment.REQUEST_UPDATE_DESCRIBE, mIntent);
                finish();
                break;
        }
    }

    @Override
    public void updateUserDescribeSuccess() {
        mBinding.nameUpdataContent.setEnabled(true);
        mBinding.nameUpdataContent.setText("修改");
        mBinding.nameUpdataContent.setBackground(getResources().getDrawable(R.drawable.buttonblue));

        mIntent.putExtra("type", 1);
        setResult(SettingFragment.REQUEST_UPDATE_DESCRIBE, mIntent);
        finish();
    }

    @Override
    public void OnHttpListenerFailed(String error) {

    }

    @Override
    public void hintMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
