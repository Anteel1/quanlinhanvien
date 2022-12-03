package com.example.quanlinhanvien;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quanlinhanvien.fragment.frm_attendance;
import com.example.quanlinhanvien.fragment.frm_nhanvien;
import com.example.quanlinhanvien.fragment.frm_trangchu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
//    TextView tv;
//    ImageView iv_menu;
//    DrawerLayout drawerLayout;
//    Toolbar toolbar;
//    NavigationView navigationView;
    Fragment fragment;
//    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FragmentContainerView fragmentContainerView = findViewById(R.id.fragment_view);

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
////                drawerLayout.closeDrawer(navigationView);
//                setTitle(item.getTitle());
//                return false;
//            }
//        });

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_view, new frm_trangchu()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new frm_trangchu();
                        break;

                    case R.id.scan:
                        fragment = new frm_attendance();
                        break;

                    case R.id.me:
                        fragment = new frm_nhanvien();
                        break;

                    default:

                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view, fragment).commit();
                return true;
            }
        });

    }


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
