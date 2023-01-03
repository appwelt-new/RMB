package com.theappwelt.rmb.model;

public class BusinessReceivedListModel {

    String businessId,receivedFrom,crossBranch,category,referralName,date,closedOn,amount,comment,
    responseStatus,responseOn,responseGiven,thankUComment;




    public BusinessReceivedListModel(String businessId, String receivedFrom, String crossBranch, String category, String referralName, String date, String closedOn, String amount, String comment, String responseStatus, String responseOn, String responseGiven, String thankUComment) {
       this.businessId = businessId;
        this.receivedFrom = receivedFrom;
        this.crossBranch = crossBranch;
        this.category = category;
        this.referralName = referralName;
        this.date = date;
        this.closedOn = closedOn;
        this.amount = amount;
        this.comment = comment;
        this.responseStatus = responseStatus;
        this.responseOn = responseOn;
        this.responseGiven = responseGiven;
        this.thankUComment = thankUComment;
    }
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getReceivedFrom() {
        return receivedFrom;
    }

    public String getThankUComment() {
        return thankUComment;
    }

    public void setThankUComment(String thankUComment) {
        this.thankUComment = thankUComment;
    }

    public void setReceivedFrom(String receivedFrom) {
        this.receivedFrom = receivedFrom;
    }

    public String getCrossBranch() {
        return crossBranch;
    }

    public void setCrossBranch(String crossBranch) {
        this.crossBranch = crossBranch;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReferralName() {
        return referralName;
    }

    public void setReferralName(String referralName) {
        this.referralName = referralName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClosedOn() {
        return closedOn;
    }

    public void setClosedOn(String closedOn) {
        this.closedOn = closedOn;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseOn() {
        return responseOn;
    }

    public void setResponseOn(String responseOn) {
        this.responseOn = responseOn;
    }

    public String getResponseGiven() {
        return responseGiven;
    }

    public void setResponseGiven(String responseGiven) {
        this.responseGiven = responseGiven;
    }
}
