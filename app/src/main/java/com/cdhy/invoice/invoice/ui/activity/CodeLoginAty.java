package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.control.CodeControl;
import com.cdhy.invoice.invoice.control.RegisterControl;
import com.cdhy.invoice.invoice.databinding.LoginCodeLoginBinding;
import com.cdhy.invoice.invoice.presenter.CodePresenter;
import com.cdhy.invoice.invoice.presenter.RegisterPresenter;
import com.cdhy.invoice.invoice.ui.requestParames.LoginParameter;

public class CodeLoginAty extends Activity implements CodeControl.View, RegisterControl.View, View.OnClickListener {
    private LoginCodeLoginBinding mBinding;
    private RegisterControl.Presenter mPresenter;
    private CodeControl.Presenter mCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.login_code_login);
        initParamter();
    }

    private void initParamter() {
        mPresenter = new RegisterPresenter(this);
        mCodePresenter = new CodePresenter(this);
        mBinding.setOnClickListener(this);
    }


    @Override
    public String getPhone() {
        return mBinding.forcodePhoneEtLogin.getText().toString();
    }

    @Override
    public void registerSuccess() {
        setResult(PasswordLoginAty.CODE_LOGIN);
        finish();
    }

    @Override
    public void hintMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_rl_login_getcode_login:
                finish();
                break;
            case R.id.bt_login_getcode:
                showDialog();
                break;
            case R.id.bt_login_forcode:
                String phone = mBinding.forcodePhoneEtLogin.getText().toString();
                String code = mBinding.forcodeCodeEtLogin.getText().toString();
                String requestParameter = new LoginParameter().loginBySms(phone, code);
                mPresenter.register(requestParameter);
                break;
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("我们将发送验证码到您的手机：" + mBinding.forcodePhoneEtLogin.getText().toString())
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
}
