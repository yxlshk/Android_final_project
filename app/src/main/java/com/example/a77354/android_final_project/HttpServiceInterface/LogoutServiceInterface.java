package com.example.a77354.android_final_project.HttpServiceInterface;

import com.example.a77354.android_final_project.ToolClass.ResponseBody;

import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by kunzhai on 2018/1/6.
 */


public interface LogoutServiceInterface {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("user/logout")
    rx.Observable<ResponseBody> logout();
}