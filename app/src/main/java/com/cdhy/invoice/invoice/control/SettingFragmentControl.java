package com.cdhy.invoice.invoice.control;

import com.cdhy.invoice.invoice.BasePresenter;
import com.cdhy.invoice.invoice.BaseView;

public class SettingFragmentControl {
    public interface View extends BaseView {
        void setUserDescribe(String userName, String userDescribe);

    }

    public interface Presenter extends BasePresenter {
        void getUserDescribe(String userId);
    }
}
