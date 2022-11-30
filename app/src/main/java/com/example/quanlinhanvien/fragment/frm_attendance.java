package com.example.quanlinhanvien.fragment;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.model.Location;
import com.example.quanlinhanvien.model.calam;
import com.example.quanlinhanvien.others.GPSTracker;
import com.example.quanlinhanvien.service.service_API;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.Result;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frm_attendance extends Fragment {
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    Button btnQRCode,btnNFC,btnUpdate;
    TextView txtTitle,txtResutl;
    ArrayList<Location> list;
    TextClock tc_gio, tc_ngay;
    LinearLayout layout_icon;
    ImageView imgCheckIn,imgCheckOut;
    FrameLayout layout_scan;
    ArrayList<calam>listCalam;
    ImageView imgCamera;
    int gio,phut;
    int maCL;
    boolean tregio;
    private HashMap config = new HashMap();
    int type;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frm_attendance, container, false);
        tc_gio = v.findViewById(R.id.tc_gio);
        tc_ngay = v.findViewById(R.id.tc_ngay);
        layout_icon = v.findViewById(R.id.layout_icon);
        txtTitle = v.findViewById(R.id.txtTitle);
        layout_scan = v.findViewById(R.id.layout_scan);
        listCalam = new ArrayList<>();
        list = new ArrayList<>();
        btnNFC = v.findViewById(R.id.btnNFC);
        btnQRCode=v.findViewById(R.id.btnQR);
        btnUpdate=v.findViewById(R.id.btnUpdateIMG);
        imgCamera = v.findViewById(R.id.imgCamera);

        demoCallAPI_calam();
        configCloudinary();
        btnQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_icon.setVisibility(View.VISIBLE);
                btnNFC.setVisibility(View.GONE);
                btnQRCode.setVisibility(View.GONE);
            }
        });
        btnNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNFC.setVisibility(View.GONE);
                btnQRCode.setVisibility(View.GONE);
            }
        });
        tc_gio.setFormat12Hour("hh:mm a");
        tc_ngay.setFormat12Hour("EEE, MMM d");
        imgCheckIn = v.findViewById(R.id.btnCheckin);
        imgCheckOut=v.findViewById(R.id.btnCheckOut);
        imgCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get localtime
                gio = LocalDateTime.now().getHour();
                phut = LocalDateTime.now().getMinute();

                layout_icon.setVisibility(View.GONE);
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText("Check in");
                layout_scan.setVisibility(View.VISIBLE);
                type = 1;
                    // gọi post lên update bảng chấm công


            }
        });
        imgCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get localtime
                gio = LocalDateTime.now().getHour();
                phut = LocalDateTime.now().getMinute();

                layout_icon.setVisibility(View.GONE);
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText("Check out");
                layout_scan.setVisibility(View.VISIBLE);
                type = 2;
                    // gọi post lên update bảng chấm công
            }
        });
        scanQRpermission();
        txtResutl =v.findViewById(R.id.resutl);
        scannerView =v.findViewById(R.id.scanner_view);
        list = new ArrayList<>();
        codeScanner(type);

        Log.d("Location",getData(0.02).size()+" ");
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
    private void codeScanner(int type){
        if(type == 1){
            mCodeScanner = new CodeScanner(getContext(),scannerView);
            mCodeScanner.setCamera(CodeScanner.CAMERA_BACK);
            mCodeScanner.setFormats(CodeScanner.ALL_FORMATS);
            mCodeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
            mCodeScanner.setScanMode(ScanMode.CONTINUOUS);
            mCodeScanner.setAutoFocusEnabled(true);
            mCodeScanner.setFlashEnabled(false);
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.add(0,new Location(0,"Cửa hàng City Food",result.toString()));
                            checkIn();
                        }
                    });
                }
            });
            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });
        }else{
            mCodeScanner = new CodeScanner(getContext(),scannerView);
            mCodeScanner.setCamera(CodeScanner.CAMERA_BACK);
            mCodeScanner.setFormats(CodeScanner.ALL_FORMATS);
            mCodeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
            mCodeScanner.setScanMode(ScanMode.CONTINUOUS);
            mCodeScanner.setAutoFocusEnabled(true);
            mCodeScanner.setFlashEnabled(false);
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.add(0,new Location(0,"Cửa hàng City Food",result.toString()));
                            checkOut();
                        }
                    });
                }
            });
            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });
        }

    }
    private void scanQRpermission(){
        int permission = ActivityCompat.checkSelfPermission((Activity)getContext(), android.Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity)getContext(),new String[]{android.Manifest.permission.CAMERA},
                    100);
        }

    }


    private ArrayList<Location> getData(double distance) {
        //data location cua hang
        list.add(0,new Location(0,"City food Store","10.8529995,106.6270037"));
        //lọc data
        ArrayList<Location> listResult = new ArrayList<>();
        for (Location location : list) {
            String vitri = location.getLglt();
            int vt = vitri.indexOf(",");
            double lat = Double.parseDouble(vitri.substring(0, vt));
            double lng = Double.parseDouble(vitri.substring(vt + 1));
            LatLng latLng = new LatLng(lat, lng);

            if (CalculationByDistance(currentLoaction(), latLng) <= distance) {
                listResult.add(location);
            }
        }

        return listResult;
    }
    private double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
    private LatLng currentLoaction() {
        GPSTracker gpsTracker = new GPSTracker(getContext());
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            return new LatLng(latitude, longitude);
        } else {
            // gpsTracker.showSettingsAlert();
            return new LatLng(0, 0);
        }
    }

    private void demoCallAPI_calam() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getModelAPI_calam()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse_calam, this::handleError_calam)
        );
    }

    private void handleResponse_calam(ArrayList<calam> list1) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        for(int i =0; i <list1.size(); i++){
            listCalam.add(new calam(list1.get(i).getMaCL(),list1.get(i).getTenCL(),
                    list1.get(i).getGioBD(),list1.get(i).getGioKT()));
        }
    }

    private void handleError_calam(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }
    private void checkIn(){
    // check distance
        if(getData(0.02).size() ==1){
            for(calam calam:listCalam){
                if(Integer.parseInt(calam.getGioBD())-1 <= gio && Integer.parseInt(calam.getGioKT())-1 >=gio){
                    maCL = calam.getMaCL();
                    layout_scan.setVisibility(View.GONE);
                    turnonCamera();
                }else{
                    Toast.makeText(getContext(), "Chấm công thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Log.d("Distance","noooooo");
            Toast.makeText(getContext(), "Bạn không nằm trong khu vực cửa hàng", Toast.LENGTH_SHORT).show();
        }
    }
    private void checkOut(){

    }
    // hien thi hinh anh
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2323 ){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgCamera.setImageBitmap(bitmap);
            imgCamera.setVisibility(View.VISIBLE);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadIMG(bitmap);
                }
            });
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void turnonCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,2323);
    }
    private void uploadIMG(Bitmap bitmap) {
        MediaManager.get().upload(String.valueOf(bitmap)).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.d("CHECK", "onStart");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Log.d("CHECK", "onProgress");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                Log.d("CHECK", "onSuccess");
                Log.d("IMG URL",resultData.get("url").toString());
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Log.d("CHECK", "onError: " + error);
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                Log.d("CHECK", "onReschedule: " + error);
            }
        }).dispatch();
    }
    private void configCloudinary() {
        config.put("cloud_name", "dnxe9l57i");
        config.put("api_key", "991189484643755");
        config.put( "api_secret", "e6ZiAtks5BeldzKgTew3IqC8KHk");
        MediaManager.init(getContext(), config);
    }
}
