package com.cdhy.invoice.invoice;

import android.content.Context;

import com.cdhy.invoice.invoice.control.MainAtyControl;

public interface BaseView<T> {
    //void setPresenter(T presenter);

    void hintMessage(String msg);

    Context getViewContext();
}
