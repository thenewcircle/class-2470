package com.marakana.android.sensors;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorsDemoActivity extends Activity implements
		SensorEventListener {
	private SensorManager sensorManager;
	private Sensor sensor;
	private TextView out;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setup the UI
		setContentView(R.layout.main);
		out = (TextView) findViewById(R.id.out);

		// Setup Sensor Manager
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, sensor,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	// --- Sensor Manager Callbacks ---
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		out.setText(String.format("Azimuth: %3.0f \nPitch: %3.0f \nRoll: %3.0f",
				event.values[0], event.values[1], event.values[2]));
	}

}