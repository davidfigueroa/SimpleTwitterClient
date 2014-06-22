package com.codepath.simpletwitterclient;

import java.util.Collection;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.simpletwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private static final int COMPOSE_TWEET = 1;
	
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onComposeClick(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, COMPOSE_TWEET);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == COMPOSE_TWEET && resultCode == RESULT_OK) {
			//this.settings = (SettingData) data.getSerializableExtra(SettingsActivity.SettingDataKeyName);
		}
	}
}
