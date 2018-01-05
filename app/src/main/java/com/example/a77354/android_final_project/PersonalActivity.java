package com.example.a77354.android_final_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77354.android_final_project.HttpServiceInterface.GetPersonalInfoService;
import com.example.a77354.android_final_project.HttpServiceInterface.LoginServiceInterface;
import com.example.a77354.android_final_project.HttpServiceInterface.LogoutServiceInterface;
import com.example.a77354.android_final_project.LoginAndRegister.LoginActivity;
import com.example.a77354.android_final_project.MyResponseBody.UserInfo;
import com.example.a77354.android_final_project.RequestBodyStruct.RegisterBody;
import com.example.a77354.android_final_project.ToolClass.HttpTool;
import com.example.a77354.android_final_project.ToolClass.ResponseBody;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shujunhuai on 2018/1/3.
 */

public class PersonalActivity extends AppCompatActivity  implements SwipeBackActivityBase {
    private SwipeBackActivityHelper swipeBackActivityHelper;
    private TextView cur_nickname;
    private TextView cur_id;
    private Button chang_information;
    private EditText nickname_change;
    private EditText password_change;
    private EditText phone_change;
    private EditText email_change;
    private EditText school_change;


    private Button confirm_change;
    private Button cancel_change;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_layout);
        initValue();
        initPersonalInfomation();
        setOnClickEvent();
        //顶部返回键的配置
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("个人中心");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && swipeBackActivityHelper != null)
            return swipeBackActivityHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackActivityHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        me.imid.swipebacklayout.lib.Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    private void initValue() {
        cur_nickname = (TextView) findViewById(R.id.cur_nickname);
        cur_id = (TextView) findViewById(R.id.cur_id);
        chang_information = (Button) findViewById(R.id.chang_information);
        confirm_change = (Button) findViewById(R.id.confirm_change);
        cancel_change = (Button) findViewById(R.id.cancel_change);
        nickname_change = (EditText) findViewById(R.id.nickname_change);
        password_change = (EditText) findViewById(R.id.password_change);
        phone_change = (EditText) findViewById(R.id.phone_change);
        email_change = (EditText) findViewById(R.id.email_change);
        school_change = (EditText) findViewById(R.id.school_change);
        logout = (Button) findViewById(R.id.logout);
    }

    private void initPersonalInfomation() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("userid", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");
        Retrofit retrofit = HttpTool.createRetrofit("http://112.124.47.197:4000/api/runner/", getApplicationContext(), "en");
        GetPersonalInfoService service = retrofit.create(GetPersonalInfoService.class);
        Log.e("test", "获取信息" + userId);
        service.getInfo(userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfo>(){
                    @Override
                    public final void onCompleted() {
                        Log.e("test", "获取信息成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("test", "a" + e.getMessage());
                    }
                    @Override
                    public void onNext(UserInfo userInfo) {
                        confirm_change.setVisibility(View.INVISIBLE);
                        cancel_change.setVisibility(View.INVISIBLE);
                        nickname_change.setText(userInfo.username);
                        cur_id.setText(userInfo.userid);
                        phone_change.setText(userInfo.phone);
                        email_change.setText(userInfo.email);
                        school_change.setText(userInfo.school);
                        cur_nickname.setText(userInfo.username);
                    }
                });
    }
    private void setOnClickEvent() {
        chang_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_change.setVisibility(View.VISIBLE);
                cancel_change.setVisibility(View.VISIBLE);
                nickname_change.setEnabled(true);
                password_change.setEnabled(true);
                phone_change.setEnabled(true);
                email_change.setEnabled(true);
                school_change.setEnabled(true);
            }
        });

        cancel_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_change.setVisibility(View.INVISIBLE);
                cancel_change.setVisibility(View.INVISIBLE);
                nickname_change.setEnabled(false);
                password_change.setEnabled(false);
                phone_change.setEnabled(false);
                email_change.setEnabled(false);
                school_change.setEnabled(false);
            }
        });

        confirm_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_change.setVisibility(View.INVISIBLE);
                cancel_change.setVisibility(View.INVISIBLE);
                nickname_change.setEnabled(false);
                password_change.setEnabled(false);
                phone_change.setEnabled(false);
                email_change.setEnabled(false);
                school_change.setEnabled(false);
                cur_nickname.setText(nickname_change.getText().toString());
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = HttpTool.createRetrofit("http://112.124.47.197:4000/api/runner/", getApplicationContext(), "en");
                LogoutServiceInterface service = retrofit.create(LogoutServiceInterface.class);
                service.logout()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>(){
                            @Override
                            public final void onCompleted() {
                                Log.e("test", "退出成功");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("test", "a" + e.getMessage());
                            }
                            @Override
                            public void onNext(ResponseBody ResponseBody) {
                                Log.e("test", ResponseBody.getMessage());
                                Toast.makeText(getApplicationContext(), ResponseBody.getMessage(), Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("cookie", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("cookie", "");
                                editor.commit();
                            //    System.exit(-1);
                            }
                        });
            }
        });
    }
}
