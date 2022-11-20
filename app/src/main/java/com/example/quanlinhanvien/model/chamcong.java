package com.example.quanlinhanvien.model;


import com.google.type.Date;
import com.google.type.DateTime;

public class chamcong {
    private int macc;
    private DateTime giobd;
    private DateTime giokt;
    private Date ngaylam;
    private String hinhanh;
    private int trangthai;
    private int manv;
    private int macl;

    public chamcong(int macc, DateTime giobd, DateTime giokt, Date ngaylam, String hinhanh, int trangthai, int manv, int macl) {
        this.macc = macc;
        this.giobd = giobd;
        this.giokt = giokt;
        this.ngaylam = ngaylam;
        this.hinhanh = hinhanh;
        this.trangthai = trangthai;
        this.manv = manv;
        this.macl = macl;
    }

    public int getMacc() {
        return macc;
    }

    public void setMacc(int macc) {
        this.macc = macc;
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

    public Date getNgaylam() {
        return ngaylam;
    }

    public void setNgaylam(Date ngaylam) {
        this.ngaylam = ngaylam;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public int getMacl() {
        return macl;
    }

    public void setMacl(int macl) {
        this.macl = macl;
    }
}
