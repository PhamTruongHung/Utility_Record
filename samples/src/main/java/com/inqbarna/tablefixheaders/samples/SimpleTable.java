package com.inqbarna.tablefixheaders.samples;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.samples.adapters.MatrixTableAdapter;
import com.inqbarna.tablefixheaders.samples.adapters.boilerDataType;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.sql.Date;
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

        // Tao database boiler data
		database = new Database(this, "boilerData.sqlite", null, 1);

		//Tao bang boiler data
		database.QueryData("CREATE TABLE IF NOT EXISTS boilerData(Id INTEGER PRIMARY KEY AUTOINCREMENT, pressureSteam DOUBLE)");

		//insert data
		database.QueryData("INSERT INTO boilerData VALUES(null, 3.2)");

        //Lay data tu database de dua vao array list boiler
        int col = 0;
        int row = 0;
        int col_MAX = 4;

        Cursor dataFromDatabase = database.GetData("SELECT * FROM boilerData");
        while (dataFromDatabase.moveToNext()){
            Double apSuat = dataFromDatabase.getDouble(1);
            int id = dataFromDatabase.getInt(0);
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = simpleDateFormat.format(Calendar.getInstance().getTime());

        Log.d(TAG, "Time: " + currentTime);

        //Tao array list boiler
        boilerDataTypeArrayList = new ArrayList<>();

        for (int i = 0; i < 20; i++){
            boilerDataTypeTmp = new boilerDataType(i, ((String) ("Ivar_" + i)), currentTime, 6.7);
            boilerDataTypeArrayList.add(boilerDataTypeTmp);

            Log.d(TAG, "id: " + boilerDataTypeArrayList.get(i).getId() + " 	name: " + boilerDataTypeArrayList.get(i).getName() + " 	size_list: " + boilerDataTypeArrayList.size());
        }

        //Date currentTime = (Date) Calendar.getInstance().getTime();
        //Log.d(TAG, "Time: " + currentTime);

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
