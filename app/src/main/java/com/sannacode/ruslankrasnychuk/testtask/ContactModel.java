package com.sannacode.ruslankrasnychuk.testtask;

import java.util.List;

/**
 * Created by rusci on 23-Mar-17.
 */

public class ContactModel {
    private int id;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String userId;

    ContactModel(){

    }

    public ContactModel(String contactName, String contactPhone, String contactEmail, String userId) {
        //this.id = id;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.userId = userId;
    }
    public ContactModel(int id, String contactName, String contactPhone, String contactEmail, String userId) {
        this.id = id;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.userId = userId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
