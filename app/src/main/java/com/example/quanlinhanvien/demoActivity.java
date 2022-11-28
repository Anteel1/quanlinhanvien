package com.example.quanlinhanvien;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.quanlinhanvien.model.lichlam;
import com.example.quanlinhanvien.service.service_API;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class demoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_lichlam);
        demoCallAPI(1,11);
    }


    private void demoCallAPI(int maNV,int thang) {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getLichlamnv(maNV,thang)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<lichlam> lichlams) {
            for (lichlam s : lichlams){
                Log.d("TAG","handleResponse"+s.getNgayLam()+""+s.getTenCL());

            }

    }


    private void handleError(Throwable error) {

        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }
}