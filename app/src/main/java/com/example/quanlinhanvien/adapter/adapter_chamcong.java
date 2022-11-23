package com.example.quanlinhanvien.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.fragment.frm_dangkylichlam;
import com.example.quanlinhanvien.model.chamcong;

import java.util.ArrayList;

public class adapter_chamcong extends BaseAdapter {
   private ArrayList<chamcong> list ;
   private Context context;



    public adapter_chamcong(ArrayList<chamcong> list, Context context) {
        this.list = list;
        this.context=context;
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
    public static class ViewOfItem{
        TextView txtnv,txtgiovao,txtgiora;

    }



    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewOfItem viewOf;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        if(view == null){
            viewOf = new ViewOfItem();
            view = inflater.inflate(R.layout.item_chamcong, null);
            viewOf.txtnv = view.findViewById(R.id.txtnv);
            viewOf.txtgiovao = view.findViewById(R.id.txtgiovao);
            viewOf.txtgiora = view.findViewById(R.id.txtgiora);

            view.setTag(viewOf);
        }else {
            viewOf = (ViewOfItem) view.getTag();
        }
        viewOf.txtnv.setText(""+list.get(i).getTenNV());
        if(list.get(i).getGiobd() == null){
            viewOf.txtgiovao.setText("     --|--     ");      }
        else {viewOf.txtgiovao.setText(""+list.get(i).getGiobd().toString());}
        if(list.get(i).getGiokt() == null){
            viewOf.txtgiora.setText("   --|--");  }
        else {viewOf.txtgiora.setText(""+list.get(i).getGiokt().toString());}

        return view;
    }




}
