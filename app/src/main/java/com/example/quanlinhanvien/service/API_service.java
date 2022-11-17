package com.example.quanlinhanvien.service;

import com.example.quanlinhanvien.model_api.nhanvien;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface API_service {
    String Base_Service ="https://circle.kynalab.com/";
    @GET("api/getAllNV")
    Observable<ArrayList<nhanvien>> getModelAPI();
}
