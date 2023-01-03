package com.theappwelt.rmb.model;

public class VisitorList {

    private String visitorName,visitorCompany,VisitorEmail,
            visitorMobile,visitorMessage;

    public VisitorList(String visitorName, String visitorCompany, String visitorEmail, String visitorMobile, String visitorMessage) {
        this.visitorName = visitorName;
        this.visitorCompany = visitorCompany;
        VisitorEmail = visitorEmail;
        this.visitorMobile = visitorMobile;
        this.visitorMessage = visitorMessage;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorCompany() {
        return visitorCompany;
    }

    public void setVisitorCompany(String visitorCompany) {
        this.visitorCompany = visitorCompany;
    }

    public String getVisitorEmail() {
        return VisitorEmail;
    }

    public void setVisitorEmail(String visitorEmail) {
        VisitorEmail = visitorEmail;
    }

    public String getVisitorMobile() {
        return visitorMobile;
    }

    public void setVisitorMobile(String visitorMobile) {
        this.visitorMobile = visitorMobile;
    }

    public String getVisitorMessage() {
        return visitorMessage;
    }

    public void setVisitorMessage(String visitorMessage) {
        this.visitorMessage = visitorMessage;
    }
}
