package com.example.quanlinhanvien.service;

import com.example.quanlinhanvien.model.calam;
import com.example.quanlinhanvien.model.cuahang;
import com.example.quanlinhanvien.model.nhanvien;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface service_API {
    String Base_Service ="https://circle.kynalab.com/";

    @GET("api/getAllNV")
    Observable<ArrayList<nhanvien>> getModelAPI();
    @GET("api/getAllCH")
    Observable<ArrayList<cuahang>> getModelCHAPI();
    @GET("api/getAllCL")
    Observable<ArrayList<calam>> getModelAPI_calam();
}
