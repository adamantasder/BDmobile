package com.khaki.jxc.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface appvouchIF {
    @POST("saveAppvouch")
    @FormUrlEncoded
    Call<ResponseBody> saveAppvouch(
            @Field("purVo") String purVo,//封装提交的Json
            @Field("pursVo") String pursVo,//封装提交的Json
            @Field("userId") String userId//封装提交的Json
    );



}
