package com.inqbarna.tablefixheaders.samples;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QRcode extends Activity {

    SurfaceView cameraPreview;
    TextView txtResult;
    TextView txtCheck;
    TextView txtTimePause;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

    ArrayList<String> listOfMachine = new ArrayList<>();

    Database database;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //------
        // Tao database boiler data
        database = new Database(this, "data.sqlite", null, 1);

        //insert data

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
        final String[] currentTime = new String[1];

        listOfMachine.add("Boiler");
        listOfMachine.add("NH3");
        //-------


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtCheck = (TextView) findViewById(R.id.txtCheck);
        txtTimePause = (TextView) findViewById(R.id.timePause);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();
        //Add Event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(QRcode.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0)
                {
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            Barcode thisBarCode = qrcodes.valueAt(0);
                            txtResult.setText(thisBarCode.rawValue);

                            //Kiem tra neu QR code la thuoc trong list machine thi dua vao database
                            if (listOfMachine.contains(thisBarCode.rawValue)){
                                txtCheck.setText("Result: Ok");
                                cameraSource.stop();
                                txtCheck.setText("Result: Ok");
                                currentTime[0] = simpleDateFormat.format(Calendar.getInstance().getTime());
                                database.QueryData("INSERT INTO boiler VALUES(null, '" + thisBarCode.rawValue + "', '" + currentTime[0] + "', 6.7)");
                                PauseCameraAndWaitTime(3);
                            } else {
                                txtCheck.setText("Result: Not Ok");
                            }
                        }
                    });
                }
            }
        });

    }

    private void PauseCameraAndWaitTime(int timeInSecond){
        new CountDownTimer(timeInSecond*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                cameraSource.stop();
                txtTimePause.setText("Wait for adding data: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                txtTimePause.setText("DONE!");
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(QRcode.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                txtCheck.setText("Result:");
                txtResult.setText("Focus on QR code");
            }
        }.start();
    }

}
