package com.theappwelt.rmb.model;

public class ReferralReceivedModel2 {

    String referral_status_id,referral_name,status,created_on,modified_on,created_by,modified_by,is_deleted;

    public ReferralReceivedModel2(String referral_status_id, String referral_name, String status, String created_on, String modified_on, String created_by, String modified_by, String is_deleted) {
        this.referral_status_id = referral_status_id;
        this.referral_name = referral_name;
        this.status = status;
        this.created_on = created_on;
        this.modified_on = modified_on;
        this.created_by = created_by;
        this.modified_by = modified_by;
        this.is_deleted = is_deleted;
    }


    public String getReferral_status_id() {
        return referral_status_id;
    }

    public void setReferral_status_id(String referral_status_id) {
        this.referral_status_id = referral_status_id;
    }

    public String getReferral_name() {
        return referral_name;
    }

    public void setReferral_name(String referral_name) {
        this.referral_name = referral_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getModified_on() {
        return modified_on;
    }

    public void setModified_on(String modified_on) {
        this.modified_on = modified_on;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }
}
