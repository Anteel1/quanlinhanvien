package com.example.quanlinhanvien;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

public class progressbarActivity extends AppCompatActivity {

    ImageView imv_gif, imv_gif_load;
    ProgressBar progressBar, progressBar1;
    Timer timer;
    TimerTask timerTask;
    int count = 0;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);

        anhxa();

        Glide.with(progressbarActivity.this).load(R.drawable.imv_gif).into(imv_gif);
//        Glide.with(progressbarActivity.this).load(R.drawable.gif_hoan_thanh).into(imv_gif_load);

        chay2();

    }

    public void anhxa() {
        imv_gif = findViewById(R.id.gif);
//        imv_gif_load = findViewById(R.id.gif_load);
        progressBar = findViewById(R.id.prb_ProgressBar);
//        progressBar1 = findViewById(R.id.prb);

    }

    public void ProgressBar_run() {


        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                count++;
                progressBar.setProgress(count);
                if (count == 100) {
                    timer.cancel();
                }
            }
        };

        timer.schedule(timerTask, 50, 100);

    }


    public void chay2() {
        new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {
                ProgressBar_run();
            }

            public void onFinish() {

                startActivity(new Intent(progressbarActivity.this, MainActivity.class));
                finish();

            }
        }.start();
    }

}

