package com.khaki.jxc.retrofit;


import com.khaki.jxc.gson.GsonNotices;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 嘉进 on 11:34.
 */

public interface INoticeBiz {
    @POST("getTheGroupAnnouncementsByPage.shtml")
    @FormUrlEncoded
    Call<GsonNotices> getNotice(@Field("userPhone") String userPhone, @Field("startPos") long startPos);
}
