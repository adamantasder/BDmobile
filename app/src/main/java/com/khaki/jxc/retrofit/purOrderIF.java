package com.khaki.jxc.retrofit;

import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface purOrderIF {


    @GET("purOrder")
    Call<ResponseBody> purOrder  (
            @Query("csysbarcode") String csysbarcode//单据条码
         );


    }