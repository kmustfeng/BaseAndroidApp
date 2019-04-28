package com.kmust.feng.baseandroidapp.http;

/**
 * Created by nls on 2017/11/14.
 */

public class HttpService {

    private static String cacheDir;

    public static void init(String cache) {
        cacheDir = cache;
    }

    public static String getCacheDir() {
        return cacheDir;
    }

    public static IHttpClient with(String host) {
        return HttpClient.initHost(host);
    }
}
