package com.cdhy.invoice.invoice.ui.util;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/13.
 */
public class MineUtil {


    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static String getHttp(String url) {
        String result = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
                Log.e("result",result);
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * post请求
     * @param url
     * @param
     * @return
     */
    public static String postHttp(String url,
                                  String s) {
        HttpEntityEnclosingRequestBase httpRequest = new HttpPost(url);
        try {
            //   httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            StringEntity uefEntity = new StringEntity(s, "UTF-8");
            uefEntity.setContentEncoding("UTF-8");
            uefEntity.setContentType("application/json");
            httpRequest.setEntity(uefEntity);
            HttpResponse httpResponse = new DefaultHttpClient()
                    .execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String strResult = EntityUtils.toString(httpResponse
                        .getEntity());
                Log.e("strResult", strResult);
                return strResult;
            } else {
                Log.e("test", httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            System.out.println("error occurs");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post
     *
     * @param url
     * @param params
     */
    public static String postHaveCookieData(String url,
                                            List<NameValuePair> params) {
        HttpEntityEnclosingRequestBase httpRequest = new HttpPost(url);
        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient()
                    .execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String strResult = EntityUtils.toString(httpResponse
                        .getEntity());
                Log.e("strResult", strResult);
                return strResult;
            } else {
                Log.e("strResult", httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }


    public static List<NameValuePair> setParms(String phone, String password) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", phone));
        params.add(new BasicNameValuePair("pass", password));
        return params;
    }

}