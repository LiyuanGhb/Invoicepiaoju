package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.control.CodeControl;
import com.cdhy.invoice.invoice.control.RegisterControl;
import com.cdhy.invoice.invoice.databinding.LoginCodeLoginBinding;
import com.cdhy.invoice.invoice.presenter.CodePresenter;
import com.cdhy.invoice.invoice.presenter.RegisterPresenter;
import com.cdhy.invoice.invoice.ui.requestParames.LoginParameter;
import com.cdhy.invoice.invoice.ui.util.CheckMobileAndEmail;
import com.cdhy.invoice.invoice.ui.util.NetWorkUtil;

public class CodeLoginAty extends Activity implements CodeControl.View, RegisterControl.View, View.OnClickListener, TextWatcher {
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

        mBinding.forcodeCodeEtLogin.addTextChangedListener(this);
        mBinding.forcodePhoneEtLogin.addTextChangedListener(this);
        isEmptyEditText();


    }


    @Override
    public String getPhone() {
        return mBinding.forcodePhoneEtLogin.getText().toString();
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
    public void registerSuccess() {
        setButtonState(true);
        CustomApplication.index = true;
        setResult(PasswordLoginAty.CODE_LOGIN);
        finish();
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
            case R.id.back_rl_login_getcode_login:
                finish();
                break;
            case R.id.bt_login_getcode:
                if (CheckMobileAndEmail.isMobileNO(mBinding.forcodePhoneEtLogin.getText().toString())) {
                    if (NetWorkUtil.isNetworkConnected(this)) {
                        showDialog();
                    } else {
                        NetWorkUtil.showNoNetWorkDlg(this);
                    }
                } else {
                    Toast.makeText(this, "请输入正确的手机号吗", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_login_forcode:
                String phone = mBinding.forcodePhoneEtLogin.getText().toString();
                String code = mBinding.forcodeCodeEtLogin.getText().toString();
                String requestParameter = new LoginParameter().loginBySms(phone, code);
                setButtonState(false);
                mPresenter.register(requestParameter);
                break;
        }
    }

    private void setButtonState(boolean state) {
        if (state) {
            mBinding.btLoginForcode.setBackground(getResources().getDrawable(R.drawable.buttonblue));
        } else {
            mBinding.btLoginForcode.setBackground(getResources().getDrawable(R.drawable.buttonblue_0));
        }
        mBinding.btLoginForcode.setEnabled(state);
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
        if (TextUtils.isEmpty(mBinding.forcodeCodeEtLogin.getText().toString()) ||
                TextUtils.isEmpty(mBinding.forcodePhoneEtLogin.getText().toString())) {
            mBinding.btLoginForcode.setBackground(getResources().getDrawable(R.drawable.buttonblue_0));
            mBinding.btLoginForcode.setEnabled(false);
        } else {
            mBinding.btLoginForcode.setBackground(getResources().getDrawable(R.drawable.buttonblue));
            mBinding.btLoginForcode.setEnabled(true);
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
            mBinding.btLoginGetcode.setBackground(getResources().getDrawable(R.drawable.buttonblue_0));
            mBinding.btLoginGetcode.setEnabled(false);
        }

        /**
         * 计时完毕时触发
         */
        @Override
        public void onFinish() {
            mBinding.btLoginGetcode.setText("获取验证码");
            // 又重新启用获取短信验证码按钮
            mBinding.btLoginGetcode.setEnabled(true);
            mBinding.btLoginGetcode.setBackgroundResource(R.drawable.buttonblue);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            mBinding.btLoginGetcode.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
