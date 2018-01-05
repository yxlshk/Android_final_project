package com.example.a77354.android_final_project.HttpServiceInterface;

import com.example.a77354.android_final_project.ToolClass.ResponseBody;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by kunzhai on 2018/1/6.
 */

public interface CheckIfLoginService {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @GET("user/login")
    rx.Observable<ResponseBody> login(@Body RequestBody loginBody);
}
