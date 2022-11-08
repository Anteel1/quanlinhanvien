package com.example.quanlinhanvien.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.LoginActivity;
import com.example.quanlinhanvien.R;

public class frm_nhanvien extends Fragment {
    private   Button btnsignup,btnupdate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_nhanvien, container, false);
        btnsignup = view.findViewById(R.id.btnsignup);
        btnupdate = view.findViewById(R.id.btnupdate);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.activity_signup, null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });




        return view;
    }
}
