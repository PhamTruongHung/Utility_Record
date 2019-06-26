package com.inqbarna.tablefixheaders.samples;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.samples.adapters.MatrixTableAdapter;

import android.app.Activity;
import android.os.Bundle;

public class SimpleTable extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

		TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
		MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<>(this, new String[][]{
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
						"ipsum",
						"irure",
						"occaecat",
						"enim",
						"laborum",
						"reprehenderit",
						"Header 6"},
				{
						"dolor",
						"fugiat",
						"nulla",
						"reprehenderit",
						"laborum",
						"consequat",
						"Header 6"},
				{
						"sit",
						"consequat",
						"laborum",
						"fugiat",
						"eiusmod",
						"enim",
						"Header 6"},
				{
						"amet",
						"nulla",
						"Excepteur",
						"voluptate",
						"occaecat",
						"et",
						"Header 6"},
				{
						"consectetur",
						"occaecat",
						"fugiat",
						"dolore",
						"consequat",
						"eiusmod",
						"Header 6"},
				{
						"adipisicing",
						"fugiat",
						"Excepteur",
						"occaecat",
						"fugiat",
						"laborum",
						"Header 6"},
				{
						"elit",
						"voluptate",
						"reprehenderit",
						"Excepteur",
						"fugiat",
						"nulla",
						"Header 6"},
				{
						"elit",
						"voluptate",
						"reprehenderit",
						"Excepteur",
						"fugiat",
						"nulla",
						"Header 6"},
				{
						"elit",
						"voluptate",
						"reprehenderit",
						"Excepteur",
						"fugiat",
						"nulla",
						"Header 6"},
				{
						"elit",
						"voluptate",
						"reprehenderit",
						"Excepteur",
						"fugiat",
						"nulla",
						"Header 6"},
		});
		tableFixHeaders.setAdapter(matrixTableAdapter);
	}
}
