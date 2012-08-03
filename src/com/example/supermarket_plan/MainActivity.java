package com.example.supermarket_plan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends Activity {
	private DataHolder dataHolder; // contain all data for databaseHelper logic
	private CustomListener customListener; // listener for Spinners & BUtton
	private DataBaseHelper databaseHelper;
	private AdapterHelper adapterHelper;

	private static final String SUPERMARKET_CITY = "SUPERMARKET_CITY";
	private static final String SUPERMARKET_NAME = "SUPERMARKET_NAME";
	private static final String SUPERMARKET_ADDRESS = "SUPERMARKET_ADDRESS";
	private static final String SUPERMARKET_CITY_ID = "SUPERMARKET_CITY_ID";
	private static final String SUPERMARKET_NAME_ID = "SUPERMARKET_NAME_ID";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dataHolder = new DataHolder();
		databaseHelper = new DataBaseHelper(this);
		adapterHelper = new AdapterHelper(this, databaseHelper, dataHolder);
		customListener = new CustomListener(dataHolder, adapterHelper);

		databaseHelper.createDataBase();
		databaseHelper.openDataBase();

		adapterHelper.setCityAdapter();
		setListener();
	}

	@Override
	protected void onPause() {
		super.onPause();
		databaseHelper.close();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		dataHolder.setSupermarketCity(savedInstanceState
				.getString(SUPERMARKET_CITY));
		dataHolder.setSupermarketName(savedInstanceState
				.getString(SUPERMARKET_NAME));
		dataHolder.setSupermarketAddress(savedInstanceState
				.getString(SUPERMARKET_ADDRESS));
		dataHolder.setSupermarketCityId(savedInstanceState
				.getInt(SUPERMARKET_CITY_ID));
		dataHolder.setSupermarketNameId(savedInstanceState
				.getInt(SUPERMARKET_NAME_ID));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(SUPERMARKET_CITY, dataHolder.getSupermarketCity());
		outState.putString(SUPERMARKET_NAME, dataHolder.getSupermarketName());
		outState.putString(SUPERMARKET_ADDRESS,
				dataHolder.getSupermarketAddress());
		outState.putInt(SUPERMARKET_CITY_ID, dataHolder.getSupermarketCityId());
		outState.putInt(SUPERMARKET_NAME_ID, dataHolder.getSupermarketNameId());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void setListener() {
		((Spinner) findViewById(R.id.supermarket_city_spinner))
				.setOnItemSelectedListener(customListener);
		((Spinner) findViewById(R.id.supermarket_name_spinner))
				.setOnItemSelectedListener(customListener);
		((Spinner) findViewById(R.id.supermarket_address_spinner))
				.setOnItemSelectedListener(customListener);
		((Button) findViewById(R.id.button_show))
				.setOnClickListener(customListener);
	}
}
