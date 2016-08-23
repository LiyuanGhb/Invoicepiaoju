package com.cdhy.invoice.invoice.control;

import com.cdhy.invoice.invoice.BasePresenter;
import com.cdhy.invoice.invoice.BaseView;
import com.cdhy.invoice.invoice.model.User.Data;


public class PassWordLoginControl {
    public interface View extends BaseView {
        void PassWordForSuccessSuccess();
        void OnHttpListenerFailed(String error);
    }

    public interface Presenter extends BasePresenter {
        void PassWordLogin(String userName, String passWord);
    }
}
