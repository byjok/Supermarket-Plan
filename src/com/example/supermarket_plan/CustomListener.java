package com.example.supermarket_plan;

import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class CustomListener implements OnItemSelectedListener, OnClickListener {
	private static final String TAG = "CustomListener";

	private DataHolder dataHolder; // contain all data for database logic
	private AdapterHelper adapterHelper; // contain methods to work with DB

	public CustomListener(DataHolder dataHolder, AdapterHelper adapterHelper) {
		this.dataHolder = dataHolder; // after changing rotation get new object
		this.adapterHelper = adapterHelper;
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {

		Spinner spinner = (Spinner) parent;
		String optionName = spinner.getPrompt().toString(); // City,Supermarket,Address
		Cursor selectedItem = (Cursor) spinner.getSelectedItem();
		selectedItem.moveToPosition(pos);

		int _id = selectedItem.getInt(0);
		String value = selectedItem.getString(1);

		switch (spinner.getId()) {
		case R.id.city_spinner:
			if (!dataHolder.getCity().equalsIgnoreCase(value)) {
				dataHolder.setCity(value); // city_name
				dataHolder.setCityId(_id); // _id
				dataHolder.setCityPos(pos);
				dataHolder.setSupermarketPos(0);
				dataHolder.setAddressPos(0);
				Log.d(TAG, "onItemSelected: " + optionName + " " + value);
			}
			adapterHelper.setSupermarketAdapter();
			break;
		case R.id.supermarket_spinner:
			if (!dataHolder.getSupermarket().equalsIgnoreCase(value)) {
				dataHolder.setSupermarket(value);
				dataHolder.setSupermarketId(_id);
				dataHolder.setSupermarketPos(pos);
				dataHolder.setAddressPos(0);
				Log.d(TAG, "onItemSelected: " + optionName + " " + value);
			}
			adapterHelper.setAddressAdapter();
			break;
		case R.id.address_spinner:
			if (!dataHolder.getAddress().equalsIgnoreCase(value)) {
				dataHolder.setAddress(value);
				dataHolder.setAddressPos(pos);
				Log.d(TAG, "onItemSelected: " + optionName + " " + value);
			}
			break;
		default:
			break;
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}

	// button show press
	public void onClick(View v) {
		Log.d(TAG, "onClick \"Show\"");
	}
}
