package com.example.quanlinhanvien.fragment;

import static com.example.quanlinhanvien.service.API_service.Base_Service;
import static com.example.quanlinhanvien.service.API_service_calam.Base_Service_calam;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.LoginActivity;
import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.adapter_calam;
import com.example.quanlinhanvien.model_api.calam;
import com.example.quanlinhanvien.model_api.model_tk;
import com.example.quanlinhanvien.model_api.nhanvien;
import com.example.quanlinhanvien.service.API_service;
import com.example.quanlinhanvien.service.API_service_calam;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frm_calam extends Fragment {

    ArrayList<calam> list;
    RecyclerView recyclerView;
    adapter_calam adapter_calam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_calam, container, false);

        recyclerView = view.findViewById(R.id.rcv_calam);
        list = new ArrayList<>();
        demoCallAPI();


        return view;
    }

    private void demoCallAPI() {

        API_service_calam requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service_calam)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(API_service_calam.class);

        new CompositeDisposable().add(requestInterface.getModelAPI_calam()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<calam> list_calam) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data

        for (int i = 0; i < list_calam.size(); i++) {
            list.add(list_calam.get(i));
        }
        loaddata(list);



        Toast.makeText(getContext(), "=============thành công", Toast.LENGTH_SHORT).show();



    }

    private void handleError(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), "=============" + error, Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }


    private void loaddata(ArrayList<calam> list){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(recyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter_calam = new adapter_calam(getContext(), list);
        recyclerView.setAdapter(adapter_calam);
    }

}
