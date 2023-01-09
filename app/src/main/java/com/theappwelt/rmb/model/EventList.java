package com.theappwelt.rmb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventList {

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


    public class MessageText {

        @SerializedName("event_id")
        @Expose
        private String eventId;
        @SerializedName("event_name")
        @Expose
        private String eventName;
        @SerializedName("event_date_and_time")
        @Expose
        private String eventDateAndTime;
        @SerializedName("event_end_date_and_time")
        @Expose
        private String eventEndDateAndTime;
        @SerializedName("event_duration")
        @Expose
        private String eventDuration;
        @SerializedName("event_description")
        @Expose
        private String eventDescription;
        @SerializedName("event_category_id")
        @Expose
        private String eventCategoryId;
        @SerializedName("event_type_id")
        @Expose
        private String eventTypeId;
        @SerializedName("event_location")
        @Expose
        private String eventLocation;
        @SerializedName("event_host")
        @Expose
        private String eventHost;
        @SerializedName("event_url")
        @Expose
        private String eventUrl;
        @SerializedName("event_status")
        @Expose
        private String eventStatus;
        @SerializedName("event_price")
        @Expose
        private String eventPrice;
        @SerializedName("event_only_member")
        @Expose
        private String eventOnlyMember;
        @SerializedName("event_rating")
        @Expose
        private String eventRating;
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

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
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

        public String getEventEndDateAndTime() {
            return eventEndDateAndTime;
        }

        public void setEventEndDateAndTime(String eventEndDateAndTime) {
            this.eventEndDateAndTime = eventEndDateAndTime;
        }

        public String getEventDuration() {
            return eventDuration;
        }

        public void setEventDuration(String eventDuration) {
            this.eventDuration = eventDuration;
        }

        public String getEventDescription() {
            return eventDescription;
        }

        public void setEventDescription(String eventDescription) {
            this.eventDescription = eventDescription;
        }

        public String getEventCategoryId() {
            return eventCategoryId;
        }

        public void setEventCategoryId(String eventCategoryId) {
            this.eventCategoryId = eventCategoryId;
        }

        public String getEventTypeId() {
            return eventTypeId;
        }

        public void setEventTypeId(String eventTypeId) {
            this.eventTypeId = eventTypeId;
        }

        public String getEventLocation() {
            return eventLocation;
        }

        public void setEventLocation(String eventLocation) {
            this.eventLocation = eventLocation;
        }

        public String getEventHost() {
            return eventHost;
        }

        public void setEventHost(String eventHost) {
            this.eventHost = eventHost;
        }

        public String getEventUrl() {
            return eventUrl;
        }

        public void setEventUrl(String eventUrl) {
            this.eventUrl = eventUrl;
        }

        public String getEventStatus() {
            return eventStatus;
        }

        public void setEventStatus(String eventStatus) {
            this.eventStatus = eventStatus;
        }

        public String getEventPrice() {
            return eventPrice;
        }

        public void setEventPrice(String eventPrice) {
            this.eventPrice = eventPrice;
        }

        public String getEventOnlyMember() {
            return eventOnlyMember;
        }

        public void setEventOnlyMember(String eventOnlyMember) {
            this.eventOnlyMember = eventOnlyMember;
        }

        public String getEventRating() {
            return eventRating;
        }

        public void setEventRating(String eventRating) {
            this.eventRating = eventRating;
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

    }
}
