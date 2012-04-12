package com.marakana.android.connectivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ConnectivityDemoActivity extends Activity {
	private TextView out;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		out = (TextView) findViewById(R.id.out);
	}

	public void onClick(View v) {
		
	}
}
