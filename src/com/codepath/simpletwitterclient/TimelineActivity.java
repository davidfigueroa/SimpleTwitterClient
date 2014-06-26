package com.codepath.simpletwitterclient;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.simpletwitterclient.fragments.HomeTimelineFragment;
import com.codepath.simpletwitterclient.fragments.MentionsTimelineFragment;
import com.codepath.simpletwitterclient.fragments.TweetListFragment;
import com.codepath.simpletwitterclient.listeners.FragmentTabListener;
import com.codepath.simpletwitterclient.models.Tweet;
import com.codepath.simpletwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity {
	private static final int COMPOSE_TWEET = 1;
	private User loggedInUser;
	private TweetListFragment fragmentTweetList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		fragmentTweetList = (TweetListFragment) getSupportFragmentManager().findFragmentById(R.layout.fragment_tweet_list);
		loadLoggedInUserInfo();
		setupTabs();
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home_timeline)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home", HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions_timeline)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions", MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
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
						fragmentTweetList.insertTweet(tweet, 0);
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
