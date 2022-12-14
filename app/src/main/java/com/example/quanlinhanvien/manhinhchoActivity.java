package com.example.quanlinhanvien;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;

public class manhinhchoActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView textView;
    Handler handler = new Handler();
    int i;
    private HashMap config = new HashMap();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhcho);

        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.tv_chay);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                chay();
                startActivity(new Intent(manhinhchoActivity.this, LoginActivity.class));
                finish();

            }
        });
        thread.start();

    configCloudinary();
    }

    public void chay() {
        for (i = 0; i < 100; i = i + 1) {
            try {
                Thread.sleep(50);
                progressBar.setProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(i + "%");
                }
            });
        }

    }
    private void configCloudinary() {
        config.put("cloud_name", "dnxe9l57i");
        config.put("api_key", "991189484643755");
        config.put("api_secret", "e6ZiAtks5BeldzKgTew3IqC8KHk");
        MediaManager.init(this, config);
    }
}