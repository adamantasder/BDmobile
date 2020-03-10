package com.khaki.jxc.fragement;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.khaki.jxc.Contants;
import com.khaki.jxc.activity.FileTypeSelectActivity;
import com.khaki.jxc.activity.FriendsInfoActivity;
import com.khaki.jxc.bean.ImageIconBean;
import com.khaki.jxc.client.R;
import com.khaki.jxc.event.NoticeEvent;
import com.khaki.jxc.gson.GsonQRCode;
import com.khaki.jxc.retrofit.IQRCodeBiz;
import com.khaki.jxc.utils.BitmapToRound_Util;
import com.khaki.jxc.utils.DateUtil;
import com.khaki.jxc.utils.DensityUtil;
import com.khaki.jxc.utils.OkHttpUtil;
import com.khaki.jxc.utils.SharedPrefUtil;
import com.khaki.jxc.utils.UserUntil;


import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.khaki.jxc.Contants.PHOTO_SERVER_IP;
import static com.khaki.jxc.Contants.SERVER_IP;
import static com.khaki.jxc.Contants.file_Server;

/**
 * Created by 嘉进 on 9:23.
 * 底部导航栏中我的Fragment
 */

public class UserInfoFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Uri imageUri;
    private Toolbar mToolbar;
    private Context mContext;
    private TextView titleTV;
    private TextView userNameTV;
    private TextView userPhonrTV;
    private ImageView photoIV;
    private String mCurrentPhotoPath = null;
    private RelativeLayout mRelativeLayout;
    private BitmapToRound_Util round_Util = new BitmapToRound_Util();
    private SuperTextView userInfo;
    private SuperTextView QRcode;
    private SuperTextView notice;
    private SuperTextView cloudDisk;
    private String QRPath;

    public static final int ALBUM_REQUEST = 100;
    public static final int CAMERA_REQUEST = 200;
    public static final int CROP_REQUEST = 300;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_info, container, false);
        mContext = getActivity();
        initView();
        return view;
    }


    public void initView() {
        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.layout_user_photo);
        photoIV = (ImageView) view.findViewById(R.id.iv_user_photo);
        titleTV = (TextView) view.findViewById(R.id.toolbar_title);
        userNameTV = (TextView) view.findViewById(R.id.tv_user_name);
        userPhonrTV = (TextView) view.findViewById(R.id.tv_user_phone);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        userInfo=(SuperTextView)view.findViewById(R.id.st_user_info);
        QRcode=(SuperTextView)view.findViewById(R.id.st_qr_code);
        cloudDisk = (SuperTextView) view.findViewById(R.id.st_cloud_disk);
        notice = (SuperTextView) view.findViewById(R.id.st_notice);

