package com.example.quanlinhanvien.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.LoginActivity;
import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.model.nhanvien;

public class frm_me extends Fragment {
    TextView tvTenNV, tvEmail;
    nhanvien nhanvien;
    CardView cvStatistics, cvShift, cvAttendance, cvLogout;

    public frm_me(nhanvien nhanvien) {
        this.nhanvien = nhanvien;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_me, container, false);

        tvTenNV = view.findViewById(R.id.tvTenNV);
        tvEmail = view.findViewById(R.id.tvEmail);
        cvStatistics = view.findViewById(R.id.cvStatistics);
        cvShift = view.findViewById(R.id.cvShift);
        cvAttendance = view.findViewById(R.id.cvAttendance);
        cvLogout = view.findViewById(R.id.cvLogout);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTenNV.setText(nhanvien.getTenNV());
        tvEmail.setText(nhanvien.getTaiKhoan());

        cvStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("statistics");
                getContext().sendBroadcast(intent);
            }
        });

        cvShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("shift");
                getContext().sendBroadcast(intent);
            }
        });

        cvAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("attendance");
                getContext().sendBroadcast(intent);
            }
        });

        cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v = getLayoutInflater().inflate(R.layout.frm_dangxuat, null);
                Button btnYes = v.findViewById(R.id.btnYes);
                Button btnNo = v.findViewById(R.id.btnNo);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(v);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                btnYes.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                btnNo.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                    }
                });

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }
}
