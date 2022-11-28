package com.example.quanlinhanvien.fragment;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.adapter_calendar;
import com.example.quanlinhanvien.model.luong;
import com.example.quanlinhanvien.model.ngaylam;
import com.example.quanlinhanvien.service.service_API;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frm_thongke extends Fragment {
    public frm_thongke(int idNV) {
        this.idNV = idNV;
    }

    adapter_calendar adapter;
    RecyclerView calendarRecyclerView;
    TextView month;
    LocalDate selectedDate;
    Button btnback, btnnext;
    TextView txtSalary;
    ArrayList<String>dayCompare;
    int idNV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_thongke, container, false);
        month = view.findViewById(R.id.tvMonth);
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        btnback = view.findViewById(R.id.btnBack);
        btnnext = view.findViewById(R.id.btnNext);
        txtSalary = view.findViewById(R.id.txtTienLuong);
        selectedDate = LocalDate.now();
        dayCompare = new ArrayList<>();
        loadData();
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMonthAction();
                loadData();
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMonthAction();
                loadData();
            }
        });
//        ArrayList<String>listDate = new ArrayList<>();
//        listDate.add(0,"2022-11-21");
//        listDate.add(1,"2022-11-24");
//        int i =0;
//        for(String date : listDate){
//                LocalDate date1 = LocalDate.parse(date);
//                int day = date1.getDayOfMonth();
//                int year = date1.getYear();
//                int month = date1.getDayOfMonth();
//                Log.d("day",year+" "+month+" "+day);
//
//                i++;
//        }
        return view;
    }
    private void loadData()
    {
        demoCallAPI_ngaylam();
        demoCallAPILuong();
        month.setText((selectedDate).getMonth()+" "+(selectedDate).getYear());
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        adapter = new adapter_calendar(getContext(),daysInMonth,dayCompare);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(adapter);
    }

    // list ngay trong thang
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    public void previousMonthAction() {
        selectedDate = selectedDate.minusMonths(1);

    }

    public void nextMonthAction() {
        selectedDate = selectedDate.plusMonths(1);

    }

    private void demoCallAPILuong() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getLuong(idNV, selectedDate.getMonth().getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(luong luong) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        Log.d("luong infor", luong.getTonggiolam() + " " + luong.getTongLuong());
        txtSalary.setText("Salary: " + luong.getTongLuong() + "00 VND");
    }

    private void handleError(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }
    private void demoCallAPI_ngaylam() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getNgayLam(idNV,selectedDate.getMonth().getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse_ngaylam, this::handleError_ngayLam)
        );
    }

    private void handleResponse_ngaylam(ArrayList<ngaylam>list) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        dayCompare.clear();
        for(int i = 0;i < list.size(); i++){
            dayCompare.add(i,list.get(i).getNgaylam());
        }
        adapter.notifyDataSetChanged();
        Log.d("Ngày làm:"," "+dayCompare.size());
    }

    private void handleError_ngayLam(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }
}
