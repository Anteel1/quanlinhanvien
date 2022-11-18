package com.example.quanlinhanvien.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlinhanvien.fragment.frm_QRcode;
import com.example.quanlinhanvien.fragment.frm_ThongTin;
import com.example.quanlinhanvien.fragment.frm_ViTri;

public class ViewPager2_1_Adapter extends FragmentStateAdapter {
    public ViewPager2_1_Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new frm_QRcode();
            case 1:
                return new frm_ThongTin();
            case 2:
                return new frm_ViTri();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
