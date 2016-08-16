package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.control.PassWordLoginControl;
import com.cdhy.invoice.invoice.databinding.PasswordLoginAtyBinding;
import com.cdhy.invoice.invoice.model.User.Data;
import com.cdhy.invoice.invoice.presenter.PassWordLoginPresenter;
import com.cdhy.invoice.invoice.ui.fragment.SettingFragment;
import com.github.lzyzsd.randomcolor.RandomColor;

/**
 * Created by ly on 2016/7/13. 账号密码登陆页
 */
public class PasswordLoginAty extends Activity implements View.OnClickListener, PassWordLoginControl.View {
    private PasswordLoginAtyBinding mPasswordLoginAtyBinding;
    private PassWordLoginControl.Presenter mPresenter;
    public static final int REGISTER_REQUEST = 1;
    public static final int CODE_LOGIN = 2;
    public static final String USERNAME = "userName";
    public static final String PASSWORD = "passWord";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPasswordLoginAtyBinding = DataBindingUtil.setContentView(this, R.layout.password_login_aty);
        init();
    }

    private void init() {
        mPresenter = new PassWordLoginPresenter(this);
        mPasswordLoginAtyBinding.setOnClickListener(this);
        setNameColor();
    }

    /*给顶部按钮设置随机颜色*/
    private void setNameColor() {
        RandomColor mRandomColor = new RandomColor();
        int mColor = mRandomColor.randomColor();
        GradientDrawable mGradientDrawable =
                (GradientDrawable) mPasswordLoginAtyBinding.pwdBtnLoginTop.getBackground();
        mGradientDrawable.setColor(mColor);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pwd_btn_login:
                String userName = mPasswordLoginAtyBinding.pwdEtPhone.getText().toString();
                String passWord = mPasswordLoginAtyBinding.pwdEtPassword.getText().toString();
                mPresenter.PassWordLogin(userName, passWord);
                break;
            case R.id.current_rl_back_current:
                finish();
                break;
            case R.id.pwd_tv_register:
                startActivityForResult(new Intent(this, RegisterAty.class), REGISTER_REQUEST);
                break;
            case R.id.pwd_tv_forget:
                startActivityForResult(new Intent(this, ResetPasswordAty.class), REGISTER_REQUEST);
                break;
            case R.id.pwd_tv_codeLogin:
                startActivity(new Intent(this, CodeLoginAty.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case REGISTER_REQUEST:
                String userName = data.getStringExtra(USERNAME);
                String passWord = data.getStringExtra(PASSWORD);
                mPasswordLoginAtyBinding.pwdEtPhone.setText(userName);
                mPasswordLoginAtyBinding.pwdEtPhone.setSelection(userName.length());

                mPasswordLoginAtyBinding.pwdEtPassword.setText(passWord);
                mPasswordLoginAtyBinding.pwdEtPassword.setSelection(passWord.length());
                break;
            case CODE_LOGIN:
                LoginSuccess();
                break;

        }
    }

    @Override
    public void PassWordForSuccessSuccess() {
        LoginSuccess();
    }

    private void LoginSuccess( ) {
        setResult(SettingFragment.REQUEST_LOGIN);
        finish();
    }

    @Override
    public void hintMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
