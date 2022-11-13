package com.example.quanlinhanvien;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.quanlinhanvien.fragment.frm_dangkylichlam;
import com.example.quanlinhanvien.fragment.frm_diemdanh;
import com.example.quanlinhanvien.fragment.frm_nhanvien;
import com.example.quanlinhanvien.fragment.frm_thongke;
import com.example.quanlinhanvien.fragment.frm_trangchu;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class bottomActivity extends AppCompatActivity {

    MeowBottomNavigation bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        bnv = findViewById(R.id.meowbottom);
        bnv.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        bnv.add(new MeowBottomNavigation.Model(2, R.drawable.ic_person));
        bnv.add(new MeowBottomNavigation.Model(3, R.drawable.ic_email));
        bnv.add(new MeowBottomNavigation.Model(4, R.drawable.ic_lock));

        bnv.show(1, true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame, new frm_trangchu())
                .commit();
        bnv.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Fragment fragment;


                switch (model.getId()) {
                    case 1:
                        replace(new frm_trangchu());

                        break;
                    case 2:
                        replace(new frm_thongke());


                        break;
                    case 3:
                        replace( new frm_dangkylichlam());

                        break;
                    case 4:
                        replace( new frm_nhanvien());

                        break;
                    default:
                        replace(new frm_trangchu());


                }

                fragmentManager
                        .beginTransaction()
                        .commit();

                return null;
            }
        });

    }
    public void replace(Fragment fragment){

        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).commit();


    }
}