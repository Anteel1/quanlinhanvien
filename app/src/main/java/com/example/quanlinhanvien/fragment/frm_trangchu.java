package com.example.quanlinhanvien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.R;

public class frm_trangchu extends Fragment {
    TextClock tc_gio, tc_ngay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_trangchu, container, false);

        tc_gio = view.findViewById(R.id.tc_gio);
        tc_ngay = view.findViewById(R.id.tc_ngay);

        tc_gio.setFormat12Hour("hh:mma ");
        tc_ngay.setFormat12Hour("E, d-M-yyyy ");



        return view;
    }
}
