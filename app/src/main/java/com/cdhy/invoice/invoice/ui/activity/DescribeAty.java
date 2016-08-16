package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.databinding.DescribeAtyBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2016/7/18. 说明
 */
public class DescribeAty extends Activity {
    private DescribeAtyBinding mDescribeAtyBinding;
    private List<Bitmap> mBitmapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDescribeAtyBinding = DataBindingUtil.setContentView(this, R.layout.describe_aty);
        init();
    }

    private void init(){
        mBitmapList = new ArrayList<>();
        saveDataToList();
        mDescribeAtyBinding.describeLvContain.setAdapter(new DescribeAdapter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void saveDataToList() {
        InputStream mInputStream;
        String IMAGE_NAME = "shuoming.jpg";//文件名
        int ROW_COUNT = 5;//生成的列数
        try {
            mInputStream = getAssets().open(IMAGE_NAME);//根据文件名获取inputsteam
            BitmapFactory.Options mOptions = new BitmapFactory.Options();
            mOptions.inJustDecodeBounds = true;
            //设置为true可以不将图片加载到内存中就能获取图片的真实宽高
            BitmapFactory.decodeStream(mInputStream, null, mOptions);//加载图片
            int width = mOptions.outWidth;
            int height = mOptions.outHeight;

            int rowHeight = height / ROW_COUNT;//没列的高度
            int top = 0; // rect 顶部
            int bottom = rowHeight; //rect 底部
            BitmapRegionDecoder mBitmapRegionDecoder = BitmapRegionDecoder.newInstance(mInputStream, false);
            //根据rect加载自定义的矩形图片
            for (int i = 0; i < ROW_COUNT; i++) {
                if (bottom >= height) bottom = height;
                Rect mRect = new Rect(0, top, width, bottom);
                mBitmapList.add(mBitmapRegionDecoder.decodeRegion(mRect, mOptions));
                top = bottom;
                bottom += rowHeight;
            }
        } catch (IOException mE) {
            mE.printStackTrace();
        }
    }

    public class DescribeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mBitmapList.size();
        }

        @Override
        public Object getItem(int position) {
            return mBitmapList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView = new ImageView(DescribeAty.this);
            mImageView.setAdjustViewBounds(true);
            mImageView.setImageBitmap(mBitmapList.get(position));
            return mImageView;
        }
    }
}
