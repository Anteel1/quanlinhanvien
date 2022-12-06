package com.example.quanlinhanvien.service;

import com.example.quanlinhanvien.model.calam;
import com.example.quanlinhanvien.model.lichlam;
import com.example.quanlinhanvien.model.luong;
import com.example.quanlinhanvien.model.ngaylam;
import com.example.quanlinhanvien.model.nhanvien;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface service_API {
    String Base_Service ="https://circle.kynalab.com/";

    @GET("api/getAllNV")
    Observable<ArrayList<nhanvien>> getModelAPI();
    @GET("api/getAllCL")
    Observable<ArrayList<calam>> getModelAPI_calam();
//    @GET("api/getLuongNV")
//    Observable<luong>getLuong(@Query("maNV") int maNV, @Query("thang") int thang);
    @GET("api/getLichSuLamNV")
    Observable<ArrayList<ngaylam>>getNgayLam(@Query("maNV") int maNV, @Query("thang") int thang);
    @GET("api/getLichLamNV")
    Observable<ArrayList<lichlam>> getLichlamnv(@Query("manv") int maNV , @Query("thang") int thang );
    @GET("api/getLuong_dungGio")
    Observable<luong> getLuongDungGio(@Query("manv") int maNV , @Query("thang") int thang );
    @GET("api/getLuong_treGio")
    Observable<luong> getLuongTreGio(@Query("manv") int maNV , @Query("thang") int thang );

    @POST("api/checkIn")
    Observable<Number>postCheckIn(@Query("MaNV") int maNV, @Query("MaCL") int maCL,@Query("HinhAnh") String hinhanh);
    @POST("api/checkOut")
    Observable<Number>postCheckOut(@Query("MaNV") int maNV, @Query("MaCL") int maCL);


    @POST("api/update_Imei_NV")
    Observable<Number> updateImei(@Query("idNV") int idNV, @Query("Imei") String Imei);


    @POST("api/update_NV_QL")
    Observable<Number> updateNV(@Query("maNV") int maNV, @Query("tenVN") String tenNV, @Query("TaiKhoan") String taikhoan,
                                @Query("MatKhau") String MatKhau, @Query("Imei") String Imei, @Query("MaCV") int MaCV);

    @POST("api/add_NV")
    Observable<Number> addnV(@Query("TenNV") String tenNV, @Query("TaiKhoan") String taikhoan);
}
