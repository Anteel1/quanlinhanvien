package com.example.quanlinhanvien.model;


import com.google.type.Date;
import com.google.type.DateTime;

public class chamcong {
    private String tenNV;
    private DateTime giobd;
    private DateTime giokt;

    public chamcong(String tenNV, DateTime giobd, DateTime giokt) {
        this.tenNV = tenNV;
        this.giobd = giobd;
        this.giokt = giokt;
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
