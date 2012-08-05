package com.example.supermarket_plan;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.Spinner;
import static com.example.supermarket_plan.Constants.CITY;
import static com.example.supermarket_plan.Constants.SUPERMARKET;
import static com.example.supermarket_plan.Constants.ADDRESS;
import static com.example.supermarket_plan.Constants.PLAN_PATH;
import static com.example.supermarket_plan.Constants.CITY_ID;
import static com.example.supermarket_plan.Constants.SUPERMARKET_ID;
import static com.example.supermarket_plan.Constants.CITY_POS;
import static com.example.supermarket_plan.Constants.SUPERMARKET_POS;
import static com.example.supermarket_plan.Constants.ADDRESS_POS;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	
	private DataHolder dataHolder; // contain all data for databaseHelper logic
	private CustomListener customListener; // listener for Spinners & BUtton
	private DatabaseHelper databaseHelper;
	private AdapterHelper adapterHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dataHolder = new DataHolder();
		restoreData(savedInstanceState); // restore data to dataHolder

		databaseHelper = new DatabaseHelper(this);
		adapterHelper = new AdapterHelper(this, databaseHelper, dataHolder);
		customListener = new CustomListener(dataHolder, adapterHelper, this);

		databaseHelper.createDataBase();
		databaseHelper.openDataBase();

		adapterHelper.setCityAdapter();
		setListener();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
		databaseHelper.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "OnResume");
		databaseHelper.openDataBase();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(CITY, dataHolder.getCity());
		outState.putString(SUPERMARKET, dataHolder.getSupermarket());
		outState.putString(ADDRESS, dataHolder.getAddress());
		outState.putString(PLAN_PATH, dataHolder.getPlanPath());
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
			dataHolder.setPlanPath(savedInstanceState.getString(PLAN_PATH));
			dataHolder.setCityId(savedInstanceState.getInt(CITY_ID));
			dataHolder.setSupermarketId(savedInstanceState
					.getInt(SUPERMARKET_ID));
			dataHolder.setCityPos(savedInstanceState.getInt(CITY_POS));
			dataHolder.setSupermarketPos(savedInstanceState
					.getInt(SUPERMARKET_POS));
			dataHolder.setAddressPos(savedInstanceState.getInt(ADDRESS_POS));
		}
	}
}
