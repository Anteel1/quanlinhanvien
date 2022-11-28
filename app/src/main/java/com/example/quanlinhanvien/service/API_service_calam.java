package com.example.quanlinhanvien.service;

import com.example.quanlinhanvien.model_api.calam;
import com.example.quanlinhanvien.model_api.nhanvien;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface API_service_calam {
    String Base_Service_calam ="https://circle.kynalab.com/";
    @GET("api/getAllCL")
    Observable<ArrayList<calam>> getModelAPI_calam();
}
