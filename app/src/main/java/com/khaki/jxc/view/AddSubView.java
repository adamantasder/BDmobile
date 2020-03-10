package com.khaki.jxc.view;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.khaki.jxc.client.R;


/**
 * Created by Administrator on 2019/3/27 0027.
 * 自定义增减按钮 通过接口回调
 */

public class AddSubView extends LinearLayout implements View.OnClickListener  {

    private Button iv_sub;
    private Button iv_add;
    private EditText etAmount;
    private String value = 1+"";
    private int va=1;
    private int minValue = 1;
    private int maxValue = 5;
    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.add_sub_view, this);
        iv_sub = findViewById(R.id.iv_sub);
        iv_add = findViewById(R.id.iv_add);
        etAmount = findViewById(R.id.etAmount);
        String value = getValue();
        setValue(value);
        /**
         * 设置点击事件
         * */
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        etAmount.setOnClickListener(this);





    }


    public String getValue() {
        String valueStr = etAmount.getText().toString().trim();
        if (!TextUtils.isEmpty(valueStr)) {
            value =valueStr;
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        etAmount.setText(value + "");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_sub:
                subNumber();
                break;
            case R.id.iv_add:
                addNumber();
                break;
             case R.id.etAmount:
                value="";
                setValue(value);
                scallNumber();

                break;
        }

    }

    private void addNumber() {

        va ++;
        setValue(va+"");
        if(onNumberChangerListener != null){
            onNumberChangerListener.onNumberChange(value);
        }
    }
    private void scallNumber() {
        va=0;
        setValue(va+"");
        etAmount.setFocusable(true);
        etAmount.setFocusableInTouchMode(true);
        etAmount.requestFocus();
        etAmount.setInputType(EditorInfo.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etAmount.clearFocus();
        if(onNumberChangerListener != null){
            onNumberChangerListener.onNumberChange(value);
        }
    }

    private void subNumber() {
        if (va > minValue) {
            va--;
        }
        setValue(va+"");
        if(onNumberChangerListener != null){
            onNumberChangerListener.onNumberChange(value);
        }
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }





    /**
     * 当数量发生变化的时候进行回调
     * */
    public interface  OnNumberChangerListener{
        /**
         * 当数据发生变化的时候回调
         * */
        void onNumberChange(String value);
    }

    private OnNumberChangerListener onNumberChangerListener;

    public void setOnNumberChangerListener(OnNumberChangerListener onNumberChangerListener) {
        this.onNumberChangerListener = onNumberChangerListener;
    }
}
