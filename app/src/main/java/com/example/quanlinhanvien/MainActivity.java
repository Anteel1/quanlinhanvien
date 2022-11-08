package com.example.quanlinhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView gioht = findViewById(R.id.giohienthi);
        TextView ngayht = findViewById(R.id.ngayhienthi);
        ImageButton nen = findViewById(R.id.nen);

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int gio = c.get(Calendar.HOUR_OF_DAY);

        if (gio >= 12) gioht.setText(hour + ":" + minute + "PM");
        else gioht.setText(hour + ":" + minute + "AM");

        if (minute <10){
            if (gio >= 12) gioht.setText(hour + ":0" + minute + "PM");
            else gioht.setText(hour + ":0" + minute + "AM");
        }



       Calendar n = Calendar.getInstance();
        int year = n.get(Calendar.YEAR);
        int month = n.get(Calendar.MONTH);
        int day = n.get(Calendar.DAY_OF_MONTH);
        ngayht.setText("Ngày " + day + " tháng " + month + " năm " + year);

//        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.hien);
//        nen.startAnimation(animation);

    }
}


