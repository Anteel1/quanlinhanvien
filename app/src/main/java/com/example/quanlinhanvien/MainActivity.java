package com.example.quanlinhanvien;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.fragment.frm_attendance;
import com.example.quanlinhanvien.fragment.frm_dangkylichlam;
import com.example.quanlinhanvien.fragment.frm_dangxuat;
import com.example.quanlinhanvien.fragment.frm_genQRcode;
import com.example.quanlinhanvien.fragment.frm_nhanvien;
import com.example.quanlinhanvien.fragment.frm_store;
import com.example.quanlinhanvien.fragment.frm_thongke;
import com.example.quanlinhanvien.fragment.frm_trangchu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    ImageView iv_menu;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    Fragment fragment;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get id nhan vien
//        Intent intent = getIntent();
//        Bundle bundle =intent.getExtras();
//        int idNV = bundle.getInt("idNV",-1);
        anhxa();
        menu_nav();
        fragment = new frm_trangchu();
        onRestoreInstanceState(savedInstanceState);
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
                    case R.id.menu_nhanvien:
                        fragment = new frm_nhanvien();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_genqrcode:
                        fragment = new frm_genQRcode();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_thongke:
                        fragment = new frm_thongke(11);
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_Logout:
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
}
