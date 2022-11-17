package com.example.quanlinhanvien;

import static com.example.quanlinhanvien.ServiceAPI.BASE_Service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.quanlinhanvien.model.ListUser;
import com.example.quanlinhanvien.model.User;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class test extends AppCompatActivity {
    ArrayList<User> list;
    User user;
    String txt;
    TextView txttest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
         txttest = findViewById(R.id.txttest);
        demoCallAPI();


    }
    private void demoCallAPI() {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.GetListUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ListUser listUser) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data

        list = listUser.getData();
    }

    private void handleError(Throwable error) {
        String a = "ktc";
        txttest.setText(a+"");

        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

}