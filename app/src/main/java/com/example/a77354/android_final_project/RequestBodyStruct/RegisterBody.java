package com.example.a77354.android_final_project.RequestBodyStruct;

/**
 * Created by kunzhai on 2018/1/5.
 */

import java.util.ArrayList;

/**
 * 删除联系人封装的请求体
 * {"phone_num": "18612185929", "imei": "123456789012345", "godin_id": "4c59396301ab6274bd7892f0b31df36e", "contact_ids": ["1475920950100"]}
 */
public class RegisterBody {
    private String userid;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String school;
    public RegisterBody(String userid, String username, String password,String phone,String email, String school) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.school = school;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchool() {
        return school;
    }
    @Override
    public String toString() {
        return "RegisterBody{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email=" + email + '\'' +
                ", school=" + school +
                '}';
    }

}