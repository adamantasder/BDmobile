package com.khaki.jxc.utils;





import com.khaki.jxc.Contants;
import com.khaki.jxc.retrofit.DownloadListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * Created by khaki on 2019-07-29.
 */

public class OkHttpUtil {
    public static final String BASE_URL = Contants.SERVER_IP;
    private static Map<String, List<Cookie>> cookieStore = new HashMap<>();
    // 创建线程池
    private static ExecutorService threadPool = Executors.newFixedThreadPool(30);
    public static String loginSessionID = null;

    public static OkHttpClient okHttpClient = null;

    public static OkHttpClient getInstance() {
        if (okHttpClient == null){
            synchronized (OkHttpUtil.class){
                if (okHttpClient == null){
                    CreateOkHttpClient();
                }
            }
        }
        return okHttpClient;
    }

    // 创建默认的OkHttpClient对象
    private static void CreateOkHttpClient(){
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl,  List<Cookie> list) {
                        cookieStore.put(httpUrl.host(), list);
                    }


                    @Override
                    public List<Cookie> loadForRequest( HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
                        return cookies == null ? new ArrayList<>() : cookies;
                    }
                })
                 /* .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder()
                                .addHeader("cookie", loginSessionID);
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })*/
                .build();
    }


    /**
     * @param url 发送请求的URL
     * @return 服务器响应字符串
     * @throws
     */
    public static String getRequest(String url) throws Exception {
        FutureTask<String> task = new FutureTask<>(() -> {
            System.out.println("=============" + url);
          //  System.out.println("=============" +  loginSessionID);
            // 创建请求对象
            Request request = new Request.Builder()
                    .url(url)
                   // .addHeader("cookie", loginSessionID)
                    .build();
            Call call = getInstance().newCall(request);
            // 发送GET请求
            Response response = call.execute();
            // 如果服务器成功地返回响应
            if (response.isSuccessful() && response.body() != null) {
                // 获取服务器响应字符串
                return response.body().string().trim();
            } else {
                return null;
            }
        });
        threadPool.submit(task); // 提交任务
        return task.get();
    }

    /**
     * @param url       发送请求的URL
     * @param rawParams 请求参数
     * @return 服务器响应字符串
     * @throws
     */
    public static String postRequest(String url, Map<String, String> rawParams) throws Exception {
        FutureTask<String> task = new FutureTask<>(() -> {
            // 构建包含请求参数的表单体
            FormBody.Builder builder = new FormBody.Builder();
            rawParams.forEach(builder::add);
            FormBody body = builder.build();
            // 创建请求对象
            Request request = new Request.Builder()
                    .url(url)
                    .post(body).build();
            Call call = getInstance().newCall(request);
            // 发送POST请求
            Response response = call.execute();
            // 如果服务器成功地返回响应
            if (response.isSuccessful() && response.body() != null) {
                // 获取服务器响应字符串
                return response.body().string().trim();
            } else {
                return null;
            }
        });
        threadPool.submit(task); // 提交任务
        return task.get();
    }








}
