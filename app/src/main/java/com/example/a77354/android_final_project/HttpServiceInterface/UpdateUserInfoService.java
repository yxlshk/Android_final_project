package com.example.a77354.android_final_project.HttpServiceInterface;

import com.example.a77354.android_final_project.ToolClass.ResponseBody;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by kunzhai on 2018/1/6.
 */

public interface UpdateUserInfoService {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @PUT("user")
    rx.Observable<ResponseBody> updateUserinfo(@Body RequestBody registerBody);
}
