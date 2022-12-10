package com.example.quanlinhanvien.fragment;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.adapter_maCV;
import com.example.quanlinhanvien.adapter.adapter_nhanvien;
import com.example.quanlinhanvien.model.nhanvien;
import com.example.quanlinhanvien.service.ItemClickListener;
import com.example.quanlinhanvien.service.service_API;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

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
    TextInputEditText txtName, txtNameUser, txtPassword, txtImei, txtMaNV;
    Spinner txtMaCV;
    Button btnsignup, btnUpdate, btnDangky;
    int maNV;
    int position;
    int maCV;

    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_nhanvien, container, false);

        txtTotal = view.findViewById(R.id.totalListNV);
        recyclerView = view.findViewById(R.id.rcv_nhanvien);
        btnsignup = view.findViewById(R.id.btnsignup);
        btnUpdate = view.findViewById(R.id.btnupdate);

        progressBar = view.findViewById(R.id.prb_login);
        progressBar.setVisibility(View.VISIBLE);

        list = new ArrayList<>();
        listNV = new ArrayList<>();
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

        adapter_nhanvien = new adapter_nhanvien(getContext(), listNV, this);
        recyclerView.setAdapter(adapter_nhanvien);
        progressBar.setVisibility(View.GONE);
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
        listNV.clear();
        for (int i = 0; i < list1.size(); i++) {
            listNV.add(list1.get(i));
        }
        Log.d("========TAG", "handleResponse: "+listNV.size());
        txtTotal.setText("Total employees: " + list1.size());
        loaddata();
    }

    private void handleError(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

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

        // spinner
        String[] dataSpinner = {"Employee","Manager","Retire"};
        adapter_maCV adapter_maCV = new adapter_maCV(getContext(),dataSpinner);
        txtMaCV.setAdapter(adapter_maCV);

        if (type == 1) {
            txtImei.setVisibility(View.GONE);
            txtMaCV.setVisibility(View.GONE);
            txtPassword.setVisibility(View.GONE);
            txtMaNV.setVisibility(View.GONE);
            btnDangky.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    demoCallAPIaddNV(txtName.getText().toString(), txtNameUser.getText().toString());
                    dialog.dismiss();
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        } else {
            txtTitle.setText("Update Profile");
            btnDangky.setText("Update");
            txtMaNV.setVisibility(View.GONE);
            txtPassword.setText(listNV.get(position).getMatKhau());
            txtNameUser.setText(listNV.get(position).getTaiKhoan());
            txtMaCV.setSelection(listNV.get(position).getMaCV()-1);
            txtImei.setText(listNV.get(position).getImei());
            txtName.setText(listNV.get(position).getTenNV());

            btnDangky.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (txtMaCV.getSelectedItem().equals("Employee")){
                        maCV = 1;
                    }else if(txtMaCV.getSelectedItem().equals("Manager")){
                        maCV =2;
                    }else{
                        maCV =4;
                    }

                    demoCallAPIupdateNV(maNV, txtName.getText().toString(),
                            txtNameUser.getText().toString(), txtPassword.getText().toString(),
                            txtImei.getText().toString(), maCV);
                    dialog.dismiss();
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
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
        if(Integer.parseInt(String.valueOf(number)) >=1){
            Toast.makeText(getContext(), "Sign up success ! ", Toast.LENGTH_SHORT).show();
            demoCallAPI();
        }else{
            Toast.makeText(getContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
        }

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
        Toast.makeText(getContext(), "Update success ! ", Toast.LENGTH_SHORT).show();
        demoCallAPI();
    }

    private void handleErrorUpdateNV(Throwable throwable) {
        Log.d("=============TAG", "handleErrorUpdateNV: " + throwable);
        Toast.makeText(getContext(), "Update failed !" + throwable, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(int pos) {
        position = pos;
        maNV= listNV.get(pos).getMaNV();
    }
}
