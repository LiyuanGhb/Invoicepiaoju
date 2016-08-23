package com.cdhy.invoice.invoice.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.databinding.CompanyFragmentBinding;
import com.cdhy.invoice.invoice.ui.activity.AuthComActivity;
import com.cdhy.invoice.invoice.ui.activity.SetCompanyActivity;
import com.cdhy.invoice.invoice.ui.util.DialogHb;

/**
 * Created by Administrator on 2016/8/6.
 */
public class CompanyFragment extends Fragment implements View.OnClickListener {
    private CompanyFragmentBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.bind(inflater.inflate(R.layout.company_fragment,container,false));
        init();
        return mBinding.getRoot();
    }

    private void init(){
        mBinding.setClickLitsener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cpy_bxsq_rl:
                //报销申请
                DialogHb.showdialog(getActivity(),"功能暂未开放");
                break;
            case R.id.cpy_bxsp_rl:
                //报销审评
                DialogHb.showdialog(getActivity(),"功能暂未开放");;
                break;
            case R.id.cpy_qyfp_rl:
                //企业发票
                DialogHb.showdialog(getActivity(),"功能暂未开放");
                break;
            case R.id.cpy_rysh_rl:
                //入企申请
                startActivity(new Intent(getActivity(), AuthComActivity.class));
                break;
            case R.id.cpy_gnsz_rl:
                //人员管理
                startActivity(new Intent(getActivity(), SetCompanyActivity.class));
                break;
        }
    }
}
