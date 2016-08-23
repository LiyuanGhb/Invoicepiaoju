package com.cdhy.invoice.invoice.control;

import com.cdhy.invoice.invoice.BasePresenter;
import com.cdhy.invoice.invoice.BaseView;
import com.cdhy.invoice.invoice.model.Card.CompanyRoot;

/**
 * Created by Administrator on 2016/8/11.
 */

public class HomeFragmentControl {
    public interface HomeFragmentView extends BaseView {
        void deleteCompanySuccess();

        void queryCompanySuccess(CompanyRoot mCompanyRoot);

        void OnHttpListenerFailed(String error);
    }

    public interface Presenter extends BasePresenter {
        void deleteCompany(String params);

        void queryCompany();

    }
}
