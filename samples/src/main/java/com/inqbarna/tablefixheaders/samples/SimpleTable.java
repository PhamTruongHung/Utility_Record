package com.inqbarna.tablefixheaders.samples;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.samples.adapters.MatrixTableAdapter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

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
						"Header 1",
						"Header 2",
						"Header 3",
						"Header 4",
						"Header 5",
						"Header 6",
						"Header 6"},
					{
						"Lorem",
						"sed",
						"do",
						"eiusmod",
						"tempor",
						"incididunt",
						"Header 6"},
				 	{
				 		"Lorem",
						 "sed",
						 "do",
						 "eiusmod",
						 "tempor",
						 "incididunt",
						 "Header 6"},
		};

		Cursor dataFromDatabase = database.GetData("SELECT * FROM boilerData");
		while (dataFromDatabase.moveToNext()){
			Double apSuat = dataFromDatabase.getDouble(1);
			int id = dataFromDatabase.getInt(0);
		}


		TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
		MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<>(this, dataIntable);
		tableFixHeaders.setAdapter(matrixTableAdapter);
	}
}
