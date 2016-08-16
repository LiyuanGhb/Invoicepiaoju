package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.control.CodeControl;
import com.cdhy.invoice.invoice.control.RegisterControl;
import com.cdhy.invoice.invoice.databinding.RegiseterGetcodeBinding;
import com.cdhy.invoice.invoice.presenter.CodePresenter;
import com.cdhy.invoice.invoice.presenter.RegisterPresenter;
import com.cdhy.invoice.invoice.ui.requestParames.LoginParameter;

public class RegisterAty extends Activity implements RegisterControl.View, CodeControl.View, View.OnClickListener {
    private RegiseterGetcodeBinding mBinding;
    private RegisterControl.Presenter mPresenter;
    private CodeControl.Presenter mCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.regiseter_getcode);
        initParameter();
    }

    private void initParameter() {
        mPresenter = new RegisterPresenter(this);
        mCodePresenter = new CodePresenter(this);
        mBinding.setOnClickListener(this);
    }

    @Override
    public void registerSuccess() {
        Intent mIntent = new Intent();
        mIntent.putExtra(PasswordLoginAty.USERNAME, mBinding.registerGetcodePhoneEt.getText().toString());
        mIntent.putExtra(PasswordLoginAty.PASSWORD, mBinding.registerGetcodePasswordEt.getText().toString());
        setResult(PasswordLoginAty.REGISTER_REQUEST, mIntent);
        finish();
    }

    @Override
    public void hintMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_rl_resister_getcode:
                finish();
                break;
            case R.id.bt_register_getcode:
                //获取验证码
                showDialog();
                break;
            case R.id.bt_register_getcode_register:
                //注册
                String phone = mBinding.registerGetcodePhoneEt.getText().toString();
                String code = mBinding.registerGetcodeCodeEt.getText().toString();
                String zsxm = mBinding.registerGetcodeNameEt.getText().toString();
                String password = mBinding.registerGetcodePasswordEt.getText().toString();
                // FIXME: 2016/8/12 保留
                //String rePassword = mBinding.registerGetcodePasswordtooEt.getText().toString();
                mPresenter.register(new LoginParameter().userRegist(phone,password,zsxm,code));
                break;
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("我们将发送验证码到您的手机：" + mBinding.registerGetcodePhoneEt.getText().toString())
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCodePresenter.getCode();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public String getPhone() {
        return mBinding.registerGetcodePhoneEt.getText().toString();
    }
}