//1        Object object = UserUntil.gsonUser.getUserHeadPortrait();

        userPhonrTV.setText(UserUntil.phone);


        mRelativeLayout.setOnClickListener(this);
        userInfo.setOnClickListener(this);
        QRcode.setOnClickListener(this);
        cloudDisk.setOnClickListener(this);
        notice.setOnClickListener(this);
        mToolbar.setTitle("");
        titleTV.setText("我的");
    }


    public void createDialog() {
        final String items[] = {"拍照", "从相册中选择"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("设置头像");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent;
                switch (which) {
                    case 0:
                        getPortraitFromCamera();
                        break;
                    case 1:
                        intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, ALBUM_REQUEST);
                        break;
                    default:
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ALBUM_REQUEST:
                Log.e("start", "imageCrop");
                if (data != null) {
                    imageCrop(data.getData());
                }
                break;
            case CAMERA_REQUEST:
                /**
                 * 获得外部存储目录
                 */
                imageCrop(imageUri);
                break;
            case CROP_REQUEST:
                if (imageUri != null) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(imageUri));
                        File file = new File(Contants.FILEPATH + "/data/portraits", UserUntil.gsonUser.getNickname());
                        Log.e("nickName", UserUntil.gsonUser.getNickname());
                        try {
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                            bos.flush();
                            bos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.e("file", file.getPath());
                        imageIconUpload(UserUntil.gsonUser.getNickname() + ".jpg", UserUntil.phone, file);
                        photoIV.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
        }

    }

    private void getPortraitFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();//创建临时图片文件
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI;
                // 兼容 Android 7.x 以上
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    photoURI = Uri.fromFile(photoFile);
                } else {
                    //FileProvider 是一个特殊的 ContentProvider 的子类，
                    //它使用 content:// Uri 代替了 file:/// Uri. ，更便利而且安全的为另一个app分享文件
                    photoURI = FileProvider.getUriForFile(mContext,
                            "com.r2.scau.moblieofficing.fileprovider",
                            photoFile);

                }
                imageUri = photoURI;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    /**
     * 创建临时图片文件
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //创建临时文件,文件前缀不能少于三个字符,后缀如果为空默认未".tmp"
        File image = File.createTempFile(
                imageFileName,  /* 前缀 */
                ".jpg",         /* 后缀 */
                storageDir      /* 文件夹 */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void imageCrop(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        //是否返回数据  false会保存图片

        imageUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/"
                + DateUtil.currentDatetime() + UserUntil.phone + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_REQUEST);
        Log.e("event", "false1");

    }


    public void imageIconUpload(String filename, String userPhone, File image) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fileName", filename)
                .addFormDataPart("userPhone", userPhone)
                .addFormDataPart("file", filename, RequestBody.create(null, image));

        final RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(Contants.SERVER_IP + "/fileServer/uploadPortrait.shtml")
                .addHeader("cookie", OkHttpUtil.loginSessionID)
                .post(requestBody)
                .build();

        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.code() == 200) {
                    ImageIconBean imageIconBean = new Gson().fromJson(response.body().string(), ImageIconBean.class);
                    Log.e("path", imageIconBean.getPath());
                    SharedPrefUtil.getInstance().put(Contants.IMAGE_ICON_URL, imageIconBean.getPath());
                    Log.e("onResponse", (String) SharedPrefUtil.getInstance().get(Contants.IMAGE_ICON_URL, ""));
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.layout_user_photo:
                if ((ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                        || (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                } else {
                    createDialog();
                }
                break;
            case R.id.st_user_info:
                Intent intent=new Intent(v.getContext(), FriendsInfoActivity.class);
                intent.putExtra("phone",UserUntil.gsonUser.getUserPhone());
                startActivity(intent);
                break;
            case R.id.st_notice:
                EventBus.getDefault().post(new NoticeEvent());
                break;
            case R.id.st_qr_code:
                getQRCode(UserUntil.gsonUser.getUserPhone());
                break;
            case R.id.st_cloud_disk:
                Intent intent1 = new Intent(getActivity(), FileTypeSelectActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                createDialog();
            } else {
                // Permission Denied
                Toast.makeText(mContext, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void showImageDialog() {


//        imageView.setBackground(getDrawable(R.mipmap.test));
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_content_circle, null);
        Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);
        final ImageView imageView = (ImageView) contentView.findViewById(R.id.QR_image);
        Log.e("path", QRPath);
        Glide.with(getContext()).load(PHOTO_SERVER_IP + QRPath).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                imageView.setBackground(resource);
                Log.e("path", QRPath);
            }
        });
//        Glide.with(this).load(QRPath).into(imageView);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(getContext(), 16f);
        params.bottomMargin = DensityUtil.dp2px(getContext(), 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }


    public void getQRCode(String Phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_IP + file_Server + "/")
                .callFactory(OkHttpUtil.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IQRCodeBiz iqrCodeBiz = retrofit.create(IQRCodeBiz.class);
        Call<GsonQRCode> call = iqrCodeBiz.getQR(Phone);
        call.enqueue(new Callback<GsonQRCode>() {
            @Override
            public void onResponse(Call<GsonQRCode> call, Response<GsonQRCode> response) {
                GsonQRCode gsonQRCode = response.body();
                if (gsonQRCode.getCode() == 200) {
                    QRPath = gsonQRCode.getPath();
                    Log.e("getQR", "success");
                    showImageDialog();
                } else {
                    Log.e("getQR", gsonQRCode.getMsg());
                }
            }

            @Override
            public void onFailure(Call<GsonQRCode> call, Throwable t) {
                Log.e("getInfo", "fail");
            }
        });
    }
}
