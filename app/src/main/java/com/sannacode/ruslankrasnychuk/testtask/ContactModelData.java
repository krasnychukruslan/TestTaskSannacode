package com.sannacode.ruslankrasnychuk.testtask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rusci on 23-Mar-17.
 */

public class ContactModelData {
    private int position;
    private int id;
    private String userId;
    private String userName;
    private static ContactModelData ourInstance = new ContactModelData();
    public static ContactModelData getOurInstance(){
        return ourInstance;
    }
    private ContactModelData(){
    }

    private final List<ContactModel> list = new ArrayList<>();

    public List<ContactModel> getList() {
        return list;
    }
    public void setList(List<ContactModel> list){
        this.list.clear();
        this.list.addAll(list);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
