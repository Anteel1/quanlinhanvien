package com.example.quanlinhanvien;

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
import androidx.fragment.app.FragmentManager;

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
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        anhxa();
        menu_nav();
        clicknavigation();


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

    private void clicknavigation() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager
                .beginTransaction()
                .replace(R.id.linear, new frm_trangchu())
                .commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


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
                        new frm_dangxuat().show(getSupportFragmentManager(),frm_dangxuat.TAG);
//                        LayoutInflater inflater = getLayoutInflater();
//                        View v = inflater.inflate(R.layout.frm_dangxuat,null);
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                        builder.setView(v);
//                        builder.show();
//
                        break;
                    default:
                        fragment = new frm_trangchu();
                        break;
                }

//                fragmentManager
//                        .beginTransaction()
//                        .replace(R.id.linear, fragment)
//                        .commit();

                drawerLayout.closeDrawer(navigationView);
                setTitle(item.getTitle());

                return false;
            }
        });
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.linear, fragment).commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.linear, fragment).commit();
        }
    }
}
