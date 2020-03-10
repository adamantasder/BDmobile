package com.khaki.jxc.retrofit;


import com.khaki.jxc.gson.GsonGroupQRCode;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dell88 on 2017/8/7 0007.
 */

public interface IGroupQRCodeBiz {
    @POST("createGroupQRcode.shtml")
    @FormUrlEncoded
    Observable<GsonGroupQRCode> getGroupQR(
            @Field("groupId") Integer groupId
    );
}
