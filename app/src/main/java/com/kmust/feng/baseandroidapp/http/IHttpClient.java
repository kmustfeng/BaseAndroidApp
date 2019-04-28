package com.kmust.feng.baseandroidapp.http;

/**
 * Created by nls on 2017/9/4.
 */

public interface IHttpClient {

    <T> T create(Class<T> className);
}
