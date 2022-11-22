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
   private ArrayList<chamcong> list1 ;
   private Context context;



    public adapter_chamcong(ArrayList<chamcong> list1, Context context) {
        this.list1 = list1;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list1.size();
    }


    @Override
    public Object getItem(int position) {
        return list1.get(position);
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
        ViewOfItem viewOfItem;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        if(view == null){
            viewOfItem = new ViewOfItem();
            view = inflater.inflate(R.layout.item_chamcong, null);
            viewOfItem.txtnv = view.findViewById(R.id.txtnv);
            viewOfItem.txtgiovao = view.findViewById(R.id.txtgiovao);
            viewOfItem.txtgiora = view.findViewById(R.id.txtgiora);

            view.setTag(viewOfItem);
        }else {
            viewOfItem = (ViewOfItem) view.getTag();
        }
        viewOfItem.txtnv.setText(""+list1.get(i).getTenNV());
        if(viewOfItem.txtgiovao == null){
            viewOfItem.txtgiovao.setText("--|--");      }
        else {viewOfItem.txtgiovao.setText(""+list1.get(i).getGiobd().toString());}
        if(viewOfItem.txtgiora == null){
            viewOfItem.txtgiora.setText("--|--");    }
        else {viewOfItem.txtgiora.setText(""+list1.get(i).getGiokt().toString());}

        return null;
    }




}
