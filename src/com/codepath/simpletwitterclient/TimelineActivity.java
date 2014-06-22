package com.codepath.simpletwitterclient;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.simpletwitterclient.models.Tweet;
import com.codepath.simpletwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private static final int COMPOSE_TWEET = 1;
	private User loggedInUser;
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
		loadLoggedInUserInfo();
	}
	
	private void loadLoggedInUserInfo() {
		TwitterClient client = SimpleTwitterClientApp.getRestClient();
		client.getAccoutCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				loggedInUser = User.fromJSON(json);
			}
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				arg0.printStackTrace();
			}
		});
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
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				arg0.printStackTrace();
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
		i.putExtra(ComposeActivity.EXTRA_USER, loggedInUser);
		startActivityForResult(i, COMPOSE_TWEET);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == COMPOSE_TWEET && resultCode == RESULT_OK) {
			String tweet = data.getStringExtra(ComposeActivity.EXTRA_TWEET);
			
			TwitterClient client = SimpleTwitterClientApp.getRestClient();
			client.postStatus(tweet, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject json) {
					Tweet tweet = Tweet.fromJSON(json);
					if (tweet != null) {
						adapter.insert(tweet, 0);
					}
				}
				@Override
				public void onFailure(Throwable arg0, String arg1) {
					arg0.printStackTrace();
				}
			});
		}
	}
}
