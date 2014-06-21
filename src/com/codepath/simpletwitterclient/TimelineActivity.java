package com.codepath.simpletwitterclient;

import java.util.Collection;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.codepath.simpletwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private TweetArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		adapter = new TweetArrayAdapter(this);
		ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
		lvTweets.setAdapter(adapter);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Tweet t = adapter.getItem(totalItemsCount - 1);
				populateTimeline(t.getUid());
			}
		});
		
		populateTimeline(0);
	}
	
	private void populateTimeline(long fromThisTweetId) {
		TwitterClient client = SimpleTwitterClientApp.getRestClient();
		client.getHomeTimeline(fromThisTweetId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				Collection<? extends Tweet> tweets = Tweet.fromJSONArray(json);
				if (tweets != null && tweets.size() > 0) {
					adapter.addAll(tweets);
				}
			}
		});
	}
}
