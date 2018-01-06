package com.example.a77354.android_final_project.HttpServiceInterface;

/**
 * Created by 77354 on 2018/1/6.
 */

import com.example.a77354.android_final_project.RunPlan.PlanEntity;
import com.example.a77354.android_final_project.ToolClass.ResponseBody;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CreatePlanServiceInterface {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("plan")
    rx.Observable<ResponseBody> create_plan(@Body RequestBody planEntity);
}
