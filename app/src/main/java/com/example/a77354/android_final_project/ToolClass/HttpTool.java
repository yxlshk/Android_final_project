package com.example.a77354.android_final_project.ToolClass;

import android.content.Context;

import com.example.a77354.android_final_project.Interceptor.AddCookiesInterceptor;
import com.example.a77354.android_final_project.Interceptor.ReceivedCookiesInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kunzhai on 2018/1/5.
 */

public class HttpTool {
    public static OkHttpClient createOkHttp(Context context, String lang) {
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(context, lang))
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .build();
        return okHttpClient;
    }

    public static Retrofit createRetrofit(String baseUrl, Context context, String lang) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createOkHttp(context, lang))
                .build();
    }


}
