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
	private String feedUrl = "http://rss.cnn.com/rss/cnn_topstories.rss";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Get the feed from the parser factory
        feed = FeedParserFactory.getParser(feedUrl, ParserType.SAX);
        
        // Parse the feed
        List<Post> posts = feed.parse();
        for( Post post: posts ) {
        		Log.d("RssDemo", post.toString());
        }
    }
}