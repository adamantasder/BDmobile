package com.khaki.jxc.activity;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.khaki.app.base.BaseActivity;
import com.khaki.jxc.client.R;
import com.khaki.jxc.event.NoticeEvent;
import com.khaki.jxc.fragement.ContactFragment;
import com.khaki.jxc.fragement.MessageFragment;
import com.khaki.jxc.fragement.NoticeFragment;
import com.khaki.jxc.fragement.UserInfoFragment;
import com.khaki.jxc.fragement.WorkFragment;


import com.khaki.jxc.widge.NoScrollViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.x;
import static android.R.attr.y;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private TextView mMessageTV;
    private TextView mNoticeTV;
    private TextView mWorkTV;
    private TextView mContactTV;
    private TextView mUserInfoTV;
    private NoScrollViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private MyFragmentPagerAdapter adapter;
    private BottomBar bottomBar;
    private long backLastPressedTimestamp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    @Override
    protected void initView() {
        setContentView(R.layout.main);

        /**初始化facebook 图片加载器*/
        Fresco.initialize(MainActivity.this);

        mMessageTV = (TextView) findViewById(R.id.tv_bottom_message);
        mNoticeTV = (TextView) findViewById(R.id.tv_bottom_notice);
        mWorkTV= (TextView) findViewById(R.id.tv_bottom_work);
        mContactTV = (TextView) findViewById(R.id.tv_bottom_contact);
        mUserInfoTV = (TextView) findViewById(R.id.tv_bottom_user);

        initViewPager();
        initialBottomView();
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

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mMessageTV.setOnClickListener(this);
        mNoticeTV.setOnClickListener(this);
        mWorkTV.setOnClickListener(this);
        mContactTV.setOnClickListener(this);
        mUserInfoTV.setOnClickListener(this);
        mMessageTV.setSelected(true);
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

        mViewPager = (NoScrollViewPager) findViewById(R.id.view_pager);
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * 将底部导航栏的TextView的状态设置为未选择
     */

    public void selected(){
        mMessageTV.setSelected(false);
        mNoticeTV.setSelected(false);
        mWorkTV.setSelected(false);
        mContactTV.setSelected(false);
        mUserInfoTV.setSelected(false);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            bottomBar.selectTabAtPosition(mViewPager.getCurrentItem());
            switch (mViewPager.getCurrentItem()) {
                case 0:
                    selected();
                    mMessageTV.setSelected(true);
                    break;
                case 1:
                    selected();
                    mNoticeTV.setSelected(true);
                    break;
                case 2:
                    selected();
                    mWorkTV.setSelected(true);
                    break;
                case 3:
                    selected();
                    mContactTV.setSelected(true);
                    break;
                case 4:
                    selected();
                    mUserInfoTV.setSelected(true);
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_bottom_message:
                selected();
                view.setSelected(true);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_bottom_notice:
                selected();
                view.setSelected(true);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tv_bottom_work:
                selected();
                view.setSelected(true);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.tv_bottom_contact:
                selected();
                view.setSelected(true);
                mViewPager.setCurrentItem(3);
                break;
            case R.id.tv_bottom_user:
                selected();
                view.setSelected(true);
                mViewPager.setCurrentItem(4);
                break;
        }
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;
        private FragmentManager fm;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fm = fm;
            this.fragmentList = fragmentList;
            Log.e("adapter", "fragment");
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
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - backLastPressedTimestamp > 2 * 1000) {
            Toast.makeText(MainActivity.this, R.string.press_back_again_to_exit, Toast.LENGTH_SHORT).show();
            backLastPressedTimestamp = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    public void setViewPagerToNotice(){

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoticeEvent(NoticeEvent event) {
        /**
         * 模拟mNoticeTV点击
         */
        long downTime = SystemClock.uptimeMillis();

        MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 1000;

        MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_UP, x, y, 0);

        mNoticeTV.onTouchEvent(downEvent);
        mNoticeTV.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();

    }


}
