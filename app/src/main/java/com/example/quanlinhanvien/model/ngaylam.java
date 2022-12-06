package com.example.quanlinhanvien.model;

public class ngaylam {
    String ngaylam;
    int trangThai;

    public ngaylam(String ngaylam, int trangthai) {
        this.ngaylam = ngaylam;
        this.trangThai = trangThai;
    }

    public String getNgaylam() {
        return ngaylam;
    }

    public void setNgaylam(String ngaylam) {
        this.ngaylam = ngaylam;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
