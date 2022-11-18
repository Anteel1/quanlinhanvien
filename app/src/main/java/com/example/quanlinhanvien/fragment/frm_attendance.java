package com.example.quanlinhanvien.fragment;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.example.quanlinhanvien.others.GPSTracker;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.Result;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class frm_attendance extends Fragment {
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    TextView txtTitle,txtResutl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frm_attendance, container, false);
        scanQRpermission();
        txtResutl =v.findViewById(R.id.resutl);
        scannerView =v.findViewById(R.id.scanner_view);
        codeScanner();
        // get location theo km
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
        ArrayList<Location> list = new ArrayList<>();
        list.add(new Location(1, "Địa điểm A", "10.7564083,106.5754643"));
        list.add(new Location(2, "Địa điểm Nhà riêng", "10.7607871,106.5871427"));
        list.add(new Location(3, "CoopMart", "10.7587318,106.584954"));
        list.add(new Location(4, "Trường tiểu học A", "10.7614933,106.5879044"));
        list.add(new Location(5, "Tòa F", "10.8525201,106.6249008"));


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
}
