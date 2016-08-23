package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.cdhy.invoice.invoice.databinding.RegiseterGetcodeBinding;
import com.cdhy.invoice.invoice.presenter.CodePresenter;
import com.cdhy.invoice.invoice.presenter.RegisterPresenter;
import com.cdhy.invoice.invoice.ui.requestParames.LoginParameter;
import com.cdhy.invoice.invoice.ui.util.CheckMobileAndEmail;
import com.cdhy.invoice.invoice.ui.util.NetWorkUtil;

public class RegisterAty extends Activity implements RegisterControl.View, CodeControl.View, View.OnClickListener, TextWatcher {
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

        mBinding.registerGetcodePhoneEt.addTextChangedListener(this);
        mBinding.registerGetcodeCodeEt.addTextChangedListener(this);
        mBinding.registerGetcodeNameEt.addTextChangedListener(this);
        mBinding.registerGetcodePasswordEt.addTextChangedListener(this);
        mBinding.registerGetcodePasswordtooEt.addTextChangedListener(this);

        mBinding.setOnClickListener(this);
        isEmptyEditText();
    }

    @Override
    public void registerSuccess() {
        setButtonState(true);
        Intent mIntent = new Intent();
        mIntent.putExtra(PasswordLoginAty.USERNAME, mBinding.registerGetcodePhoneEt.getText().toString());
        mIntent.putExtra(PasswordLoginAty.PASSWORD, mBinding.registerGetcodePasswordEt.getText().toString());
        setResult(PasswordLoginAty.REGISTER_REQUEST, mIntent);
        finish();
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
            case R.id.back_rl_resister_getcode:
                finish();
                break;
            case R.id.bt_register_getcode:
                //获取验证码
                if (CheckMobileAndEmail.isMobileNO(mBinding.registerGetcodePhoneEt.getText().toString())) {
                    if(NetWorkUtil.isNetworkConnected(this)){
                        showDialog();
                    }else{
                        NetWorkUtil.showNoNetWorkDlg(this);
                    }
                } else {
                    Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_register_getcode_register:
                //注册
                String phone = mBinding.registerGetcodePhoneEt.getText().toString();
                String code = mBinding.registerGetcodeCodeEt.getText().toString();
                String zsxm = mBinding.registerGetcodeNameEt.getText().toString();
                String password = mBinding.registerGetcodePasswordEt.getText().toString();
                // FIXME: 2016/8/12 保留
                //String rePassword = mBinding.registerGetcodePasswordtooEt.getText().toString();
                setButtonState(false);
                mPresenter.register(new LoginParameter().userRegist(phone, password, zsxm, code));
                break;
        }
    }

    private void setButtonState(boolean state){
        if(state){
            mBinding.btRegisterGetcodeRegister.setBackground(getResources().getDrawable(R.drawable.buttonblue));
        }else{
            mBinding.btRegisterGetcodeRegister.setBackground(getResources().getDrawable(R.drawable.buttonblue_0));
        }
        mBinding.btRegisterGetcodeRegister.setEnabled(state);
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

    @Override
    public void getCodeSuccess() {
        new TimeCount(60 * 1000, 1000).start();
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
        if (TextUtils.isEmpty(mBinding.registerGetcodeCodeEt.getText().toString())
                || TextUtils.isEmpty(mBinding.registerGetcodeNameEt.getText().toString())
                || TextUtils.isEmpty(mBinding.registerGetcodePasswordEt.getText().toString())
                || TextUtils.isEmpty(mBinding.registerGetcodePasswordtooEt.getText().toString())
                || TextUtils.isEmpty(mBinding.registerGetcodePhoneEt.getText().toString())) {
            mBinding.btRegisterGetcodeRegister.setBackground(getResources().getDrawable(R.drawable.buttonblue_0));
            mBinding.btRegisterGetcodeRegister.setEnabled(false);
        } else {
            mBinding.btRegisterGetcodeRegister.setBackground(getResources().getDrawable(R.drawable.buttonblue));
            mBinding.btRegisterGetcodeRegister.setEnabled(true);
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
            mBinding.btRegisterGetcode.setBackground(getResources().getDrawable(R.drawable.buttonblue_0));
            mBinding.btRegisterGetcode.setEnabled(false);
        }

        /**
         * 计时完毕时触发
         */
        @Override
        public void onFinish() {
            mBinding.btRegisterGetcode.setText("获取验证码");
            // 又重新启用获取短信验证码按钮
            mBinding.btRegisterGetcode.setEnabled(true);
            mBinding.btRegisterGetcode.setBackgroundResource(R.drawable.buttonblue);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            //mGetcode.setClickable(false);
            mBinding.btRegisterGetcode.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
