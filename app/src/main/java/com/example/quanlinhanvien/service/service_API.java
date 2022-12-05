package com.example.quanlinhanvien.service;

import com.example.quanlinhanvien.model.cuahang;
import com.example.quanlinhanvien.model_api.calam;
import com.example.quanlinhanvien.model_api.nhanvien;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface service_API {
    String Base_Service ="https://circle.kynalab.com/";

    @GET("api/getAllNV")// nhân viên
    Observable<ArrayList<nhanvien>> getModelAPI();
    @GET("api/getAllCH")
    Observable<ArrayList<cuahang>> getModelCHAPI();
    @GET("api/getAllCL")
    Observable<ArrayList<calam>> getModelAPI_calam();

    @POST("api/update_Imei_NV")
    Observable<Number> updateImei(@Query("idNV") int idNV, @Query("Imei") String Imei);


    @POST("api/update/_NV/_QL")
    Observable<Number> updateNV(@Query("maNV") int maNV, @Query("tenVN") String tenNV, @Query("TaiKhoan") String taikhoan,
                                @Query("MatKhau") String MatKhau, @Query("Imei") String Imei, @Query("MaCV") int MaCV);

    @POST("api/add_NV")
    Observable<Number> addnV(@Query("TenNV") String tenNV, @Query("TaiKhoan") String taikhoan);
}
