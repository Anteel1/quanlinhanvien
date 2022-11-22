package com.example.quanlinhanvien.model;




public class chamcong {
    private String tenNV;
    private String giobd;
    private String giokt;

    public chamcong(String tenNV, String giobd, String giokt) {
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

    public String getGiobd() {
        return giobd;
    }

    public void setGiobd(String giobd) {
        this.giobd = giobd;
    }

    public String getGiokt() {
        return giokt;
    }

    public void setGiokt(String giokt) {
        this.giokt = giokt;
    }
}
