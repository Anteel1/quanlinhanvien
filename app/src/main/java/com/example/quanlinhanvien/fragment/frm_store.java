package com.example.quanlinhanvien.fragment;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.adapter_store;
import com.example.quanlinhanvien.model.cuahang;
import com.example.quanlinhanvien.service.service_API;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frm_store extends Fragment {
    ArrayList<String> list,listLocation;
    RecyclerView recyclerView;
    TextView txtTotal;
    adapter_store adapter_store;
    Button btnsignup,btnUpdate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_diemdanh, container, false);
        txtTotal = view.findViewById(R.id.totalListNV);
        recyclerView = view.findViewById(R.id.rcv_nhanvien);
        btnsignup = view.findViewById(R.id.btnsignup);
        btnUpdate = view.findViewById(R.id.btnupdate);
        list = new ArrayList<>();
        listLocation=new ArrayList<>();
        loaddata();
        demoCallAPI();
        return view;
    }
    private void loaddata(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter_store = new adapter_store(getContext(),list,listLocation);
        recyclerView.setAdapter(adapter_store);
    }
    private void demoCallAPI() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getModelCHAPI()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<cuahang> list1) {
        for(int i =0; i <list1.size(); i++){
            list.add(i,list1.get(i).getTenCH());
            listLocation.add(i,list1.get(i).getDiaChi());
        }
        adapter_store.notifyDataSetChanged();
        txtTotal.setText("Total store: "+list1.size());
    }

    private void handleError(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
    }
}
