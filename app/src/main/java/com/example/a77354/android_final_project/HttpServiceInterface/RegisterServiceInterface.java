package com.example.a77354.android_final_project.HttpServiceInterface;

import com.example.a77354.android_final_project.RequestBodyStruct.RegisterBody;
import com.example.a77354.android_final_project.ToolClass.ResponseBody;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by kunzhai on 2018/1/5.
 */

public interface RegisterServiceInterface {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("user/register")
    rx.Observable<ResponseBody> register(@Body RequestBody registerBody);
}
