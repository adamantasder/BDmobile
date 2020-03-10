package com.khaki.jxc.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bd.appupdateutils.AppUpdateManager;
import com.bd.appupdateutils.common.UpdateType;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.khaki.jxc.Contants;
import com.khaki.jxc.bean.AppVersion;

import com.khaki.jxc.client.R;

import com.khaki.jxc.fragement.ContactFragment;
import com.khaki.jxc.fragement.MessageFragment;
import com.khaki.jxc.fragement.NoticeFragment;
import com.khaki.jxc.fragement.UserInfoFragment;
import com.khaki.jxc.fragement.WorkFragment;
import com.khaki.jxc.utils.OkHttpUtil;
import com.khaki.jxc.widge.NoScrollViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author khaki<br>
 * @version 1.0
 */
public class AuctionClientActivity extends AppCompatActivity  implements View.OnClickListener
{

    private TextView mMessageTV;
    private TextView mNoticeTV;
    private TextView mWorkTV;
    private TextView mContactTV;
    private TextView mUserInfoTV;
    private NoScrollViewPager mViewPager;
    private BottomBar bottomBar;
    private List<Fragment> mFragmentList;
    private MyFragmentPagerAdapter adapter;
    private ProgressDialog progressDialog;
    private String  TAG = "MainActivity: ";
    private  String downloadUrl = "http://223.100.49.159:9010/tender/BD.apk";
    private String savaDirPath = "testLoad";  //保存的地址
    //定义notify的id，避免与其它的notification的处理冲突
    private static final int NOTIFY_ID = 0;
    private static final String CHANNEL = "update";
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private int progressStatus = 0;
    private int[] data = new int[100];
    private int hasData = 0;
    private  int fileSize=0;//下载文件长度
    private  AppVersion app= new AppVersion();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定加载R.layout.activity_main对应的界面布局文件
        // 但实际上该应用会根据屏幕分辨率家在不同的界面布局文件
        setContentView(R.layout.main);
        mMessageTV = (TextView) findViewById(R.id.tv_bottom_message);
        mNoticeTV = (TextView) findViewById(R.id.tv_bottom_notice);
        mWorkTV= (TextView) findViewById(R.id.tv_bottom_work);
        mContactTV = (TextView) findViewById(R.id.tv_bottom_contact);
        mUserInfoTV = (TextView) findViewById(R.id.tv_bottom_user);
        if(initVersion() >0){
            Log.e("initVersion", "initVersion====>"+initVersion());
             showUpdaloadDialog(Contants.APP_IP);

        };
        initViewPager();
        initialBottomView();

    }
