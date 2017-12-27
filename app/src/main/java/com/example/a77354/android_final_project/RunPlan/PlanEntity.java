package com.example.a77354.android_final_project.RunPlan;

/**
 * Created by shujunhuai on 2017/12/26.
 */
//跑步计划的class
public class PlanEntity {
    private String planTime;
    private String planPlace;
    private String planPartner; //  约跑小伙伴

    public PlanEntity(String t, String p, String paner) {
        this.planTime = t;
        this.planPlace = p;
        this.planPartner = paner;
    }

    public void setPlanPartner(String planPartner) {
        this.planPartner = planPartner;
    }

    public void setPlanPlace(String planPlace) {
        this.planPlace = planPlace;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public String getPlanPartner() {
        return planPartner;
    }

    public String getPlanPlace() {
        return planPlace;
    }

    public String getPlanTime() {
        return planTime;
    }
}
