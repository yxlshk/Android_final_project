package com.example.a77354.android_final_project.RunPlan;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by 77354 on 2018/1/6.
 */

public class PlanGetFromService {
    private String planid;
    private String planname;
    private String author;
    private String runtime;
    private ArrayList partner; //  约跑小伙伴
    private String place;

    public PlanGetFromService(String planid, String n, String a, String t, String p, ArrayList paner) {
        this.planid = planid;
        this.planname = n;
        this.author = a;
        this.runtime = t;
        this.place = p;
        this.partner = paner;
    }

    public void setPlanId(String planId) {
        this.planid = planId;
    }

    public void setPlanName(String planName) {
        this.planname = planName;
    }

    public void setAuthor(String Author) {
        this.author = Author;
    }

    public void setPlanPartner(ArrayList planPartner) {
        this.partner = planPartner;
    }

    public void setPlanPlace(String planPlace) {
        this.place = planPlace;
    }

    public void setPlanTime(String planTime) {
        this.runtime = planTime;
    }

    public String getPlanId() {
        return planid;
    }

    public String getPlanName() {
        return planname;
    }

    public String getAuthor() {
        return author;
    }
    public ArrayList getPlanPartner() {
        return partner;
    }

    public String getPlanPlace() {
        return place;
    }

    public String getPlanTime() {
        return runtime;
    }
}
