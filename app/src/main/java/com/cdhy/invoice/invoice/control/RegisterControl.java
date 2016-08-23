package com.cdhy.invoice.invoice.control;

import com.cdhy.invoice.invoice.BasePresenter;
import com.cdhy.invoice.invoice.BaseView;
import com.cdhy.invoice.invoice.model.RegisterBean;

public class RegisterControl {
    public interface View extends BaseView {
        void registerSuccess();
        void OnHttpListenerFailed(String error);
    }


    public interface Presenter extends BasePresenter {
        void register(String requestParameter);
    }
}
