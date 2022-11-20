package com.example.quanlinhanvien.fragment;

import static com.example.quanlinhanvien.ServiceAPI.BASE_Service;
import static com.example.quanlinhanvien.ServiceAPI.BASE_ServiceCC;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.LoginActivity;
import com.example.quanlinhanvien.MainActivity;
import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.ServiceAPI;
import com.example.quanlinhanvien.model.User;
import com.example.quanlinhanvien.model.chamcong;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frm_dangkylichlam extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_dangkylichlam, container, false);

        return view;
    }
    private void demoCallAPI() {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_ServiceCC)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.GetListChamcong()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleRespons, this::handleError)
        );
    }

    private void handleRespons(ArrayList<chamcong> list1) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data



    }



    private void handleError(Throwable error) {
        String a = "";


        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

    private void CallAPIcc() {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.GetListUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError1)
        );
    }

    private void handleError1(Throwable throwable) {
    }

    private void handleResponse(ArrayList<User> list2) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data

        for (int i = 0; i <= list2.size(); i++) {

        }
    }






}
