package com.example.quanlinhanvien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.model.nhanvien;

public class frm_me extends Fragment {
    TextView tvTenNV, tvEmail;
    nhanvien nhanvien;

    public frm_me(nhanvien nhanvien) {
        this.nhanvien = nhanvien;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_me, container, false);

        tvTenNV = view.findViewById(R.id.tvTenNV);
        tvEmail = view.findViewById(R.id.tvEmail);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTenNV.setText(nhanvien.getTenNV());
        tvEmail.setText(nhanvien.getTaiKhoan());
    }
}
