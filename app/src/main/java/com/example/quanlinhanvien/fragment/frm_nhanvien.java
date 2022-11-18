package com.example.quanlinhanvien.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.adapter_nhanvien;

import java.util.ArrayList;

public class frm_nhanvien extends Fragment {
    EditText edtname, edtusername, edtpassword, edtphonenum, edtaddress, edtid;
    TextView txtva_name, tv_validate_username, tv_validate_password, tv_validate_phone_number, tv_validate_address, tv_validate_id;
    private Button btnsignup, btnupdate, btndangky, btnthaydoi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_nhanvien, container, false);
        btnsignup = view.findViewById(R.id.btnsignup);
        btnupdate = view.findViewById(R.id.btnupdate);
        //
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_signup, null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();
                edtname = view1.findViewById(R.id.edtname);
                txtva_name = view1.findViewById(R.id.tv_validate_name);
                //
                edtusername = view1.findViewById(R.id.edtusername);
                tv_validate_username = view1.findViewById(R.id.tv_validate_username);
                //
                edtpassword = view1.findViewById(R.id.edtpassword);
                tv_validate_password = view1.findViewById(R.id.tv_validate_password);
                //
                edtaddress = view1.findViewById(R.id.edtaddress);
                tv_validate_address = view1.findViewById(R.id.tv_validate_address);
                //
                edtphonenum = view1.findViewById(R.id.edtphonenum);
                tv_validate_phone_number = view1.findViewById(R.id.tv_validate_phone_number);
                //
                edtid = view1.findViewById(R.id.edtid);
                tv_validate_id = view1.findViewById(R.id.tv_validate_id);
                btndangky = view1.findViewById(R.id.btndangky);
                btndangky.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kiemtra();

                    }
                });


            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view2 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update, null);
                builder.setView(view2);
                Dialog dialog = builder.create();
                dialog.show();
                edtname = view2.findViewById(R.id.edtname);
                txtva_name = view2.findViewById(R.id.tv_validate_name);
                //
                edtusername = view2.findViewById(R.id.edtusername);
                tv_validate_username = view2.findViewById(R.id.tv_validate_username);
                //
                edtpassword = view2.findViewById(R.id.edtpassword);
                tv_validate_password = view2.findViewById(R.id.tv_validate_password);
                //
                edtaddress = view2.findViewById(R.id.edtaddress);
                tv_validate_address = view2.findViewById(R.id.tv_validate_address);
                //
                edtphonenum = view2.findViewById(R.id.edtphonenum);
                tv_validate_phone_number = view2.findViewById(R.id.tv_validate_phone_number);
                //
                edtid = view2.findViewById(R.id.edtid);
                tv_validate_id = view2.findViewById(R.id.tv_validate_id);
                btnthaydoi = view2.findViewById(R.id.btnthaydoi);
                btnthaydoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kiemtra();
                    }
                });

            }
        });






        return view;
    }

    public void kiemtra() {
        if (edtname.getText().toString().equals("")) {
            txtva_name.setText("Không được để trống");
        }
        //
        if (edtusername.getText().toString().equals("")) {
            tv_validate_username.setText("Không được để trống");
        }
        //
        if (edtpassword.getText().toString().equals("")) {
            tv_validate_password.setText("Không được để trống");
        }
        //
        if (edtphonenum.getText().toString().equals("")) {
            tv_validate_phone_number.setText("Không được để trống");
        }
        //
        if (edtaddress.getText().toString().equals("")) {
            tv_validate_address.setText("Không được để trống");
        }
        //
        if (edtid.getText().toString().equals("")) {
            tv_validate_id.setText("Không được để trống");
        }


    }
}
