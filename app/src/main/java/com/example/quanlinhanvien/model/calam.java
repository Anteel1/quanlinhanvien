package com.example.quanlinhanvien.model;

public class calam {
    public int maCL;
    public String tenCL;
    public String gioBD;
    public String gioKT;

    public calam(int maCL, String tenCL, String gioBD, String gioKT) {
        this.maCL = maCL;
        this.tenCL = tenCL;
        this.gioBD = gioBD;
        this.gioKT = gioKT;
    }

    public int getMaCL() {
        return maCL;
    }

    public void setMaCL(int maCL) {
        this.maCL = maCL;
    }

    public String getTenCL() {
        return tenCL;
    }

    public void setTenCL(String tenCL) {
        this.tenCL = tenCL;
    }

    public String getGioBD() {
        return gioBD;
    }

    public void setGioBD(String gioBD) {
        this.gioBD = gioBD;
    }

    public String getGioKT() {
        return gioKT;
    }

    public void setGioKT(String gioKT) {
        this.gioKT = gioKT;
    }
}
