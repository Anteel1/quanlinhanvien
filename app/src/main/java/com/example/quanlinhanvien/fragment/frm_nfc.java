package com.example.quanlinhanvien.fragment;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlinhanvien.R;

public class frm_nfc extends Fragment {
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writtingTag;
    EditText txtInput;
    Button btnWrite;
    Tag tag;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.frm_nfc_write,container,false);
        txtInput=v.findViewById(R.id.active_tag);
        btnWrite=v.findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(tag ==null){
                        Toast.makeText(getContext(), "No card exception", Toast.LENGTH_SHORT).show();
                    }else{
                        write("10.8525148,106.6249008",tag);
                        Toast.makeText(getContext(), "Write success", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){
                    Log.d("Exception:",ex.toString());
                }
            }
        });
        nfcAdapter = NfcAdapter.getDefaultAdapter(getContext());
        if(nfcAdapter ==null){
            Toast.makeText(getContext(), "This device not support  NFC", Toast.LENGTH_SHORT).show();

        }
        readfromIntent(getActivity().getIntent());
        pendingIntent=PendingIntent.getActivity(getContext(),1212,new Intent(getContext(),getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);
        IntentFilter tagDetected =  new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTag = new IntentFilter[]{tagDetected};
        return v;
    }
    private void readfromIntent(Intent intent){
        String action = intent.getAction();
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
            || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
            || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){

        }
    }
}

