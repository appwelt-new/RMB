package com.theappwelt.rmb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GivenBusinessList {


    public class MessageText {

        @SerializedName("closed_business_id")
        @Expose
        private String closedBusinessId;
        @SerializedName("given_by_id")
        @Expose
        private String givenById;
        @SerializedName("given_to_id")
        @Expose
        private String givenToId;
        @SerializedName("is_cross_branch")
        @Expose
        private String isCrossBranch;
        @SerializedName("closed_date_and_time")
        @Expose
        private String closedDateAndTime;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("referral_id")
        @Expose
        private String referralId;
        @SerializedName("business_transaction_amount")
        @Expose
        private String businessTransactionAmount;
        @SerializedName("creator_comment")
        @Expose
        private String creatorComment;
        @SerializedName("thankyou_date_and_time")
        @Expose
        private String thankyouDateAndTime;
        @SerializedName("thankyou_comment")
        @Expose
        private String thankyouComment;
        @SerializedName("thankyou_status")
        @Expose
        private String thankyouStatus;
        @SerializedName("created_on")
        @Expose
        private String createdOn;
        @SerializedName("modified_on")
        @Expose
        private String modifiedOn;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("modified_by")
        @Expose
        private String modifiedBy;
        @SerializedName("is_deleted")
        @Expose
        private String isDeleted;
        @SerializedName("closed_status")
        @Expose
        private String closedStatus;
        @SerializedName("business_category")
        @Expose
        private String businessCategory;
        @SerializedName("referral_name")
        @Expose
        private String referralName;
        @SerializedName("referral_comment")
        @Expose
        private String referralComment;
        @SerializedName("referral_given_on")
        @Expose
        private String referralGivenOn;
        @SerializedName("given_by")
        @Expose
        private String givenBy;
        @SerializedName("given_by_mobile")
        @Expose
        private String givenByMobile;
        @SerializedName("given_to")
        @Expose
        private String givenTo;
        @SerializedName("given_to_mobile")
        @Expose
        private String givenToMobile;

        public String getClosedBusinessId() {
            return closedBusinessId;
        }

        public void setClosedBusinessId(String closedBusinessId) {
            this.closedBusinessId = closedBusinessId;
        }

        public String getGivenById() {
            return givenById;
        }

        public void setGivenById(String givenById) {
            this.givenById = givenById;
        }

        public String getGivenToId() {
            return givenToId;
        }

        public void setGivenToId(String givenToId) {
            this.givenToId = givenToId;
        }

        public String getIsCrossBranch() {
            return isCrossBranch;
        }

        public void setIsCrossBranch(String isCrossBranch) {
            this.isCrossBranch = isCrossBranch;
        }

        public String getClosedDateAndTime() {
            return closedDateAndTime;
        }

        public void setClosedDateAndTime(String closedDateAndTime) {
            this.closedDateAndTime = closedDateAndTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReferralId() {
            return referralId;
        }

        public void setReferralId(String referralId) {
            this.referralId = referralId;
        }

        public String getBusinessTransactionAmount() {
            return businessTransactionAmount;
        }

        public void setBusinessTransactionAmount(String businessTransactionAmount) {
            this.businessTransactionAmount = businessTransactionAmount;
        }

        public String getCreatorComment() {
            return creatorComment;
        }

        public void setCreatorComment(String creatorComment) {
            this.creatorComment = creatorComment;
        }

        public String getThankyouDateAndTime() {
            return thankyouDateAndTime;
        }

        public void setThankyouDateAndTime(String thankyouDateAndTime) {
            this.thankyouDateAndTime = thankyouDateAndTime;
        }

        public String getThankyouComment() {
            return thankyouComment;
        }

        public void setThankyouComment(String thankyouComment) {
            this.thankyouComment = thankyouComment;
        }

        public String getThankyouStatus() {
            return thankyouStatus;
        }

        public void setThankyouStatus(String thankyouStatus) {
            this.thankyouStatus = thankyouStatus;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(String modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getClosedStatus() {
            return closedStatus;
        }

        public void setClosedStatus(String closedStatus) {
            this.closedStatus = closedStatus;
        }

        public String getBusinessCategory() {
            return businessCategory;
        }

        public void setBusinessCategory(String businessCategory) {
            this.businessCategory = businessCategory;
        }

        public String getReferralName() {
            return referralName;
        }

        public void setReferralName(String referralName) {
            this.referralName = referralName;
        }

        public String getReferralComment() {
            return referralComment;
        }

        public void setReferralComment(String referralComment) {
            this.referralComment = referralComment;
        }

        public String getReferralGivenOn() {
            return referralGivenOn;
        }

        public void setReferralGivenOn(String referralGivenOn) {
            this.referralGivenOn = referralGivenOn;
        }

        public String getGivenBy() {
            return givenBy;
        }

        public void setGivenBy(String givenBy) {
            this.givenBy = givenBy;
        }

        public String getGivenByMobile() {
            return givenByMobile;
        }

        public void setGivenByMobile(String givenByMobile) {
            this.givenByMobile = givenByMobile;
        }

        public String getGivenTo() {
            return givenTo;
        }

        public void setGivenTo(String givenTo) {
            this.givenTo = givenTo;
        }

        public String getGivenToMobile() {
            return givenToMobile;
        }

        public void setGivenToMobile(String givenToMobile) {
            this.givenToMobile = givenToMobile;
        }

    }

    @SerializedName("message_code")
    @Expose
    private String messageCode;
    @SerializedName("message_text")
    @Expose
    private List<MessageText> messageText = null;

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public List<MessageText> getMessageText() {
        return messageText;
    }

    public void setMessageText(List<MessageText> messageText) {
        this.messageText = messageText;
    }

}
