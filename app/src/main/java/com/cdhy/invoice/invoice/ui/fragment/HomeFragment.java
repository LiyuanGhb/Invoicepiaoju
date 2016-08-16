package com.cdhy.invoice.invoice.ui.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cdhy.invoice.invoice.control.HomeFragmentControl;
import com.cdhy.invoice.invoice.model.Card.CompanyRoot;
import com.cdhy.invoice.invoice.model.Card.Data;
import com.cdhy.invoice.invoice.presenter.HomeFragmentPresenter;
import com.cdhy.invoice.invoice.ui.activity.CurrentActivity;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.databinding.HomeFragmentBinding;
import com.cdhy.invoice.invoice.ui.activity.UpdateCompanyAty;
import com.cdhy.invoice.invoice.ui.adapter.ViewPagerAdapter;
import com.cdhy.invoice.invoice.ui.requestParames.CompanyParameter;

import java.util.List;

import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener, HomeFragmentControl.HomeFragmentView {
    private HomeFragmentBinding mBinding;
    private int currentIndex; //当前显示的名片下标
    private HomeFragmentPresenter mPresenter;
    private List<Data> mCardBeanList;
    public static final int ADD_COMPANY = 1; //添加公司标识符
    public static final int UPDATE_COMPANY = 2;//更新公司标识符

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.bind(inflater.inflate(R.layout.home_fragment, container, false));
        initParameters();
        Log.i(TAG, "onCreateView: ");
        return mBinding.getRoot();
    }

    private void initParameters() {
        mPresenter = new HomeFragmentPresenter(this);
        mBinding.setOnClickListener(this);
        mBinding.viewpagerContain.addOnPageChangeListener(this);
        mPresenter.queryCompany();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentIndex = position;
        mBinding.companyDescribe.setText(mCardBeanList.get(currentIndex).getNAME());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.home_btn_add:
                startActivityForResult(new Intent(getActivity(), CurrentActivity.class), ADD_COMPANY);
                break;
            case R.id.home_btn_update:
                Intent mIntent = new Intent(getActivity(), UpdateCompanyAty.class);
                mIntent.putExtra("companyBean",
                        mCardBeanList.get(mBinding.viewpagerContain.getCurrentItem()));
                startActivityForResult(mIntent, UPDATE_COMPANY);
                break;
            case R.id.home_btn_delete:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setTitle("请确认删除信息");
                mBuilder.setMessage(mCardBeanList.get(currentIndex).getNAME());
                mBuilder.setNegativeButton("取消", null);
                mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteCompany(mCardBeanList.get(currentIndex).getID());
                    }
                });
                mBuilder.create();
                mBuilder.show();
                break;
        }
    }

    @Override
    public void hintMessage(String msg) {
        Log.i(TAG, "hintMessage: " + msg);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteCompanySuccess() {
        mPresenter.queryCompany();
        mBinding.companyDescribe.setText(mCardBeanList.get(mBinding.viewpagerContain.getCurrentItem()).getNAME());
    }

    @Override
    public void queryCompanySuccess(CompanyRoot mCompanyRoot) {
        mCardBeanList = mCompanyRoot.getData();
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getActivity(), mCardBeanList);
        mBinding.viewpagerContain.setAdapter(mViewPagerAdapter);

        mBinding.companyDescribe.setText(mCardBeanList.get(mBinding.viewpagerContain.getCurrentItem()).getNAME());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case ADD_COMPANY:
                mPresenter.queryCompany();
                break;
            case UPDATE_COMPANY:
                mPresenter.queryCompany();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
