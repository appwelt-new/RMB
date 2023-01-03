package com.theappwelt.rmb.model;

public class MeetingRequestedListModel {
    String name, topic, date, location, meetingId, status;

    public MeetingRequestedListModel(String name, String topic, String date, String location, String meeting_Id, String status) {
        this.name = name;
        this.topic = topic;
        this.date = date;
        this.location = location;
        this.meetingId = meeting_Id;
        this.status = status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
