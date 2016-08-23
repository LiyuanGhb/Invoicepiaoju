package com.cdhy.invoice.invoice.control;

import com.cdhy.invoice.invoice.BasePresenter;
import com.cdhy.invoice.invoice.BaseView;

/**
 * Created by Administrator on 2016/8/15.
 */

public class UpdateDescribeControl {
    public interface View extends BaseView{
        void updateUserDescribeSuccess();
        void OnHttpListenerFailed(String error);
    }

    public interface Presenter extends BasePresenter{
        void updateUserDescribe(String zsxm);
    }

}
