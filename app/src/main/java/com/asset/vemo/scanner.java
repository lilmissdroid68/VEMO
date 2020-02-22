package com.asset.vemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.w3c.dom.Text;

import java.util.Scanner;

public class scanner extends AppCompatActivity {

    private static final String TAG = "scanner";

    CodeScanner codeScanner;    //scanner.java
    CodeScannerView scannView;  //scanner.java
    TextView resultData;        //scanner.java
    DatabaseHelper mDatabaseHelper; //DatabaseHelper.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        scannView = findViewById(R.id.scannerView);
        resultData = findViewById(R.id.resultOfQr); //TextView
        codeScanner = new CodeScanner(this,scannView);
        mDatabaseHelper = new DatabaseHelper(this); //DatabaseHelper.java

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        resultData.setText(result.getText()); //get data to scan
                        //Adding Visitor Array
                        String newVisitor = result.getText().toString();
                        if (newVisitor.length() == 0) {
                            Toast.makeText(scanner.this, "Please rescan", Toast.LENGTH_SHORT).show();
                            codeScanner.startPreview();
                        }else {
                            AddData(newVisitor);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }
                });
            }
        });

        scannView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });
    }

    private void AddData(String newVisitor) {
        boolean insertData = mDatabaseHelper.addData(newVisitor);
//        if(insertData){
//            toastMessage("Data Successfully Inserted!");
//        }else {
//            toastMessage("Something went wrong");
//        }

    }

    protected void onResume() {
            super.onResume();
            requestForCamera();
        }



    private void requestForCamera(){
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(scanner.this, "Camera Permission is Required.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

}

