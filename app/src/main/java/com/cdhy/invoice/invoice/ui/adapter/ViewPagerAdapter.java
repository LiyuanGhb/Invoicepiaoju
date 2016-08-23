package com.cdhy.invoice.invoice.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cdhy.invoice.invoice.QRUtil;
import com.cdhy.invoice.invoice.model.Card.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private SparseArray<ImageView> mSparseArray;
    private HashMap<Integer, String> map;
    private List<Data> mCardBean;
    showimg showimgs;


    public ViewPagerAdapter(Context mContext, List<Data> mCardBean, showimg showimgs) {
        this.mContext = mContext;
        this.mCardBean = mCardBean;
        mSparseArray = new SparseArray<>();
        map = new HashMap<>();
        this.showimgs = showimgs;
    }

    @Override
    public int getCount() {
        return mCardBean.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView mImageView = createImageView(position);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showimgs.show(map.get(position));
            }
        });
        (container).addView(mImageView);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView mImageView = createImageView(position);
        (container).removeView(mImageView);
    }

    private ImageView createImageView(int key) {
        if (mSparseArray.get(key) == null) {
            Data cardBean = mCardBean.get(key);
            ImageView mImageView = new ImageView(mContext);
            String url = QRUtil.createString(
                    cardBean.getUSERID(),
                    cardBean.getNAME(),
                    cardBean.getNSRSBH(),
                    cardBean.getADDRESS(),
                    cardBean.getPHONE(),
                    cardBean.getKHH(),
                    cardBean.getYHZH()
            );
            Bitmap mBitmap = QRUtil.createQRImage(url);
            mImageView.setImageBitmap(mBitmap);
            mSparseArray.put(key, mImageView);
            map.put(key, url);
        }
        return mSparseArray.get(key);
    }

    public interface showimg {
        public void show(String s);
    }
}
