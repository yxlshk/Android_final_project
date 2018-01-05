package com.example.a77354.android_final_project.LoginAndRegister;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.a77354.android_final_project.HttpServiceInterface.RegisterServiceInterface;
import com.example.a77354.android_final_project.R;
import com.example.a77354.android_final_project.RequestBodyStruct.RegisterBody;
import com.example.a77354.android_final_project.ToolClass.HttpTool;
import com.example.a77354.android_final_project.ToolClass.ResponseBody;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shujunhuai on 2018/1/2.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText reg_userId;
    private EditText reg_password;
    private EditText reg_phone;
    private EditText reg_email;
    private EditText reg_school;
    private Button registerBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        initValue();
        setOnClickEvent();
    }

    private void initValue() {
        reg_userId = (EditText)findViewById(R.id.reg_userId);
        reg_password = (EditText)findViewById(R.id.reg_password);
        reg_phone = (EditText)findViewById(R.id.reg_phone);
        reg_email = (EditText)findViewById(R.id.reg_email);
        reg_school = (EditText)findViewById(R.id.reg_school);
        registerBut = (Button) findViewById(R.id.register);
    }

    private void setOnClickEvent() {
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = reg_userId.getText().toString();
                String password = reg_password.getText().toString();
                String phone = reg_phone.getText().toString();
                String email = reg_email.getText().toString();
                String school = reg_school.getText().toString();
                RegisterBody reqBody = new RegisterBody(username, username, password, phone, email, school);

                Retrofit retrofit = HttpTool.createRetrofit("http://112.124.47.197:4000/api/runner/", getApplicationContext(), "en");
                RegisterServiceInterface service = retrofit.create(RegisterServiceInterface.class);
                Gson gson = new Gson();
                String postInfoStr = gson.toJson(reqBody);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postInfoStr);

                service.register(body)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody >(){
                            @Override
                            public final void onCompleted() {
                                Log.e("test", "完成传输");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(RegisterActivity.this, e.hashCode() + "确认信息是否符合标准", Toast.LENGTH_SHORT).show();
                                Log.e("test", e.getMessage());
                            }
                            @Override
                            public void onNext(ResponseBody responseBody) {
                                Log.e("test", responseBody.getMessage());
                                Toast.makeText(getApplicationContext(), responseBody.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
