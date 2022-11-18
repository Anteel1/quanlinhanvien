package com.example.quanlinhanvien.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.quanlinhanvien.LoginActivity;
import com.example.quanlinhanvien.R;

public class frm_dangxuat extends DialogFragment {
    Button dialogYes;
    Button dialogNo;
    Dialog dialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.frm_dangxuat);
        dialogYes = dialog.findViewById(R.id.btnYes);
        dialogNo = dialog.findViewById(R.id.btnNo);
        dialogYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        dialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static String TAG = "Exit";
}

