package com.inqbarna.tablefixheaders.samples;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.samples.adapters.MatrixTableAdapter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SimpleTable extends Activity {

	Database database;

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

		//select data
		int col = 0;
		int row = 0;

		 String dataIntable[][] = new String[][]{
					{
						"Location",
						"Time check",
						"Was ok?"},
		};

        ArrayList<String> list_1 = new ArrayList<String>();
        ArrayList<String> list_2 = new ArrayList<String>();
        ArrayList<String> list_3 = new ArrayList<String>();

        ArrayList<ArrayList<String>> listAll = new ArrayList<ArrayList<String>>();

        list_1.add("Hau");
        list_1.add("Hau");
        list_1.add("Uti");

        list_2.add("Hau");
        list_2.add("Hau");
        list_2.add("Uti");

        list_3.add("Hau");
        list_3.add("Hau");
        list_3.add("Uti");

        listAll.add(list_1);
        listAll.add(list_2);
        listAll.add(list_3);

		String[][] testTable;
		testTable = new String[3][3];

		//testTable = (String[][]) listAll.toArray();

		//Log.d(TAG, "onCreate: " + testTable);

		//dataIntable = (String[][]) listAll.toArray();

        ///

		Cursor dataFromDatabase = database.GetData("SELECT * FROM boilerData");
		while (dataFromDatabase.moveToNext()){
			col = 0;
			Double apSuat = dataFromDatabase.getDouble(1);
			int id = dataFromDatabase.getInt(0);
		}


		TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
		MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<>(this, dataIntable);
		tableFixHeaders.setAdapter(matrixTableAdapter);
	}
}
