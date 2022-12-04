package com.example.quanlinhanvien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class frm_trangchu extends Fragment {
    TextClock tc_gio;
    TextView tv_ngay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_trangchu, container, false);

//        im.animate().translationY(-1100).setDuration(800).setStartDelay(600).translationX(-200);
//        gio.animate().translationY(-250).setDuration(800).setStartDelay(600).translationX(-90);

        tc_gio = view.findViewById(R.id.tc_gio);
        tv_ngay = view.findViewById(R.id.tv_ngay);

        Date date = Calendar.getInstance().getTime();
        String[] date2 = String.valueOf(date).split(" ");

        Log.d("TAG", "onCreateView: " + date);

        tc_gio.setFormat12Hour("hh:mm a");
        tv_ngay.setText(date2[0] + " " + date2[1] + " " + date2[2]);

        return view;
    }
}
