package com.cdhy.invoice.invoice.control;


import com.cdhy.invoice.invoice.BasePresenter;
import com.cdhy.invoice.invoice.BaseView;

public class MainAtyControl {
    public interface View extends BaseView<Presenter> {
        void initFragmentAndTitle(int index,String title);

    }

    public interface Presenter extends BasePresenter {
        void showFragment(int index,String title);
    }
}
