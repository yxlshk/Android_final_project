package com.example.a77354.android_final_project.HttpServiceInterface;

import com.example.a77354.android_final_project.MyResponseBody.UserInfo;
import com.example.a77354.android_final_project.RequestBodyStruct.RegisterBody;
import com.example.a77354.android_final_project.ToolClass.ResponseBody;

import java.util.List;


import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by kunzhai on 2018/1/5.
 */

public interface GetPersonalInfoService {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @GET("user")
    rx.Observable<UserInfo> getInfo(@Query("userid") String userid);
}
