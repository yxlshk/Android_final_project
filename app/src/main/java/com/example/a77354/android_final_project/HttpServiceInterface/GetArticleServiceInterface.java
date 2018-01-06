package com.example.a77354.android_final_project.HttpServiceInterface;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by 77354 on 2018/1/6.
 */

public interface GetArticleServiceInterface {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @GET("articles")
    rx.Observable<Map<String, Map<String, String>>> getArticle();
}