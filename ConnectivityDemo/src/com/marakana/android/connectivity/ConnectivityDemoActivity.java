package com.marakana.android.connectivity;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class ConnectivityDemoActivity extends Activity {
	private TextView out;
	private WifiManager wifi;
	private WifiScanCompleteRecevier receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.main);
		wifi = (WifiManager) getSystemService(WIFI_SERVICE);
		out = (TextView) findViewById(R.id.out);
		
		receiver = new WifiScanCompleteRecevier();
		registerReceiver(receiver, new IntentFilter( WifiManager.SCAN_RESULTS_AVAILABLE_ACTION ));
	}

	/** Called when the button is clicked. */
	public void onClick(View v) {
		setProgressBarIndeterminateVisibility(true);

		wifi.startScan();
		out.setText("Output:\n\n");
		getConfiguredWifiNetworks();
		getAllNetworkInfo();
	}

	private void getConfiguredWifiNetworks() {
		
		List<WifiConfiguration> networks = wifi.getConfiguredNetworks();
		
		out.append("\nWifi Access Points:\n\n");
		for(WifiConfiguration config: networks) {
			out.append("\n\n"+config.toString());
		}
	}
	
	/** Prints info about all networks. */
	private void getAllNetworkInfo() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
		
		out.append("\nNetworks:\n\n");
		for(NetworkInfo info: networkInfos) {
			out.append("\n\n"+info.toString());
		}
	}
	
	/** Called when the wifi scan results are available. */
	class WifiScanCompleteRecevier extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			setProgressBarIndeterminateVisibility(false);

			List<ScanResult> results = wifi.getScanResults();
			
			out.append("\nWifi Scan Results:\n\n");
			for(ScanResult result: results) {
				out.append("\n\n"+result.toString());
			}
		}
		
	}
}
