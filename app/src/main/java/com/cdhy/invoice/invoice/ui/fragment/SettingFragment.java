package com.cdhy.invoice.invoice.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.control.SettingFragmentControl;
import com.cdhy.invoice.invoice.databinding.InfoFragmentBinding;
import com.cdhy.invoice.invoice.model.User.Data;
import com.cdhy.invoice.invoice.model.User.UserMessage;
import com.cdhy.invoice.invoice.presenter.SettingPresenter;
import com.cdhy.invoice.invoice.ui.activity.DescribeAty;
import com.cdhy.invoice.invoice.ui.activity.PasswordLoginAty;
import com.cdhy.invoice.invoice.ui.activity.SetComActivity;
import com.cdhy.invoice.invoice.ui.activity.SetIpActivity;
import com.cdhy.invoice.invoice.ui.activity.UserMessageAty;
import com.cdhy.invoice.invoice.ui.util.DialogHb;
import com.cdhy.invoice.invoice.ui.util.SharedPreferencesHelper;
import com.cdhy.invoice.invoice.ui.util.UpdateVersionService;

public class SettingFragment extends Fragment implements View.OnClickListener, SettingFragmentControl.View {
    private InfoFragmentBinding mBinding;
    private SettingFragmentControl.Presenter mPresenter;
    private SharedPreferencesHelper mSharedPreferencesHelper;
    /*登陆*/
    public static final int REQUEST_LOGIN = 1;
    /*修改用户信息*/
    public static final int REQUEST_UPDATE_DESCRIBE = 2;
    /*操作类型 0无操作 1修改 2退出登录*/
    public int FUNCTION_TYPE = 0;

    public static final String PARAMETER_ZSXM = "zsxm";
    public static final String PARAMETER_PHONE = "phone";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.bind(inflater.inflate(R.layout.info_fragment, container, false));
        init();
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void init() {
        mBinding.setOnClickListener(this);
        mPresenter = new SettingPresenter(this);

        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());
        if (CustomApplication.LOGIN_STATE) {
            mPresenter.getUserDescribe(CustomApplication.athID);
            // FIXME: 2016/8/15 接口更新后修改
            mBinding.infoUserNameTv.setText(CustomApplication.ZSXM);
            mBinding.infoUserNameBottomTv.setText(CustomApplication.userName);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_bbgx_rl:
                /*版本更新*/
                updateVersionService();
                break;

            case R.id.info_bbh_rl:
                /*版本号*/
                DialogHb.showdialog(getActivity(), "服务暂未开放");
                break;

            case R.id.info_czsm_rl:
                /*操作说明*/
                startActivity(new Intent(getActivity(), DescribeAty.class));
                break;

            case R.id.info_fwxy_rl:
                /*服务协议*/
                DialogHb.showdialog(getActivity(), "服务暂未开放");
                break;

            case R.id.info_head_rl:
                /*用户登录 验证用户是否登陆 登陆：跳转UserMessageAty 没有登录：PassWordLoginAty*/
                if (CustomApplication.LOGIN_STATE) {
                    Intent mIntent = new Intent(getActivity(), UserMessageAty.class);
                    mIntent.putExtra(PARAMETER_ZSXM, mBinding.infoUserNameTv.getText().toString());
                    mIntent.putExtra(PARAMETER_PHONE, mBinding.infoUserNameBottomTv.getText().toString());
                    startActivityForResult(mIntent, REQUEST_UPDATE_DESCRIBE);
                } else {
                    startActivityForResult(new Intent(getActivity(), PasswordLoginAty.class), REQUEST_LOGIN);
                }
                break;

            case R.id.info_mpsz_rl:
                /*默认名片设置*/
                SetComActivity.what = 1;
                startActivity(new Intent(getActivity(), SetComActivity.class));
                break;

            case R.id.info_qyrz_rl:
                /*企业认证*/
                if (CustomApplication.LOGIN_STATE) {
                    SetComActivity.what = 2;
                    startActivity(new Intent(getActivity(), SetComActivity.class));
                } else {
                    startActivityForResult(new Intent(getActivity(), PasswordLoginAty.class), REQUEST_LOGIN);
                }
                break;
            case R.id.info_smqsz_rl:
                /*扫描枪设置*/
                startActivity(new Intent(getActivity(), SetIpActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode) {
            CustomApplication.index = true;
            switch (requestCode) {
                case REQUEST_LOGIN:
                    Data userBean = UserMessage.newInstance().getUserBean();
                    mBinding.infoUserNameTv.setText(userBean.getZSXM());
                    mBinding.infoUserNameBottomTv.setText(userBean.getUSERNAME());
                    CustomApplication.LOGIN_STATE = true;
                    CustomApplication.athID = userBean.getAthID();
                    CustomApplication.userId = userBean.getID();
                    CustomApplication.ZSXM = userBean.getZSXM();
                    CustomApplication.userName = userBean.getUSERNAME();

                    mSharedPreferencesHelper.setBooleanToShared(SharedPreferencesHelper.LOGIN_STATE, true);
                    mSharedPreferencesHelper.setParameterToShared(SharedPreferencesHelper.ATHID, userBean.getAthID());
                    mSharedPreferencesHelper.setParameterToShared(SharedPreferencesHelper.USER_ID, userBean.getID());
                    mSharedPreferencesHelper.setParameterToShared(SharedPreferencesHelper.ZSXM, userBean.getZSXM());
                    mSharedPreferencesHelper.setParameterToShared(SharedPreferencesHelper.USERNAME, userBean.getUSERNAME());
                    break;
                case REQUEST_UPDATE_DESCRIBE:
                    switch (FUNCTION_TYPE = data.getIntExtra("type", 0)) {
                        case 0:
                            break;
                        case 1:
                            mPresenter.getUserDescribe(CustomApplication.athID);
                            break;
                        case 2:
                            mBinding.infoUserNameTv.setText("未登陆");
                            mBinding.infoUserNameBottomTv.setText("未登陆");
                            break;
                    }
                    break;
            }
        }
    }

    @Override
    public void setUserDescribe(String userName, String userDescribe) {
        mBinding.infoUserNameTv.setText(userName);
        mBinding.infoUserNameBottomTv.setText(userDescribe);
    }

    @Override
    public void OnHttpListenerFailed(String error) {

    }

    @Override
    public void hintMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    /**
     * 检查版本更新
     */
    private void updateVersionService() {
        String url = "http://www.hydzfp.com/test/wxfp/checkVersion/check.do";//在线升级更新
        UpdateVersionService.chose = 1;
        UpdateVersionService updateVersionService = new UpdateVersionService(url, getActivity());// 创建更新业务对象
        updateVersionService.checkUpdate();// 调用检查更新的方法,如果可以更新.就更新.不能更新就提示已经是最新的版本了
    }
}
