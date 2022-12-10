package com.example.quanlinhanvien.fragment;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frm_thongke extends Fragment implements OnChartValueSelectedListener {
    public frm_thongke(int idNV) {
        this.idNV = idNV;
    }
    ArrayList<ngaylam>listNgaylam;
    adapter_calendar adapter;
    RecyclerView calendarRecyclerView;
    TextView month;
    LocalDate selectedDate;
    Button btnback, btnnext;
    TextView txtSalary;
    float[] trangthai;
    Double luongTong;
    int idNV;
    PieChart mChart;
    float[] yData ;

    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_thongke, container, false);
        month = view.findViewById(R.id.tvMonth);
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        btnback = view.findViewById(R.id.btnBack);
        btnnext = view.findViewById(R.id.btnNext);
        txtSalary = view.findViewById(R.id.txtTienLuong);

        progressBar = view.findViewById(R.id.prb_login);
        progressBar.setVisibility(View.VISIBLE);
        // chart
        mChart = view.findViewById(R.id.piechart);
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("Thống kê");
        mChart.setCenterTextSize(10);
        mChart.setDrawEntryLabels(true);
        mChart.setOnChartValueSelectedListener(this);

        selectedDate = LocalDate.now();
        listNgaylam = new ArrayList<>();
        loadData();
        btnback.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnnext.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                previousMonthAction();
                loadData();
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
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
        luongTong = 0.0;
        demoCallAPI();
        demoCallAPI_luongDungGio();
        demoCallAPI_luongTreGio();
        txtSalary.setText("Salary:"+luongTong+"00VND");
        month.setText((selectedDate).getMonth()+" "+(selectedDate).getYear());
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        adapter = new adapter_calendar(getContext(),daysInMonth,listNgaylam);
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
    private void demoCallAPI() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getNgayLam(idNV,selectedDate.getMonth().getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<ngaylam> list1) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        listNgaylam.clear();
        int dungGio = 0;
        int treGio = 0;
        int dangLam =0;
        for(int i =0; i <list1.size(); i++){
            listNgaylam.add(i,list1.get(i));
            Integer integer = list1.get(i).getTrangthai();
            if(integer !=null){
                if(integer ==1){
                    dungGio++;
                }else if(integer == 2){
                    treGio++;
                }else{
                    dangLam++;
                }
            }
        }
        trangthai = new float[]{dungGio,treGio,dangLam};
        addDataSet(mChart);
        adapter.notifyDataSetChanged();
        Log.d("size:"," "+list1.size());
    }

    private void handleError(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

    private void demoCallAPI_luongTreGio() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getLuongTreGio(idNV,selectedDate.getMonth().getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse_treGio, this::handleError_treGio)
        );
    }

    private void handleResponse_treGio(luong luong) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        luongTong += luong.getTienLuong();
        txtSalary.setText("Salary:"+luongTong+"00NVD");
    }

    private void handleError_treGio(Throwable error) {
        Log.d("erro-tre gio :", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

    private void demoCallAPI_luongDungGio() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getLuongDungGio(idNV,selectedDate.getMonth().getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse_dungGio, this::handleError_dungGio)
        );
    }

    private void handleResponse_dungGio(luong luong) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        luongTong += luong.getTienLuong();
        txtSalary.setText("Salary:"+luongTong+"00NVD");
    }

    private void handleError_dungGio(Throwable error) {
        Log.d("erro-dung gio :", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }


    // pie chart
    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
    private void addDataSet(PieChart pieChart) {
        ArrayList<PieEntry> entrys = new ArrayList<>();
        yData = trangthai;
        String[] xData = { "Ontime", "Late", "Working" };
        for (int i = 0; i < yData.length;i++){
                entrys.add(new PieEntry(yData[i], xData[i]));
        }
        PieDataSet pieDataSet=new PieDataSet(entrys," ");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setValueTextSize(12);
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.parseColor("#73AB6B"));
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        pieDataSet.setColors(colors);
        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
        progressBar.setVisibility(View.GONE);
    }
}
