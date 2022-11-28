package com.example.quanlinhanvien.model_api;

public class model_tk {
    private String email, password, imei;

    public model_tk(String email, String password, String imei) {
        this.email = email;
        this.password = password;
        this.imei = imei;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
