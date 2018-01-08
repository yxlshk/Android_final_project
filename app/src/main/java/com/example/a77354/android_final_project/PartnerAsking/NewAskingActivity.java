package com.example.a77354.android_final_project.PartnerAsking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a77354.android_final_project.HttpServiceInterface.CreateArticleInterface;
import com.example.a77354.android_final_project.HttpServiceInterface.CreatePlanServiceInterface;
import com.example.a77354.android_final_project.R;
import com.example.a77354.android_final_project.RunPlan.AddNewPlanActivity;
import com.example.a77354.android_final_project.RunPlan.PlanEntity;
import com.example.a77354.android_final_project.ToolClass.HttpTool;
import com.example.a77354.android_final_project.ToolClass.ResponseBody;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shujunhuai on 2018/1/4.
 */

public class NewAskingActivity extends AppCompatActivity {
    EditText new_asking_content;
    Button ok_new;
    Button cancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_new_asking);
        initView();
        setOnClickEvent();
    }

    public void initView() {
        new_asking_content = (EditText) findViewById(R.id.new_asking_content);
        ok_new = (Button) findViewById(R.id.ok_new_btn);
        cancle = (Button) findViewById(R.id.cancel_new_btn);
    }

    private void setOnClickEvent() {
        ok_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = new_asking_content.getText().toString();
                PartnerAskingEnty reqBody = new PartnerAskingEnty(content, "None");

                Retrofit retrofit = HttpTool.createRetrofit("http://112.124.47.197:4000/api/runner/", getApplicationContext(), "en");
                CreateArticleInterface service = retrofit.create(CreateArticleInterface.class);
                Gson gson = new Gson();
                String postInfoStr = gson.toJson(reqBody);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postInfoStr);
                Log.i("json",postInfoStr.toString());
                service.create_article(body)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>(){
                            @Override
                            public final void onCompleted() {
                            //    Log.e("test", "完成传输");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(NewAskingActivity.this, e.hashCode() + "确认信息是否符合标准", Toast.LENGTH_SHORT).show();
                                Log.e("test", e.getMessage());
                            }
                            @Override
                            public void onNext(ResponseBody responseBody) {
                                Log.e("test", responseBody.getMessage());
                                if(responseBody.getMessage().equals("Create article successfully")) {
                                    Toast.makeText(getApplicationContext(), responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent( NewAskingActivity.this, PartnerAskingActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), responseBody.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
