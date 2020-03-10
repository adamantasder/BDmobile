package com.khaki.jxc.client;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.khaki.jxc.client.R;

/**
 *
 * @author khaki<br>
 * @version 1.0
 */
public class SplashActivity extends Activity {

    Handler mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                // 结束该Activity
                finish();
            }
        },1000);
    }
}
