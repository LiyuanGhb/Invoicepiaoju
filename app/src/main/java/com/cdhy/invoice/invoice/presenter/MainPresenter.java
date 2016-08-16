package com.cdhy.invoice.invoice.presenter;

import android.app.Fragment;

import com.cdhy.invoice.invoice.control.MainAtyControl;


public class MainPresenter implements MainAtyControl.Presenter {
    private MainAtyControl.View mView;

    public MainPresenter(MainAtyControl.View mView) {
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void showFragment(Fragment fragment, String title) {
        mView.initFragmentAndTitle(fragment, title);
    }
}
