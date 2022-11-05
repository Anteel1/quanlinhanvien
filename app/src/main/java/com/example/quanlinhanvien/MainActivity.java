package com.example.quanlinhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView gioht = findViewById(R.id.giohienthi);
        TextView ngayht = findViewById(R.id.ngayhienthi);
        ImageButton nen = findViewById(R.id.nen);


        //hien thi gio

                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);
                int gio = c.get(Calendar.HOUR_OF_DAY);
                if (gio >= 12) {
                    gioht.setText(hour + ":" + minute + "PM");
                } else {
                    gioht.setText(hour + ":" + minute + "AM");
                }
        if (minute <10){
            if (gio >= 12) {
                gioht.setText(hour + ":0" + minute + "PM");
            } else {
                gioht.setText(hour + ":0" + minute + "AM");
          }
        }

        //hien thi ngay
        String thuchu="vv";
        String thangchu="vv";

       Calendar n = Calendar.getInstance();
        int year = n.get(Calendar.YEAR);
        int month = n.get(Calendar.MONTH);
        int day = n.get(Calendar.DAY_OF_MONTH);
        int thu = n.get(Calendar.DAY_OF_WEEK);
        switch (thu){
            case 2 : thuchu ="Monday";
            break;
            case 3 : thuchu ="Tuesday ";
                break;
            case 4 : thuchu ="Wednesday ";
                break;
            case 5 : thuchu ="Thursday";
                break;
            case 6 : thuchu ="Friday ";
                break;
            case 7 : thuchu ="Saturday ";
                break;
            case 1 : thuchu ="Sunday ";
                break;
        }
        switch (month+1){
            case 1 : thangchu="Jan";
            break;
            case 2 : thangchu="Feb";
                break;
            case 3 : thangchu="Mar";
                break;
            case 4 : thangchu="Apr";
                break;
            case 5 : thangchu="May";
                break;
            case 6 : thangchu="Jun";
                break;
            case 7 : thangchu="Jul";
                break;
            case 8 : thangchu="Aug";
                break;
            case 9 : thangchu="Sep";
                break;
            case 10 : thangchu="Oct";
                break;
            case 11 : thangchu="Nov";
                break;
            case 12 : thangchu="Dec";
                break;
        }
        ngayht.setText(thuchu+","+day+" "+thangchu+" "+year);

       //
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.hien);
        nen.startAnimation(animation);
        }
    }