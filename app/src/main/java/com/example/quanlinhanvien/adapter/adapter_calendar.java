package com.example.quanlinhanvien.adapter;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.model.ngaylam;
import com.example.quanlinhanvien.service.service_API;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.time.LocalDate;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class adapter_calendar extends RecyclerView.Adapter<adapter_calendar.ViewHolder> {
    Context context;
    ArrayList<String>dayOfMonth;
    final ArrayList<String>dayCompare = new ArrayList<>();
    public adapter_calendar(Context context,ArrayList<String>dayOfMonth){
        this.context=context;
        this.dayOfMonth=dayOfMonth;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.item_date,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        demoCallAPI();
        String day = dayOfMonth.get(position);
        for (int i =0;i<dayCompare.size();i++){
            LocalDate date1 = LocalDate.parse(dayCompare.get(i));
            String day1 =String.valueOf(date1.getDayOfMonth());
            int year = date1.getYear();
            int month = date1.getDayOfMonth();
            Log.d("day",year+" "+month+" "+day);
            if(day.equalsIgnoreCase(day1)){
                holder.item.setTextColor(Color.WHITE);
                holder.item.setBackgroundColor(Color.parseColor("#73AB6B"));
            }
        }

        holder.item.setText(day);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_date);
        }
    }

    @Override
    public int getItemCount() {
        return dayOfMonth.size();
    }
    private void demoCallAPI() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getNgayLam(1,LocalDate.now().getMonth().getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<ngaylam>list) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        int i =0;
        for(ngaylam ngaylam : list){
            dayCompare.add(i,ngaylam.getNgaylam());
            i++;
        }
    }

    private void handleError(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }
}