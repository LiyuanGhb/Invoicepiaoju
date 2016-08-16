package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.control.UpdateDescribeControl;
import com.cdhy.invoice.invoice.databinding.EcusercontentBinding;
import com.cdhy.invoice.invoice.model.User.Data;
import com.cdhy.invoice.invoice.presenter.UpdateDescribePresenter;
import com.cdhy.invoice.invoice.ui.custom.CustomContain;
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
        Button mButton = (Button) findViewById(R.id.message_back);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomApplication.isLogin = false;
                finish();
            }
        });
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
                mIntent.putExtra("type",0);
                setResult(SettingFragment.REQUEST_UPDATE_DESCRIBE,mIntent);
                finish();
                break;
            case R.id.name_updata_content:
                mPresenter.updateUserDescribe(mBinding.nameContent.getText().toString());
                break;
            case R.id.message_back:
                CustomApplication.athID = "";
                CustomApplication.LOGIN_STATE = false;

                SharedPreferencesHelper mSharedPreferencesHelper = new SharedPreferencesHelper(this);
                mSharedPreferencesHelper.setBooleanToShared(SharedPreferencesHelper.LOGIN_STATE, false);
                mSharedPreferencesHelper.setParameterToShared(SharedPreferencesHelper.ATHID, "");
                mIntent.putExtra("type", 2);
                setResult(SettingFragment.REQUEST_UPDATE_DESCRIBE, mIntent);
                finish();
                break;
        }
    }

    @Override
    public void updateUserDescribeSuccess() {
        mIntent.putExtra("type", 1);
        setResult(SettingFragment.REQUEST_UPDATE_DESCRIBE, mIntent);
        finish();
    }

    @Override
    public void hintMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
