package com.example.quanlinhanvien.fragment;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.adapter_nhanvien;
import com.example.quanlinhanvien.model_api.nhanvien;
import com.example.quanlinhanvien.service.ItemClickListener;
import com.example.quanlinhanvien.service.service_API;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frm_nhanvien extends Fragment implements  ItemClickListener {
    ArrayList<String> list;
    ArrayList<nhanvien> listNV;
    RecyclerView recyclerView;
    TextView txtTotal, txtTitle;
    adapter_nhanvien adapter_nhanvien;
    String ngay;
    TextInputEditText txtName, txtNameUser, txtPassword, txtMaCV, txtImei, txtMaNV;
    Button btnsignup, btnUpdate, btnDangky;
    int maNV;
    HashMap<String, Integer> id = new HashMap<String, Integer>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_nhanvien, container, false);

        txtTotal = view.findViewById(R.id.totalListNV);
        recyclerView = view.findViewById(R.id.rcv_nhanvien);
        btnsignup = view.findViewById(R.id.btnsignup);
        btnUpdate = view.findViewById(R.id.btnupdate);

        list = new ArrayList<>();
        listNV = new ArrayList<>();
        loaddata();
        demoCallAPI();

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(1, -1);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(2, maNV);
            }
        });




        return view;
    }

    private void loaddata() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter_nhanvien = new adapter_nhanvien(getContext(), list, listNV, this);
        recyclerView.setAdapter(adapter_nhanvien);


    }

    private void demoCallAPI() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getModelAPI()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<nhanvien> list1) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data

        for (int i = 0; i < list1.size(); i++) {
            list.add(i, list1.get(i).getTenNV());
            nhanvien nv = list1.get(i);
            listNV.add(list1.get(i));
        }
        Log.d("========TAG", "handleResponse: "+listNV.size());
        adapter_nhanvien.notifyDataSetChanged();
        txtTotal.setText("Total employees: " + list1.size());
    }

    private void handleError(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

//    private void showDatePicker() {
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                getContext(), this, Calendar.getInstance().get(Calendar.YEAR),
//                Calendar.getInstance().get(Calendar.MONTH),
//                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
//        );
//        datePickerDialog.show();
//    }
//
//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        ngay = dayOfMonth + "-" + month + "-" + year;
////        txtCreateDate.setText(ngay);
//    }

    private void openDialog(int type, int manv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        View v2 = inflater1.inflate(R.layout.dialog_signup, null);
        builder.setView(v2);
        Dialog dialog = builder.create();
        dialog.show();
        txtTitle = v2.findViewById(R.id.txtTitle);
        txtName = v2.findViewById(R.id.txtName);
        txtNameUser = v2.findViewById(R.id.txtNameUser);
        txtPassword = v2.findViewById(R.id.txtPassword);
        txtMaNV = v2.findViewById(R.id.txtMaNV);
        txtImei = v2.findViewById(R.id.txtImei);
        txtMaCV = v2.findViewById(R.id.txtMaCV);
        btnDangky = v2.findViewById(R.id.btndangky);
        if (type == 1) {
            txtImei.setVisibility(View.GONE);
            txtMaCV.setVisibility(View.GONE);
            txtPassword.setVisibility(View.GONE);
            txtMaNV.setVisibility(View.GONE);
            btnDangky.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    demoCallAPIaddNV(txtName.getText().toString(), txtNameUser.getText().toString());
                }
            });
        } else {
            txtTitle.setText("Update Profile");
            btnDangky.setText("Update");
            txtMaNV.setVisibility(View.GONE);
            txtPassword.setText(listNV.get(manv).getMatKhau());
            txtNameUser.setText(listNV.get(manv).getTaiKhoan());
            txtMaCV.setText(listNV.get(manv).getMaCV()+"");
            txtImei.setText(listNV.get(manv).getImei());
            txtName.setText(listNV.get(manv).getTenNV());

            txtMaNV.setText(""+manv);
            btnDangky.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    demoCallAPIupdateNV(listNV.get(manv).getMaNV(), txtName.getText().toString(),
                            txtNameUser.getText().toString(), txtPassword.getText().toString(),
                            txtImei.getText().toString(), Integer.parseInt(txtMaCV.getText().toString()));
                }
            });
            Toast.makeText(getContext(), "Cập nhật", Toast.LENGTH_SHORT).show();
        }

    }

    private void demoCallAPIaddNV(String tenNV, String taikhoan) {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.addnV(tenNV, taikhoan)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseAddNV, this::handleErrorAddNV)
        );
    }


    private void handleResponseAddNV(Number number) {
        Toast.makeText(getContext(), "add nhân viên thành công", Toast.LENGTH_SHORT).show();
    }

    private void handleErrorAddNV(Throwable throwable) {
    }

    private void demoCallAPIupdateNV(int maNV, String TenNV, String TaiKhoan, String MatKhau, String Imei, int MaCV) {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.updateNV(maNV, TenNV, TaiKhoan, MatKhau, Imei, MaCV)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseUpdateNV, this::handleErrorUpdateNV)
        );
    }

    private void handleResponseUpdateNV(Number number) {
        Toast.makeText(getContext(), "update thành công ", Toast.LENGTH_SHORT).show();
    }

    private void handleErrorUpdateNV(Throwable throwable) {
        Log.d("=============TAG", "handleErrorUpdateNV: " + throwable);
        Toast.makeText(getContext(), "update thất bại " + throwable, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(int pos) {
        id.put("id", pos);
        maNV =id.get("id");

        Toast.makeText(getContext(), "đã chọn ", Toast.LENGTH_SHORT).show();

    }
}
