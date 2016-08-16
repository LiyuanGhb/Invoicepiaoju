package com.cdhy.invoice.invoice.control;

import android.app.Fragment;

import com.cdhy.invoice.invoice.BasePresenter;
import com.cdhy.invoice.invoice.BaseView;

/**
 * Created by Administrator on 2016/8/11.
 */

public class MainAtyControl {
    public interface View extends BaseView<Presenter> {
        void initFragmentAndTitle(Fragment fragment,String title);
    }

    public interface Presenter extends BasePresenter {
        void showFragment(Fragment fragment,String title);
    }
}
