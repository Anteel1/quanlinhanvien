package com.example.quanlinhanvien.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlinhanvien.fragment.frm_genQRcode;
import com.example.quanlinhanvien.fragment.frm_map;
import com.example.quanlinhanvien.fragment.frm_storeDetail;

public class adapter_viewpager extends FragmentStateAdapter {
    public adapter_viewpager(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new frm_storeDetail();
            case 1:
                return new frm_genQRcode();
            case 2:
                return new frm_map();
            default:
                return new frm_storeDetail();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
