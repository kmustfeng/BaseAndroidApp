package com.kmust.feng.baseandroidapp.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

/**
 * Created by hzy on 2017/3/21.
 */

public class GsonUtil {
    public static final String TAG = "framework.GsonUtil";
    private static final Gson prettyIns = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    private static final Gson ins = new Gson();

    public static Gson getIns() {
        return ins;
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        try {
            return ins.fromJson(json, tClass);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static <T> T fromJson(JSONObject response, TypeToken<T> typeToken) {
        if (response == null) {
            Log.e(TAG, "response is null");
            return null;
        }
        try {
            return ins.fromJson(response.toString(), typeToken.getType());
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        try {
            return ins.fromJson(json, typeToken.getType());
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static String toJson(Object obj) {
        return ins.toJson(obj);
    }

    public static String toPrettyJson(Object o) {
        return prettyIns.toJson(o);
    }
}
