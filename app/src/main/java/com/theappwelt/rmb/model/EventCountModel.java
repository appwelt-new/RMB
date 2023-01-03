package com.theappwelt.rmb.model;

public class EventCountModel {

    String monthEvent,monthMeetings,weekEvents,weekMeetings,greatBhet,greatBhetInvites,inviteToReferral,inviteByReferral,inviteTpVisitor,inviteByVisitor,branchMembers;

    public EventCountModel(String monthEvent, String monthMeetings, String weekEvents, String weekMeetings, String greatBhet, String greatBhetInvites, String inviteToReferral, String inviteByReferral, String inviteTpVisitor, String inviteByVisitor, String branchMembers) {
        this.monthEvent = monthEvent;
        this.monthMeetings = monthMeetings;
        this.weekEvents = weekEvents;
        this.weekMeetings = weekMeetings;
        this.greatBhet = greatBhet;
        this.greatBhetInvites = greatBhetInvites;
        this.inviteToReferral = inviteToReferral;
        this.inviteByReferral = inviteByReferral;
        this.inviteTpVisitor = inviteTpVisitor;
        this.inviteByVisitor = inviteByVisitor;
        this.branchMembers = branchMembers;
    }


    public String getMonthEvent() {
        return monthEvent;
    }

    public void setMonthEvent(String monthEvent) {
        this.monthEvent = monthEvent;
    }

    public String getMonthMeetings() {
        return monthMeetings;
    }

    public void setMonthMeetings(String monthMeetings) {
        this.monthMeetings = monthMeetings;
    }

    public String getWeekEvents() {
        return weekEvents;
    }

    public void setWeekEvents(String weekEvents) {
        this.weekEvents = weekEvents;
    }

    public String getWeekMeetings() {
        return weekMeetings;
    }

    public void setWeekMeetings(String weekMeetings) {
        this.weekMeetings = weekMeetings;
    }

    public String getGreatBhet() {
        return greatBhet;
    }

    public void setGreatBhet(String greatBhet) {
        this.greatBhet = greatBhet;
    }

    public String getGreatBhetInvites() {
        return greatBhetInvites;
    }

    public void setGreatBhetInvites(String greatBhetInvites) {
        this.greatBhetInvites = greatBhetInvites;
    }

    public String getInviteToReferral() {
        return inviteToReferral;
    }

    public void setInviteToReferral(String inviteToReferral) {
        this.inviteToReferral = inviteToReferral;
    }

    public String getInviteByReferral() {
        return inviteByReferral;
    }

    public void setInviteByReferral(String inviteByReferral) {
        this.inviteByReferral = inviteByReferral;
    }

    public String getInviteTpVisitor() {
        return inviteTpVisitor;
    }

    public void setInviteTpVisitor(String inviteTpVisitor) {
        this.inviteTpVisitor = inviteTpVisitor;
    }

    public String getInviteByVisitor() {
        return inviteByVisitor;
    }

    public void setInviteByVisitor(String inviteByVisitor) {
        this.inviteByVisitor = inviteByVisitor;
    }

    public String getBranchMembers() {
        return branchMembers;
    }

    public void setBranchMembers(String branchMembers) {
        this.branchMembers = branchMembers;
    }
}
