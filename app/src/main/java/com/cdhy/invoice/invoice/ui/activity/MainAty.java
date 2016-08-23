package com.cdhy.invoice.invoice.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zby on 2016/8/5. 首页
 */
public class MainAty extends BaseActivity implements MainAtyControl.View, RadioGroup.OnCheckedChangeListener {
    private MainLayoutBinding mBinding;
    private NavigationBottomBinding mNavigationBottomBinding;
    private MainPresenter mPresenter;
    private FragmentManager mFragmentManager;

    private HomeFragment mHomeFragment = null;
    private InvoiceFragment mInvoiceFragment = null;
    private CompanyFragment mCompanyFragment = null;
    private SettingFragment mSettingFragment = null;

    private int currentFragment = 0;//记录当前Fragmentment
    private static final int HOME = 0;
    private static final int INVOICE = 1;
    private static final int COMPANY = 2;
    private static final int INFO = 3;
    private List<Fragment> mFragmentList;
    private List<Button> mButtonList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_layout);
        initParams();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", currentFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        currentFragment = savedInstanceState.getInt("position");
        setTabSelection(currentFragment);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initParams() {
        mNavigationBottomBinding =
                DataBindingUtil.bind(mBinding.mainNavigationBottom.getRoot());
        mNavigationBottomBinding.navContain.setOnCheckedChangeListener(this);
        mNavigationBottomBinding.navHome
                .setCompoundDrawables(null, setDrawableTop(R.mipmap.home_page_0), null, null);
        mButtonList = new ArrayList<>();

        mFragmentManager = getFragmentManager();
        mFragmentList = new ArrayList<>();
        mPresenter = new MainPresenter(this);
        mPresenter.showFragment(HOME, "首页");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.nav_home:
                mPresenter.showFragment(HOME, "首页");
                mNavigationBottomBinding.navHome
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.home_page_0), null, null);
                mNavigationBottomBinding.navInvoice
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.fpgl), null, null);
                mNavigationBottomBinding.navCompany
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.company), null, null);
                mNavigationBottomBinding.navInfo
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.info), null, null);
                break;
            case R.id.nav_invoice:
                mPresenter.showFragment(INVOICE, "发票管理");
                mNavigationBottomBinding.navHome
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.home_page), null, null);
                mNavigationBottomBinding.navInvoice
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.fpgl_0), null, null);
                mNavigationBottomBinding.navCompany
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.company), null, null);
                mNavigationBottomBinding.navInfo
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.info), null, null);
                break;
            case R.id.nav_company:
                mPresenter.showFragment(COMPANY, "我的企业");
                mNavigationBottomBinding.navHome
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.home_page), null, null);
                mNavigationBottomBinding.navInvoice
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.fpgl), null, null);
                mNavigationBottomBinding.navCompany
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.company_0), null, null);
                mNavigationBottomBinding.navInfo
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.info), null, null);
                break;
            case R.id.nav_info:
                mPresenter.showFragment(INFO, "我的信息");
                mNavigationBottomBinding.navHome
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.home_page), null, null);
                mNavigationBottomBinding.navInvoice
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.fpgl), null, null);
                mNavigationBottomBinding.navCompany
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.company), null, null);
                mNavigationBottomBinding.navInfo
                        .setCompoundDrawables(null, setDrawableTop(R.mipmap.info_0), null, null);
                break;
        }
    }

    private Drawable setDrawableTop(int res) {
        Drawable mDrawable = getResources().getDrawable(res);
        mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
        return mDrawable;
    }


    @Override
    public void initFragmentAndTitle(int index, String title) {
        setTabSelection(index);
        mBinding.setTitle(title);
    }

    private void setTabSelection(int position) {
        currentFragment = position;
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        hideFragment();
        switch (position) {
            case HOME:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    mFragmentTransaction.add(R.id.main_fragment_contain, mHomeFragment);
                    mFragmentList.add(mHomeFragment);
                } else {
                    mFragmentTransaction.show(mHomeFragment);
                }
                break;
            case INVOICE:
                if (mInvoiceFragment == null) {
                    mInvoiceFragment = new InvoiceFragment();
                    mFragmentTransaction.add(R.id.main_fragment_contain, mInvoiceFragment);
                    mFragmentList.add(mInvoiceFragment);
                } else {
                    mFragmentTransaction.show(mInvoiceFragment);
                }
                break;
            case COMPANY:
                if (mCompanyFragment == null) {
                    mCompanyFragment = new CompanyFragment();
                    mFragmentTransaction.add(R.id.main_fragment_contain, mCompanyFragment);
                    mFragmentList.add(mCompanyFragment);
                } else {
                    mFragmentTransaction.show(mCompanyFragment);
                }
                break;
            case INFO:
                if (mSettingFragment == null) {
                    mSettingFragment = new SettingFragment();
                    mFragmentTransaction.add(R.id.main_fragment_contain, mSettingFragment);
                    mFragmentList.add(mSettingFragment);
                } else {
                    mFragmentTransaction.show(mSettingFragment);
                }
                break;
        }
        mFragmentTransaction.commitAllowingStateLoss();
    }

    private void hideFragment() {
        if (mFragmentList.size() > 0) {
            for (Fragment mFragment : mFragmentList) {
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.hide(mFragment);
                mFragmentTransaction.commitAllowingStateLoss();
            }
        }
    }


    @Override
    public void hintMessage(String msg) {

    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
