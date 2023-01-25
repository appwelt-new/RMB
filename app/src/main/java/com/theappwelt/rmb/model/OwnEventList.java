package com.theappwelt.rmb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OwnEventList {

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

        @SerializedName("member_event_id")
        @Expose
        private String memberEventId;
        @SerializedName("event_name")
        @Expose
        private String eventName;
        @SerializedName("event_date_and_time")
        @Expose
        private String eventDateAndTime;
        @SerializedName("event_end_date_and_time")
        @Expose
        private Object eventEndDateAndTime;
        @SerializedName("event_type_id")
        @Expose
        private String eventTypeId;
        @SerializedName("event_type_name")
        @Expose
        private String eventTypeName;

        public String getMemberEventId() {
            return memberEventId;
        }

        public void setMemberEventId(String memberEventId) {
            this.memberEventId = memberEventId;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getEventDateAndTime() {
            return eventDateAndTime;
        }

        public void setEventDateAndTime(String eventDateAndTime) {
            this.eventDateAndTime = eventDateAndTime;
        }

        public Object getEventEndDateAndTime() {
            return eventEndDateAndTime;
        }

        public void setEventEndDateAndTime(Object eventEndDateAndTime) {
            this.eventEndDateAndTime = eventEndDateAndTime;
        }

        public String getEventTypeId() {
            return eventTypeId;
        }

        public void setEventTypeId(String eventTypeId) {
            this.eventTypeId = eventTypeId;
        }

        public String getEventTypeName() {
            return eventTypeName;
        }

        public void setEventTypeName(String eventTypeName) {
            this.eventTypeName = eventTypeName;
        }

    }
}
