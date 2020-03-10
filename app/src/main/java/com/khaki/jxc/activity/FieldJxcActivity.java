package com.khaki.jxc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.khaki.app.base.BaseActivity;
import com.khaki.jxc.Contants;
import com.khaki.jxc.bean.Contact;

import java.util.List;

import okhttp3.OkHttpClient;

import com.xys.libzxing.zxing.activity.CaptureActivity;

public class FieldJxcActivity extends BaseActivity {
    private static final String TAG = "FieldJxcActivity";
    private OkHttpClient okHttpClient;

    private String omType = null;       //大类型
    private String omSubType = null;    //小类型
    private EditText location_input, reason_input;
    private String omReasonString = "";
    private String startDateString = null;
    private String startTimeString = null;
    private String endDateString = null;
    private String endTimeString = null;
    private List<Contact> mContactList;
    private Handler handler;
    private Message message;
    private long officeManageId = -1;     //初始化 返回的事务id
    private Intent intent;
    private Bundle bundle;
    //代表从上一个页面传进来的参数
    private int fieldworkType;



    /**
     *
     * */

/**
 * 二维码
 * */
public void openQRCodeActivity() {
    Intent intent = new Intent(this, CaptureActivity.class);
    startActivityForResult(intent, Contants.RequestCode.QRSCAN);
}

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
