package com.theappwelt.rmb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeetingSummaryList {

    @SerializedName("message_code")
    @Expose
    private Integer messageCode;
    @SerializedName("message_text")
    @Expose
    private List<MessageText> messageText = null;

    public Integer getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(Integer messageCode) {
        this.messageCode = messageCode;
    }

    public List<MessageText> getMessageText() {
        return messageText;
    }

    public void setMessageText(List<MessageText> messageText) {
        this.messageText = messageText;
    }

    public class MessageText {

        @SerializedName("purpose_of_meeting")
        @Expose
        private String purposeOfMeeting;
        @SerializedName("bussiness_done")
        @Expose
        private String bussinessDone;
        @SerializedName("no_of_member_attended")
        @Expose
        private String noOfMemberAttended;
        @SerializedName("no_of_rotarian_member")
        @Expose
        private String noOfRotarianMember;
        @SerializedName("no_of_nonrotarian_member")
        @Expose
        private String noOfNonrotarianMember;
        @SerializedName("reference_given")
        @Expose
        private String referenceGiven;
        @SerializedName("summary")
        @Expose
        private String summary;

        public String getPurposeOfMeeting() {
            return purposeOfMeeting;
        }

        public void setPurposeOfMeeting(String purposeOfMeeting) {
            this.purposeOfMeeting = purposeOfMeeting;
        }

        public String getBussinessDone() {
            return bussinessDone;
        }

        public void setBussinessDone(String bussinessDone) {
            this.bussinessDone = bussinessDone;
        }

        public String getNoOfMemberAttended() {
            return noOfMemberAttended;
        }

        public void setNoOfMemberAttended(String noOfMemberAttended) {
            this.noOfMemberAttended = noOfMemberAttended;
        }

        public String getNoOfRotarianMember() {
            return noOfRotarianMember;
        }

        public void setNoOfRotarianMember(String noOfRotarianMember) {
            this.noOfRotarianMember = noOfRotarianMember;
        }

        public String getNoOfNonrotarianMember() {
            return noOfNonrotarianMember;
        }

        public void setNoOfNonrotarianMember(String noOfNonrotarianMember) {
            this.noOfNonrotarianMember = noOfNonrotarianMember;
        }

        public String getReferenceGiven() {
            return referenceGiven;
        }

        public void setReferenceGiven(String referenceGiven) {
            this.referenceGiven = referenceGiven;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

    }

}
