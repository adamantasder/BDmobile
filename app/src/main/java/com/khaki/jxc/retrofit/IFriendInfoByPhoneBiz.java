package com.khaki.jxc.retrofit;


import com.khaki.jxc.gson.GsonUsers;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dell88 on 2017/8/5 0005.
 */

public interface IFriendInfoByPhoneBiz {
    @POST("getUserInfo.shtml")
    @FormUrlEncoded
    Call<GsonUsers> getInfo(
            @Field("userPhone") String userPhone
    );
}
