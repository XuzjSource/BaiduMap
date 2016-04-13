package com.example.location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tvLocationPoint;
	double latitude;// 纬度
	double longitude;// 经度

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvLocationPoint = (TextView) findViewById(R.id.my_location_point);
		Intent intent = getIntent();
		if (intent.hasExtra("x") && intent.hasExtra("y")) {
			Bundle bundle = intent.getExtras();
			latitude = bundle.getDouble("x");
			longitude = bundle.getDouble("y");
			tvLocationPoint.setText("纬度:" + latitude + "经度:" + longitude);
		} else {
			tvLocationPoint.setText("纬度:无" + "经度:无");
		}

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.location_btn:
			startActivity(new Intent(this, MyLocation.class));
			break;
		case R.id.texthtml:
			startActivity(new Intent(this, HtmlView.class));
			break;
		case R.id.show_my_location_btn:
			Intent intent = new Intent(this, ShowMyLocation.class);
			Bundle bundle = new Bundle();
			bundle.putDouble("x", latitude);
			bundle.putDouble("y", longitude);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		}
	}
}
