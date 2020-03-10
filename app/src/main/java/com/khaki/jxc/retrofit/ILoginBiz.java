package com.khaki.jxc.retrofit;



import com.khaki.jxc.gson.GsonUsers;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ILoginBiz {
    @POST("mobileLogin.shtml")
    @FormUrlEncoded
    Call<ResponseBody> login(
            @Field("userPhone") String userPhone,
            @Field("password") String password,
            @Field("isRememberMe") Boolean isRememberMe
    );

    @POST("getUserInfo.shtml")
    @FormUrlEncoded
    Call<GsonUsers> getUserInfo(
            @Field("userPhone") String userPhone
    );
}
