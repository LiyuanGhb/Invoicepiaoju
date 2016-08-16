package com.cdhy.invoice.invoice.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhy.invoice.invoice.R;

/**
 * 自定义顶部导航栏 可以自己设置图标的显示状态 和标题
 */
public class CustomContain extends LinearLayout {
    private TextView mContainTv;

    public CustomContain(Context context) {
        super(context);
        initNavigationTop(context);
    }

    public CustomContain(Context context, AttributeSet attrs) {
        super(context, attrs);
        initNavigationTop(context, attrs);
    }

    public CustomContain(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initNavigationTop(context, attrs);
    }

    /*初始化方法*/
    private void initNavigationTop(Context context) {
        init(context);
    }

    private void initNavigationTop(Context context, AttributeSet attrs) {
        init(context);
        initControlAttrs(context, attrs);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.custom_contain, this, true);
        mContainTv = (TextView) view.findViewById(R.id.contain_tv);
    }

    private void initControlAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Function_Contain);


        if (typedArray.getResourceId(
                R.styleable.Function_Contain_setLeftBackground, 0
        ) != 0) {
            Drawable mDrawable = getResources().getDrawable(typedArray.getResourceId(
                    R.styleable.Function_Contain_setLeftBackground, 0
            ));
            mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
            mContainTv.setCompoundDrawables(mDrawable, null, null, null);
        }

        if (!TextUtils.isEmpty(typedArray.getString(R.styleable.Function_Contain_setTitleDescribe))) {
            mContainTv.setText(typedArray.getString(R.styleable.Function_Contain_setTitleDescribe));
        }

        typedArray.recycle();
    }
}
