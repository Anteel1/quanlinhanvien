package com.example.quanlinhanvien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.adapter_viewpager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class frm_store extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_store, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager);

        adapter_viewpager adapter = new adapter_viewpager(this);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: tab.setText("Info");break;
                    case 1: tab.setText("QR");break;
                    case 2 :tab.setText("Map");break;
                    case 3 :tab.setText("NFC");break;
                }
            }
        }).attach();

        return view;
    }
}
