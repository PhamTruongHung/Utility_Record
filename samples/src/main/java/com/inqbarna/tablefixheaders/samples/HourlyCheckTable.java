package com.inqbarna.tablefixheaders.samples;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.samples.adapters.MatrixTableAdapter;
import com.inqbarna.tablefixheaders.samples.adapters.boilerDataType;
import com.inqbarna.tablefixheaders.samples.adapters.hourlyCheckType;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class HourlyCheckTable extends Activity {

    Database database;

    ArrayList<hourlyCheckType> hourlyCheckTypeArrayList;
    hourlyCheckType hourlyCheckTypeTmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

        // Database duoc tao o mainactivity nhung vao day phai goi lai moi chay duoc
        database = new Database(this, "data.sqlite", null, 1);

        //Tao array list boiler de nhan du lieu tu database
        hourlyCheckTypeArrayList = new ArrayList<>();

        Cursor dataFromDatabase = database.GetData("SELECT * FROM hourly_check");
        while (dataFromDatabase.moveToNext()){
            int id = dataFromDatabase.getInt(0);
            String dateOfCheck = dataFromDatabase.getString(1);
            String timeOfCheck = dataFromDatabase.getString(2);
            String location = dataFromDatabase.getString(3);
            String personCheck = dataFromDatabase.getString(4);

            hourlyCheckTypeTmp = new hourlyCheckType(id, dateOfCheck, timeOfCheck, location, personCheck);
            hourlyCheckTypeArrayList.add(hourlyCheckTypeTmp);

            Log.d(TAG, "onCreate:id " + id + " dateOfCheck: " + dateOfCheck + " timeOfCheck: " + timeOfCheck + " location: " + location + " personCheck: " + personCheck);
        }

        //Lay data tu database de dua vao array list boiler
        int col = 0;
        int row = 0;
        int col_MAX = 5;

        String dataIntable[][] = new String[hourlyCheckTypeArrayList.size()][col_MAX]; // Cong them mot de chua cho cho header

        for (row = 0; row < hourlyCheckTypeArrayList.size(); row++){
            if (row == 0){
                dataIntable[row][0] = "ID";
                dataIntable[row][1] = "Date";
                dataIntable[row][2] = "Time";
                dataIntable[row][3] = "Location";
                dataIntable[row][4] = "Person";
            } else {
                dataIntable[row][0] = String.valueOf(hourlyCheckTypeArrayList.get(row).getId());
                dataIntable[row][1] = String.valueOf(hourlyCheckTypeArrayList.get(row).getDateOfCheck());
                dataIntable[row][2] = String.valueOf(hourlyCheckTypeArrayList.get(row).getTimeOfCheck());
                dataIntable[row][3] = String.valueOf(hourlyCheckTypeArrayList.get(row).getLocation());
                dataIntable[row][4] = String.valueOf(hourlyCheckTypeArrayList.get(row).getPersonCheck());
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
