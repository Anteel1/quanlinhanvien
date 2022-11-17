package com.example.quanlinhanvien.model;

public class User {
    private int manv;
    private String taikhoan;
    private String matkhau;
    private String tennv;
    private String ngaysinh;
    private String diachi;
    private String ngaydk;
    private String imei;
    private int mach;
    private int macv;

    public User(int manv, String taikhoan, String matkhau, String tennv, String ngaysinh, String diachi, String ngaydk, String imei, int mach, int macv) {
        this.manv = manv;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.tennv = tennv;
        this.ngaysinh = ngaysinh;
        this.diachi = diachi;
        this.ngaydk = ngaydk;
        this.imei = imei;
        this.mach = mach;
        this.macv = macv;
    }

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getNgaydk() {
        return ngaydk;
    }

    public void setNgaydk(String ngaydk) {
        this.ngaydk = ngaydk;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getMach() {
        return mach;
    }

    public void setMach(int mach) {
        this.mach = mach;
    }

    public int getMacv() {
        return macv;
    }

    public void setMacv(int macv) {
        this.macv = macv;
    }
}
