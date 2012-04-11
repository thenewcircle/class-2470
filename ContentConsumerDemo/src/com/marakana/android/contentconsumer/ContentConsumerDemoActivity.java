package com.marakana.android.contentconsumer;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.SimpleCursorAdapter;

public class ContentConsumerDemoActivity extends ListActivity {
	private static final String[] FROM = { Settings.NameValueTable.NAME,
			Settings.NameValueTable.VALUE, Settings.NameValueTable._ID };
	private static final int[] TO = { R.id.text_name, R.id.text_value,
			R.id.text_id };
	private Cursor cursor;
	private SimpleCursorAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		cursor = getContentResolver().query(Settings.System.CONTENT_URI, null,
				null, null, null);

		adapter = new SimpleCursorAdapter(this, R.layout.item, cursor, FROM, TO);

		setListAdapter(adapter);
	}
}