package com.example.quanlinhanvien.fragment;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.adapter_lichlam;
import com.example.quanlinhanvien.model.lichlam;
import com.example.quanlinhanvien.model.nhanvien;
import com.example.quanlinhanvien.service.service_API;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class frm_lichlam extends Fragment {
    Button btnBack, btnNext,btnthemlichlam,btnchonngay,btnthem;
    ArrayList<lichlam> list;
    ArrayList<nhanvien> listnv;
    ArrayList<Integer> listmanv;
    ArrayList<String> lichlams;
    ListView listviewll;
    TextView tvMonth,txtshowngay,txtshowca;
    Month thanght;
    int idnv,manv,po;
    Switch switchst;
    int yearn,monthn,dayn;
    Spinner spnchonten;




    public frm_lichlam(int idnv) {
        this.idnv = idnv;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_lichlam, container, false);
        tvMonth = view.findViewById(R.id.tvMonth);
        thanght = LocalDate.now().getMonth();
        tvMonth.setText(thanght + " " + LocalDate.now().getYear());
        listviewll = view.findViewById(R.id.listviewll);
        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnNext);
        btnthemlichlam= view.findViewById(R.id.btnthemlichlam);
        switchst = view.findViewById(R.id.switchst);
        if (idnv!=8){  btnthemlichlam.setVisibility(View.GONE); }







        list = new ArrayList<>();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thangtruoc();
                tvMonth.setText(thanght + " " + LocalDate.now().getYear());
                demoCallAPI(idnv, thanght.getValue());
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thangsau();
                tvMonth.setText(thanght + " " + LocalDate.now().getYear());
                demoCallAPI(idnv, thanght.getValue());
            }
        });
        demoCallAPI(idnv, LocalDate.now().getMonth().getValue());
        btnthemlichlam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        return view;
    }

    private void thangtruoc() {
        thanght = thanght.minus(1);
    }

    private void thangsau() {
        thanght = thanght.plus(1);
    }

    private void demoCallAPI(int maNV, int thang) {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getLichlamnv(maNV, thang)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<lichlam> lichlams) {
        list.clear();
        for (int i = 0; i < lichlams.size(); i++) {
            list.add(lichlams.get(i));
        }

        show(list);

    }


    private void handleError(Throwable error) {
        Log.d("TAG", "loi_________________");
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

    private void show(ArrayList<lichlam> listll) {
        adapter_lichlam adapter_lichlam = new adapter_lichlam(listll, getContext());
        listviewll.setAdapter(adapter_lichlam);


    }

    private void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater1 =getActivity().getLayoutInflater();
        View v2 =inflater1.inflate(R.layout.dialog_themlichlam,null);
        builder.setView(v2);
        btnchonngay=v2.findViewById(R.id.btnchonngay);
        txtshowngay=v2.findViewById(R.id.txtshowngay);
        switchst=v2.findViewById(R.id.switchst);
        txtshowca=v2.findViewById(R.id.txtshowca);
        spnchonten=v2.findViewById(R.id.spnchonten);

         int calam = 1;
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        txtshowngay.setText(day + "-" + (month+1) + "-" + year);
        btnthem = v2.findViewById(R.id.btnthem);
        demoCallAPINV();






        switchst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                txtshowca.setText(
                        (switchst.isChecked() ? "Ca Tối" : "Ca sáng"));
            //    boolean val =   remember switchst.isChecked();

            }
        });

        btnchonngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtshowngay.setText(dayOfMonth + "-" + (month+1) + "-" + year);
                        dayn= dayOfMonth;
                        monthn = month+1;
                        yearn=year;
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date=yearn+"-"+monthn+"-"+dayn;
                Toast.makeText(getContext(), ""+date, Toast.LENGTH_SHORT).show();
                demoAddAPI(manv,1,date);
                demoCallAPINV();


//                if(spnchonten.getSelectedItem().equals( listnv.get(i).getTenNV()))  {
//                    manv=listnv.get(i).getMaNV();
//                }


//
//                if ( ){
//                    calam= 1;
//                }
//                else {
//                    calam =2;}
            }
        });
        Dialog dialog = builder.create();
        dialog.show();

    }
    private void demoAddAPI(int idNV, int maCL, String ngaylam) {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.addLichlamnv(idNV, maCL,ngaylam)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseadd, this::handleErroradd)
        );
    }

    private void handleResponseadd(Number number) {


    }


    private void handleErroradd(Throwable error) {
        Log.d("TAG", "loi2_________________");
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }
    private void demoCallAPINV() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getModelAPI()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseNV, this::handleErrorNV)
        );
    }

    private void handleResponseNV(ArrayList<nhanvien> list1) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        lichlams = new ArrayList<String>();
        for(int i =0; i <list1.size(); i++){
            lichlams.add(list1.get(i).getTenNV());
         //   listmanv.add(list1.get(i).getMaNV());

         //   listnv.add(list1.get(i));
        }
        spnchonten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manv=list1.get(position).getMaNV();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,lichlams);
        spnchonten.setAdapter(arrayAdapter);



    }

    private void handleErrorNV(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }



    }






