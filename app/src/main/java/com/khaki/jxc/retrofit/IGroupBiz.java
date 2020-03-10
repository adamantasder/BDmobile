package com.khaki.jxc.retrofit;



import com.khaki.jxc.gson.GsonGroups;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 嘉进 on 19:35.
 */

public interface IGroupBiz {
    @POST("getAllGroupByUser.shtml")
    @FormUrlEncoded
    Call<GsonGroups> getGroup(
            @Field("userPhone") String userPhone
    );
}
