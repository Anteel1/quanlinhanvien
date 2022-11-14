package com.example.quanlinhanvien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.quanlinhanvien.R;

public class UserInfoDialog  extends DialogFragment {



    TextView txtDoYouWantLogOut;
    Button btnNo;
    Button btnYes;
    //Được dùng khi khởi tạo dialog mục đích nhận giá trị
    public static UserInfoDialog newInstance(String data) {
        UserInfoDialog dialog = new UserInfoDialog();
        Bundle args = new Bundle();
        args.putString("data", data);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //lấy giá trị bundle
        String data = getArguments().getString("data", "");
        txtDoYouWantLogOut = (TextView) view.findViewById(R.id.txtDoYouWantLogOut);
        btnNo = (Button) view.findViewById(R.id.btnNo);
        btnYes = (Button) view.findViewById(R.id.btnYes);
        txtDoYouWantLogOut.setText("Do you want log out");
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }
}
