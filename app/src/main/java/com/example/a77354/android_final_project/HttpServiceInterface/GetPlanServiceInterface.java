package com.example.a77354.android_final_project.HttpServiceInterface;

import com.example.a77354.android_final_project.RunPlan.PlanGetFromService;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by 77354 on 2018/1/6.
 */
public interface GetPlanServiceInterface {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @GET("plans")
    rx.Observable<Map<String, PlanGetFromService>> getPlan();
}