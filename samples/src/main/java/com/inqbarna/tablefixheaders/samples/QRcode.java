package com.inqbarna.tablefixheaders.samples;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inqbarna.tablefixheaders.samples.adapters.hourlyCheckType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

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

    String personCheck;

    DatabaseReference databaseReference_QR;
    hourlyCheckType hourlyCheckTypeTmp = new hourlyCheckType();

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
        DialogAddPersonCheck();
        //------
        // Tao database boiler data
        database = new Database(this, "data.sqlite", null, 1);

        //insert data

        final SimpleDateFormat simpleDateFormat_date = new SimpleDateFormat("yy MM dd");
        final SimpleDateFormat simpleDateFormat_time = new SimpleDateFormat("HH mm ss");
        final String[] currentTime = new String[2];

        listOfMachine.add("GLYCOL");
        listOfMachine.add("CHILLER");
        listOfMachine.add("BCA");
        listOfMachine.add("COOLING TOWER");
        listOfMachine.add("IVAR");
        listOfMachine.add("MIURA 1");
        listOfMachine.add("MIURA 2");
        listOfMachine.add("MIURA 3");
        listOfMachine.add("CE2");
        listOfMachine.add("CE680");
        listOfMachine.add("CE46");
        listOfMachine.add("ZT22");
        listOfMachine.add("ZT37");
        listOfMachine.add("ZT55");
        listOfMachine.add("IR132K");
        listOfMachine.add("CO2 Evaporator");
        //-------
        databaseReference_QR = FirebaseDatabase.getInstance().getReference();

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
                                currentTime[0] = simpleDateFormat_date.format(Calendar.getInstance().getTime());
                                currentTime[1] = simpleDateFormat_time.format(Calendar.getInstance().getTime());
                                //Add to SQLite database
                                database.QueryData("INSERT INTO hourly_check VALUES(null, '" + currentTime[0] + "', '" + currentTime[1] + "', '" + thisBarCode.rawValue + "', '" + personCheck + "')");
                                //Add to firebase
                                //hourlyCheckTypeTmp = new hourlyCheckType(1, currentTime[0], currentTime[1], thisBarCode.rawValue, personCheck);
                                databaseReference_QR.child("Hourly check").child(currentTime[0]).child(thisBarCode.rawValue).child(currentTime[1]).setValue(0);
                                PauseCameraAndWaitTime(5);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_person_check, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuAdd){
            DialogAddPersonCheck();
        }

        return super.onOptionsItemSelected(item);
    }

    private void DialogAddPersonCheck(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_person_check);

        final EditText editTextAddPersonCheck = (EditText) dialog.findViewById(R.id.editTextAddPersonCheck);
        Button btnAdd = (Button) dialog.findViewById(R.id.buttonAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String personCheckInput = editTextAddPersonCheck.getText().toString();
                if(personCheckInput.equals("")){
                    Toast.makeText(QRcode.this, "Add person check name...", Toast.LENGTH_SHORT).show();
                } else {
                    personCheck = personCheckInput;
                    Toast.makeText(QRcode.this, "Added!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

}
