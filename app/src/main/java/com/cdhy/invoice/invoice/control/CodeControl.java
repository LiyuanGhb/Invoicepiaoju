package com.cdhy.invoice.invoice.control;

import com.cdhy.invoice.invoice.BasePresenter;
import com.cdhy.invoice.invoice.BaseView;

public class CodeControl {
    public interface View extends BaseView{
        String getPhone();
    }

    public interface Presenter extends BasePresenter{
        void getCode();
    }
}
