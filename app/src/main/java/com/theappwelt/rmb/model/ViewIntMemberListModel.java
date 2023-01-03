package com.theappwelt.rmb.model;

public class ViewIntMemberListModel {
    String member_first_name,member_last_name,member_email,member_mobile,member_address;

    public ViewIntMemberListModel(String member_first_name, String member_last_name, String member_email, String member_mobile, String member_address) {
        this.member_first_name = member_first_name;
        this.member_last_name = member_last_name;
        this.member_email = member_email;
        this.member_mobile = member_mobile;
        this.member_address = member_address;
    }

    public String getMember_first_name() {
        return member_first_name;
    }

    public void setMember_first_name(String member_first_name) {
        this.member_first_name = member_first_name;
    }

    public String getMember_last_name() {
        return member_last_name;
    }

    public void setMember_last_name(String member_last_name) {
        this.member_last_name = member_last_name;
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

    public String getMember_address() {
        return member_address;
    }

    public void setMember_address(String member_address) {
        this.member_address = member_address;
    }

}

