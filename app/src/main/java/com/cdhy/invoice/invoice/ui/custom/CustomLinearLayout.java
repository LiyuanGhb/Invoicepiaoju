package com.cdhy.invoice.invoice.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhy.invoice.invoice.R;

public class CustomLinearLayout extends RelativeLayout {
    private TextView mDescribe;
    private EditText mDetails;
    private Button mFunction;

    public CustomLinearLayout(Context context) {
        super(context);
        initLayout(context);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context, attrs);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context, attrs);
    }

    private void initLayout(Context context) {
        initView(context);
    }

    private void initLayout(Context context, AttributeSet attrs) {
        initView(context);
        initAttributeSet(context, attrs);
    }

    private void initView(Context context) {
        View mView = LayoutInflater.from(context).inflate(R.layout.custom_linear_layout, this, true);
        mDescribe = (TextView) mView.findViewById(R.id.cl_describe_tv);
        mDetails = (EditText) mView.findViewById(R.id.cl_describe_et);
        mFunction = (Button) mView.findViewById(R.id.cl_describe_btn);
    }

    private void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.Custom_LinearLayout);

        if (mTypedArray.getBoolean(R.styleable.Custom_LinearLayout_setButtonVisible, false)) {
            mFunction.setVisibility(VISIBLE);
        } else {
            mFunction.setVisibility(GONE);
        }

        if (!TextUtils.isEmpty(mTypedArray.getString(R.styleable.Custom_LinearLayout_setDescribe))) {
            mDescribe.setText(mTypedArray.getString(R.styleable.Custom_LinearLayout_setDescribe));
        }

        if (!TextUtils.isEmpty(mTypedArray.getString(R.styleable.Custom_LinearLayout_setDetails))) {
            mDetails.setHint(mTypedArray.getString(R.styleable.Custom_LinearLayout_setDetails));
        }

        mTypedArray.recycle();
    }

    public void setFunctionClickListener(OnClickListener mOnClickListener) {
        mFunction.setOnClickListener(mOnClickListener);
    }

    public String getDetails() {
        return mDetails.getText().toString();
    }

    public void setDetails(String str) {
        mDetails.setText(str);
        mDetails.setSelection(str.length());
    }

    public void setButtonText(String text){
        mFunction.setText(text);
    }

    public void setButtonEnabled(boolean state){
        mFunction.setEnabled(state);
    }

    public void setButtonBackGround(int resId){
        mFunction.setBackground(getResources().getDrawable(resId));
    }

}
