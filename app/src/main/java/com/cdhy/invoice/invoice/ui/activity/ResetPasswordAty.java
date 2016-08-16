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
import com.cdhy.invoice.invoice.databinding.RestpasswordBinding;
import com.cdhy.invoice.invoice.presenter.CodePresenter;
import com.cdhy.invoice.invoice.presenter.RegisterPresenter;
import com.cdhy.invoice.invoice.ui.requestParames.LoginParameter;

public class ResetPasswordAty extends Activity implements CodeControl.View, RegisterControl.View, View.OnClickListener {
    private RestpasswordBinding mBinding;
    private RegisterControl.Presenter mPresenter;
    private CodeControl.Presenter mCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.restpassword);
        initParameter();
    }

    private void initParameter() {
        mBinding.setOnClickListener(this);
        mPresenter = new RegisterPresenter(this);
        mCodePresenter = new CodePresenter(this);
    }

    @Override
    public String getPhone() {
        return mBinding.restpasswordPhoneEt.getText().toString();
    }

    @Override
    public void hintMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_rl_restpassword:
                finish();
                break;
            case R.id.bt_restpassword_getcode:
                showDialog();
                break;
            case R.id.bt_restpassword_register:
                String userName = mBinding.restpasswordPhoneEt.getText().toString();
                String passWord = mBinding.restpasswordPasswordEt.getText().toString();
                // FIXME: 2016/8/12 保留
                //String rePassWord = mBinding.restpasswordPasswordtooEt.getText().toString();
                String code = mBinding.restpasswordCodeEt.getText().toString();
                mPresenter.register(new LoginParameter().resetPwd(userName,passWord,code));
                break;
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("我们将发送验证码到您的手机：" + mBinding.restpasswordPhoneEt.getText().toString())
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
    public void registerSuccess() {
        Intent mIntent = new Intent();
        mIntent.putExtra(PasswordLoginAty.USERNAME, mBinding.restpasswordPhoneEt.getText().toString());
        mIntent.putExtra(PasswordLoginAty.PASSWORD, mBinding.restpasswordPasswordEt.getText().toString());
        setResult(PasswordLoginAty.REGISTER_REQUEST);
        finish();
    }
}
