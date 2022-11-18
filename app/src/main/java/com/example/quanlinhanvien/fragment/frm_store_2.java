package com.example.quanlinhanvien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.ViewPager2_1_Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class frm_store_2 extends Fragment {
    int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_store_2, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2);

        viewPager2.setAdapter(new ViewPager2_1_Adapter(getActivity()));
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("QR Code");
                        break;

                    case 1:
                        tab.setText("Thông tin");
                        break;

                    case 2:
                        tab.setText("Vị trí");
                        break;

                    default:

                        break;
                }
            }
        }).attach();
        return view;
    }

    public frm_store_2(int position){
        this.position = position;
    }
}
