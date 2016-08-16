package com.cdhy.invoice.invoice.control;

import com.cdhy.invoice.invoice.BasePresenter;
import com.cdhy.invoice.invoice.BaseView;
import com.cdhy.invoice.invoice.model.Card.Data;


public class CreateCompanyControl {
    public interface View extends BaseView{

        void createCompanySuccess();

        void updateCompanySuccess();

        void queryByCompanyNameSuccess();

        void queryByCompanySHSuccess();

        Data getCreateCompanyDescribe();

    }

    public interface Presenter extends BasePresenter{
        void queryByCompanyName(String companyName);

        /**
         * @param sh 税号
         */
        void queryByCompanySH(String sh);

        void createCompany();

        void updateCompany();
    }
}
