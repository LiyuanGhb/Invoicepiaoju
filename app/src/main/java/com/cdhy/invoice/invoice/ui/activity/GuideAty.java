package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.databinding.GuideMainAtyBinding;
import com.cdhy.invoice.invoice.ui.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ly on 2016/7/13. 引导页
 */
public class GuideAty extends Activity {
    private GuideMainAtyBinding mGuideMainAtyBinding;
    private List<Integer> list;
    private Map<Integer, ImageView> mImageViewMap;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGuideMainAtyBinding = DataBindingUtil.setContentView(this, R.layout.guide_main_aty);
        init();
    }

    /**
     * 判断应用是否第一次安装
     * true 启用引导功能 并设置安装状态为 false
     * false 跳转欢迎WelcomeAty欢迎页面
     */
    private void init() {

        SharedPreferencesHelper mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        if (mSharedPreferencesHelper.getBooleanByShared(SharedPreferencesHelper.RUN_STATEMENT, true)) {
            initImageResList();
            mImageViewMap = new HashMap<>();
            mGuideMainAtyBinding.vpGuideContain.setAdapter(new GuideAdapter());
            mSharedPreferencesHelper.setBooleanToShared(SharedPreferencesHelper.RUN_STATEMENT, false);
        } else {
            startActivity(new Intent(GuideAty.this, WelcomeAty.class));
            finish();
        }
    }

    private void initImageResList() {
        list = new ArrayList<>();
        TypedArray array = this.getResources().obtainTypedArray(R.array.GuideArray);
        int length = array.length();
        for (int i = 0; i < length; i++) {
            list.add(array.getResourceId(i, 0));
        }
        array.recycle();
    }

    public class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int resId = list.get(position);
            ImageView mImageView = mImageViewMap.get(resId);
            if (mImageView == null) {
                mImageView = new ImageView(GuideAty.this);
                mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                mImageView.setImageResource(resId);
                mImageViewMap.put(resId, mImageView);
            }

            if (position == (list.size() - 1)) {
                mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(GuideAty.this, MainAty.class));
                        finish();
                    }
                });
            }
            (container).addView(mImageView);
            return mImageView;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            int resId = list.get(position);
            (container).removeView(mImageViewMap.get(resId));
        }
    }
}
