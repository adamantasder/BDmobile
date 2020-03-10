package com.khaki.jxc;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.khaki.jxc.utils.Utils;


public class MyApplication   extends Application {
    private static MyApplication instance;
    private static Context context;
        public void onCreate() {

            super.onCreate();
            Log.e("context", context.toString());
            instance = this;

        }
    public static Context getInstance() {
        return instance;
    }
    /**
     * 获取 Application Context
     * 该 Context 可用于文件/资源相关操作,不可用作 UI 的上下文
     * @return
     */
    public static Context getGlobalContext() {
        return context;
    }

}
