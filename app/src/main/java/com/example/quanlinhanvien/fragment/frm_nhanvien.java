package com.example.quanlinhanvien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.adapter.adapter_nhanvien;

import java.util.ArrayList;

public class frm_nhanvien extends Fragment {

    ArrayList<String> list;
    RecyclerView recyclerView;
    adapter_nhanvien adapter_nhanvien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_nhanvien, container, false);

        recyclerView = view.findViewById(R.id.rcv_nhanvien);
        list = new ArrayList<>();

        loaddata(list);




        return view;
    }

    public void loaddata(ArrayList<String> list1){
        data();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(recyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter_nhanvien = new adapter_nhanvien(getContext(), list1);
        recyclerView.setAdapter(adapter_nhanvien);
    }

    public void data(){
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        list.size();

    }


}
