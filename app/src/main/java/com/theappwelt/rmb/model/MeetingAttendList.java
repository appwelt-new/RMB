package com.theappwelt.rmb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeetingAttendList {


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

        @SerializedName("meeting_id")
        @Expose
        private String meetingId;
        @SerializedName("attend_member_id")
        @Expose
        private String attendMemberId;
        @SerializedName("attend_status")
        @Expose
        private String attendStatus;
        @SerializedName("member_email")
        @Expose
        private String memberEmail;
        @SerializedName("member_mobile")
        @Expose
        private String memberMobile;
        @SerializedName("member_first_name")
        @Expose
        private String memberFirstName;
        @SerializedName("member_last_name")
        @Expose
        private String memberLastName;

        public String getMeetingId() {
            return meetingId;
        }

        public void setMeetingId(String meetingId) {
            this.meetingId = meetingId;
        }

        public String getAttendMemberId() {
            return attendMemberId;
        }

        public void setAttendMemberId(String attendMemberId) {
            this.attendMemberId = attendMemberId;
        }

        public String getAttendStatus() {
            return attendStatus;
        }

        public void setAttendStatus(String attendStatus) {
            this.attendStatus = attendStatus;
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

    }

}
