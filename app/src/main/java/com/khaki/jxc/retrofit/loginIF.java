package com.khaki.jxc.retrofit;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface loginIF {
    @POST("users/login")
    @FormUrlEncoded
    Call<ResponseBody> getPost  (
            @Field("userId") String userId,
            @Field("userPwd") String userPwd,
              @Field("isRememberMe") Boolean isRememberMe);
}
