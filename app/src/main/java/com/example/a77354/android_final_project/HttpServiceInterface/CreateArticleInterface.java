package com.example.a77354.android_final_project.HttpServiceInterface;

import com.example.a77354.android_final_project.ToolClass.ResponseBody;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by 77354 on 2018/1/6.
 */

public interface CreateArticleInterface {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("article")
    rx.Observable<ResponseBody> create_article(@Body RequestBody planEntity);
}