package com.khaki.jxc.retrofit;



import com.khaki.jxc.gson.GsonCreateReport;
import com.khaki.jxc.gson.GsonSetReportUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 嘉进 on 10:24.
 */

public interface IReportBiz {

    @POST("createDailyReport.shtml")
    @FormUrlEncoded
    Call<GsonCreateReport> createReport(
            @Field("userPhone") String userPhone,
            @Field("jobDone") String jobDone,
            @Field("jobToDo") String jobToDo,
            @Field("jobCoordinated") String jobCoordinated,
            @Field("jobRemark") String jobRemark,
            @Field("jobTime") String jobTime,
            @Field("jobType") long jobType
    );


    @POST("createDailyReportUser.shtml")
    @FormUrlEncoded
    Call<GsonSetReportUser> setReportUser(
            @Field("userPhone") String userPhone,
            @Field("dailyReportId") long dailyReportId
    );


}
