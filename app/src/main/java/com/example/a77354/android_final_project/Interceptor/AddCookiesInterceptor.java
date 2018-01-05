package com.example.a77354.android_final_project.Interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by kunzhai on 2018/1/5.
 */

public class AddCookiesInterceptor implements Interceptor {
    private Context context;
    private String lang;

    public AddCookiesInterceptor(Context context, String lang) {
        super();
        this.context = context;
        this.lang = lang;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain == null)
            Log.d("http", "Addchain == null");
        final Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        Observable.just(sharedPreferences.getString("cookie", ""))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String cookie) {
                        if (cookie.contains("lang=ch")){
                            cookie = cookie.replace("lang=ch","lang="+lang);
                        }
                        if (cookie.contains("lang=en")){
                            cookie = cookie.replace("lang=en","lang="+lang);
                        }
                        Log.e("test", "addCookie" + cookie);
                        //添加cookie
//                        Log.d("http", "AddCookiesInterceptor"+cookie);
                        builder.addHeader("sessionid", cookie);

                    }
                });
        return chain.proceed(builder.build());
    }
}