package com.theappwelt.rmb.model;

import android.content.Context;

public class BusinessGivenListModel {

    Context context;
   String businessReceivedFrom , memberName,crossedBranch,referralName,businessCategory,date,closedOn,amount,comment,
           responseStatus,responseOn,responseGiven,thankUComment;


    public BusinessGivenListModel(Context context,
                                  String businessReceivedFrom,
                                  String memberName,
                                  String crossedBranch,
                                  String businessCategory,
                                  String referralName,
                                  String date,
                                  String closedOn,
                                  String amount,
                                  String comment,
                                  String responseStatus,
                                  String responseOn,
                                  String responseGiven, String thankUComment) {
        this.context = context;
        this.businessReceivedFrom = businessReceivedFrom;
        this.memberName = memberName;
        this.crossedBranch = crossedBranch;
        this.businessCategory = businessCategory;
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


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getBusinessReceivedFrom() {
        return businessReceivedFrom;
    }

    public void setBusinessReceivedFrom(String businessReceivedFrom) {
        this.businessReceivedFrom = businessReceivedFrom;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCrossedBranch() {
        return crossedBranch;
    }

    public void setCrossedBranch(String crossedBranch) {
        this.crossedBranch = crossedBranch;
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

    public String getThankUComment() {
        return thankUComment;
    }

    public void setThankUComment(String thankUComment) {
        this.thankUComment = thankUComment;
    }
}
