package com.example.supermarket_plan;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class CustomListener implements OnItemSelectedListener {
	private static final String TAG = "CustomListener";

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {

		String parentInfo = ((Spinner) parent).getPrompt().toString();
		Log.d(TAG, "onItemSelected: " + parent.getSelectedItem().toString()
				+ " " + parentInfo);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
