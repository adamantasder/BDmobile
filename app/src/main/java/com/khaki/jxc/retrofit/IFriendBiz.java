package com.khaki.jxc.retrofit;



import com.khaki.jxc.gson.GsonFriends;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 嘉进 on 18:52.
 */

public interface IFriendBiz {
    @POST("findfriends.shtml")
    @FormUrlEncoded
    Call<GsonFriends> getFriend(
            @Field("userPhone") String userPhone
    );
}
