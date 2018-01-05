package com.example.a77354.android_final_project.LoginAndRegister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77354.android_final_project.HttpServiceInterface.LoginServiceInterface;
import com.example.a77354.android_final_project.HttpServiceInterface.RegisterServiceInterface;
import com.example.a77354.android_final_project.MainActivity;
import com.example.a77354.android_final_project.R;
import com.example.a77354.android_final_project.RequestBodyStruct.LoginBody;
import com.example.a77354.android_final_project.RequestBodyStruct.RegisterBody;
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
 * Created by shujunhuai on 2017/12/27.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //临时写的登录逻辑 账号123 密码123
        final EditText username = (EditText) findViewById(R.id.userId);
        final EditText password = (EditText) findViewById(R.id.password);
        Button signin = (Button) findViewById(R.id.sign_in);
        checkIfHasLogin();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginBody reqBody = new LoginBody(username.getText().toString(), password.getText().toString());
                Retrofit retrofit = HttpTool.createRetrofit("http://112.124.47.197:4000/api/runner/", getApplicationContext(), "en");
                LoginServiceInterface service = retrofit.create(LoginServiceInterface.class);
                Gson gson = new Gson();
                String postInfoStr = gson.toJson(reqBody);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postInfoStr);

                service.login(body)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody >(){
                            @Override
                            public final void onCompleted() {
                                Log.e("test", "登陆成功");
                                SharedPreferences sharedPreferences = getSharedPreferences("userid", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String Userid = username.getText().toString();
                                editor.putString("userid", Userid);
                                editor.commit();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(LoginActivity.this, e.hashCode() + "账号/密码错误", Toast.LENGTH_SHORT).show();
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

        //给"立即注册"添加下划线
        TextView register = (TextView) findViewById(R.id.register_now);
        register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //"立即注册"点击跳转到注册页面
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void checkIfHasLogin() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("cookie", Context.MODE_PRIVATE);

    }
}
