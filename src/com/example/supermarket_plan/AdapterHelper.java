package com.example.supermarket_plan;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class AdapterHelper {
	private Context context;
	private DataBaseHelper databaseHelper;
	private DataHolder dataHolder;

	public AdapterHelper(Context context, DataBaseHelper databaseHelper,
			DataHolder dataHolder) {
		this.context = context;
		this.databaseHelper = databaseHelper;
		this.dataHolder = dataHolder;
	}

	@SuppressWarnings("deprecation")
	public SimpleCursorAdapter getCityAdapter() {
		Cursor cursor = databaseHelper.getCityTable();
		String[] from = new String[] { DataBaseHelper.CITY_NAME };
		int[] to = new int[] { android.R.id.text1 }; // from
														// simple_spinner_dropdown_item
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
				android.R.layout.simple_spinner_item, cursor, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	public SimpleCursorAdapter getSupermarketAdapter() {
		Cursor cursor = databaseHelper.getSupermarketTable(dataHolder
				.getCityId());
		String[] from = new String[] { DataBaseHelper.SUPERMARKET_NAME };
		int[] to = new int[] { android.R.id.text1 }; // from
														// simple_spinner_dropdown_item
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
				android.R.layout.simple_spinner_item, cursor, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	@SuppressWarnings("deprecation")
	public SimpleCursorAdapter getAddressAdapter() {
		Cursor cursor = databaseHelper.getAddressTable(
				dataHolder.getCityId(),
				dataHolder.getSupermarketId());
		String[] from = new String[] { DataBaseHelper.ADDRESS_NAME };
		int[] to = new int[] { android.R.id.text1 }; // from
														// simple_spinner_dropdown_item
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
				android.R.layout.simple_spinner_item, cursor, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	public void setCityAdapter() {
		((Spinner) (((Activity) context)
				.findViewById(R.id.city_spinner)))
				.setAdapter(getCityAdapter());
	}

	public void setSupermarketAdapter() {
		Spinner spinner = (Spinner)((Activity) context).findViewById(R.id.supermarket_spinner);
		spinner.setAdapter(getSupermarketAdapter());
		if(dataHolder.getSupermarketPos() != 0){
			spinner.setSelection(dataHolder.getSupermarketPos(), true);
		}
		spinner.setEnabled(true);
	}
	
	public void setAddressAdapter() {
		Spinner spinner = (Spinner)((Activity) context).findViewById(R.id.address_spinner);
		spinner.setAdapter(getAddressAdapter());
		if(dataHolder.getAddressPos() != 0){
			spinner.setSelection(dataHolder.getAddressPos(), true);
		}
		spinner.setEnabled(true);
	}
}
