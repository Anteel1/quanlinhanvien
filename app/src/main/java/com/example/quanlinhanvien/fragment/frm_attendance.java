package com.example.quanlinhanvien.fragment;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.bumptech.glide.Glide;
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

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frm_attendance extends Fragment {
    int idNV;
    public frm_attendance(int idNV) {
        this.idNV = idNV;
    }
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    Button btnQRCode, btnNFC, btnUpdate;
    TextView txtTitle, txtResutl;
    ArrayList<Location> list;
    TextClock tc_gio;
    TextView tc_ngay;
    LinearLayout layout_icon,layoutNFC;
    ImageView imgCheckIn, imgCheckOut;
    FrameLayout layout_scan;
    ArrayList<calam> listCalam;
    ImageView imgCamera;
    String url;
    int gio, phut;
    int maCL;
    int check;
    String dataNFC;

    ProgressBar progressBar;
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
        btnQRCode = v.findViewById(R.id.btnQR);
        btnUpdate = v.findViewById(R.id.btnUpdateIMG);
        imgCamera = v.findViewById(R.id.imgCamera);
        layoutNFC = v.findViewById(R.id.layout_readNFC);
        progressBar = v.findViewById(R.id.prb_login);


        tc_gio.setFormat12Hour("hh:mm a");
        tc_ngay.setText(LocalDate.now().getDayOfMonth()+","+LocalDate.now().getDayOfWeek()+","+LocalDate.now().getMonth()+","+LocalDate.now().getYear());
        imgCheckIn = v.findViewById(R.id.btnCheckin);
        imgCheckOut = v.findViewById(R.id.btnCheckOut);


        btnQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_icon.setVisibility(View.VISIBLE);
                btnNFC.setVisibility(View.GONE);
                btnQRCode.setVisibility(View.GONE);
                check =1;

            }
        });
        btnNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutNFC.setVisibility(View.VISIBLE);
                layout_icon.setVisibility(View.VISIBLE);
                btnNFC.setVisibility(View.GONE);
                btnQRCode.setVisibility(View.GONE);
                check= 0;
            }
        });
        imgCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get localtime
                gio = LocalDateTime.now().getHour();
                phut = LocalDateTime.now().getMinute();
                layout_icon.setVisibility(View.GONE);
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText("Check in");
                if(check == 1){
                    layout_scan.setVisibility(View.VISIBLE);
                    codeScanner(1);
                }else{
                    getDATANFC(1);
                }
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
                if(check ==1){
                    layout_scan.setVisibility(View.VISIBLE);
                    codeScanner(2);
                }else{
                    getDATANFC(2);
                }
            }
        });

        txtResutl = v.findViewById(R.id.resutl);
        scannerView = v.findViewById(R.id.scanner_view);
        list = new ArrayList<>();

        scanQRpermission();
        demoCallAPI_calam();
        return v;
    }

    private void getDATANFC(int check) {

        if (getActivity() != null && getActivity().getIntent().hasExtra("dataNFC")) {
            dataNFC = getActivity().getIntent().getStringExtra("dataNFC");
            if(dataNFC != null){
                list.clear();
                list.add(0,new Location(0,"City food store 1",dataNFC));
                list.add(1,new Location(1,"Nhà riêng","10.7612992,106.5872721"));
                if(check ==1){
                    checkIn();
                }else{
                    checkOut();
                }
            }
        }else{
            Toast.makeText(getContext(), "Failed to get NFC TAG!", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        mCodeScanner.startPreview();
//    }
//
//    @Override
//    public void onPause() {
//        mCodeScanner.releaseResources();
//        super.onPause();
//    }

    private void codeScanner(int check) {
        mCodeScanner = new CodeScanner(getContext(), scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //data location cua hang
                        list.clear();
                        list.add(0, new Location(0, "City food Store", result.toString()));
                        // data mau
                        list.add(1,new Location(1,"Nhà riêng","10.7612992,106.5872721"));
                        if(check ==1){
                            checkIn();
                        }else{
                            checkOut();
                        }

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

    private void scanQRpermission() {
        int permission = ActivityCompat.checkSelfPermission((Activity) getContext(), android.Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{android.Manifest.permission.CAMERA},
                    100);
        }

    }


    private ArrayList<Location> getData(double distance) {
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
            Toast.makeText(getContext(), "Get GPS success", Toast.LENGTH_SHORT).show();
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
        for (int i = 0; i < list1.size(); i++) {
            listCalam.add(new calam(list1.get(i).getMaCL(), list1.get(i).getTenCL(),
                    list1.get(i).getGioBD(), list1.get(i).getGioKT()));
        }
    }

    private void handleError_calam(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }

    private void checkIn() {
        // check distance
        int ok = 1;
        if (getData(2).size() == 1) {
            for (calam calam : listCalam) {
                if (Integer.parseInt(calam.getGioBD().substring(0,2)) - 1 <= gio && Integer.parseInt(calam.getGioKT().substring(0,2)) - 1 >= gio) {
                    maCL = calam.getMaCL();
                    layout_scan.setVisibility(View.GONE);
                    layoutNFC.setVisibility(View.GONE);
                    ok = 2;
                    Toast.makeText(getContext(), "Ok check location success", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            if(ok == 2 ){
                turnonCamera();
            }else{
                Toast.makeText(getContext(), "Check in failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("Distance", "noooooo");
            Toast.makeText(getContext(), "You are not in the workplace", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkOut() {
        if (getData(2).size() == 1) {
            for (calam calam : listCalam) {
                if (Integer.parseInt(calam.getGioKT().substring(0,2))  <= gio && Integer.parseInt(calam.getGioBD().substring(0,2)) +1< gio) {
                    maCL = calam.getMaCL();
                    layout_scan.setVisibility(View.GONE);
                    layoutNFC.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Ok check location success", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            if(maCL != 1 & maCL  !=2){
                Toast.makeText(getContext(), "Too early to off shift", Toast.LENGTH_SHORT).show();
            }else{
                demoCallAPI_checkOut();
            }
        } else {
            Log.d("Distance", "noooooo");
            Toast.makeText(getContext(), "You are not in the workplace", Toast.LENGTH_SHORT).show();
        }
    }

    // hien thi hinh anh
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2323) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Glide.with(getContext()).load(bitmap).into(imgCamera);
            imgCamera.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        // gen file sau khi chup
                        File root  = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        File file =  new File(root + "/img_request");
                        file.mkdirs();
                        Random generation =  new Random();
                        int n = 10000;
                        n = generation.nextInt(n);
                        String stringFile = "Image_"+n+".png";
                        File fileImg =  new File(file,stringFile);
                        Log.d("file:"," "+fileImg);
                        if(fileImg.exists())
                            fileImg.delete();
                        FileOutputStream out = new FileOutputStream(fileImg);
                        bitmap.compress(Bitmap.CompressFormat.PNG,90,out);
                        Log.d("file_img"," "+fileImg);
                        uploadIMG(fileImg);
                        out.flush();
                        out.close();
                    }catch (Exception ex){
                        Log.d("exception   ",ex.toString());
                        Toast.makeText(getContext(), "Can not get Image !", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void turnonCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2323);
    }

    private void uploadIMG(File file) {
       MediaManager.get().upload(Uri.fromFile(file)).callback(new UploadCallback() {
           @Override
           public void onStart(String requestId) {

           }

           @Override
           public void onProgress(String requestId, long bytes, long totalBytes) {

           }

           @Override
           public void onSuccess(String requestId, Map resultData) {
               Log.d("Result",resultData.get("url").toString());
               url = resultData.get("url").toString();
                // post o day
               demoCallAPI(url );
               imgCamera.setVisibility(View.GONE);
               btnUpdate.setVisibility(View.GONE);
               txtTitle.setVisibility(View.GONE);
               btnNFC.setVisibility(View.VISIBLE);
               btnQRCode.setVisibility(View.VISIBLE);
           }

           @Override
           public void onError(String requestId, ErrorInfo error) {

           }

           @Override
           public void onReschedule(String requestId, ErrorInfo error) {

           }
       }).dispatch();
    }
    // api here
    private void demoCallAPI(String url) {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.postCheckIn(idNV,maCL,url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse_checkIn, this::handleError_checkIn)
        );
    }

    private void handleResponse_checkIn(Number number) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        if(Integer.parseInt(String.valueOf(number))  ==2){
            Toast.makeText(getContext(), "Success. Thank you !", Toast.LENGTH_SHORT).show();
            txtTitle.setVisibility(View.GONE);
            btnNFC.setVisibility(View.VISIBLE);
            btnQRCode.setVisibility(View.VISIBLE);
            Log.d("size:"," "+number);
        }else{
            Toast.makeText(getContext(), "Something went wrong, contact developer", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError_checkIn(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }
    private void demoCallAPI_checkOut() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.postCheckOut(idNV,maCL)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse_checkOut, this::handleError_checkOut)
        );
    }

    private void handleResponse_checkOut(Number number) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        if(Integer.parseInt(String.valueOf(number))  ==2 ){
            Toast.makeText(getContext(), "Success. Thank you !", Toast.LENGTH_SHORT).show();
            txtTitle.setVisibility(View.GONE);
            btnNFC.setVisibility(View.VISIBLE);
            btnQRCode.setVisibility(View.VISIBLE);
            Log.d("size:"," "+number);
        }else{
            Toast.makeText(getContext(), "Something went wrong, contact developer", Toast.LENGTH_SHORT).show();
        }

    }

    private void handleError_checkOut(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }
}