private void updateSer(){

    new AppUpdateManager
            .Builder()
            //当前Activity
            .setContext(AuctionClientActivity.this)
            .setType(UpdateType.Slience)
            //更新地址
            .setUrl(Contants.APP_IP)
            //实现httpManager接口的对象
            .setVersion(app.getServerVersion())
            .setNotes(app.getUpgradeInfo().replace("\\n","\n"))
            .setTitle(Contants.AppName)
            .setType(UpdateType.Force)
            .build()
            .update();
}
    private void initialBottomView() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.bottom_message:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.bottom_notice:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.bottom_work:
                        mViewPager.setCurrentItem(2);
                        break;
                  case R.id.bottom_contact:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.bottom_user:
                        mViewPager.setCurrentItem(4);
                        break;
                }
            }
        });
    }
             //判断版本号 不等返回"0"
             private int initVersion(){
                 PackageManager packageManager=getPackageManager();
                 //getPackageName()是你当前类的包名，0代表是获取版本信息

                 String url = OkHttpUtil.BASE_URL +"AppVersion" ;
                 String  result=null;
                 Gson gson = new Gson();
                 try {
                     result=OkHttpUtil.getRequest(url);
                     Log.e("result","result====>"+result);
                     //Json的解析类对象
                     JsonParser parser = new JsonParser();
                     JsonArray jsonArray = parser.parse(result).getAsJsonArray();
                     //加强for循环遍历JsonArray
                     for (JsonElement user : jsonArray) {
                         //使用GSON，直接转成Bean对象
                         app = gson.fromJson(user, AppVersion.class);

                     }
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 PackageManager appmanager=getPackageManager();
                 String code = null;
                 try{
                     PackageInfo version=appmanager.getPackageInfo(getPackageName(),0);
                   //  Log.e("version","version====>"+version.getLongVersionCode());
                     code=version.versionCode+"";
                 }catch(PackageManager.NameNotFoundException e){
                     e.printStackTrace();
                 }
                int aa=compareVersion(app.getServerVersion(),code);
                 Log.e("aa","aa====>"+aa);
                return  aa;
             }
             /**
              * 版本号比较
              *0代表相等，1代表version1大于version2，-1代表version1小于version2
              * @param version1
              * @param version2
              * @return
              */
             public static int compareVersion(String version1, String version2) {
                 if (version1.equals(version2)) {
                     return 0;
                 }
                 String[] version1Array = version1.split("\\.");
                 String[] version2Array = version2.split("\\.");
                 int index = 0;
                 // 获取最小长度值
                 int minLen = Math.min(version1Array.length, version2Array.length);
                 int diff = 0;
                 // 循环判断每位的大小
                 while (index < minLen
                         && (diff = Integer.parseInt(version1Array[index])
                         - Integer.parseInt(version2Array[index])) == 0) {
                     index++;
                 }
                 if (diff == 0) {
                     // 如果位数不一致，比较多余位数
                     for (int i = index; i < version1Array.length; i++) {
                         if (Integer.parseInt(version1Array[i]) > 0) {
                             return 1;
                         }
                     }

                     for (int i = index; i < version2Array.length; i++) {
                         if (Integer.parseInt(version2Array[i]) > 0) {
                             return -1;
                         }
                     }
                     return 0;
                 } else {
                     return diff > 0 ? 1 : -1;
                 }
             }
             //更新提示框
             private void showUpdaloadDialog(final String downloadUrl){
                 // 这里的属性可以一直设置，因为每次设置后返回的是一个builder对象
                 AlertDialog.Builder builder = new AlertDialog.Builder(this);
                 // 设置提示框的标题
                 //不关闭写法
                 builder.setCancelable(false);
                 builder.setTitle("版本升级").
                         setIcon(R.mipmap.ic_launcher). // 设置提示框的图标
                         setMessage("发现新版本！请及时更新").// 设置要显示的信息
                         setPositiveButton("确定", new DialogInterface.OnClickListener() {// 设置确定按钮
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         updateSer();

                     }
                 }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         System.exit(0);//强制更新，取消更新便退出程序

                     }
                 });
                 AlertDialog alertDialog = builder.create();

                 // 显示对话框
                 alertDialog.show();

             }

    public void initViewPager(){
        mFragmentList = new ArrayList<>();
        MessageFragment mMessageFragment = new MessageFragment();
        NoticeFragment mNoticeFragment = new NoticeFragment();
        WorkFragment mWorkFragment = new WorkFragment();
        ContactFragment mContactFragment = new ContactFragment();
        UserInfoFragment mUserInfoFragment = new UserInfoFragment();

       mFragmentList.add(mMessageFragment);
        mFragmentList.add(mNoticeFragment);
        mFragmentList.add(mWorkFragment);
        mFragmentList.add(mContactFragment);
        mFragmentList.add(mUserInfoFragment);

        mViewPager = findViewById(R.id.view_pager);
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);

    }

    @Override
    public void onClick(View v) {

    }


    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;
        private FragmentManager fm;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fm = fm;
            this.fragmentList = fragmentList;

        }


        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup vg, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(vg,
                    position);
            fm.beginTransaction().show(fragment).commit();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Fragment fragment = fragmentList.get(position);
            fm.beginTransaction().hide(fragment).commit();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

    }

}
