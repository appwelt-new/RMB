package com.theappwelt.rmb.model;

public class MeetingCreatedListModel {

    String name,topic, date, location, meetingId, initiator_id, status;

    public MeetingCreatedListModel(String topic, String date, String location, String meetingId, String initiator_id, String status) {
        this.topic = topic;
        this.date = date;
        this.location = location;
        this.meetingId = meetingId;
        this.initiator_id = initiator_id;
        this.status = status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInitiator_id() {
        return initiator_id;
    }

    public void setInitiator_id(String initiator_id) {
        this.initiator_id = initiator_id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }
}
