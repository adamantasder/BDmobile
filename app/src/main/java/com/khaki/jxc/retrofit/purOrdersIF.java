package com.khaki.jxc.retrofit;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PartMap;


public interface purOrdersIF {
    @POST("purOrders")
    @FormUrlEncoded
    Call<ResponseBody> savepurOrders  (
            @Field ("purVo") String purVo,//封装提交的Json
              @Field ("pursVo") String pursVo,//封装提交的Json
            @Field ("userId") String userId//封装提交的Json
    );
    @POST("purOrd")
    @FormUrlEncoded
    Call<ResponseBody> saveOutOrders  (
            @Field ("purVo") String purVo,//封装提交的Json
            @Field ("pursVo") String pursVo,//封装提交的Json
            @Field ("userId") String userId//封装提交的Json
    );


}
