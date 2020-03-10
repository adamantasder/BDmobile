package com.khaki.jxc.activity;


import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import android.util.Log;

import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import com.allen.library.SuperTextView;

import com.google.gson.Gson;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.khaki.jxc.Contants;
import com.khaki.jxc.adapter.InventoryAdapter;
import com.khaki.jxc.bean.PurOrdersVo;

import com.khaki.jxc.bean.PurOderVo;



import com.khaki.jxc.client.R;
import com.khaki.jxc.client.util.DialogUtil;

import com.khaki.jxc.retrofit.purOrdersIF;
import com.khaki.jxc.utils.OkHttpUtil;

import com.khaki.jxc.view.SlideRecyclerView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * 二维码扫描后的信息显示页面
 */
public class vouch_scaninActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private TextView toolBarText;
    private SuperTextView vouch_code;//入库单
    private SuperTextView cPOID;//订单号
    private SuperTextView cBusType;//业务类型
    private SuperTextView dep;//申请部门
    private SuperTextView cPersonCode;//申请人
    private SuperTextView dPODate;//单据日期
    private SuperTextView warehouse; //厂库
    private String csysbarcode; //单号(扫描传值)
    private String userId;//用户Id
    private PurOderVo pur;
    private PurOrdersVo pv;
    private SlideRecyclerView recycler_view_list;
    private List<PurOrdersVo> mInventories;
    private InventoryAdapter mInventoryAdapter;
    private Button btnSumbit;
    private SharedPreferences preference;

    public vouch_scaninActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vouch_scanin);
        Intent intent = getIntent();
        csysbarcode = intent.getStringExtra("code");
        //验证权限
    // 获取全局变量用户Id
        preference= PreferenceManager.getDefaultSharedPreferences(this);
        userId= (String)preference.getString(Contants.ACCOUNT_PREF, "");
        if( valiuser()==2){
            DialogUtil.showDialog(this,
                    "订单已全部入库，请换码扫描", true);
        }
       else  if( valiuser()==1){
            initData();//母体显示
               initDatass();//子体显示
       btnSumbit.setOnClickListener(view ->{
            try {
               String aa= addInventory();//母单
                String bb= addInventorys();//子单
                //连接服务器
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Contants.SERVER_IP)
                        .build();
                purOrdersIF pursBiz = retrofit.create(purOrdersIF.class);//向服务器传值
                Call<ResponseBody> call = pursBiz.savepurOrders(aa,bb,userId);
                Log.e("POST传值call", "call "+call.request());
                //回调 通讯成功返回1正确 -1 失败
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String str = response.body().string();
                            Log.e("POST传值", "POST传值"+str);
                            SharedPreferences.Editor editor = preference.edit();
                            if (str.equals("1")) {
                                Headers headers = response.headers();
                                DialogUtil.showDialog(vouch_scaninActivity.this,
                                        "成功提交", true);
                            } else {
                                Log.e("POST传值", "失败");
                                DialogUtil.showDialog(vouch_scaninActivity.this,
                                        "保存失败，请重试", false);
                            }
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("服务器连接错误", "fail"+t.getMessage());
                        DialogUtil.showDialog(vouch_scaninActivity.this, "服务器响应异常，请稍后再试！", false);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
         }
         else{
             DialogUtil.showDialog(this,
                     "无权操作该单据或者扫码不正确，请换码或重新扫描", true);
         }
        valipur();//验证入库单

    }

    //获取二维码传值母体
    protected void initData() {

        vouch_code = findViewById(R.id.vouch_code);
       btnSumbit=findViewById(R.id.btnScanSumbit);
        cBusType= findViewById(R.id.vouch_type);
        dep = findViewById(R.id.vouch_dep);
        cPersonCode = findViewById(R.id.vouch_user);
        dPODate = findViewById(R.id.vouch_data);
        warehouse = findViewById(R.id.vouch_maker);
        // 定义发送请求的URL
        String url = OkHttpUtil.BASE_URL + "purOrder/"+csysbarcode;
        Log.i("csysbarcode", "csysbarcode " + csysbarcode);
        Log.i("url", "url " + url);
        //连接服务器
        String  result=null;
        try {
              result=OkHttpUtil.getRequest(url);
            Log.i("result", "result " + result);
           // jsonArray=new JSONArray(result);
          //  Log.i("jsonArray", "jsonArray " + jsonArray);
            Gson gson = new Gson();
            //Json的解析类对象
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(result).getAsJsonArray();
            //ArrayList<PurOderVo> userBeanList = new ArrayList<>();
            //加强for循环遍历JsonArray
            for (JsonElement user : jsonArray) {
                //使用GSON，直接转成Bean对象
                  pur = gson.fromJson(user, PurOderVo.class);
               // userBeanList.add(pur);
            }
            vouch_code.setRightString(pur.getcPOID());
            cBusType.setRightString(pur.getcBusType());
            dep.setRightString(pur.getcDepName());
            cPersonCode.setRightString(pur.getUsername());
            dPODate.setRightString(pur.getdPODate());
            warehouse.setRightString(pur.getcWhName());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //获取二维码传值子体
      protected void initDatass(){

        String  result=null;
        // 定义发送请求的URL
        String url = OkHttpUtil.BASE_URL + "purOrders/" + csysbarcode;
        recycler_view_list =  findViewById(R.id.recycle);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        recycler_view_list.addItemDecoration(itemDecoration);
         mInventories = new ArrayList<>();
        try {
            result=OkHttpUtil.getRequest(url);
            Log.i("ret", "ret " + result);
            Gson gson = new Gson();
            //Json的解析类对象
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(result).getAsJsonArray();
            //加强for循环遍历JsonArray
            for (JsonElement user : jsonArray) {
                //使用GSON，直接转成Bean对象
                pv = gson.fromJson(user, PurOrdersVo.class);
                // userBeanList.add(pur);
                mInventories.add(pv);
                Log.i("rets", "rets " + pv.getIvouchrowno());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mInventoryAdapter = new InventoryAdapter(this, mInventories);
        recycler_view_list.setAdapter(mInventoryAdapter);
        mInventoryAdapter.setOnDeleteClickListener((view, position) -> {
            mInventories.remove(position);
            mInventoryAdapter.notifyDataSetChanged();
            recycler_view_list.closeMenu();
        });
    }

//验证用户单据权限
    protected int valiuser(){
        // 获取全局变量用户Id
        Log.i("userId", "userId: "+userId);
        // 定义发送请求的URL
        String url = OkHttpUtil.BASE_URL + "purOrder/"+csysbarcode+"/"+userId;
        Log.i("url", "url " + url);

        //连接服务器
        String  Userresult=null;
        try {
            Userresult=OkHttpUtil.getRequest(url);
            Log.i("result", "Userresult " + Userresult);
            if(Userresult!=null && Integer.parseInt(Userresult)==1){
                return 1;
            }
            if(Userresult!=null && Integer.parseInt(Userresult)==-1){
                return -1;
            }
            if(Userresult!=null && Integer.parseInt(Userresult)==2){
                return 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**扫码时对入库单据进行校验initData()
     * 全部商品入库 2 部分商品入库 1 未入库 0
     * @return
     */
    private boolean valipur()
    {

        return true;
    }
//封装子单数锯 提交的数据
private  String addInventorys() throws Exception
{
    // 使用Map封装请求参数
     ArrayList<Map<String, String>> houseRes = new ArrayList<>();

    recycler_view_list=findViewById(R.id.recycle);
    //获取母节点数据

    String  v1=vouch_code.getRightString();
    String v2=cBusType.getRightString();
    String v3=dep.getRightString();
    String v4=cPersonCode.getRightString();
    String v5= dPODate.getRightString();
    String v6=warehouse.getRightString();
    //获取子节点数据ii

    for(int i=0; i<recycler_view_list.getChildCount();i++){
        Log.i("ret", "recycler_view_list.getChildCount() " +(recycler_view_list.getAdapter().getItemCount()-i) );//getChildCount()显示可视点
        Map<String, String> map = new HashMap<>();
        map.clear();//清除原先map的数据
        View  layout =  recycler_view_list.getChildAt(i);//一定要用view，否则得不到数据 getChildAt(i) 获取可视的点
        TextView ivouchrowno=layout.findViewById(R.id.ivouchrow);
        TextView cInvCode=layout.findViewById(R.id.cInvCode);
        TextView cInvName=layout.findViewById(R.id.cInvName);
        TextView  cInvStd=layout.findViewById(R.id.cInvStd);
        TextView  iQuantity=layout.findViewById(R.id.iQuantity);
        map.put("ivouchrowno", ivouchrowno.getText().toString());
        map.put("cInvCode", cInvCode.getText().toString());
        map.put("cInvName", cInvName.getText().toString());
        map.put("cInvStd", cInvStd.getText().toString());
        map.put("iQuantity", iQuantity .getText().toString());
        Log.i("map", "map " +i+"分割"+ map);
        houseRes.add(map);
    }
    Log.i("houseRes", "houseRes " + houseRes );
    Gson gson=new Gson();
     String aa=gson.toJson(houseRes);
    Log.i("aa", "aa " + aa );
    if(houseRes.isEmpty()){
        Log.i("houseRes", "houseRes " + "数据集为空" );
        DialogUtil.showDialog(this,"没有待入库存货，请刷新",false);
   return null;

    }
    return aa;
    // 定义发送请求的URL
    //  String url = HttpUtil.BASE_URL + "kinds";
    // 发送请求
    // return HttpUtil.postRequest(url, map);
}

//封装母单数据
private  String addInventory() throws Exception
{

    recycler_view_list=findViewById(R.id.recycle);
    //获取母节点数据

    String  v1=vouch_code.getRightString();
    String v2=cBusType.getRightString();
    String v3=dep.getRightString();
    String v4=cPersonCode.getRightString();
    String v5= dPODate.getRightString();
    String v6=warehouse.getRightString();
    // 使用Map封装请求参数
        Map<String, String> map = new HashMap<>();

        map.put("vouch_code", v1);
        map.put("cBusType", v2);
        map.put("dep", v3);
        map.put("cPersonCode",v4);
        map.put("dPODate",v5);
    map.put("warehouse", v6);
        Log.i("map", "map " + map);
    Gson gson=new Gson();
    String aa=gson.toJson(map);
    Log.i("aa", "aa " + aa );
    if(map.isEmpty()){
        Log.i("map", "map " + "数据集为空" );
        DialogUtil.showDialog(this,"单据错误，请重刷",false);
        return null;

    }
    return aa;
    // 定义发送请求的URL
    //  String url = HttpUtil.BASE_URL + "kinds";
    // 发送请求
    // return HttpUtil.postRequest(url, map);
}
}



