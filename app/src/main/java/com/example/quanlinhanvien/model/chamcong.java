package com.example.quanlinhanvien.model;




public class chamcong {
    private Object tenNV;
    private Object giobd;
    private Object giokt;

    public chamcong(Object tenNV, Object giobd, Object giokt) {
        this.tenNV = tenNV;
        this.giobd = giobd;
        this.giokt = giokt;
    }



    public Object getTenNV() {
        return tenNV;
    }

    public void setTenNV(Object tenNV) {
        this.tenNV = tenNV;
    }

    public Object getGiobd() {
        return giobd;
    }

    public void setGiobd(Object giobd) {
        this.giobd = giobd;
    }

    public Object getGiokt() {
        return giokt;
    }

    public void setGiokt(Object giokt) {
        this.giokt = giokt;
    }
}
