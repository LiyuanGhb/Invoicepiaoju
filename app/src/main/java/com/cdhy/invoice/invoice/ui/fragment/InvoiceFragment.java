package com.cdhy.invoice.invoice.ui.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.databinding.InvoiceFragmentBinding;
import com.cdhy.invoice.invoice.ui.activity.SetIpActivity;
import com.cdhy.invoice.invoice.ui.custom.CustomContain;
import com.cdhy.invoice.invoice.ui.util.DialogHb;

import scanner.CaptureActivity;

public class InvoiceFragment extends Fragment implements View.OnClickListener {
    private InvoiceFragmentBinding mBinding;
    private CustomContain mCustomContain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.bind(inflater.inflate(R.layout.invoice_fragment, container, false));
        initParameter();
        return mBinding.getRoot();
    }

    private void initParameter() {
        mBinding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quickopen_rl:
                if (CustomApplication.saveIp) {
                    Intent mIntent = new Intent(getActivity(),CaptureActivity.class);
                    mIntent.putExtra("what", CaptureActivity.QuickOpen);
                    startActivity(mIntent);
                } else {
                    new AlertDialog.Builder(getActivity()).setTitle("未匹配快开").setItems(new String[]{"匹配快开"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    startActivity(new Intent(getActivity(), SetIpActivity.class));
                                    break;
                            }
                        }
                    }).setNegativeButton("取消", null).show();
                }
                break;
            case R.id.invoice_code:
                DialogHb.showdialog(getActivity(), "服务暂未开放");
                break;
            case R.id.invoice_query:
                DialogHb.showdialog(getActivity(), "服务暂未开放");
                break;
            case R.id.invoice_draw:
                DialogHb.showdialog(getActivity(), "服务暂未开放");
                break;
        }
    }
}
