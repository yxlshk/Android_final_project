package com.example.a77354.android_final_project.HttpServiceInterface;

import com.example.a77354.android_final_project.ToolClass.ResponseBody;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by 77354 on 2018/1/6.
 */
public interface DeletePlanServiceInterface {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @DELETE("plan")
    rx.Observable<ResponseBody> deletePlan(@Path("id") String planid);
}