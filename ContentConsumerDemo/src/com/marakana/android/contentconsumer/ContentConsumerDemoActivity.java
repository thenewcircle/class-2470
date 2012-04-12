package com.marakana.android.contentconsumer;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class ContentConsumerDemoActivity extends ListActivity {
	private static final String[] FROM = { Contacts.DISPLAY_NAME,
			Contacts.LAST_TIME_CONTACTED, Contacts._ID };
	private static final int[] TO = { R.id.text_name, R.id.text_value,
			R.id.text_id };
	private static final String selection = Contacts.HAS_PHONE_NUMBER + "=1";
	private static String[] selectionArgs = null;

	private Cursor cursor;
	private SimpleCursorAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		cursor = getContentResolver().query(Contacts.CONTENT_URI, null,
				selection, selectionArgs,
				Contacts.LAST_TIME_CONTACTED + " DESC");

		adapter = new SimpleCursorAdapter(this, R.layout.item, cursor, FROM, TO);
		adapter.setViewBinder(viewBinder);

		setListAdapter(adapter);
	}

	/** View binder to customize the binding between data and the view. */
	private ViewBinder viewBinder = new ViewBinder() {
		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

			if (view.getId() != R.id.text_value)
				// No custom binding necessary
				return false;

			// Do the custom binding
			CharSequence relTime = "";
			long timestamp = cursor.getLong(columnIndex);
			if (timestamp > 0) {
				relTime = DateUtils.getRelativeTimeSpanString(timestamp);
			}
			((TextView) view).setText(relTime);

			((TextView) view).append("\n"
					+ getContactData(cursor.getLong( cursor.getColumnIndex("_id"))));

			return true;
		}
	};

	/**
	 * Helper method to retrieve contact's data.
	 * 
	 * @param contactId
	 *            Contact's unique ID
	 * @return Data about this contact, including all phone numbers
	 */
	private String getContactData(long contactId) {
		Uri baseUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String[] projection = { CommonDataKinds.Phone.TYPE,
				CommonDataKinds.Phone.NUMBER };
		String selection = Data.CONTACT_ID + "=" + contactId;
		String[] selectionArgs = null;
		String orderBy = null;
		
		Log.d("ContentDemo", Long.toString(contactId));

		Cursor phoneCursor = getContentResolver().query(baseUri, projection,
				selection, selectionArgs, orderBy);

		int typeIdx = phoneCursor.getColumnIndex(CommonDataKinds.Phone.TYPE);
		int phoneIdx = phoneCursor.getColumnIndex(CommonDataKinds.Phone.NUMBER);

		StringBuilder builder = new StringBuilder();
		while (phoneCursor.moveToNext()) {
			int phoneTypeResource = CommonDataKinds.Phone
					.getTypeLabelResource(phoneCursor.getInt(typeIdx));
			String phoneType = getString(phoneTypeResource);
			String phoneNumber = phoneCursor.getString(phoneIdx);
			builder.append(phoneType).append(": ").append(phoneNumber)
					.append("\n\t");
		}
		return builder.toString();
	}
}