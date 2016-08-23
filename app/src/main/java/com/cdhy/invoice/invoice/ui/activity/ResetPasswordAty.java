package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Network;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.control.CodeControl;
import com.cdhy.invoice.invoice.control.RegisterControl;
import com.cdhy.invoice.invoice.databinding.RestpasswordBinding;
import com.cdhy.invoice.invoice.presenter.CodePresenter;
import com.cdhy.invoice.invoice.presenter.RegisterPresenter;
import com.cdhy.invoice.invoice.ui.requestParames.LoginParameter;
import com.cdhy.invoice.invoice.ui.util.CheckMobileAndEmail;
import com.cdhy.invoice.invoice.ui.util.NetWorkUtil;

public class ResetPasswordAty extends Activity implements CodeControl.View, RegisterControl.View, View.OnClickListener, TextWatcher {
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

        mBinding.restpasswordPasswordEt.addTextChangedListener(this);
        mBinding.restpasswordPhoneEt.addTextChangedListener(this);
        mBinding.restpasswordCodeEt.addTextChangedListener(this);
        mBinding.restpasswordPasswordtooEt.addTextChangedListener(this);

        isEmptyEditText();

    }

    @Override
    public String getPhone() {
        return mBinding.restpasswordPhoneEt.getText().toString();
    }

    @Override
    public void getCodeSuccess() {
        new TimeCount(60 * 1000, 1000).start();
    }

    @Override
    public void OnHttpListenerFailed(String error) {
        setButtonState(true);
    }

    @Override
    public void hintMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_rl_restpassword:
                finish();
                break;
            case R.id.bt_restpassword_getcode:
                if (CheckMobileAndEmail.isMobileNO(mBinding.restpasswordPhoneEt.getText().toString())) {
                    if (NetWorkUtil.isNetworkConnected(this)) {
                        showDialog();
                    } else {
                        NetWorkUtil.showNoNetWorkDlg(this);
                    }
                } else {
                    Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_restpassword_register:
                String userName = mBinding.restpasswordPhoneEt.getText().toString();
                String passWord = mBinding.restpasswordPasswordEt.getText().toString();
                // FIXME: 2016/8/12 保留
                //String rePassWord = mBinding.restpasswordPasswordtooEt.getText().toString();
                String code = mBinding.restpasswordCodeEt.getText().toString();
                setButtonState(false);
                mPresenter.register(new LoginParameter().resetPwd(userName, passWord, code));
                break;
        }
    }

    private void setButtonState(boolean state) {
        if (state) {
            mBinding.btRestpasswordRegister.setBackground(getResources().getDrawable(R.drawable.buttonblue));
        } else {
            mBinding.btRestpasswordRegister.setBackground(getResources().getDrawable(R.drawable.buttonblue_0));
        }
        mBinding.btRestpasswordRegister.setEnabled(state);
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
        setButtonState(true);
        Intent mIntent = new Intent();
        mIntent.putExtra(PasswordLoginAty.USERNAME, mBinding.restpasswordPhoneEt.getText().toString());
        mIntent.putExtra(PasswordLoginAty.PASSWORD, mBinding.restpasswordPasswordEt.getText().toString());
        setResult(PasswordLoginAty.REGISTER_REQUEST, mIntent);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        isEmptyEditText();
    }

    private void isEmptyEditText() {
        if (TextUtils.isEmpty(mBinding.restpasswordCodeEt.getText().toString())
                || TextUtils.isEmpty(mBinding.restpasswordPasswordEt.getText().toString())
                || TextUtils.isEmpty(mBinding.restpasswordPasswordtooEt.getText().toString())
                || TextUtils.isEmpty(mBinding.restpasswordPhoneEt.getText().toString())) {
            mBinding.btRestpasswordRegister.setBackground(getResources().getDrawable(R.drawable.buttonblue_0));
            mBinding.btRestpasswordRegister.setEnabled(false);
        } else {
            mBinding.btRestpasswordRegister.setBackground(getResources().getDrawable(R.drawable.buttonblue));
            mBinding.btRestpasswordRegister.setEnabled(true);
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
            mBinding.btRestpasswordGetcode.setBackground(getResources().getDrawable(R.drawable.buttonblue_0));
            mBinding.btRestpasswordGetcode.setEnabled(false);
        }

        /**
         * 计时完毕时触发
         */
        @Override
        public void onFinish() {
            mBinding.btRestpasswordGetcode.setText("获取验证码");
            // 又重新启用获取短信验证码按钮
            mBinding.btRestpasswordGetcode.setEnabled(true);
            mBinding.btRestpasswordGetcode.setBackgroundResource(R.drawable.buttonblue);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            //mGetcode.setClickable(false);
            mBinding.btRestpasswordGetcode.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
