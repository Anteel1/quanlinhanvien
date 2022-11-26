package com.example.quanlinhanvien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.adapter_calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class frm_thongke extends Fragment {
    adapter_calendar adapter;
    RecyclerView calendarRecyclerView;
    TextView month;
    LocalDate selectedDate;
    Button btnback,btnnext;
    TextView txtSalary;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_thongke, container, false);
        month = view.findViewById(R.id.tvMonth);
        calendarRecyclerView= view.findViewById(R.id.calendarRecyclerView);
        btnback = view.findViewById(R.id.btnBack);
        btnnext = view.findViewById(R.id.btnNext);
        txtSalary= view.findViewById(R.id.txtTienLuong);
        txtSalary.setText("Salary: +2000000 VND");
        selectedDate = LocalDate.now();
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
        month.setText((selectedDate).getMonth()+" "+(selectedDate).getYear());
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        adapter = new adapter_calendar(getContext(),daysInMonth);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(adapter);
    }
    // list ngay trong thang
    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }
    public void previousMonthAction()
    {
        selectedDate = selectedDate.minusMonths(1);

    }

    public void nextMonthAction()
    {
        selectedDate = selectedDate.plusMonths(1);

    }


}
