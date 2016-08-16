package com.cdhy.invoice.invoice.ui.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.databinding.InvoiceFragmentBinding;
import com.cdhy.invoice.invoice.ui.custom.CustomContain;

public class InvoiceFragment extends Fragment {
    private InvoiceFragmentBinding mBinding;
    private CustomContain mCustomContain;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.bind(inflater.inflate(R.layout.invoice_fragment,container,false));
        //mCustomNavigationTop = (CustomNavigationTop) mBinding.getRoot().findViewById(R.id.nmb);
        return mBinding.getRoot();
    }

}
