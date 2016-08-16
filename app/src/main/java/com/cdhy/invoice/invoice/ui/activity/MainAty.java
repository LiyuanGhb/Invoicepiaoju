package com.cdhy.invoice.invoice.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.control.MainAtyControl;
import com.cdhy.invoice.invoice.databinding.MainLayoutBinding;
import com.cdhy.invoice.invoice.databinding.NavigationBottomBinding;
import com.cdhy.invoice.invoice.presenter.MainPresenter;
import com.cdhy.invoice.invoice.ui.BaseActivity;
import com.cdhy.invoice.invoice.ui.fragment.CompanyFragment;
import com.cdhy.invoice.invoice.ui.fragment.HomeFragment;
import com.cdhy.invoice.invoice.ui.fragment.InvoiceFragment;
import com.cdhy.invoice.invoice.ui.fragment.SettingFragment;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by zby on 2016/8/5. 首页
 */
public class MainAty extends BaseActivity implements View.OnClickListener, MainAtyControl.View {
    private MainLayoutBinding mBinding;
    private MainPresenter mPresenter;
    private Map<String, Boolean> mIsCreateFragment;

    private HomeFragment mHomeFragment = null;
    private InvoiceFragment mInvoiceFragment = null;
    private CompanyFragment mCompanyFragment = null;
    private SettingFragment mSettingFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_layout);
        initParams();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("System", "onDestroy: ");
    }

    private void initParams() {
        NavigationBottomBinding mNavigationBottomBinding =
                DataBindingUtil.bind(mBinding.mainNavigationBottom.getRoot());
        mNavigationBottomBinding.setOnClickListener(this);

        mPresenter = new MainPresenter(this);
        mIsCreateFragment = new HashMap<>();

        mPresenter.showFragment(new HomeFragment(), "票聚");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_home:
              /*  if (mIsCreateFragment.get("HomeFragment")) {
                    visibleFragment(true, mHomeFragment);
                    visibleFragment(false, mInvoiceFragment);
                    visibleFragment(false, mCompanyFragment);
                    visibleFragment(false, mSettingFragment);
                } else {*/
                    mHomeFragment = new HomeFragment();
                    mPresenter.showFragment(mHomeFragment, "首页");
               // }
                break;
            case R.id.nav_invoice:
             /*   if (mIsCreateFragment.get("InvoiceFragment")) {
                    visibleFragment(false, mHomeFragment);
                    visibleFragment(true, mInvoiceFragment);
                    visibleFragment(false, mCompanyFragment);
                    visibleFragment(false, mSettingFragment);
                } else {*/
                    mInvoiceFragment = new InvoiceFragment();
                    mPresenter.showFragment(mInvoiceFragment, "我的发票");
                /*}*/
                break;
            case R.id.nav_company:
             /*   if (mIsCreateFragment.get("CompanyFragment")) {
                    visibleFragment(false, mHomeFragment);
                    visibleFragment(false, mInvoiceFragment);
                    visibleFragment(true, mCompanyFragment);
                    visibleFragment(false, mSettingFragment);
                } else {*/
                    mCompanyFragment = new CompanyFragment();
                    mPresenter.showFragment(mCompanyFragment, "我的名片");
               /* }*/
                break;
            case R.id.nav_info:
       /*         if (mIsCreateFragment.get("SettingFragment")) {
                    visibleFragment(false, mHomeFragment);
                    visibleFragment(false, mInvoiceFragment);
                    visibleFragment(false, mCompanyFragment);
                    visibleFragment(true, mSettingFragment);
                } else {*/
                    mSettingFragment = new SettingFragment();
                    mPresenter.showFragment(mSettingFragment, "我的信息");
           /*     }*/
                break;
        }
    }

    private FragmentManager mFragmentManager;

    @Override
    public void initFragmentAndTitle(Fragment fragment, String title) {
        mBinding.setTitle(title);
        if (mFragmentManager == null) {
            mFragmentManager = getFragmentManager();
        }
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.main_fragment_contain, fragment);
        mIsCreateFragment.put(fragment.getClass().getSimpleName(), true);
        mFragmentTransaction.commit();
    }

    private void visibleFragment(boolean hint, Fragment fragment) {
        if (mFragmentManager == null) {
            mFragmentManager = getFragmentManager();
        }
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        if (hint) {
            mFragmentTransaction.show(fragment);
        } else {
            mFragmentTransaction.hide(fragment);
        }
        mFragmentTransaction.commit();
    }

    @Override
    public void hintMessage(String msg) {

    }
}
