package com.example.supermarket_plan;

import java.io.Serializable;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class MainActivity extends Activity {
	private DataHolder dataHolder; // contain all data for databaseHelper logic
	private CustomListener customListener; // listener for Spinners & BUtton
	private DataBaseHelper databaseHelper;
	private AdapterHelper adapterHelper;

	private static final String CITY = "CITY";
	private static final String SUPERMARKET = "SUPERMARKET";
	private static final String ADDRESS = "ADDRESS";
	private static final String CITY_ID = "CITY_ID";
	private static final String SUPERMARKET_ID = "SUPERMARKET_ID";
	private static final String CITY_POS = "CITY_POS";
	private static final String SUPERMARKET_POS = "SUPERMARKET_POS";
	private static final String ADDRESS_POS = "ADDRESS_POS";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dataHolder = new DataHolder();
		restoreData(savedInstanceState);
		databaseHelper = new DataBaseHelper(this);
		adapterHelper = new AdapterHelper(this, databaseHelper, dataHolder);
		customListener = new CustomListener(dataHolder, adapterHelper);

		databaseHelper.createDataBase();
		databaseHelper.openDataBase();

		//if (dataHolder.getCity() == "") {
			adapterHelper.setCityAdapter();
		//}
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
		// dataHolder.setCity(savedInstanceState
		// .getString(CITY));
		// dataHolder.setSupermarket(savedInstanceState
		// .getString(SUPERMARKET));
		// dataHolder.setAddress(savedInstanceState
		// .getString(ADDRESS));
		// dataHolder.setCityId(savedInstanceState
		// .getInt(CITY_ID));
		// dataHolder.setSupermarketId(savedInstanceState
		// .getInt(SUPERMARKET_ID));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(CITY, dataHolder.getCity());
		outState.putString(SUPERMARKET, dataHolder.getSupermarket());
		outState.putString(ADDRESS, dataHolder.getAddress());
		outState.putInt(CITY_ID, dataHolder.getCityId());
		outState.putInt(SUPERMARKET_ID, dataHolder.getSupermarketId());
		outState.putInt(CITY_POS, dataHolder.getCityPos());
		outState.putInt(SUPERMARKET_POS, dataHolder.getSupermarketPos());
		outState.putInt(ADDRESS_POS, dataHolder.getAddressPos());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void setListener() {
		((Spinner) findViewById(R.id.city_spinner))
				.setOnItemSelectedListener(customListener);
		((Spinner) findViewById(R.id.supermarket_spinner))
				.setOnItemSelectedListener(customListener);
		((Spinner) findViewById(R.id.address_spinner))
				.setOnItemSelectedListener(customListener);
		((Button) findViewById(R.id.button_show))
				.setOnClickListener(customListener);
	}

	private void restoreData(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			dataHolder.setCity(savedInstanceState.getString(CITY));
			dataHolder
					.setSupermarket(savedInstanceState.getString(SUPERMARKET));
			dataHolder.setAddress(savedInstanceState.getString(ADDRESS));
			dataHolder.setCityId(savedInstanceState.getInt(CITY_ID));
			dataHolder.setSupermarketId(savedInstanceState
					.getInt(SUPERMARKET_ID));
			dataHolder.setCityPos(savedInstanceState.getInt(CITY_POS));
			dataHolder.setSupermarketPos(savedInstanceState.getInt(SUPERMARKET_POS));
			dataHolder.setAddressPos(savedInstanceState.getInt(ADDRESS_POS));
		}
	}
}
