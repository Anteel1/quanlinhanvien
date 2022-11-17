package com.example.quanlinhanvien;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextView tv_forgotpassword;
    TextInputLayout txtlayoutEmail,txtLayoutPassword;
    TextInputEditText edt_email, edt_password;
    Button btn_signin;
    Intent intent;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemtra();
                intent =new Intent(LoginActivity.this, MainActivity.class);
                if (kiemtra_email() && kiemtra_password()){
                    startActivity(intent);
                    finish();
                }
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
    }

    //kiểm tra không được bỏ trống thông tin đăng nhập
    public boolean kiemtra_email() {
        String email = edt_email.getText().toString();

        //validate email
        if (!email.isEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                bundle = new Bundle();
                bundle.putString("email", email);
                intent.putExtras(bundle);
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
            bundle = new Bundle();
            bundle.putString("password", password);
            intent.putExtras(bundle);
            return true;
        }else{
            return false;
        }
    }

    //kiểm tra các edit text

    public void kiemtra(){

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
        }else{
            thongbao_password += "Password không được để trống";
            txtLayoutPassword.setHelperText(thongbao_password);
            txtLayoutPassword.setHelperTextColor(getResources().getColorStateList(R.color.red));
        }
    }
}