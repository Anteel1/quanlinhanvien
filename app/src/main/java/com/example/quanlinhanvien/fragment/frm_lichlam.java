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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.adapter_lichlam;
import com.example.quanlinhanvien.model.lichlam;
import com.example.quanlinhanvien.model.nhanvien;
import com.example.quanlinhanvien.service.service_API;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class frm_lichlam extends Fragment {
    Button btnBack, btnNext, btnchonngay, btnthem, btnthemcuoi, btnhuycuoi;
    FloatingActionButton btnthemlichlam;
    TextView txtnv, txtcalam, txtngaylam, txtgiovao, txtgiora;
    ArrayList<lichlam> list, listll;
    ArrayList<nhanvien> listnv ;
    ArrayList<Integer> listmanv;
    ArrayList<String> lichlams;
    ListView listviewll;
    String txtten;
    TextView tvMonth, txtshowngay, txtshowca;
    Month thanght;

    int idnv, manv, macl ;
    Switch switchst;
    int yearn, monthn, dayn;
    Spinner spnchonten;
    int chucvu;
    ProgressBar progressBar;


    public frm_lichlam(int idnv, int chucvu) {
        this.idnv = idnv;
        this.chucvu = chucvu;
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
        btnthemlichlam = view.findViewById(R.id.btnthemlichlam);
        switchst = view.findViewById(R.id.switchst);

        progressBar = view.findViewById(R.id.prb_login);
        progressBar.setVisibility(View.VISIBLE);

        // get year month day
        yearn = LocalDate.now().getYear();
        monthn = LocalDate.now().getMonthValue();
        dayn = LocalDate.now().getDayOfMonth();
        
        if(chucvu ==1 || chucvu !=2){
            btnthemlichlam.setVisibility(View.GONE);
        }
        list = new ArrayList<>();
        listnv = new ArrayList<>();

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
        btnthemlichlam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                openDialog();
            }
        });
        demoCallAPI(idnv,thanght.getValue());
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
        Log.d("TAG", "Erro:"+error.toString());
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

    private void show(ArrayList<lichlam> listll) {
        adapter_lichlam adapter_lichlam = new adapter_lichlam(listll, getContext());
        listviewll.setAdapter(adapter_lichlam);
        progressBar.setVisibility(View.GONE);
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        View v2 = inflater1.inflate(R.layout.dialog_themlichlam, null);
        builder.setView(v2);
        btnchonngay = v2.findViewById(R.id.btnchonngay);
        txtshowngay = v2.findViewById(R.id.txtshowngay);
        switchst = v2.findViewById(R.id.switchst);
        txtshowca = v2.findViewById(R.id.txtshowca);
        spnchonten = v2.findViewById(R.id.spnchonten);
        Dialog dialog = builder.create();
        dialog.show();
        demoCallAPINV();
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        txtshowngay.setText(day + "-" + (month + 1) + "-" + year);
        btnthem = v2.findViewById(R.id.btnthem);
        switchst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtshowca.setText(
                        (switchst.isChecked() ? "Morning shift" : "Night shift"));
            }
        });

        btnchonngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtshowngay.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        dayn = dayOfMonth;
                        monthn = month + 1;
                        yearn = year;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        switchst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    macl = 2;
                } else macl = 1;

            }
        });
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clik1
                dialog.dismiss();
                openDialogthemll();
            }
        });
    }

    private void demoAddAPI(int idNV, int maCL, String ngaylam) {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.addLichlamnv(idNV, maCL, ngaylam)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseadd, this::handleErroradd)
        );
    }

    private void handleResponseadd(Number number) {
        if(Integer.parseInt(String.valueOf(number)) == 1){
            Toast.makeText(getContext(), "Create success !", Toast.LENGTH_SHORT).show();
            demoCallAPI(idnv,thanght.getValue());
        }
    }


    private void handleErroradd(Throwable error) {
        Log.d("TAG", "Erro:"+error);
        progressBar.setVisibility(View.GONE);
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
        lichlams = new ArrayList<>();
        listnv = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            lichlams.add(list1.get(i).getTenNV());
            listnv.add(list1.get(i));
        }
        spnchonten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manv = list1.get(position).getMaNV();
                txtten = list1.get(position).getTenNV();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lichlams);
        spnchonten.setAdapter(arrayAdapter);
        progressBar.setVisibility(View.GONE);
    }

    private void handleErrorNV(Throwable error) {
        Log.d("erro", error.toString());
        progressBar.setVisibility(View.GONE);
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

    //  dialog add lich
    private void openDialogthemll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        View v2 = inflater1.inflate(R.layout.dialog_lichlam, null);
        builder.setView(v2);
        Dialog dialog = builder.create();
        dialog.show();
        txtnv = v2.findViewById(R.id.txtnv);
        txtcalam = v2.findViewById(R.id.txtcalam);
        txtngaylam = v2.findViewById(R.id.txtngaylam);
        txtgiovao = v2.findViewById(R.id.txtgiovao);
        txtgiora = v2.findViewById(R.id.txtgiora);
        btnthemcuoi = v2.findViewById(R.id.btnthemcuoi);
        btnhuycuoi = v2.findViewById(R.id.btnhuycuoi);
        txtngaylam.setText(yearn + "-" + monthn + "-" + dayn);
        txtnv.setText(txtten);

        if (macl == 1) {
            txtcalam.setText("Morning shift");
            txtgiovao.setText("08:00:");
            txtgiora.setText("15:00");
        } else {
            txtcalam.setText("Night shift");
            txtgiovao.setText("15:00:");
            txtgiora.setText("22:00");
        }
        btnthemcuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    String date = yearn + "-" + monthn + "-" + dayn;
                    demoAddAPI(manv, macl, date);
                    dialog.dismiss();
            }

        });
        btnhuycuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


}






