package com.example.quanlinhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        test truyền dữ liệu
        tv = findViewById(R.id.tv);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String e = bundle.getString("email");
        String ep= bundle.getString("email");
        tv.setText(bundle.getString("email")+"     "+bundle.getString("password"));

         */
    }
}