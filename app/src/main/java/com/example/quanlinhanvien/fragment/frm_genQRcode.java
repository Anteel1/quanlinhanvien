package com.example.quanlinhanvien.fragment;

import static android.content.Context.WINDOW_SERVICE;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;


public class frm_genQRcode extends Fragment {
    private Bitmap bitmap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_genqrcode_layout, container, false);
        ImageView ivQRCode = view.findViewById(R.id.ivQRCode);
        Button btnGenerate = view.findViewById(R.id.btnGenerate);
        Button btnSave = view.findViewById(R.id.btnSave);
        EditText edtInput = view.findViewById(R.id.edtInput);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtInput.getText().toString())) {
                    Toast.makeText(getContext(), "Nhập nội dung cần tạo QR", Toast.LENGTH_SHORT).show();
                } else {
                    WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int dimen = Math.min(width, height);
                    dimen = dimen * 3 / 4;

                    QRGEncoder qrgEncoder = new QRGEncoder(edtInput.getText().toString(), null, QRGContents.Type.TEXT, dimen);

                    //chỉnh màu sắc của QR
                    qrgEncoder.setColorBlack(Color.BLACK);
                    qrgEncoder.setColorWhite(Color.WHITE);

                    //hiển thị QR
                    bitmap = qrgEncoder.getBitmap(0);
                    ivQRCode.setImageBitmap(bitmap);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xin quyền
                String filename = edtInput.getText().toString().trim();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    try {
                        ContentResolver resolver = view.getContext().getContentResolver();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename + ".jpg");
                        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
                        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                        OutputStream fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        Objects.requireNonNull(fos).close();
                        Toast.makeText(getContext(), "Đã lưu QRCode", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        try {
                            String savePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera/";
                            boolean save = new QRGSaver().save(savePath, filename, bitmap, QRGContents.ImageType.IMAGE_JPEG);
                            String result = save ? "Đã lưu QRCode" : "Lưu thất bại";
                            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    }
                }
            }
        });
        return view;
    }
}
