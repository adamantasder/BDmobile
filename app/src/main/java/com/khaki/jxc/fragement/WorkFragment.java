package com.khaki.jxc.fragement;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.khaki.jxc.Contants;

import com.khaki.jxc.activity.FieldJxcActivity;
import com.khaki.jxc.activity.FieldWorkActivity;
import com.khaki.jxc.activity.FileTypeSelectActivity;


import com.khaki.jxc.activity.PU_vouchActivity;
import com.khaki.jxc.activity.ReportActivity;
import com.khaki.jxc.activity.SignOfficeActivity;
import com.khaki.jxc.activity.vouch_scanOutActivity;
import com.khaki.jxc.activity.vouch_scaninActivity;
import com.khaki.jxc.client.R;
import com.khaki.jxc.utils.ImageUtils;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * Created by khaki on 9:21.
 * 底部导航栏中工作的Fragment
 * scanin  canout 功能 其他调用FieldWorkActivity
 */

public class WorkFragment extends Fragment implements View.OnClickListener {

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private View view;
    private Context mContext;
    private Toolbar mToolbar;
    private TextView titleTV;
    private TextView tv_scanin;
    private ImageView dateReportBtn;
    private ImageView weekReportBtn;
    private ImageView monthReportBtn;

    /**  图标 */
    private ImageView leaveBtn;
    private ImageView gooutBtn;
    private ImageView busniess_trip;
    private ImageView work_overtimeBtn;
    private ImageView clouddiskBtn;
    private ImageView signOfficeBtn;
    private ImageView videoMeetingBtn;
    private ImageView jxc_vouch_scan_in;
    private ImageView jxc_vouch_scan_out;
    private ImageView jxc_vouch_checked  ;
    private ImageView jxc_vouch;
    private ImageView energy_energy;
    private ImageView energy_realdata;
    private ImageView energy_product;
    private ImageView energy_point;





    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.fragment_work, container, false);
        mContext = getActivity();
     initView();
        return view;
    }

    public void initView(){
        titleTV = (TextView) view.findViewById(R.id.toolbar_title);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        dateReportBtn = (ImageView) view.findViewById(R.id.btn_day_report);
        weekReportBtn = (ImageView) view.findViewById(R.id.btn_week_report);
        monthReportBtn = (ImageView) view.findViewById(R.id.btn_month_report);
        leaveBtn = (ImageView) view.findViewById(R.id.btn_leave);
        gooutBtn = (ImageView) view.findViewById(R.id.btn_goout);
        busniess_trip = (ImageView) view.findViewById(R.id.btn_business_trip);
        work_overtimeBtn = (ImageView) view.findViewById(R.id.btn_work_overtime);
        clouddiskBtn = (ImageView) view.findViewById(R.id.btn_cloud_disk);
        signOfficeBtn = (ImageView) view.findViewById(R.id.btn_sign_in);
        videoMeetingBtn = (ImageView) view.findViewById(R.id.btn_video_meeting);
        jxc_vouch_scan_in= (ImageView) view.findViewById(R.id.jxc_vouch_scan_in);
         jxc_vouch_scan_out= (ImageView) view.findViewById(R.id.jxc_vouch_scan_out);
        jxc_vouch_checked  = (ImageView) view.findViewById(R.id.jxc_vouch_checked);
        jxc_vouch= (ImageView) view.findViewById(R.id.jxc_vouch);
        energy_energy= (ImageView) view.findViewById(R.id.energy_energy);
         energy_realdata= (ImageView) view.findViewById(R.id.energy_realdata);
         energy_product = (ImageView) view.findViewById(R.id.energy_product);
        energy_point = (ImageView) view.findViewById(R.id.energy_point);
        tv_scanin= view.findViewById(R.id.tv_scanin);
        ImageUtils.setUserRectImageIcon(mContext, dateReportBtn, "日");
        ImageUtils.setUserRectImageIcon(mContext, weekReportBtn, "周");
        ImageUtils.setUserRectImageIcon(mContext, monthReportBtn, "月");


        dateReportBtn.setOnClickListener(this);
        weekReportBtn.setOnClickListener(this);
        monthReportBtn.setOnClickListener(this);
        leaveBtn.setOnClickListener(this);
        gooutBtn.setOnClickListener(this);
        busniess_trip.setOnClickListener(this);
        work_overtimeBtn.setOnClickListener(this);
        clouddiskBtn.setOnClickListener(this);
        signOfficeBtn.setOnClickListener(this);
        videoMeetingBtn.setOnClickListener(this);
        jxc_vouch_scan_in.setOnClickListener(this);
        jxc_vouch_scan_out.setOnClickListener(this);
        jxc_vouch_checked.setOnClickListener(this);
        jxc_vouch.setOnClickListener(this);
        energy_energy.setOnClickListener(this);
        energy_realdata.setOnClickListener(this);
        energy_product.setOnClickListener(this);
        energy_point.setOnClickListener(this);

        mToolbar.setTitle("");
        titleTV.setText("工作");
    }

    public void openActivity(int type){
        Intent intent = new Intent(mContext, ReportActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void openFieldWorkActivity(int type){
        Intent intent = new Intent(mContext, FieldWorkActivity.class);
        intent.putExtra("fieldworkType", type);
        startActivity(intent);
    }
    public void openVouchScanOutActivity(int type){
        Intent intent = new Intent(mContext, vouch_scanOutActivity.class);
        intent.putExtra("fieldworkType", type);
        startActivity(intent);
    }
    public void openPu_vouchActivity(int type){
        Intent intent = new Intent(mContext, PU_vouchActivity.class);
        intent.putExtra("fieldworkType", type);
        startActivity(intent);
    }
    public void openFieldEnergyActivity(int type){
        Intent intent = new Intent(mContext, FieldJxcActivity.class);
        intent.putExtra("fieldworkType", type);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_day_report:
                openActivity(Contants.OPEN_DAY_REPORTY);
                break;
            case R.id.btn_week_report:
                openActivity(Contants.OPEN_WEEK_REPORT);
                break;
            case R.id.btn_month_report:
                openActivity(Contants.OPEN_MONTH_REPORT);
                break;
            case R.id.btn_leave:
                openFieldWorkActivity(Contants.FIELDWORK.OPEN_LEAVE);
                break;
            case R.id.btn_goout:
                openFieldWorkActivity(Contants.FIELDWORK.OPEN_GO_OUT);
                break;
            case R.id.btn_business_trip:
                openFieldWorkActivity(Contants.FIELDWORK.OPEN_TRAVEL);
                break;
            case R.id.btn_work_overtime:
                openFieldWorkActivity(Contants.FIELDWORK.OPEN_OVERTIME);
                break;
            case R.id.btn_cloud_disk:
                Intent intent = new Intent(getActivity(), FileTypeSelectActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_sign_in:
                requestLocationPermission();
                break;
        /*    case R.id.btn_video_meeting:
                Intent intent2video = new Intent(getActivity(), io.agora.openvcall.ui.MainActivity.class);
                startActivity(intent2video);
                break;*/
        /*jxc*/
            case R.id.jxc_vouch_scan_in:
                //动态权限申请
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    openQRCodeActivity();

                }
                break;
                case R.id.jxc_vouch_scan_out:
                openVouchScanOutActivity(Contants.FIELDWORK.OPEN_VOUCHSCANO);
                break;case R.id.jxc_vouch_checked:
                openFieldWorkActivity(Contants.FIELDWORK.OPEN_LEAVE);
                break;case R.id.jxc_vouch:
                openPu_vouchActivity(Contants.FIELDWORK.OPEN_LEAVE);
                break;case R.id.energy_energy:
                openFieldWorkActivity(Contants.FIELDWORK.OPEN_LEAVE);
                break;case R.id.energy_realdata:
                openFieldWorkActivity(Contants.FIELDWORK.OPEN_LEAVE);
                break;case R.id.energy_product:
                openFieldWorkActivity(Contants.FIELDWORK.OPEN_LEAVE);
                break;case R.id.energy_point:
                openFieldWorkActivity(Contants.FIELDWORK.OPEN_LEAVE);
                break;

            default:
                break;
        }
    }

    private void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // 申请权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                // 已经申请权限
                Intent intent2 = new Intent(getActivity(), SignOfficeActivity.class);
                startActivity(intent2);
            }
        } else {
            Intent intent2 = new Intent(getActivity(), SignOfficeActivity.class);
            startActivity(intent2);
        }
    }
    /**
     * 跳转到扫码界面扫码
     */
    public void openQRCodeActivity() {

        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, Contants.RequestCode.QRSCAN);

    }
