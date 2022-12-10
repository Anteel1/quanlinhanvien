package com.example.quanlinhanvien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlinhanvien.R;

public class adapter_maCV extends ArrayAdapter<String> {
    String[] list;
    Context context;
    TextView txt;
    public adapter_maCV(@NonNull Context context,String[] list) {
        super(context, 0,list);
        this.list = list;
        this.context= context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v =inflater.inflate(R.layout.item_spinner,null);
        }
        txt = v.findViewById(R.id.txtSpinner);
        txt.setText(list[position]);
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v =inflater.inflate(R.layout.item_spinner,null);
        }
        txt = v.findViewById(R.id.txtSpinner);
        txt.setText(list[position]);
        return v;
    }
}
