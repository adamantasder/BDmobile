package com.khaki.jxc.retrofit;

import com.khaki.jxc.gson.GsonGroupInfo;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dell88 on 2017/8/6 0006.
 */

public interface IGroupInfoBiz {
    @POST("getGroupInfo.shtml")
    @FormUrlEncoded
    Observable<GsonGroupInfo> getGroupInfo(
            @Field("groupId") Integer groupId
    );
}