/*    public void openQRCodeOutActivity() {

        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, Contants.RequestCode.QRSCANO);

    }*/
    //扫描后回传跳转
    @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Contants.RequestCode.QRSCAN:
                if (resultCode == RESULT_OK) {
                    /*
                     * Create by khaki in 2017/08/04
                     * resultdata代表的是 二维码内部储存的信息
                     *
                     * 自己解析resultdata中的字段 然后在做相应操作
                     */
                    String resultdata = data.getStringExtra("result");
                    Log.e("二维码扫描结果", resultdata);
                  //  String[] resultarr = resultdata.split(":");

                    if (resultdata != null) {
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent(getActivity(), vouch_scaninActivity.class);
                            bundle.putString("code", resultdata);
                            intent.putExtras(bundle);
                            startActivity(intent);
                       // tv_scanin.setText(resultdata);
                    }

                } else if (resultCode == RESULT_CANCELED) {
                    Log.e("二维码扫描结果", "用户选择取消");
                }
                break;
    /*        case Contants.RequestCode.QRSCANO:
                if (resultCode == RESULT_OK) {
                    String resultdata = data.getStringExtra("result");
                    Log.e("二维码扫描结果", resultdata);
                    //  String[] resultarr = resultdata.split(":");

                    if (resultdata != null) {
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(getActivity(), vouch_scanOutActivity.class);
                        bundle.putString("code", resultdata);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                } else if (resultCode == RESULT_CANCELED) {
                    Log.e("二维码扫描结果", "用户选择取消");
                }
                break;*/
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 申请权限成功
                    Intent intent2 = new Intent(getActivity(), SignOfficeActivity.class);
                    startActivity(intent2);
                    openQRCodeActivity();
                } else {
                    // 用户勾选了不再询问，提示用户手动打开权限
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                        Toast.makeText(getActivity(), "相机权限已经被禁止", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

        }

    }



}
