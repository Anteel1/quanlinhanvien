package com.example.quanlinhanvien.service;

import com.example.quanlinhanvien.model.calam;
import com.example.quanlinhanvien.model.chamcong;
import com.example.quanlinhanvien.model.cuahang;
import com.example.quanlinhanvien.model.lichlam;
import com.example.quanlinhanvien.model.nhanvien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


public interface service_API {
    String Base_Service ="https://circle.kynalab.com/";


    @GET("api/getAllNV")
    Observable<ArrayList<nhanvien>> getModelAPI();
    @GET("api/getAllCH")
    Observable<ArrayList<cuahang>> getModelCHAPI();
    @GET("api/getAllCL")
    Observable<ArrayList<calam>> getModelAPI_calam();
    @GET("api/getNV1")
    Observable<ArrayList<chamcong>> getModelAPI_chamcong();
    @GET("api/getLichLamNV")
    Observable<ArrayList<lichlam>> getLichlamnv(@Query("manv") int maNV , @Query("thang") int thang );
    @POST("api/add_lichlam")
    Observable<Number> addLichlamnv(@Query("idNV") int idNV , @Query("maCL") int maCL,@Query("ngaylam") String ngaylam );

//    @Override("api/getLichLamNV")
//    Observable<ArrayList<lichlam>> getLichlamnv(@Query("manv") int maNV , @Query("thang") int thang );
}
