package com.codepath.simpletwitterclient;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.simpletwitterclient.fragments.HomeTimelineFragment;
import com.codepath.simpletwitterclient.fragments.MentionsTimelineFragment;
import com.codepath.simpletwitterclient.fragments.TweetListFragment;
import com.codepath.simpletwitterclient.models.Tweet;
import com.codepath.simpletwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity {
	private static final int COMPOSE_TWEET = 1;
	private User loggedInUser;
	private TweetListFragment fragmentHomeTimeline, fragmentMentions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		//load info of logged in user
		loadLoggedInUserInfo();
		
		//instantiate main fragment to prevent race conditions
		fragmentHomeTimeline = new HomeTimelineFragment();
		
		//setuo view pager of tabs
		ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
		ContentPagerAdapter adapterViewPager = new ContentPagerAdapter(getSupportFragmentManager());
		vpPager.setAdapter(adapterViewPager);	
	}
	
	private void loadLoggedInUserInfo() {
		TwitterClient client = SimpleTwitterClientApp.getRestClient();
		client.getAccoutCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				loggedInUser = User.fromJSON(json);
			}
			@Override
			public void onFailure(Throwable t, String arg1) {
				t.printStackTrace();
				Toast.makeText(TimelineActivity.this, "Error loading logged in user info. " + t.getMessage(), Toast.LENGTH_LONG).show();
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
			Tweet tweet = (Tweet) data.getSerializableExtra(ComposeActivity.EXTRA_TWEET);
			fragmentHomeTimeline.insertTweet(tweet, 0);
		}
	}
	
	public class ContentPagerAdapter extends FragmentPagerAdapter {
		public ContentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			if (arg0 == 0) return fragmentHomeTimeline;
			if (fragmentMentions == null) {
				fragmentMentions = new MentionsTimelineFragment();
			}
			return fragmentMentions;
		}

		@Override
		public int getCount() {
			return 2;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			if (position == 0) return "Home";
			return "Mentions";
		}
	}
}
