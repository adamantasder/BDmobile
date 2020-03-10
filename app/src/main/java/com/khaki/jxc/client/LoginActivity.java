package com.khaki.jxc.client;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.khaki.jxc.Contants;
import com.khaki.jxc.activity.AuctionClientActivity;
import com.khaki.jxc.bean.User;
import com.khaki.jxc.bean.untils.OkHttpUntil;
import com.khaki.jxc.client.util.DialogUtil;

import com.khaki.jxc.event.LoginFinishEvent;
import com.khaki.jxc.retrofit.loginIF;
import com.khaki.jxc.smack.SmackListenerManager;
import com.khaki.jxc.smack.SmackManager;
import com.khaki.jxc.utils.OkHttpUtil;
import com.khaki.jxc.utils.RetrofitUntil;
import com.khaki.jxc.utils.SharedPrefUtil;
import com.khaki.jxc.utils.UserUntil;
import com.khaki.jxc.widge.popview.PopField;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.io.IOException;
import java.util.List;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Callback;


/**
 * Program Name: 登录<br>
 * Date:2019-06-21<br>
 *
 * @author khaki<br>
 * @version 1.0
 */
public class LoginActivity extends Activity {
    // 定义界面中两个文本框
    User uf=null;
    private EditText etName;
    private EditText etPass;
    private String loginSessionID;
    private CheckBox rememberPwd;
    private  SharedPreferences  preference;
    private String userPwd;
    Response<ResponseBody> response;
    private static final int MY_PERMISSIONS_REQUEST_USER_ID = 1;
    // 登录特效
    private PopField mPopField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);//恢复原有的样式
        setContentView(R.layout.login);
        // 获取界面中两个编辑框
        etName = findViewById(R.id.userEditText);
        etPass = findViewById(R.id.pwdEditText);

        ImageView img=findViewById(R.id.image_user);
        ImageView imgP=findViewById(R.id.image_password);
        //从 SharedPreferences 中获取【是否记住密码】参数
         preference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = preference.getBoolean(Contants.REMEMBER_PWD_PREF, false);
         rememberPwd = (CheckBox) findViewById(R.id.remember_pwd);

        if (isRemember) {//设置【账号】与【密码】到文本框，并勾选【记住密码】
            etName.setText(preference.getString(Contants.ACCOUNT_PREF, ""));
            etPass.setText(preference.getString(Contants.PASSWORD_PREF, ""));
            rememberPwd.setChecked(true);
        }

        // 定义并获取界面中两个按钮
        Button bnLogin = findViewById(R.id.bnLogin);
    //    Button bnCancel = findViewById(R.id.bnCancel);
        // 为bnCancal按钮的单击事件绑定事件监听器

       // 获取用户editText焦点 更换图片
         etName.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener(){
             @Override
             public void onFocusChange(View v, boolean hasFocus) {

                 if (hasFocus) {
                     img.setImageResource(R.drawable.login_icon_username_focus);
                 }else {

                     img.setImageResource(R.drawable.login_icon_username_initial);

                 }
             }
         });
        // 获取密码editText焦点 更换图片
        etPass.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    imgP.setImageResource(R.drawable.login_icon_password_focus);
                }else {

                    imgP.setImageResource(R.drawable.login_icon_password_initial);

                }
            }
        });
        bnLogin.setOnClickListener(view -> {
            // 执行输入校验
            if (validate()) // ①
            {
                // 如果登录成功
                login();
            }

        });
    }

    public void login() {
        String userId = etName.getText().toString();
        String userPwd = etPass.getText().toString();
     /*   Map<String, String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("userPwd",userPwd);*/
        //连接服务器
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contants.SERVER_IP)
                .build();
        loginIF loginBiz = retrofit.create(loginIF.class);//向服务器传值
        Call<ResponseBody> call = loginBiz.getPost(userId,userPwd,true);
        Log.e("POST传值call", "call "+call.request());
        //回调 通讯成功返回1正确 -1 失败
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    Log.e("login", "登陆成功");
                    SharedPreferences.Editor editor = preference.edit();
                    if (rememberPwd.isChecked()) {//记住账号与密码
                        editor.putBoolean(Contants.REMEMBER_PWD_PREF, true);
                        editor.putString(Contants.ACCOUNT_PREF, userId);
                        editor.putString(Contants.PASSWORD_PREF,userPwd);
                    } else {//清空数据
                        editor.clear();
                    }
                    editor.apply();

                    if (str.equals("1")) {
                        //共享
                      //  SharedPrefUtil.getInstance().put(Contants.SP_LOGIN_USER_ID_KEY, userId);
                     // SharedPrefUtil.getInstance().put(Contants.SP_LOGIN_PASSWORD_KEY, userPwd);
                        String url = OkHttpUtil.BASE_URL + "users/"+userId;
                        String  result=null;
                        try {
                            result=OkHttpUtil.getRequest(url);
                            Gson gson = new Gson();
                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            JsonArray jsonArray = parser.parse(result).getAsJsonArray();
                            //加强for循环遍历JsonArray
                            for (JsonElement user : jsonArray) {
                                //使用GSON，直接转成Bean对象
                                uf = gson.fromJson(user, User.class);


                                // userBeanList.add(pur);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.d("DEPARTMENT", "DEPARTMENT-size: " +  uf.getUsername());
                        editor.putString(Contants.DEPARTMENT, uf.getcDept());
                        editor.putString(Contants.USERNAME, uf.getUsername());
                        editor.apply();
                        Headers headers = response.headers();
                        Log.d("info_headers", "header " + headers);
                        List<String> cookies = headers.values("Set-Cookie");
                        String session = cookies.get(0);
                        Log.d("info_cookies", "onResponse-size: " + cookies);
                        loginSessionID = session.substring(0, session.indexOf(";"));
                        OkHttpUntil.loginSessionID = loginSessionID;

                        Log.i("info_s", "session is  :" + OkHttpUntil.loginSessionID );

                        RetrofitUntil.type = Contants.LOGIN_IN_GET_DATA;
                       // RetrofitUntil.getUserInfo();
                       // RetrofitUntil.getFriend();
                        //RetrofitUntil.getGroupInfo();
                      // loginOpenFire();
                        // 启动AuctionClientActivity
                       Intent intent = new Intent(LoginActivity.this, AuctionClientActivity.class);
                       startActivity(intent);
                        // 结束该Activity
                       finish();

                    } else {
                        Log.e("login", "用户名或者密码错误，请重新输入！");
                        DialogUtil.showDialog(LoginActivity.this,
                                "用户名或者密码错误，请重新输入！", false);
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.e("服务器连接错误", "fail"+t.getMessage());
            DialogUtil.showDialog(LoginActivity.this, "服务器响应异常，请稍后再试！", false);
        }
    });
            }

            //即时聊天
    public void loginOpenFire(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SmackManager smack = SmackManager.getInstance();
                smack.login(UserUntil.userId, userPwd);
                try {
                    SmackListenerManager.addGlobalListener();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if (requestCode == MY_PERMISSIONS_REQUEST_USER_ID)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                initialPath();
            }
            else
            {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void initialPath() {
        File file = new File(Contants.FILEPATH+"/data/portraits");
        if(!file.exists()){
            file.mkdirs();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginFinishEvent(LoginFinishEvent finishEvent) {
        Intent intent = new Intent(LoginActivity.this, AuctionClientActivity.class);
        startActivity(intent);
        finish();
    }

    protected void initData() {
        CharSequence userId = (CharSequence) SharedPrefUtil.getInstance().get(Contants.SP_LOGIN_USER_ID_KEY,"");
        CharSequence userPwd = (CharSequence) SharedPrefUtil.getInstance().get(Contants.SP_LOGIN_PASSWORD_KEY,"");
        if (etName != null){

            etName.setText(userId);
            if (userPwd != null){
                etPass.setText((CharSequence) SharedPrefUtil.getInstance().get(Contants.SP_LOGIN_PASSWORD_KEY,""));
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_USER_ID);
        }
        else{
            initialPath();
        }
    }


   /// 对用户输入的用户名、密码进行校验
    private boolean validate() {
        String username = etName.getText().toString().trim();
        if (username.equals("")) {
            DialogUtil.showDialog(this, "用户账户是必填项！", false);
            return false;
        }
        String pwd = etPass.getText().toString().trim();
        if (pwd.equals("")) {
            DialogUtil.showDialog(this, "用户口令是必填项！", false);
            return false;
        }
        return true;
    }

}

