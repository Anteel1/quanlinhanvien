package com.example.quanlinhanvien.model;


import com.google.type.DateTime;

public class chamcong {
    private int maCL;
    private String tenNV;
    private DateTime giobd;
    private DateTime giokt;

    public chamcong(int maCL, String tenNV, DateTime giobd, DateTime giokt) {
        this.maCL = maCL;
        this.tenNV = tenNV;
        this.giobd = giobd;
        this.giokt = giokt;
    }

    public int getMaCL() {
        return maCL;
    }

    public void setMaCL(int maCL) {
        this.maCL = maCL;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public DateTime getGiobd() {
        return giobd;
    }

    public void setGiobd(DateTime giobd) {
        this.giobd = giobd;
    }

    public DateTime getGiokt() {
        return giokt;
    }

    public void setGiokt(DateTime giokt) {
        this.giokt = giokt;
    }
}
