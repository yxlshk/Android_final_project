package com.example.a77354.android_final_project.LoginAndRegister;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77354.android_final_project.MainActivity;
import com.example.a77354.android_final_project.R;

/**
 * Created by shujunhuai on 2017/12/27.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //临时写的登录逻辑 账号123 密码123
        final EditText username = (EditText) findViewById(R.id.userName);
        final EditText password = (EditText) findViewById(R.id.password);
        Button signin = (Button) findViewById(R.id.sign_in);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("123")
                        && password.getText().toString().equals("123")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "账号123，密码123", Toast.LENGTH_LONG).show();
                }
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
}
