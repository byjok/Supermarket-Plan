package com.example.supermarket_plan;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Spinner;
import com.example.supermarket_plan.R;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setSnipperListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void setSnipperListener() {
		CustomListener onSelectedItemListener = new CustomListener();
		((Spinner) findViewById(R.id.supermarket_city_spinner))
				.setOnItemSelectedListener(onSelectedItemListener);
		((Spinner) findViewById(R.id.supermarket_name_spinner))
				.setOnItemSelectedListener(onSelectedItemListener);
		((Spinner) findViewById(R.id.supermarket_address_spinner))
				.setOnItemSelectedListener(onSelectedItemListener);
	}
}
