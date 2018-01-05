package com.example.a77354.android_final_project.RequestBodyStruct;

/**
 * Created by kunzhai on 2018/1/5.
 */

public class LoginBody {
    private String userid;
    private String password;
    public LoginBody(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
