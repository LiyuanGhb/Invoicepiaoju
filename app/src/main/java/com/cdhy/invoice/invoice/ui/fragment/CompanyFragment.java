package com.cdhy.invoice.invoice.ui.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.databinding.CompanyFragmentBinding;
import com.cdhy.invoice.invoice.ui.custom.CustomContain;

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
                Toast.makeText(getActivity(), "报销申请", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cpy_bxsp_rl:
                //报销审评
                Toast.makeText(getActivity(), "报销审评", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cpy_qyfp_rl:
                //企业发票
                Toast.makeText(getActivity(), "企业发票", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cpy_rysh_rl:
                //人员审核
                Toast.makeText(getActivity(), "人员审核", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cpy_gnsz_rl:
                //功能设置
                Toast.makeText(getActivity(), "功能设置", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
