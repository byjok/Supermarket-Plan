package com.example.supermarket_plan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import static com.example.supermarket_plan.Constants.CITY_ID;
import static com.example.supermarket_plan.Constants.SUPERMARKET_ID;
import static com.example.supermarket_plan.Constants.PLAN_PATH;

public class PlanActivity extends Activity {
	
	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan);
		Intent intent = getIntent();
		int cityId = intent.getIntExtra(CITY_ID, 0);
		int supermarketId = intent.getIntExtra(SUPERMARKET_ID, 0);
		String planPath = intent.getStringExtra(PLAN_PATH);
		int id = getResources().getIdentifier(planPath, "drawable", getPackageName());
		ImageView iv = (ImageView)findViewById(R.id.imageView);
		iv.setImageResource(id);
	}
}
