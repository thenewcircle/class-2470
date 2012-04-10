package com.marakana.android.rss;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.marakana.java.android.parser.FeedParser;
import com.marakana.java.android.parser.FeedParserFactory;
import com.marakana.java.android.parser.ParserType;
import com.marakana.java.android.parser.Post;

public class RssDemoActivity extends Activity {
	private String feedUrl = "http://marakana.com/s/feed.rss";
	private TextView out;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Workaround for Intel proxy for emulator
		// System.setProperty("http.proxyHost", "proxy.rr.intel.com");
		// System.setProperty("http.proxyPort", "911");

		// Setup UI
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.main);
		out = (TextView) findViewById(R.id.out);

		// Parse the feed
		new FeedParserTask().execute(feedUrl);
	}

	/**
	 * AsycTask that processes parsing of the feed on separate worker thread.
	 * Input is the feed URL string, progress is ignored and output is number of
	 * new feeds.
	 */
	class FeedParserTask extends AsyncTask<String, Void, List<Post>> {

		/** Happens on the UI thread before the background task starts. */
		@Override
		protected void onPreExecute() {
			// Start the progress bar
			setProgressBarIndeterminateVisibility(true);
		}

		/** Background work to be done on a separate thread. */
		@Override
		protected List<Post> doInBackground(String... params) {
			// Get the feed from the parser factory
			
			// TODO Handle the exception in case we can't parse the feed url
			FeedParser feed = FeedParserFactory.getParser(params[0],
					ParserType.SAX);
			return feed.parse();
		}

		/**
		 * Work that happens once we are done with the background task. It
		 * executes on the main/UI thread.
		 */
		@Override
		protected void onPostExecute(List<Post> posts) {
			for (Post post : posts) {
				out.append(post.getTitle() + "\n");
			}
			// Stop the progress bar
			setProgressBarIndeterminateVisibility(false);
		}

	}
}