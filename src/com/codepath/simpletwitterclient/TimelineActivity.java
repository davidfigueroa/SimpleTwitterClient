package com.codepath.simpletwitterclient;

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
		
		populateTimeline();
	}
	
	private void populateTimeline() {
		TwitterClient client = SimpleTwitterClientApp.getRestClient();
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				//Log.d("debug", json.toString());
				adapter.addAll(Tweet.fromJSONArray(json));
			}
		});
	}
}
