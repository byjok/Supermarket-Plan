package com.example.supermarket_plan;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Spinner;
import com.example.supermarket_plan.R;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	
	private DataHolder dataHolder; // contain all data for database logic
	private CustomListener onSelectedItemListener; // listener for onSelectedItem in Spinners
	
	private static final String SUPERMARKET_CITY = "SUPERMARKET_CITY";
	private static final String SUPERMARKET_NAME = "SUPERMARKET_NAME";
	private static final String SUPERMARKET_ADDRESS = "SUPERMARKET_ADDRESS";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dataHolder = new DataHolder();
		onSelectedItemListener = new CustomListener(dataHolder);
		
		setContentView(R.layout.activity_main);
		setSnipperListener();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		dataHolder.setSupermarketCity(savedInstanceState.getString(SUPERMARKET_CITY));
		dataHolder.setSupermarketName(savedInstanceState.getString(SUPERMARKET_NAME));
		dataHolder.setSupermarketAddress(savedInstanceState.getString(SUPERMARKET_ADDRESS));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(SUPERMARKET_CITY, dataHolder.getSupermarketCity());
		outState.putString(SUPERMARKET_NAME, dataHolder.getSupermarketName());
		outState.putString(SUPERMARKET_ADDRESS, dataHolder.getSupermarketAddress());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void setSnipperListener() {		
		((Spinner) findViewById(R.id.supermarket_city_spinner))
				.setOnItemSelectedListener(onSelectedItemListener);
		((Spinner) findViewById(R.id.supermarket_name_spinner))
				.setOnItemSelectedListener(onSelectedItemListener);
		((Spinner) findViewById(R.id.supermarket_address_spinner))
				.setOnItemSelectedListener(onSelectedItemListener);
	}
}
