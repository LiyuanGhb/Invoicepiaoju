package com.cdhy.invoice.invoice.control;

import android.content.Context;

import com.cdhy.invoice.invoice.BasePresenter;
import com.cdhy.invoice.invoice.BaseView;

public class CodeControl {
    public interface View extends BaseView {
        String getPhone();

        void getCodeSuccess();

        void OnHttpListenerFailed(String error);
    }

    public interface Presenter extends BasePresenter {
        void getCode();
    }
}
