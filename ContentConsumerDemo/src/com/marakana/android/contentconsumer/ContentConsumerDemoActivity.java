package com.marakana.android.contentconsumer;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContentConsumerDemoActivity extends Activity {
	private static final String[] FROM = { Settings.NameValueTable.NAME, Settings.NameValueTable.VALUE };
	private static final int[] TO = { android.R.id.text1, android.R.id.text2 };
	private ListView list;
	private Cursor cursor;
	private SimpleCursorAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		cursor = getContentResolver().query(Settings.System.CONTENT_URI, null,
				null, null, null);

		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, FROM, TO);

		list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);
	}
}