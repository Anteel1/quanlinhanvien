package com.example.quanlinhanvien.model;

public class luong {
    float tonggiolam;
    float tongLuong;
    luong(){};
    public luong(float tonggiolam, float tongLuong) {
        this.tonggiolam = tonggiolam;
        this.tongLuong = tongLuong;
    }

    public float getTonggiolam() {
        return tonggiolam;
    }

    public void setTonggiolam(float tonggiolam) {
        this.tonggiolam = tonggiolam;
    }

    public float getTongLuong() {
        return tongLuong;
    }

    public void setTongLuong(float tongLuong) {
        this.tongLuong = tongLuong;
    }
}
