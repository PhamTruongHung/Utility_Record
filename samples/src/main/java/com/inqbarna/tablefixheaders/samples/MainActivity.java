package com.inqbarna.tablefixheaders.samples;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inqbarna.tablefixheaders.samples.adapters.hourlyCheckType;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;


public class MainActivity extends ListActivity {

	Database database;

	//DatabaseReference databaseReference;

	//hourlyCheckType hourlyCheckTypeTmp = new hourlyCheckType(1, "19.07.04", "23:02:04", "Miura 3", "Hung");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		B b[] = new B[] {
				new B(getString(R.string.style_adapter), StyleTable.class),
				new B(getString(R.string.family_adapter), FamilyTable.class),
				new B("QRcode", QRcode.class),
				new B("Hourly Check", HourlyCheckTable.class),
		};
		setListAdapter(new ArrayAdapter<B>(this, android.R.layout.simple_list_item_1, android.R.id.text1, b));


		//Tao database cho toan bo he thong
		// Tao database
		database = new Database(this, "data.sqlite", null, 1);
		//Tao bang boiler data
		database.QueryData("CREATE TABLE IF NOT EXISTS boiler(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200), checkTime VARCHAR(200), pressureSteam DOUBLE)");
		//Tao bang hourly_check
		database.QueryData("CREATE TABLE IF NOT EXISTS hourly_check(Id INTEGER PRIMARY KEY AUTOINCREMENT, dateOfCheck VARCHAR(200), timeOfCheck VARCHAR(200), location VARCHAR(200), personCheck VARCHAR(200))");
		//-------------------

//		databaseReference = FirebaseDatabase.getInstance().getReference();
//
//		databaseReference.child("Utility").child("Miura 2").child("Steam pressure").setValue(6.7);
//		databaseReference.child("Hourly check").setValue(hourlyCheckTypeTmp);
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
