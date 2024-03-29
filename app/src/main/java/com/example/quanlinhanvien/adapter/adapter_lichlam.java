package com.example.quanlinhanvien.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.model.chamcong;
import com.example.quanlinhanvien.model.lichlam;

import java.util.ArrayList;

public class adapter_lichlam extends BaseAdapter {
    private ArrayList<lichlam> list;
    private Context context;


    public adapter_lichlam(ArrayList<lichlam> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewOfItem {
        TextView txtnv, txtgiovao, txtgiora, txtngaylam, txtcalam;

    }


    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewOfItem viewOf;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        if (view == null) {
            viewOf = new ViewOfItem();
            view = inflater.inflate(R.layout.item_lichlam, null);
            viewOf.txtnv = view.findViewById(R.id.txtnv);
            viewOf.txtcalam = view.findViewById(R.id.txtcalam);
            viewOf.txtngaylam = view.findViewById(R.id.txtngaylam);
            viewOf.txtgiovao = view.findViewById(R.id.txtgiovao);
            viewOf.txtgiora = view.findViewById(R.id.txtgiora);

            view.setTag(viewOf);
        } else {
            viewOf = (ViewOfItem) view.getTag();
        }

        viewOf.txtnv.setText("" + list.get(i).getTenNV());
        viewOf.txtcalam.setText("" + list.get(i).getTenCL());
        viewOf.txtngaylam.setText("" + list.get(i).getNgayLam().substring(0, 10));

        if (list.get(i).getGioBD() == null) {
            viewOf.txtgiovao.setText("     --|--     ");
        } else {
            viewOf.txtgiovao.setText("" + list.get(i).getGioBD().toString());
        }
        if (list.get(i).getGioKT() == null) {
            viewOf.txtgiora.setText("   --|--");
        } else {
            viewOf.txtgiora.setText("" + list.get(i).getGioKT().toString());
        }

        return view;
    }


}