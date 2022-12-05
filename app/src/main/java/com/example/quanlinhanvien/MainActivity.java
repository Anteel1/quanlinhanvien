package com.example.quanlinhanvien;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.fragment.frm_timekeeping;
import com.example.quanlinhanvien.fragment.frm_calam;
import com.example.quanlinhanvien.fragment.frm_lichlam;
import com.example.quanlinhanvien.fragment.frm_me;
import com.example.quanlinhanvien.fragment.frm_thongke;
import com.example.quanlinhanvien.fragment.frm_trangchu;
import com.example.quanlinhanvien.model.nhanvien;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    //    TextView tv;
//    ImageView iv_menu;
//    DrawerLayout drawerLayout;
//    Toolbar toolbar;
//    NavigationView navigationView;
    Fragment fragment;
    nhanvien nhanvien;
    Toolbar toolbar;
    TextView tvTitle;
    ImageView imgNav;
    IntentFilter intentFilter = new IntentFilter();
//    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentFilter.addAction("statistics");
        intentFilter.addAction("shift");
        intentFilter.addAction("attendance");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tvTitle);
        imgNav = findViewById(R.id.imgNav);

        Bundle bundle = getIntent().getExtras();
        nhanvien = (nhanvien) bundle.getSerializable("nv");

        imgNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view, new frm_me(nhanvien)).commit();
                toolbar.setVisibility(View.GONE);
            }
        });

        //get id nhan vien
//        intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        int idNV = 1;
//        bundle.getInt("idNV", -1)
//        anhxa();
//        menu_nav();
//        fragment = new frm_trangchu();
//        onRestoreInstanceState(savedInstanceState);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.menu_home:
//                        fragment = new frm_trangchu();
//                        onRestoreInstanceState(savedInstanceState);
//                        break;
//                    case R.id.menu_Attendance:
//                        fragment = new frm_attendance();
//                        onRestoreInstanceState(savedInstanceState);
//                        break;
//                    case R.id.menu_store:
//                        fragment = new frm_store();
//                        onRestoreInstanceState(savedInstanceState);
//                        break;
//                    case R.id.menu_lichlam:
//                        fragment = new frm_lichlam(idNV);
//                        onRestoreInstanceState(savedInstanceState);
//                        break;
//                    case R.id.menu_calam:
//                        fragment = new frm_calam();
//                        onRestoreInstanceState(savedInstanceState);
//                        break;
//                    case R.id.menu_nhanvien:
//                        fragment = new frm_nhanvien();
//                        onRestoreInstanceState(savedInstanceState);
//                        break;
//                    case R.id.menu_genqrcode:
//                        fragment = new frm_genQRcode();
//                        onRestoreInstanceState(savedInstanceState);
//                        break;
//                    case R.id.menu_thongke:
//                        fragment = new frm_thongke(idNV);
//                        onRestoreInstanceState(savedInstanceState);
//                        break;
//                    case R.id.menu_Logout:
//                        new frm_dangxuat().show(getSupportFragmentManager(), frm_dangxuat.TAG);
//                        break;
//                    default:
//                        fragment = new frm_trangchu();
//                        break;
//                }
//                drawerLayout.closeDrawer(navigationView);
//                setTitle(item.getTitle());
//                return false;
//            }
//        });

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_view, new frm_trangchu()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new frm_trangchu();
                        toolbar.setVisibility(View.VISIBLE);
                        tvTitle.setText("Employees Manager");
                        imgNav.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.scan:
                        fragment = new frm_timekeeping();
                        toolbar.setVisibility(View.GONE);
                        break;

                    case R.id.me:
                        fragment = new frm_me(nhanvien);
                        toolbar.setVisibility(View.GONE);
                        break;

                    default:

                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view, fragment).commit();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "statistics":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view, new frm_thongke(nhanvien.getMaNV())).commit();
                    toolbar.setVisibility(View.VISIBLE);
                    tvTitle.setText("Statistics");
                    imgNav.setVisibility(View.VISIBLE);
                    break;

                case "shift":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view, new frm_calam()).commit();
                    toolbar.setVisibility(View.VISIBLE);
                    tvTitle.setText("Shift");
                    imgNav.setVisibility(View.VISIBLE);
                    break;

                case "attendance":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view, new frm_lichlam(nhanvien.getMaNV())).commit();
                    toolbar.setVisibility(View.VISIBLE);
                    tvTitle.setText("Attendance");
                    imgNav.setVisibility(View.VISIBLE);
                    break;

                default:

                    break;
            }
        }
    };


//    public void anhxa() {
//        toolbar = findViewById(R.id.toolbar);
//        iv_menu = findViewById(R.id.iv_menu_toolbar);
//        drawerLayout = findViewById(R.id.drawerLayout);
//        navigationView = findViewById(R.id.navigation_view);
//    }

//    public void menu_nav() {
//        iv_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(navigationView);
//            }
//        });
//    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().
//                    add(R.id.fragment_view, fragment).commit();
//        } else {
//            getSupportFragmentManager().beginTransaction().
//                    replace(R.id.fragment_view, fragment).commit();
//
//        }
//    }

}
