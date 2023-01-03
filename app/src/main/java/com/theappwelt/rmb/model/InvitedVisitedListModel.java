package com.theappwelt.rmb.model;

public class InvitedVisitedListModel {

    String name,lastName,company,email,mobile,message,id,business_id;


    public InvitedVisitedListModel(String id,String name, String lastName, String company, String email, String mobile, String message,String business_id) {
        this.name = name;
        this.company = company;
        this.email = email;
        this.mobile = mobile;
        this.message = message;
        this.lastName=lastName;
        this.id = id;
        this.business_id = business_id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
