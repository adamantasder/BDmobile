package com.khaki.jxc.retrofit;



import com.khaki.jxc.gson.GsonOmImages;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dell88 on 2017/8/14 0014.
 */

public interface IGetOmImage {
    @POST("queryOmImages.shtml")
    @FormUrlEncoded
    Observable<GsonOmImages> getImage(
            @Field("userPhone") String userPhone,
            @Field("officeManageId") long omid
    );
}
