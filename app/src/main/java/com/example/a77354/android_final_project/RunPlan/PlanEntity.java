package com.example.a77354.android_final_project.RunPlan;

/**
 * Created by shujunhuai on 2017/12/26.
 */
//跑步计划的class
public class PlanEntity {
    private String planname;
    private String runtime;
    private String place;
    private String partner; //  约跑小伙伴

    public PlanEntity(String n, String t, String p, String paner) {
        this.planname = n;
        this.runtime = t;
        this.place = p;
        this.partner = paner;
    }

    public void setPlanName(String planName) {
        this.planname = planName;
    }

    public void setPlanPartner(String planPartner) {
        this.partner = planPartner;
    }

    public void setPlanPlace(String planPlace) {
        this.place = planPlace;
    }

    public void setPlanTime(String planTime) {
        this.runtime = planTime;
    }

    public String getPlanName() {
        return planname;
    }

    public String getPlanPartner() {
        return partner;
    }

    public String getPlanPlace() {
        return place;
    }

    public String getPlanTime() {
        return runtime;
    }
}
