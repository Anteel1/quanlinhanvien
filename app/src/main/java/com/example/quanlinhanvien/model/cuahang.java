package com.example.quanlinhanvien.model;

public class cuahang {
    int maCH;
    String tenCH,diaChi;
    public cuahang(){};
    public cuahang(int maCH, String tenCH, String diaChi) {
        this.maCH = maCH;
        this.tenCH = tenCH;
        this.diaChi = diaChi;
    }

    public int getMaCH() {
        return maCH;
    }

    public void setMaCH(int maCH) {
        this.maCH = maCH;
    }

    public String getTenCH() {
        return tenCH;
    }

    public void setTenCH(String tenCH) {
        this.tenCH = tenCH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
