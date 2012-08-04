package com.example.supermarket_plan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import static com.example.supermarket_plan.Constants.CITY_ID;
import static com.example.supermarket_plan.Constants.SUPERMARKET_ID;
import static com.example.supermarket_plan.Constants.PLAN_PATH;

public class CustomListener implements OnItemSelectedListener, OnClickListener {
	private static final String TAG = "CustomListener";
	
	private DataHolder dataHolder; // contain all data for database logic
	private AdapterHelper adapterHelper; // contain methods to work with DB
	private Context context;

	public CustomListener(DataHolder dataHolder, AdapterHelper adapterHelper, Context context) {
		this.dataHolder = dataHolder; // after changing rotation get new object
		this.adapterHelper = adapterHelper;
		this.context = context;
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
				dataHolder.setPlanPath(selectedItem.getString(2));
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
		Intent intent = new Intent(context, PlanActivity.class);
		intent.putExtra(CITY_ID, dataHolder.getCityId());
		intent.putExtra(SUPERMARKET_ID, dataHolder.getSupermarketId());
		intent.putExtra(PLAN_PATH, dataHolder.getPlanPath());
		((Activity)context).startActivity(intent);
		Log.d(TAG, "onClick \"Show\"");
	}
}
