package com.theappwelt.rmb.model;

public class MyReqListModel {
    String requirement,priority,request_needupto_date,request_valid_date,requirement_id;

    public MyReqListModel(String requirement, String priority, String request_needupto_date, String request_valid_date, String requirement_id) {
        this.requirement = requirement;
        this.priority = priority;
        this.request_needupto_date = request_needupto_date;
        this.request_valid_date = request_valid_date;
        this.requirement_id = requirement_id;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRequest_needupto_date() {
        return request_needupto_date;
    }

    public void setRequest_needupto_date(String request_needupto_date) {
        this.request_needupto_date = request_needupto_date;
    }

    public String getRequest_valid_date() {
        return request_valid_date;
    }

    public void setRequest_valid_date(String request_valid_date) {
        this.request_valid_date = request_valid_date;
    }

    public String getRequirement_id() {
        return requirement_id;
    }

    public void setRequirement_id(String requirement_id) {
        this.requirement_id = requirement_id;
    }
}

