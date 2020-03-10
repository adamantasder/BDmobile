package com.khaki.jxc.retrofit;

public interface DownloadCallBack {

    void onProgress(int progress);

    void onCompleted();

    void onError(String msg);
}
