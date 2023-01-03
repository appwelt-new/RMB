package com.theappwelt.rmb.model;

public class Members {
    String businessName;
    String memberFirstName;
    String memberLastName;
    String memberEmail;
    String memberMobile;
    String memberAddress;
    String categoryName;


    public Members(String businessName, String memberFirstName, String memberLastName, String memberEmail, String memberMobile, String memberAddress, String categoryName) {
        this.businessName = businessName;
        this.memberFirstName = memberFirstName;
        this.memberLastName = memberLastName;
        this.memberEmail = memberEmail;
        this.memberMobile = memberMobile;
        this.memberAddress = memberAddress;
        this.categoryName = categoryName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }



    public String getMemberFirstName() {
        return memberFirstName;
    }

    public void setMemberFirstName(String memberFirstName) {
        this.memberFirstName = memberFirstName;
    }

    public String getMemberLastName() {
        return memberLastName;
    }

    public void setMemberLastName(String memberLastName) {
        this.memberLastName = memberLastName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
