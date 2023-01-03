package com.theappwelt.rmb.model;

public class ReferralsGivenListModel {

    String memberName;
    String referralName;
    String ReferralStatus;
    String email;
    String phone;
    String address;
    String comments;
    String referralId;
    String rotarian;


    public ReferralsGivenListModel(String referralId, String memberName, String referralName, String referralStatus, String email, String phone, String address, String comments, String rotarian) {
        this.memberName = memberName;
        this.referralName = referralName;
        ReferralStatus = referralStatus;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.comments = comments;
        this.referralId = referralId;
        this.rotarian = rotarian;
    }

    public String getRotarian() {
        return rotarian;
    }

    public void setRotarian(String rotarian) {
        this.rotarian = rotarian;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getReferralName() {
        return referralName;
    }

    public void setReferralName(String referralName) {
        this.referralName = referralName;
    }

    public String getReferralStatus() {
        return ReferralStatus;
    }

    public void setReferralStatus(String referralStatus) {
        ReferralStatus = referralStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }
}
