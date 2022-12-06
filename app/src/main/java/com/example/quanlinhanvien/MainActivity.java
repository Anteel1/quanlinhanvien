package com.example.quanlinhanvien;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.fragment.frm_attendance;
import com.example.quanlinhanvien.fragment.frm_calam;
import com.example.quanlinhanvien.fragment.frm_dangxuat;
import com.example.quanlinhanvien.fragment.frm_genQRcode;
import com.example.quanlinhanvien.fragment.frm_lichlam;
import com.example.quanlinhanvien.fragment.frm_nhanvien;
import com.example.quanlinhanvien.fragment.frm_store;
import com.example.quanlinhanvien.fragment.frm_thongke;
import com.example.quanlinhanvien.fragment.frm_trangchu;
import com.google.android.material.navigation.NavigationView;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    ImageView iv_menu;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    Fragment fragment;
    Intent intent;
    String data;

    private NfcAdapter nfcAdapter = null;
    private IntentFilter[] intentFiltersArray = null;
    private String[][] techListsArray = null;
    private PendingIntent pendingIntent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // nfc
        initNFC();
        //get id nhan vien
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        int idNV = bundle.getInt("idNV", -1);
        anhxa();
        menu_nav();
        fragment = new frm_trangchu();
        onRestoreInstanceState(savedInstanceState);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        fragment = new frm_trangchu();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_Attendance:
                        fragment = new frm_attendance(idNV);
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_store:
                        fragment = new frm_store();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_lichlam:
                        fragment = new frm_lichlam(idNV);
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_calam:
                        fragment = new frm_calam();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_nhanvien:
                        fragment = new frm_nhanvien();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_genqrcode:
                        fragment = new frm_genQRcode();
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_thongke:
                        fragment = new frm_thongke(idNV);
                        onRestoreInstanceState(savedInstanceState);
                        break;
                    case R.id.menu_Logout:
                        new frm_dangxuat().show(getSupportFragmentManager(), frm_dangxuat.TAG);
                        break;
                    default:
                        fragment = new frm_trangchu();
                        break;
                }
                drawerLayout.closeDrawer(navigationView);
                setTitle(item.getTitle());
                return false;
            }
        });
    }


    public void anhxa() {
        toolbar = findViewById(R.id.toolbar);
        iv_menu = findViewById(R.id.iv_menu_toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
    }

    public void menu_nav() {
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragment_view, fragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_view, fragment).commit();

        }
    }
    private void initNFC(){
        NfcManager nfcManager = (NfcManager)getSystemService(Context.NFC_SERVICE);
//        nfcAdapter = nfcManager.getDefaultAdapter();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcManager != null) {
            Toast.makeText(this,"NFC Manager ready ..." , Toast.LENGTH_SHORT).show();
        }

        if (nfcAdapter != null) {
            Toast.makeText(this,"NFC Adapter ready ...", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(
                    this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);
        }else {
            pendingIntent = PendingIntent.getActivity(
                    this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }
        intentFiltersArray = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)};
        techListsArray = new String[][]{new String[]{NfcA.class.getName()}, new String[]{NfcB.class.getName()}, new String[]{IsoDep.class.getName()}};
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            PendingIntent nfcPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
        }else {
            PendingIntent nfcPendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        }
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            try {
                nfcAdapter.disableForegroundDispatch(this);
            } catch (IllegalStateException ex) {
                Log.e("ATHTAG", "Error disabling NFC foreground dispatch", ex);
            }
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (fragment instanceof frm_attendance) {
            // Use NFC to read tag
            String action = intent.getAction();
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                if (tag != null) {
                    Ndef ndef = Ndef.get(tag);
                    if (ndef == null) {
                        // NDEF is not supported by this Tag.
                        Toast.makeText(this, "NDEF is not supported", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    NdefMessage ndefMessage = ndef.getCachedNdefMessage();

                    NdefRecord ndefRecord1 = ndefMessage.getRecords()[0];
                    byte[] payload1 = ndefRecord1.getPayload();
                    String textEncoding1 = ((payload1[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                    int languageCodeLength1 = payload1[0] & 0077;
                    try
                    {
                        data = new String(payload1, languageCodeLength1 + 1, payload1.length - languageCodeLength1 - 1, textEncoding1);
                        Toast.makeText(this, "Đã đọc được NFC", Toast.LENGTH_SHORT).show();
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(this, "No tag found", Toast.LENGTH_SHORT).show();
                }
            }
        }
        intent.putExtra("dataNFC",data);
        setIntent(intent);
    }

}
