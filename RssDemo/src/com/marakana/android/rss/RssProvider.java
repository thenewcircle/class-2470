package com.marakana.android.rss;

import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.marakana.java.android.parser.FeedParser;
import com.marakana.java.android.parser.FeedParserFactory;
import com.marakana.java.android.parser.ParserType;
import com.marakana.java.android.parser.Post;

public class RssProvider extends ContentProvider {
	private static final String FEED_URL = "http://marakana.com/s/feed.rss";
	private static final UriMatcher URI_MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);
	private static final int POST_DIR = 1;
	private static final int POST_ITEM = 2;
	static {
		URI_MATCHER.addURI(RssContract.AUTHORITY, RssContract.PATH, POST_DIR);
		URI_MATCHER.addURI(RssContract.AUTHORITY, RssContract.PATH + "/#",
				POST_ITEM);
	}
	private static final String[] COLUMNS = { RssContract.Columns._ID,
			RssContract.Columns.TITLE, RssContract.Columns.LINK,
			RssContract.Columns.DESCRIPTION, RssContract.Columns.DATE };

	private FeedParser feed;

	/** Called only first time when the provider is created. */
	@Override
	public boolean onCreate() {
		feed = FeedParserFactory.getParser(FEED_URL, ParserType.SAX);
		return (feed != null) ? true : false;
	}

	@Override
	public String getType(Uri uri) {
		switch (URI_MATCHER.match(uri)) {
		case POST_DIR:
			return RssContract.CONTENT_TYPE;
		case POST_ITEM:
			return RssContract.CONTENT_ITEM_TYPE;
		}
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		MatrixCursor cursor = new MatrixCursor(COLUMNS);

		// Get all the posts from the feed
		List<Post> posts;
		try {
			posts = feed.parse();
		} catch (Exception e) {
			return null;
		}

		// Copy posts to cursor
		for (Post post : posts) {
			cursor.newRow().add(post.hashCode()).add(post.getTitle())
					.add(post.getLink()).add(post.getDescription())
					.add(post.getDate());
		}

		return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
