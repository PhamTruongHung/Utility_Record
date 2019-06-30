package com.inqbarna.tablefixheaders.samples;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;


public class MainActivity extends ListActivity {

	Database database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		B b[] = new B[] {
				new B(getString(R.string.simple_adapter), SimpleTable.class),
				new B(getString(R.string.style_adapter), StyleTable.class),
				new B(getString(R.string.family_adapter), FamilyTable.class),
				new B("QRcode", QRcode.class),
		};
		setListAdapter(new ArrayAdapter<B>(this, android.R.layout.simple_list_item_1, android.R.id.text1, b));


		//Tao database cho toan bo he thong
		// Tao database boiler data
		database = new Database(this, "data.sqlite", null, 1);

		//Tao bang boiler data
		database.QueryData("CREATE TABLE IF NOT EXISTS boiler(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200), checkTime VARCHAR(200), pressureSteam DOUBLE)");

		//insert data

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
		String currentTime = simpleDateFormat.format(Calendar.getInstance().getTime());

		Log.d(TAG, "Time: " + currentTime);

		database.QueryData("INSERT INTO boiler VALUES(null, 'Ivar', '" + currentTime + "', 6.7)");
		//-------------------

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		startActivity(new Intent(this, ((B) l.getItemAtPosition(position)).class1));
	}

	private class B {
		private final String string;
		private final Class<? extends Activity> class1;

		B(String string, Class<? extends Activity> class1) {
			this.string = string;
			this.class1 = class1;
		}

		@Override
		public String toString() {
			return string;
		}
	}





}
