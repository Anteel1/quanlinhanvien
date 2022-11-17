package com.example.quanlinhanvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlinhanvien.fragment.frm_dangkylichlam;
import com.example.quanlinhanvien.fragment.frm_dangxuat;
import com.example.quanlinhanvien.fragment.frm_diemdanh;
import com.example.quanlinhanvien.fragment.frm_nhanvien;
import com.example.quanlinhanvien.fragment.frm_thaydoi_lichlam;
import com.example.quanlinhanvien.fragment.frm_thongke;
import com.example.quanlinhanvien.fragment.frm_trangchu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    ImageView iv_menu;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView ngayht,txttennv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //  ngayht = findViewById(R.id.ngayhienthi);
        //  ImageButton nen = findViewById(R.id.nen);
        anhxa();

//        xuly_toolbar();
        menu_nav();
        clicknavigation();

        //hien thi ngay
//        ngayht();
        //

    }
    //



    //    public void ngayht() {
//        String thuchu = "vv";
//        String thangchu = "vv";
//
//        Calendar n = Calendar.getInstance();
//        int year = n.get(Calendar.YEAR);
//        int month = n.get(Calendar.MONTH);
//        int day = n.get(Calendar.DAY_OF_MONTH);
//        ngayht.setText("Day"+day+",Month"+month+" Year "+year);
//
//       //
//
//        }

    public void anhxa() {
        toolbar = findViewById(R.id.toolbar);
        iv_menu = findViewById(R.id.iv_menu_toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        txttennv =findViewById(R.id.txttennv);
    }

    public void menu_nav() {
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });
    }

    private void clicknavigation() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        frm_trangchu frm_trangchu = new frm_trangchu();
        fragmentManager
                .beginTransaction()
                .replace(R.id.linear, frm_trangchu)
                .commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;

                switch (item.getItemId()) {
                    case R.id.menu_home:
                        fragment = new frm_trangchu();
                        break;
                    case R.id.menu_diemdanh:
                        fragment = new frm_diemdanh();
                        break;
                    case R.id.menu_dk_lichlam:
                        fragment = new frm_dangkylichlam();
                        break;
                    case R.id.menu_nhanvien:
                        fragment = new frm_nhanvien();
                        break;
                    case R.id.menu_thaydoilichlam:
                        fragment = new frm_thaydoi_lichlam();
                        break;
                    case R.id.menu_thongke:
                        fragment = new frm_thongke();
                        break;
                    case R.id.menu_Logout:
                        fragment = new frm_dangxuat();
                        break;
                    default:
                        fragment = new frm_trangchu();
                        break;
                }

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.linear, fragment)
                        .commit();

                drawerLayout.closeDrawer(navigationView);
                setTitle(item.getTitle());

                return false;
            }
        });
    }
}
