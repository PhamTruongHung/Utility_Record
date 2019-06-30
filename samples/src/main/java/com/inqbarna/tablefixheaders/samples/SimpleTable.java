package com.inqbarna.tablefixheaders.samples;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.samples.adapters.MatrixTableAdapter;
import com.inqbarna.tablefixheaders.samples.adapters.boilerDataType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class SimpleTable extends Activity {

	Database database;

	ArrayList<boilerDataType> boilerDataTypeArrayList;
	boilerDataType boilerDataTypeTmp;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

        // Database duoc tao o mainactivity nhung vao day phai goi lai moi chay duoc
		database = new Database(this, "data.sqlite", null, 1);

        //Tao array list boiler de nhan du lieu tu database
        boilerDataTypeArrayList = new ArrayList<>();

        Cursor dataFromDatabase = database.GetData("SELECT * FROM boiler");
        while (dataFromDatabase.moveToNext()){
            int id = dataFromDatabase.getInt(0);
            String name = dataFromDatabase.getString(1);
            String checkTime = dataFromDatabase.getString(2);
            Double apSuat = dataFromDatabase.getDouble(3);

            boilerDataTypeTmp = new boilerDataType(id, name, checkTime, apSuat);
            boilerDataTypeArrayList.add(boilerDataTypeTmp);

            Log.d(TAG, "onCreate:id " + id + " name: " + name + " checkTime: " + checkTime + " apSuat: " + apSuat);
        }

        //Lay data tu database de dua vao array list boiler
        int col = 0;
        int row = 0;
        int col_MAX = 4;

        String dataIntable[][] = new String[boilerDataTypeArrayList.size()][col_MAX]; // Cong them mot de chua cho cho header

        for (row = 0; row < boilerDataTypeArrayList.size(); row++){
            if (row == 0){
                dataIntable[row][0] = "ID";
                dataIntable[row][1] = "Name";
                dataIntable[row][2] = "Time";
                dataIntable[row][3] = "Pressure Steam";
            } else {
                dataIntable[row][0] = String.valueOf(boilerDataTypeArrayList.get(row).getId());
                dataIntable[row][1] = String.valueOf(boilerDataTypeArrayList.get(row).getName());
                dataIntable[row][2] = String.valueOf(boilerDataTypeArrayList.get(row).getCheckTime());
                dataIntable[row][3] = String.valueOf(boilerDataTypeArrayList.get(row).getPressureSteam());
            }
        }

        //Chuyen doi tu array list sang array two demension de dua vao ham.
//        String dataIntable[][] = new String[][]{
//                {
//                        "Location",
//                        "Time check",
//                        "Was ok?"},
//        };


		TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
		MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<>(this, dataIntable);
		tableFixHeaders.setAdapter(matrixTableAdapter);
	}
}
