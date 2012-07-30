package com.example.supermarket_plan;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class CustomListener implements OnItemSelectedListener {
	private static final String TAG = "CustomListener";

	private DataHolder dataHolder; // contain all data for database logic

	public CustomListener(DataHolder _dataHolder) {
		this.dataHolder = _dataHolder; // after changing rotation get new
										// _dataHolder
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {

		Spinner spinner = (Spinner) parent;
		String optionName = spinner.getPrompt().toString(); // City,Supermarket,Address
		String value = spinner.getSelectedItem().toString(); // selected value
																// string

		switch (spinner.getId()) {
		case R.id.supermarket_city_spinner:
			if (dataHolder.getSupermarketCity() != value) {
				dataHolder.setSupermarketCity(value);
				Log.d(TAG, "onItemSelected: " + optionName + " " + value);
			}
			break;
		case R.id.supermarket_name_spinner:
			if (dataHolder.getSupermarketName() != value) {
				dataHolder.setSupermarketName(value);
				Log.d(TAG, "onItemSelected: " + optionName + " " + value);
			}
			break;
		case R.id.supermarket_address_spinner:
			if (dataHolder.getSupermarketAddress() != value) {
				dataHolder.setSupermarketAddress(value);
				Log.d(TAG, "onItemSelected: " + optionName + " " + value);
			}
			break;
		default:
			break;
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}
