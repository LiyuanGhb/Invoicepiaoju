package com.cdhy.invoice.invoice.control;

import com.cdhy.invoice.invoice.BasePresenter;
import com.cdhy.invoice.invoice.BaseView;
import com.cdhy.invoice.invoice.model.Card.Data;
import com.cdhy.invoice.invoice.model.VagueCompanyBean;


public class CreateCompanyControl {
    public interface View extends BaseView {

        void createCompanySuccess();

        void updateCompanySuccess();

        void queryByCompanyNameSuccess(VagueCompanyBean mVagueCompanyBean);

        void queryByCompanySHSuccess(VagueCompanyBean mVagueCompanyBean);

        Data getCreateCompanyDescribe();

        String getCompanyName();

        String getCompanySH();

        void OnHttpListenerFailed(String error);

    }

    public interface Presenter extends BasePresenter {

        void queryByCompanyName();

        void queryByCompanySH();

        void createCompany();

        void updateCompany();
    }
}
