package com.example.quanlinhanvien;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.quanlinhanvien.model_api.nhanvien;
import com.example.quanlinhanvien.service.service_API;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 101;
    ProgressBar progressBar;
    TextView tv_forgotpassword;
    TextInputLayout txtlayoutEmail, txtLayoutPassword;
    TextInputEditText edt_email, edt_password;
    Button btn_signin;
    Intent intent;
    Bundle bundle;
    String IMEINumber, imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhxa();
        imei = getImei();
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                kiemtra();
                demoCallAPI();
//                intent =new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//                getImei();

            }
        });


    }


    //ánh xạ
    public void anhxa() {
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        tv_forgotpassword = findViewById(R.id.tv_forgotpassword);
        txtlayoutEmail = findViewById(R.id.txtlayoutEmail);
        txtLayoutPassword = findViewById(R.id.txtlayoutPassword);
        btn_signin = findViewById(R.id.btn_signin);
        progressBar = findViewById(R.id.prb_login);
    }

    //kiểm tra không được bỏ trống thông tin đăng nhập
//    public boolean kiemtra_email() {
//        String email = edt_email.getText().toString();
//
//        //validate email
//        if (!email.isEmpty()) {
//            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                bundle = new Bundle();
//                bundle.putString("email", email);
//                intent.putExtras(bundle);
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
//
//    }
//
//    public boolean kiemtra_password() {
//        //validate password
//        String password = edt_password.getText().toString();
//
//        if (!password.isEmpty()) {
//            bundle = new Bundle();
//            bundle.putString("password", password);
//            intent.putExtras(bundle);
//            return true;
//        }else{
//            return false;
//        }
//    }

    //kiểm tra các edit text

    public void kiemtra() {

        String thongbao_email = "";
        String email = edt_email.getText().toString();

        //validate email
        if (!email.isEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            } else {
                thongbao_email += "Hãy nhập đúng email của bạn";
                txtlayoutEmail.setHelperText(thongbao_email);
                txtlayoutEmail.setHelperTextColor(getResources().getColorStateList(R.color.red));
            }
        } else {
            thongbao_email += "Email không được để trống";
            txtlayoutEmail.setHelperText(thongbao_email);
            txtlayoutEmail.setHelperTextColor(getResources().getColorStateList(R.color.red));
        }

        String password = edt_password.getText().toString();

        String thongbao_password = "";
        if (!password.isEmpty()) {
        } else {
            thongbao_password += "Password không được để trống";
            txtLayoutPassword.setHelperText(thongbao_password);
            txtLayoutPassword.setHelperTextColor(getResources().getColorStateList(R.color.red));
        }
    }

    private void demoCallAPI() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getModelAPI()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

     private void handleResponse(ArrayList<nhanvien> list1) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
         Toast.makeText(LoginActivity.this, "thành công", Toast.LENGTH_SHORT).show();

         for (int i = 0; i < list1.size(); i++) {
            if ((edt_email.getText().toString()).equals(list1.get(i).getTaiKhoan())) {
                if ((edt_password.getText().toString()).equals(list1.get(i).getMatKhau())) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    txtLayoutPassword.setHelperText("Sai mật khẩu");
                    progressBar.setVisibility(View.GONE);
                    txtLayoutPassword.setHelperTextColor(getResources().getColorStateList(R.color.red));
                    break;
                }
            }
        }
    }

    private void handleError(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

    //get api
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public String getImei() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);

        }

        IMEINumber = telephonyManager.getDeviceId();
        Log.d("============TAG", "getImei: "+IMEINumber);
        return IMEINumber;
    }

}