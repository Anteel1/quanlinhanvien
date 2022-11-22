package com.example.quanlinhanvien;

import static com.example.quanlinhanvien.ServiceAPI.BASE_Service;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.util.Log;

import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.quanlinhanvien.model.User;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TextView tv_forgotpassword, tv_validate_password, tv_validate_email;
    EditText edt_email, edt_password;
    Button btn_signin;
    Intent intent;
    Bundle bundle;
    ImageView logo;
    ArrayList<User> list;
    User user;
    int kt = 0;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        list = new ArrayList<>();
        anhxa();
//        Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.hien);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prb_run();
                tv_validate_password.setText("");
                tv_validate_email.setText("");


            }
        });

    }


    //ánh xạ
    public void anhxa() {
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        tv_forgotpassword = findViewById(R.id.tv_forgotpassword);
        tv_validate_email = findViewById(R.id.tv_validate_email);
        tv_validate_password = findViewById(R.id.tv_validate_password);
        btn_signin = findViewById(R.id.btn_signin);
        progressBar = findViewById(R.id.prb_login);

    }

    //kiểm tra không được bỏ trống thông tin đăng nhập
    public boolean kiemtra_email() {
        String email = edt_email.getText().toString();

        //validate email
        if (!email.isEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean kiemtra_password() {
        //validate password
        String password = edt_password.getText().toString();

        if (!password.isEmpty()) {

            return true;
        } else {
            return false;
        }
    }


    //kiểm tra các edit text

    public void kiemtra() {

        String thongbao_email = "";
        String email = edt_email.getText().toString();

        //validate email
        if (!email.isEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            } else {
                thongbao_email += "hãy nhập đúng email của bạn";
                tv_validate_email.setText(thongbao_email);
            }
        } else {
            thongbao_email += "email không được để trống";
            tv_validate_email.setText(thongbao_email);
        }

        String password = edt_password.getText().toString();

        String thongbao_password = "";
        if (!password.isEmpty()) {
        } else {
            thongbao_password += "password không được để trống";
            tv_validate_password.setText(thongbao_password);
        }
        demoCallAPI();
    }

    private void demoCallAPI() {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.GetListUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<User> list1) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data

        for (int i = 0; i <= list1.size(); i++) {
            if ((edt_email.getText().toString()).equals(list1.get(i).getTaiKhoan())) {
                if ((edt_password.getText().toString()).equals(list1.get(i).getMatKhau())) {
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    if (kiemtra_email() && kiemtra_password()) {
                        startActivity(intent);
                        finish();
                    }
                    i = list1.size() + 1;
                } else {
                    tv_validate_password.setText("sai mật khẩu");
                    i = list1.size() + 1;
                }
            }
        }
    }


    private void handleError(Throwable error) {
        String a = "";

        Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

    public void prb_run() {
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                progressBar.setVisibility(View.VISIBLE);
            }

            public void onFinish() {
                progressBar.setVisibility(View.GONE);
                kiemtra();
                demoCallAPI();


            }
        }.start();
    }


}