package com.marakana.android.rss;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.marakana.java.android.parser.FeedParser;
import com.marakana.java.android.parser.FeedParserFactory;
import com.marakana.java.android.parser.ParserType;
import com.marakana.java.android.parser.Post;

public class RssDemoActivity extends Activity {
	private FeedParser feed;
	private String feedUrl = "http://marakana.com/s/feed.rss";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Workaround for Intel proxy for emulator
        System.setProperty("http.proxyHost", "proxy.rr.intel.com");
        System.setProperty("http.proxyPort", "911");
        
        setContentView(R.layout.main);
        
        // Get the feed from the parser factory
        feed = FeedParserFactory.getParser(feedUrl, ParserType.SAX);
        
        // Parse the feed
        new ParseFeedThread().start();
    }
    
    class ParseFeedThread extends Thread {
    		public void run() {
    	        List<Post> posts = feed.parse();
    	        for( Post post: posts ) {
    	        		Log.d("RssDemo", post.getTitle() );
    	        }
    		}
    }
}