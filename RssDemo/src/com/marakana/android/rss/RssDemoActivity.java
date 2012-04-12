package com.marakana.android.rss;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.widget.SimpleCursorAdapter;

public class RssDemoActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	private static final String[] FROM = { RssContract.Columns.TITLE };
	private static final int[] TO = { android.R.id.text1 };
	private SimpleCursorAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		// Setup the adapter
		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, null, FROM, TO);
		setListAdapter(adapter);
		
		// Initialize the loader
		getLoaderManager().initLoader(47, null, this);
	}

	// --- LoaderManager.LoaderCallbacks<Cursor> callbacks ---
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		setProgressBarIndeterminateVisibility(true);
		return new CursorLoader(this, RssContract.CONTENT_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		setProgressBarIndeterminateVisibility(false);
		adapter.changeCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		setProgressBarIndeterminateVisibility(false);
		adapter.changeCursor(null);
	}
}
