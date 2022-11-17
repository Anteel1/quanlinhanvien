package com.example.quanlinhanvien.model;

import java.util.ArrayList;

public class ListUser {
    private ArrayList<User> data;

    public ListUser(ArrayList<User> data) {
        this.data = data;
    }

    public ArrayList<User> getData() {
        return data;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }
}
