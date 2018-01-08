package com.example.a77354.android_final_project.RunPlan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77354.android_final_project.HttpServiceInterface.CreatePlanServiceInterface;
import com.example.a77354.android_final_project.HttpServiceInterface.RegisterServiceInterface;
import com.example.a77354.android_final_project.LoginAndRegister.RegisterActivity;
import com.example.a77354.android_final_project.R;
import com.example.a77354.android_final_project.ToolClass.HttpTool;
import com.example.a77354.android_final_project.ToolClass.ResponseBody;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shujunhuai on 2018/1/3.
 */

public class AddNewPlanActivity extends AppCompatActivity {
    private EditText planTime;
    private EditText planPlace;
    private EditText planPartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_run_plan_layout);
        initValue();
        setOnClickEvent();
    }

    public void initValue() {
        planTime = (EditText) findViewById(R.id.new_plan_time);
        planPlace = (EditText) findViewById(R.id.new_plan_place);
        planPartner = (EditText) findViewById(R.id.new_plan_partner);
    }

    private void setOnClickEvent() {
        Button ok_new_btn = (Button) findViewById(R.id.ok_new_btn);
        ok_new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = planTime.getText().toString();
                String place = planPlace.getText().toString();
                String partner = planPartner.getText().toString();
                PlanEntity reqBody = new PlanEntity("None", time, place, partner);

                Retrofit retrofit = HttpTool.createRetrofit("http://112.124.47.197:4000/api/runner/", getApplicationContext(), "en");
                CreatePlanServiceInterface service = retrofit.create(CreatePlanServiceInterface.class);
                Gson gson = new Gson();
                String postInfoStr = gson.toJson(reqBody);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postInfoStr);
                Log.i("json",postInfoStr.toString());
                service.create_plan(body)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody >(){
                            @Override
                            public final void onCompleted() {
                        //        Log.e("test", "完成传输");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(AddNewPlanActivity.this, e.hashCode() + "时间格式应为2000-01-01 00:00", Toast.LENGTH_SHORT).show();
                                Log.e("test", e.getMessage());
                            }
                            @Override
                            public void onNext(ResponseBody responseBody) {
                                Log.e("test", responseBody.getMessage());
                                if (responseBody.getMessage().equals("Create plan successfully")) {
                                    Toast.makeText(getApplicationContext(), responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), responseBody.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        Button cancle = (Button) findViewById(R.id.cancel_new_btn);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });
    }
}
