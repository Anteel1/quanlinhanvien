package com.example.quanlinhanvien.fragment;

import static com.example.quanlinhanvien.service.service_API.Base_Service;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.model.Location;
import com.example.quanlinhanvien.model.cuahang;
import com.example.quanlinhanvien.others.GPSTracker;
import com.example.quanlinhanvien.service.service_API;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.Result;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frm_attendance extends Fragment {
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    TextView txtTitle,txtResutl,tc_ngay;
    ArrayList<Location> list;
    TextClock tc_gio;
    LinearLayout layout_icon;
    ImageView imgCheckIn,imgCheckOut;
    FrameLayout layout_scan;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frm_attendance, container, false);
        //set time
        tc_gio = v.findViewById(R.id.tc_gio);
        tc_ngay = v.findViewById(R.id.tc_ngay);
        layout_icon = v.findViewById(R.id.layout_icon);
        txtTitle = v.findViewById(R.id.txtTitle);
        layout_scan = v.findViewById(R.id.layout_scan);
        //
        tc_gio.setFormat12Hour("hh:mma");
        tc_ngay.setText(LocalDate.now().getDayOfWeek().toString()+","+LocalDate.now().getMonth()+" "+LocalDate.now().getDayOfMonth());
        imgCheckIn = v.findViewById(R.id.btnCheckin);
        imgCheckOut=v.findViewById(R.id.btnCheckOut);
        imgCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_icon.setVisibility(View.GONE);
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText("Check in");
                layout_scan.setVisibility(View.VISIBLE);
                checkIn();
            }
        });
        imgCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_icon.setVisibility(View.GONE);
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText("Check out");
                layout_scan.setVisibility(View.VISIBLE);
                checkOut();
            }
        });
        scanQRpermission();
        txtResutl =v.findViewById(R.id.resutl);
        scannerView =v.findViewById(R.id.scanner_view);
        list = new ArrayList<>();
        codeScanner();
        demoCallAPI();
        if(getData(0.02).size() == 1){
            Toast.makeText(getContext(), "Chấm công thành công", Toast.LENGTH_SHORT).show();
            // gọi post lên update bảng chấm công
        }
        // get location theo km
        Log.d("Location",getData(20).size()+" ");
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
    private void codeScanner(){
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
                        txtResutl.setText(result.toString());
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
    private void scanQRpermission(){
        int permission = ActivityCompat.checkSelfPermission((Activity)getContext(), android.Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity)getContext(),new String[]{android.Manifest.permission.CAMERA},
                    100);
        }

    }


    private ArrayList<Location> getData(double distance) {
        //data mẫu
//        list.add(new Location(1, "Địa điểm A", "10.7564083,106.5754643"));
//        list.add(new Location(2, "Địa điểm Nhà riêng", "10.7607871,106.5871427"));
//        list.add(new Location(3, "CoopMart", "10.7587318,106.584954"));
//        list.add(new Location(4, "Trường tiểu học A", "10.7614933,106.5879044"));
//        list.add(new Location(5, "Tòa F", "10.8525201,106.6249008"));
        // viet api goi vi tri cua hang
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
    private void demoCallAPI() {

        service_API requestInterface = new Retrofit.Builder()
                .baseUrl(Base_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(service_API.class);

        new CompositeDisposable().add(requestInterface.getModelCHAPI()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<cuahang> list1) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        for(int i =0; i <list1.size(); i++){
            String locationName = list1.get(i).getTenCH();
            String locationLeLg = list1.get(i).getDiaChi();
            list.add(i,new Location(i,locationName,locationLeLg));
        }
    }

    private void handleError(Throwable error) {
        Log.d("erro", error.toString());
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }
    private void checkIn(){

    }
    private void checkOut(){

    }
}
