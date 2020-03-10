package com.khaki.jxc.retrofit;

import com.khaki.jxc.Contants;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
    public static final String BASE_URL = Contants.APP_IP;

    @Streaming
    @GET
    Observable<ResponseBody> executeDownload(@Header("Range") String range, @Url() String url);

}