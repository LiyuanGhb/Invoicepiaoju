package com.cdhy.invoice.invoice.nohttp;

import android.content.Context;
import android.widget.Toast;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.ui.util.NetWorkUtil;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;


public class HttpResponseListener<T> implements OnResponseListener<T> {
    /*自己写一个dialog来显示*/
    //private WaitDialog mWaitDialog;
    /**
     * 当前请求
     */
    private Request<T> mRequest;

    /**
     * 结果回调
     */
    private HttpListener<T> callback;

    /**
     * 是否显示dialog
     */
    private Context context;

    /**
     * @param context      context用来实例化dialog
     * @param request      请求对象
     * @param httpCallback 回调对象
     * @param canCancel    是否允许用户取消请求
     * @param isLoading    是否显示dialog
     */
    HttpResponseListener(Context context, Request<T> request, HttpListener<T> httpCallback, boolean canCancel, boolean isLoading) {
        this.context = context;
        this.mRequest = request;
  /*      if (isLoading) {// 需要显示dialog
            mWaitDialog = new WaitDialog(context);
            mWaitDialog.setCancelable(canCancel);
            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mRequest.cancel();// dialog被用户关闭时, 取消当前请求
                }
            });
        }*/
        this.callback = httpCallback;
        //this.isLoading = isLoading;
    }

    HttpResponseListener(Context context, Request<T> request, HttpListener<T> httpCallback) {
        this.context = context;
        this.mRequest = request;
        this.callback = httpCallback;
    }

    @Override
    public void onStart(int what) {
        /*if (!context.isFinishing && mWaitDialog != null && !mWaitDialog.isShowing())
            mWaitDialog.show();*/
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        if (callback != null)
            callback.onSucceed(what, response);
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        if (callback != null) {
            if (exception instanceof NetworkError) {
                CustomApplication.isIntent = 1;
                NetWorkUtil.showNoNetWorkDlg(context);
            } else if (exception instanceof TimeoutError) {
                Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();
            }
            callback.onFailed(what, url, tag, exception.getMessage(), responseCode, networkMillis);
        }
    }

    @Override
    public void onFinish(int what) {
        /*if (mWaitDialog != null && mWaitDialog.isShowing())
            mWaitDialog.dismiss();*/
    }
}
