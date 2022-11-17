package com.example.quanlinhanvien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.R;

public class frm_trangchu extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_trangchu, container, false);
        ImageView im = view.findViewById(R.id.image);
        TextView gio = view.findViewById(R.id.giohienthi);



//        im.animate().translationY(-1100).setDuration(800).setStartDelay(600).translationX(-200);
//        gio.animate().translationY(-250).setDuration(800).setStartDelay(600).translationX(-90);

        return view;
    }
}
