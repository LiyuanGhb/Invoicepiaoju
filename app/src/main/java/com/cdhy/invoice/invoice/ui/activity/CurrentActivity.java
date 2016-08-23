package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.control.CreateCompanyControl;
import com.cdhy.invoice.invoice.databinding.CurrentBinding;
import com.cdhy.invoice.invoice.model.Card.Data;
import com.cdhy.invoice.invoice.model.VagueCompanyBean;
import com.cdhy.invoice.invoice.presenter.CreateCompanyPresenter;
import com.cdhy.invoice.invoice.ui.adapter.ComListAdapter;
import com.cdhy.invoice.invoice.ui.fragment.HomeFragment;

import java.util.List;

/**
 * Created by ly on 2016/6/13. createCompany
 */
public class CurrentActivity extends Activity implements CreateCompanyControl.View, View.OnClickListener {
    private CurrentBinding mBinding;
    private CreateCompanyPresenter mCompanyPresenter;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.current);
        initParams();
    }

    private void initParams() {
        mBinding.setOnCliclistener(this);
        mBinding.currentQymc.setFunctionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.currentQymc.getDetails())) {
                    Toast.makeText(CurrentActivity.this, "企业名称不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    setQueryByCompanyName(false);
                    mCompanyPresenter.queryByCompanyName();
                }

            }
        });
        mBinding.currentQysh.setFunctionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.currentQysh.getDetails())) {
                    Toast.makeText(CurrentActivity.this, "企业税号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    setQueryByCompanySH(false);
                    mCompanyPresenter.queryByCompanySH();
                }

            }
        });

        mCompanyPresenter = new CreateCompanyPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_top:
                setResult(HomeFragment.NOT_FUNCTION);
                finish();
                break;
            case R.id.bt_bing_current:
                if (TextUtils.isEmpty(mBinding.currentQymc.getDetails())) {
                    Toast.makeText(this, "企业名称不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    setCreateCompany(false);
                    mCompanyPresenter.createCompany();
                }
                break;
        }

    }


    @Override
    public void createCompanySuccess() {
        setCreateCompany(true);
        setResult(HomeFragment.ADD_COMPANY);
        finish();
    }

    @Override
    public void updateCompanySuccess() {
        /*创建公司和修改公司用的同一个借口 创建公司不做修改工作*/
    }

    @Override
    public void queryByCompanyNameSuccess(VagueCompanyBean mVagueCompanyBean) {
        setQueryByCompanyName(true);
        getPopupWindow(mVagueCompanyBean.getData());
    }

    @Override
    public void queryByCompanySHSuccess(VagueCompanyBean mVagueCompanyBean) {
        setQueryByCompanySH(true);
        getPopupWindow(mVagueCompanyBean.getData());
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
    public Data getCreateCompanyDescribe() {
        Data companyBean = new Data();
        String companyName = mBinding.currentQymc.getDetails();
        String companySH = mBinding.currentQysh.getDetails();
        String companyAddress = mBinding.currentQydz.getDetails();
        String companyPhone = mBinding.currentQydh.getDetails();
        String companyKhh = mBinding.currentKhh.getDetails();
        String companyYhzh = mBinding.currentYhzh.getDetails();
        companyBean.setNAME(companyName);
        companyBean.setNSRSBH(companySH);
        companyBean.setADDRESS(companyAddress);
        companyBean.setPHONE(companyPhone);
        companyBean.setKHH(companyKhh);
        companyBean.setYHZH(companyYhzh);
        return companyBean;
    }

    @Override
    public String getCompanyName() {
        return mBinding.currentQymc.getDetails();
    }

    @Override
    public String getCompanySH() {
        return mBinding.currentQysh.getDetails();
    }

    @Override
    public void OnHttpListenerFailed(String error) {
        setQueryByCompanyName(true);
        setQueryByCompanySH(true);
        setCreateCompany(true);
    }

    /***
     * 获取PopupWindow实例
     */

    private void getPopupWindow(List<VagueCompanyBean.DataBean> list) {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow(list);
        }
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow(final List<VagueCompanyBean.DataBean> list) {
        View popupWindow_view = getLayoutInflater().inflate(R.layout.popcomlist,
                null,
                false);
        ListView listView = (ListView) popupWindow_view.findViewById(R.id.pop_list);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        ComListAdapter ecuAdapter = new ComListAdapter(this, list, true);
        listView.setAdapter(ecuAdapter);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        System.out.println("heigth : " + dm.heightPixels);

        int hight = (int) (dm.heightPixels * (0.75));
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.WRAP_CONTENT, hight, true);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);

        popupWindow.showAtLocation(mBinding.btBingCurrent, Gravity.CENTER, 0, 0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mBinding.currentQymc.setDetails(list.get(i).getGsmc());
                mBinding.currentQysh.setDetails(list.get(i).getNsrsbh());
                mBinding.currentKhh.setDetails(list.get(i).getKhh());
                mBinding.currentQydz.setDetails(list.get(i).getGsdz());
                mBinding.currentQydh.setDetails(list.get(i).getDh());
                mBinding.currentYhzh.setDetails(list.get(i).getYhzh());
                popupWindow.dismiss();
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
                popupWindow = null;
            }
        });

        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    WindowManager.LayoutParams params = getWindow().getAttributes();
                    params.alpha = 1f;
                    getWindow().setAttributes(params);
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    private void setQueryByCompanyName(boolean state) {
        if (state) {
            mBinding.currentQymc.setButtonText("查询");
            mBinding.currentQymc.setButtonBackGround(R.drawable.buttonblue);
        } else {
            mBinding.currentQymc.setButtonText("查询中");
            mBinding.currentQymc.setButtonBackGround(R.drawable.buttonblue_0);
        }
        mBinding.currentQymc.setButtonEnabled(state);
    }

    private void setQueryByCompanySH(boolean state) {
        if (state) {
            mBinding.currentQysh.setButtonText("查询");
            mBinding.currentQysh.setButtonBackGround(R.drawable.buttonblue);
        } else {
            mBinding.currentQysh.setButtonText("查询中");
            mBinding.currentQysh.setButtonBackGround(R.drawable.buttonblue_0);
        }
        mBinding.currentQysh.setButtonEnabled(state);
    }

    private void setCreateCompany(boolean state) {
        if (state) {
            mBinding.btBingCurrent.setBackground(getResources().getDrawable(R.drawable.buttonblue));
        } else {
            mBinding.btBingCurrent.setBackground(getResources().getDrawable(R.drawable.buttonblue_0));
        }
        mBinding.btBingCurrent.setEnabled(state);
    }
}