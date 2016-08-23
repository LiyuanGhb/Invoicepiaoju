package com.cdhy.invoice.invoice.nohttp;

import com.yolanda.nohttp.rest.Response;

public interface HttpListener<T> {
    /**
     * 请求失败
     */
    void onSucceed(int what, Response<T> response);

    /**
     * 请求成功
     */
    void onFailed(int what, String url, Object tag, String error, int resCode, long ms);
}
