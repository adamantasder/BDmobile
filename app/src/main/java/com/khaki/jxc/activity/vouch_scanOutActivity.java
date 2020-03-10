package com.khaki.jxc.activity;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.khaki.jxc.Contants;
import com.khaki.jxc.adapter.InventoryAdapter;
import com.khaki.jxc.adapter.InventoryOutAdapter;
import com.khaki.jxc.bean.Inventory;
import com.khaki.jxc.bean.PurOderVo;
import com.khaki.jxc.bean.PurOrdersVo;
import com.khaki.jxc.client.R;
import com.khaki.jxc.client.util.DialogUtil;
import com.khaki.jxc.retrofit.purOrdersIF;
import com.khaki.jxc.utils.OkHttpUtil;
import com.khaki.jxc.view.AddSubView;
import com.khaki.jxc.view.SlideRecyclerView;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class vouch_scanOutActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private TextView toolBarText;
    private SuperTextView vouch_code;//入库单
    private SuperTextView cPOID;//订单号
    private SuperTextView cBusType;//业务类型
    private SuperTextView dep;//申请部门
    private SuperTextView cPersonCode;//申请人
    private SuperTextView dPODate;//单据日期
    private SuperTextView warehouse; //厂库
    private SuperTextView vouch_items;//添加订单信息
    private String csysbarcode; //单号(扫描传值)
    private String userId;//用户Id
    private String depart;//用户部门
    private String username;//用户部门
    private PurOderVo pur;
    private Inventory pv;
    private SlideRecyclerView recycler_view_list;
    private List<Inventory> mInventories;
    private InventoryOutAdapter mInventoryAdapter;
    private Button btnSumbit;
    private SharedPreferences preference;
    //声明控件
    private Spinner spinner;

    private ArrayAdapter arr_adapter = null;

    private List<String> list = new ArrayList<String>();

    private TextView text;

    private String spinnerText;

    private String spinnerStr;

    public vouch_scanOutActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vouch_scanout);
        Intent intent = getIntent();
        vouch_items=findViewById(R.id.vouch_items);
        warehouse = findViewById(R.id.vouch_maker);
        toolBarText = findViewById(R.id.toolbar_title);
        mToolBar = findViewById(R.id.toolbar);

        mToolBar.setTitle("");
        toolBarText.setText("存货出库");
        /**
         * 根据实际需求对需要的View设置点击事件
         */
        vouch_items.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                superTextView.setCbChecked(!superTextView.getCbisChecked());
            }
        }).setRightImageViewClickListener(new SuperTextView.OnRightImageViewClickListener() {
            @Override
            public void onClickListener(ImageView imageView) {
                Toast.makeText(vouch_scanOutActivity.this, "添加出库存货", Toast.LENGTH_SHORT).show();
                //动态权限申请
                if (ContextCompat.checkSelfPermission(vouch_scanOutActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(vouch_scanOutActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    openQRCodeActivity();
                }
            }
        });
        //验证权限
// 获取全局变量用户Id
        Log.e("二维码扫描结果", "用户选择取消"+csysbarcode);
        preference= PreferenceManager.getDefaultSharedPreferences(this);
        userId= preference.getString(Contants.ACCOUNT_PREF, "");
        depart= preference.getString(Contants.DEPARTMENT, "");
        username= preference.getString(Contants.USERNAME, "");
        initData();
        btnSumbit.setOnClickListener(view -> {
            try {
                String aa = addInventory();//母单
                String bb = addInventorys();//子单
                   if (valipur()) {
                //连接服务器
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Contants.SERVER_IP)
                        .build();
                purOrdersIF pursBiz = retrofit.create(purOrdersIF.class);//向服务器传值
                Call<ResponseBody> call = pursBiz.saveOutOrders(aa, bb, userId);
                Log.e("POST传值call", "call " + call.request());
                //回调 通讯成功返回1正确 -1 失败
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String str = response.body().string();
                            Log.e("POST传值", "POST传值" + str);
                            SharedPreferences.Editor editor = preference.edit();
                            if (str.equals("1")) {
                                Headers headers = response.headers();
                                DialogUtil.showDialog(vouch_scanOutActivity.this,
                                        "成功提交", true);
                            } else {
                                Log.e("POST传值", "失败");
                                DialogUtil.showDialog(vouch_scanOutActivity.this,
                                        "保存失败，请重试", false);
                            }
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("服务器连接错误", "fail" + t.getMessage());
                        DialogUtil.showDialog(vouch_scanOutActivity.this, "服务器响应异常，请稍后再试！", false);
                    }
                });
                   }
            } catch (Exception e) {
                e.printStackTrace();
            }

         });

    }

    /**
     * 跳转到扫码界面扫码
     */
    public void openQRCodeActivity() {

        Intent intent = new Intent(vouch_scanOutActivity.this, CaptureActivity.class);
        startActivityForResult(intent, Contants.RequestCode.QRSCAN);

    }
    //扫描后回传跳转
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Contants.RequestCode.QRSCAN:
                if (resultCode == RESULT_OK) {
                      csysbarcode = data.getStringExtra("result");
                    Log.e("二维码扫描结果", csysbarcode);
                    if (csysbarcode != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("code", csysbarcode);
                        initDatass(csysbarcode);
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Log.e("二维码扫描结果", "用户选择取消");
                }
                break;
        }
    }
        //获取母单用户信息及单据号
  protected void initData() {


      vouch_code = findViewById(R.id.vouch_codeout);
      btnSumbit=findViewById(R.id.btnScanSumbit);
      cBusType= findViewById(R.id.vouch_type);
      dep = findViewById(R.id.vouch_dep);
      cPersonCode = findViewById(R.id.vouch_user);
      dPODate = findViewById(R.id.vouch_data);

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
    //获取当前时间
      Date date = new Date(System.currentTimeMillis());

      String url = OkHttpUtil.BASE_URL + "purOr/"+userId;
      Log.i("url", "url " + url);
      //连接服务器
      String  result=null;
      try {
          result=OkHttpUtil.getRequest(url);
          Log.i("result", "result " + result);
         Gson gson = new Gson();
         Map<String,String> map=new HashMap<>();
          JsonParser parser = new JsonParser();
          JsonArray jsonArray = parser.parse(result).getAsJsonArray();
          ArrayList<PurOderVo> pursList = gson.fromJson(jsonArray,
                  new TypeToken<ArrayList<PurOderVo>>() {
                  }.getType());
          for(int i=0;i<pursList.size();i++) {
              Log.i("pursList", "pursList " + pursList.get(i).getcWhName());
              list.add(pursList.get(i).getcWhName());
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      //-----下拉框始
      //关联控件
      spinner = (Spinner) findViewById(R.id.spinner);

      //适配器
      arr_adapter = new ArrayAdapter(this, R.layout.simple_spinner_item, list);

      //设置样式
      arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

      //加载spinner适配器
      spinner.setAdapter(arr_adapter);

      //Spinner 选择数据监听事件
      spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

          public void onItemSelected(AdapterView<?> arg0, View arg1,
                                     int arg2, long arg3) {
              //初始化不显示被选择的第一项
              arg1.setVisibility(View.INVISIBLE) ;
              spinnerText = arr_adapter.getItem(arg2).toString();
              warehouse.setRightString(spinnerText);
          }
          public void onNothingSelected(AdapterView<?> arg0) {
              spinnerText = "";
              warehouse.setRightString(spinnerText);
          }
      });
      //-----下拉框末
    //加载数据
         vouch_code.setRightString("");
          cBusType.setRightString("其他出库");
          dep.setRightString(depart);
          cPersonCode.setRightString(username);
          dPODate.setRightString(simpleDateFormat.format(date));
          warehouse.setRightString("");



  }



    //获取二维码传值子体
    protected void initDatass(String  csysbarcode){
        String  result=null;
        String bb=null;
        try {
            //获取子表之前的数据
         bb= addInventorys();//子单

            Log.e("bb", "bb------>"+bb);
            Log.e("bb", "bb.length------>"+bb.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mInventories = new ArrayList<>();
        // 定义发送请求的URL
         String wareh=warehouse.getRightString();
        String url = OkHttpUtil.BASE_URL + "purOrd/" + csysbarcode+"/"+ wareh;
        try {
            result=OkHttpUtil.getRequest(url);
            if(bb.length()>2){
                bb=bb.substring(0,bb.length()-1);
                result=result.substring(1,result.length());
                Log.i("bbb", "bbb " + bb);
                Log.i("ccc", "ccc " + result);
                result=bb+","+result;}
            Log.i("ret", "ret " + result);

            Gson gson = new Gson();
            //Json的解析类对象
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(result).getAsJsonArray();
            //加强for循环遍历JsonArray
            for (JsonElement user : jsonArray) {
                //使用GSON，直接转成Bean对象
                pv = gson.fromJson(user, Inventory.class);
                mInventories.add(pv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        recycler_view_list =  findViewById(R.id.recycleOut);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        recycler_view_list.addItemDecoration(itemDecoration);
        Log.i("mInventories", "mInventories " + mInventories.get(0).getcInvCode());
        //   获取数据，向适配器传数据，绑定适配器
        mInventoryAdapter = new InventoryOutAdapter(this, mInventories);
        recycler_view_list.setAdapter(mInventoryAdapter);
        //   添加动画
        recycler_view_list.setItemAnimator(new DefaultItemAnimator());


        mInventoryAdapter.setOnDeleteClickListener((view, position) -> {
            mInventories.remove(position);
            mInventoryAdapter.notifyDataSetChanged();
            recycler_view_list.closeMenu();
        });
    }


    /**扫码时对入库单据进行校验initData()
     * 全部商品入库 2 部分商品入库 1 未入库 0
     * @return
     */
    private boolean valipur()
    {
        for(int i=0; i<recycler_view_list.getChildCount();i++) {
            Map<String, String> map = new HashMap<>();
            map.clear();//清除原先map的数据
            View layout = recycler_view_list.getChildAt(i);//一定要用view，否则得不到数据
            TextView ivouchrowno = layout.findViewById(R.id.ivouchrownoout);
            TextView cInvName = layout.findViewById(R.id.cInvNameout);
            TextView currentIQ = layout.findViewById(R.id.currentIQ);
            AddSubView iQuantity = layout.findViewById(R.id.iQuantityout);
            float a= Float.parseFloat(currentIQ.getText().toString());
            float b=Float.parseFloat(iQuantity.getValue());
            if(a-b<0){
                DialogUtil.showDialog(this,"行"+ivouchrowno.getText().toString()
                        +","+cInvName.getText().toString()+",出库数不能大于库存数",false);
                return false;
            }
        }
        return true;
    }
//封装子单数锯 提交的数据
private  String addInventorys() throws Exception
{
    // 使用Map封装请求参数
     ArrayList<Map<String, String>> houseRes = new ArrayList<>();

    recycler_view_list=findViewById(R.id.recycleOut);
    //获取母节点数据
    String  v1=vouch_code.getRightString();
    String v2=cBusType.getRightString();
    String v3=dep.getRightString();
    String v4=cPersonCode.getRightString();
    String v5= dPODate.getRightString();
    String v6=warehouse.getRightString();
    //获取子节点数据
    for(int i=0; i<recycler_view_list.getChildCount();i++){
        Map<String, String> map = new HashMap<>();
        map.clear();//清除原先map的数据
        View  layout =  recycler_view_list.getChildAt(i);//一定要用view，否则得不到数据
        TextView ivouchrowno=layout.findViewById(R.id.ivouchrownoout);
        TextView cInvCode=layout.findViewById(R.id.cInvCodeout);
        TextView cInvName=layout.findViewById(R.id.cInvNameout);
        TextView  cInvStd=layout.findViewById(R.id.cInvStdout);
        TextView  currentIQ=layout.findViewById(R.id.currentIQ);
        AddSubView iQuantity=layout.findViewById(R.id.iQuantityout);

        map.put("ivouchrowno", ivouchrowno.getText().toString());
        map.put("cInvCode", cInvCode.getText().toString());
        map.put("cInvName", cInvName.getText().toString());
        map.put("cInvStd", cInvStd.getText().toString());
        map.put("currentIQ", currentIQ.getText().toString());
        map.put("iQuantity", iQuantity.getValue()+"");
        Log.i("map", "map " +i+"分割"+ map);
        houseRes.add(map);
    }
    Log.i("houseRes", "houseRes " + houseRes );
    Gson gson=new Gson();
     String aa=gson.toJson(houseRes);
    Log.i("aa", "aa " + aa.length() );

    return aa;

}

//封装母单数据
private  String addInventory() throws Exception
{

    recycler_view_list=findViewById(R.id.recycleOut);
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

}
}



