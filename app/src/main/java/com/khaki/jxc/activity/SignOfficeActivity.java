package com.khaki.jxc.activity;


import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.khaki.jxc.client.R;
import com.khaki.jxc.event.SignOfficeEvent;
import com.khaki.jxc.utils.RetrofitUntil;
import com.khaki.jxc.utils.ToastUtils;
import com.khaki.jxc.widge.popview.PopField;


import android.app.Activity;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SignOfficeActivity extends Activity implements GeocodeSearch.OnGeocodeSearchListener,
        AMap.OnMyLocationChangeListener, View.OnClickListener {

    private MapView mMapView = null;
    private GeocodeSearch geocoderSearch;
    private MyLocationStyle myLocationStyle;
    private AMap aMap;
    private TextView addressTextView;

    private ImageView mSignOfficeBtn;
    String formatAddress;
    private PopField mPopField;
    private ImageView unSignIv;
    private TextView unSingTv;
    private ImageView backIv;
    private TextView clockTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_office);
        EventBus.getDefault().register(this);

        mPopField = PopField.attach2Window(this);
        initialView(savedInstanceState);
    }

    private void initialView(Bundle savedInstanceState) {
        addressTextView = (TextView) findViewById(R.id.office_point_tv);
        mSignOfficeBtn = (ImageView) findViewById(R.id.sign_iv);
        unSignIv = (ImageView) findViewById(R.id.un_sign_iv);
        unSingTv = (TextView) findViewById(R.id.un_sign_tv);
        backIv = (ImageView) findViewById(R.id.back_iv);
        clockTv = (TextView) findViewById(R.id.clock_itv);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
       clockTv.setText(df.format(new Date()));// new Date()为获取当前系统时间

        backIv.setOnClickListener(this);
        mSignOfficeBtn.setOnClickListener(this);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

        //初始化地图控制器对象
        aMap = mMapView.getMap();
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        myLocationStyle.strokeWidth(0f);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_focuse_mark));
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(this);
        aMap.getUiSettings();
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        if(rCode == 1000){
            formatAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
            addressTextView.setText(formatAddress);
            Log.d("located","success:"+formatAddress);
        }else{
            Log.e("located","error");
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onMyLocationChange(Location location) {
        LatLonPoint latLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 0, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.sign_iv:
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String curTime = df.format(new Date());// new Date()为获取当前系统时间
                if (formatAddress == null || formatAddress.equals("")){
                    formatAddress = "成都郫县梦翔大夏";
                }
                RetrofitUntil.signOffice(curTime,  formatAddress );
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.item_sign_in_btn, null);
                final ImageView loginBtn2 = (ImageView) addView.findViewById(R.id.sign_iv);
                loginBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        String curTime = df.format(new Date());// new Date()为获取当前系统时间
                        RetrofitUntil.signOffice(curTime,  formatAddress );
                    }
                });
                mPopField.popView(mSignOfficeBtn, addView, true);
                break;
            default:
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginFinishEvent(SignOfficeEvent signOfficeEvent) {
        ToastUtils.show(SignOfficeActivity.this, "签到成功");
        unSignIv.setImageResource(R.drawable.checkbox_album_pressed);
        unSingTv.setText("已签到");
    }


}
