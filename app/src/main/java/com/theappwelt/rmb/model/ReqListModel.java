package com.theappwelt.rmb.model;

public class ReqListModel {

    String member_first_name, requirement, requirement_id, priority, member_email, member_mobile,status;

    public ReqListModel(String requirement_id, String member_first_name, String member_last_name, String requirement, String priority, String member_email, String member_mobile, String status) {
        this.requirement_id = requirement_id;
        this.member_first_name = member_first_name;
        this.requirement = requirement;
        this.priority = priority;
        this.member_email = member_email;
        this.member_mobile = member_mobile;
        this.status = status;

    }

    public String getMember_first_name() {
        return member_first_name;
    }

    public void setMember_first_name(String member_first_name) {
        this.member_first_name = member_first_name;
    }


    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getRequirement_id() {
        return requirement_id;
    }

    public void setRequirement_id(String requirement_id) {
        this.requirement_id = requirement_id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public String getMember_mobile() {
        return member_mobile;
    }

    public void setMember_mobile(String member_mobile) {
        this.member_mobile = member_mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
