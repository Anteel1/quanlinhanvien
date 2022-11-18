package com.example.quanlinhanvien;

import static com.example.quanlinhanvien.service.API_service.Base_Service;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlinhanvien.model_api.model_tk;
import com.example.quanlinhanvien.model_api.nhanvien;
import com.example.quanlinhanvien.service.API_service;
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
    ArrayList<model_tk> list;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        list = new ArrayList<>();
        anhxa();
        demoCallAPI();

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_validate_password.setText("");
                tv_validate_email.setText("");
                kiemtra();

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
    }


    private void demoCallAPI() {

        API_service requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(API_service.class);

        new CompositeDisposable().add(requestInterface.getModelAPI()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<nhanvien> list_tk) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        for (int i = 0; i < list_tk.size(); i++) {
            list.add(new model_tk(list_tk.get(i).getTaiKhoan(), list_tk.get(i).getMatKhau()));
        }
        intent = new Intent(LoginActivity.this, MainActivity.class);
        if (kiemtra_email() && kiemtra_password()) {
            String email = edt_email.getText().toString();
            String password = edt_password.getText().toString();
            if (check_login(new model_tk(email, password))) {
                prb_run();
            }
        }
        Log.d("=========TAG", "handleResponse: " + list.size());


    }

    private void handleError(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(LoginActivity.this, "=============" + error, Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

    public boolean check_login(model_tk tk) {

        for (int i = 0; i < list.size(); i++) {
            if (tk.getEmail().equals(list.get(i).getEmail())
                    && tk.getPassword().equals(list.get(i).getPassword())) {
                return true;
            }
        }
        return false;
    }

    public void prb_run() {
        new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {
                progressBar.setVisibility(View.VISIBLE);
            }

            public void onFinish() {
                startActivity(intent);
                finish();
            }
        }.start();
    }

}