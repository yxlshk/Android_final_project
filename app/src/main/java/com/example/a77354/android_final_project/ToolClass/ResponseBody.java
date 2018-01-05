package com.example.a77354.android_final_project.ToolClass;

import java.util.Map;

/**
 * Created by kunzhai on 2018/1/5.
 */

public class ResponseBody {
    private String message;
    private Map<String, String> data;
    private String session;
    public void setMessage(String message) {
        this.message = message;
    }
    public void setSession(String session) {
        this.session = session;
    }
    public String getMessage() {
        return message;
    }
    public String getSession() { return session;}
    public String getErrorMessage() {
        if(data != null && data.get("error")!=null) {
            return data.get("error");
        } else {
            return "";
        }
    }
}
