package com.example.quanlinhanvien;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.fragment.frm_attendance;
import com.example.quanlinhanvien.fragment.frm_calam;
import com.example.quanlinhanvien.fragment.frm_dangkylichlam;
import com.example.quanlinhanvien.fragment.frm_dangxuat;
import com.example.quanlinhanvien.fragment.frm_genQRcode;
import com.example.quanlinhanvien.fragment.frm_lichlam;
import com.example.quanlinhanvien.fragment.frm_nhanvien;
import com.example.quanlinhanvien.fragment.frm_store;
import com.example.quanlinhanvien.fragment.frm_thongke;
import com.example.quanlinhanvien.fragment.frm_trangchu;
import com.example.quanlinhanvien.model.nhanvien;
import com.example.quanlinhanvien.service.service_API;
import com.google.android.material.navigation.NavigationView;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView usname;
    ImageView iv_menu;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    Fragment fragment;
    private View headerLayout;
    int idnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        menu_nav();
        Intent i = this.getIntent();
        idnv = i.getIntExtra("idnv",0);
        demoCallAPINV();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        fragment = new frm_trangchu();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_Attendance:
                        fragment = new frm_attendance();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_store:
                        fragment = new frm_store();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_dk_lichlam:
                        fragment = new frm_dangkylichlam();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_lichlam:
                        fragment = new frm_lichlam(idnv);
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_calam:
                        fragment = new frm_calam();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_nhanvien:
                        fragment = new frm_nhanvien();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_genqrcode:
                        fragment = new frm_genQRcode();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_thongke:
                        fragment = new frm_thongke();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_Logout:
//                        fragment = new frm_dangxuat();
                        new frm_dangxuat().show(getSupportFragmentManager(),frm_dangxuat.TAG);
                        break;
                    default:
                        fragment = new frm_trangchu();
                        break;
                }
                drawerLayout.closeDrawer(navigationView);
                setTitle(item.getTitle());
                return false;
            }
        });
    }


    public void anhxa() {
        toolbar = findViewById(R.id.toolbar);
        iv_menu = findViewById(R.id.iv_menu_toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView = findViewById(R.id.navigation_view);
        headerLayout=navigationView.getHeaderView(0);
        usname=headerLayout.findViewById(R.id.name);




    }

    public void menu_nav() {
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragment_view, fragment).commit();

        }
        else {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_view, fragment).commit();

        }
    }
    private void demoCallAPINV() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getModelAPI()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseNV, this::handleErrorNV)
        );
    }

    private void handleResponseNV(ArrayList<nhanvien> list1) {

        for (int i = 0; i < list1.size(); i++) {
            if (idnv==list1.get(i).getMaNV()){
                usname.setText(list1.get(i).getTenNV());

            }
        }
    }
    private void handleErrorNV(Throwable error) {
        Log.d("erro", error.toString());
    }



}
