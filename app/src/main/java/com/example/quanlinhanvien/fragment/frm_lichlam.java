package com.example.quanlinhanvien.fragment;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.adapter_lichlam;
import com.example.quanlinhanvien.model.lichlam;
import com.example.quanlinhanvien.service.service_API;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frm_lichlam extends Fragment {
    Button btnBack,btnNext;
    EditText edtthang, edtidnv;
    ArrayList<lichlam> list;
    ListView listviewll;
    TextView tvMonth;
    Month thanght;
    int idnv;
    public frm_lichlam(int idnv){
        this.idnv =idnv;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_lichlam, container, false);
        tvMonth = view.findViewById(R.id.tvMonth);
        tvMonth.setText(LocalDate.now().getMonth()+" "+LocalDate.now().getYear());
        thanght = LocalDate.now().getMonth();
        listviewll = view.findViewById(R.id.listviewll);
        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnNext);
        list = new ArrayList<>();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thangtruoc();
                demoCallAPI(idnv,thanght.getValue());
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thangsau();
                demoCallAPI(idnv,thanght.getValue());
            }
        });
                demoCallAPI(idnv, LocalDate.now().getMonth().getValue());




        return view;
    }
    private void thangtruoc(){
        thanght =thanght.minus(1);
    }
    private void thangsau(){
        thanght =thanght.plus(1);
    }

    private void demoCallAPI(int maNV, int thang) {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getLichlamnv(maNV, thang)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<lichlam> lichlams) {
        for (int i = 0; i < lichlams.size(); i++) {
            list.add(lichlams.get(i));
        }

        show(list);

    }


    private void handleError(Throwable error) {
        Log.d("TAG", "loi_________________");
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

    private void show(ArrayList<lichlam> listll) {
        adapter_lichlam adapter_lichlam = new adapter_lichlam(listll, getContext());
        listviewll.setAdapter(adapter_lichlam);


    }
}
