package com.example.quanlinhanvien.fragment;

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

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class frm_trangchu extends Fragment {
    TextClock tc_gio;
    TextView tc_ngay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_trangchu, container, false);

//        im.animate().translationY(-1100).setDuration(800).setStartDelay(600).translationX(-200);
//        gio.animate().translationY(-250).setDuration(800).setStartDelay(600).translationX(-90);

        tc_gio = view.findViewById(R.id.tc_gio);
        tc_ngay = view.findViewById(R.id.tc_ngay);

        Date date = Calendar.getInstance().getTime();
        String[] date2 = String.valueOf(date).split(" ");

        tc_gio.setFormat12Hour("hh:mm a");
        tc_ngay.setText(date2[0] + ", " + date2[1] + " " + date2[2]);
        Log.d("TAG", "onCreateView: " + date);
//        tc_ngay.setText(LocalDate.now().getDayOfMonth()+", "+LocalDate.now().getDayOfWeek()+", "
//        +LocalDate.now().getMonth()+", "+LocalDate.now().getYear());

        return view;
    }
}
