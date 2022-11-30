package com.example.quanlinhanvien.model;

public class lichlam {
    private int maCC;
    private String tenNV ;
    private String ngayLam ;
    private String tenCL ;
    private String gioBD ;
    private String gioKT ;

    public lichlam(int maCC, String tenNV, String ngayLam, String tenCL, String gioBD, String gioKT) {
        this.maCC = maCC;
        this.tenNV = tenNV;
        this.ngayLam = ngayLam;
        this.tenCL = tenCL;
        this.gioBD = gioBD;
        this.gioKT = gioKT;
    }

    public int getMaCC() {
        return maCC;
    }

    public void setMaCC(int maCC) {
        this.maCC = maCC;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(String ngayLam) {
        this.ngayLam = ngayLam;
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
