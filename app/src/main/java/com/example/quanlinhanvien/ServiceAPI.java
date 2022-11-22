package com.example.quanlinhanvien;

import com.example.quanlinhanvien.model.User;
import com.example.quanlinhanvien.model.chamcong;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ServiceAPI {
    String BASE_Service = "https://circle.kynalab.com/";
    @GET("api/getAllNV")
    Observable<ArrayList<User>> GetListUser();
    String BASE_ServiceCC = "https://circle.kynalab.com/";
    @GET("api/getAllCL")
    Observable<ArrayList<chamcong>> GetListChamcong();

}
