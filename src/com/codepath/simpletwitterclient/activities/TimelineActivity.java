package com.codepath.simpletwitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.simpletwitterclient.R;
import com.codepath.simpletwitterclient.fragments.HomeTimelineFragment;
import com.codepath.simpletwitterclient.fragments.MentionsTimelineFragment;
import com.codepath.simpletwitterclient.fragments.TweetListFragment;
import com.codepath.simpletwitterclient.models.Tweet;

public class TimelineActivity extends FragmentActivity {
	private static final int COMPOSE_TWEET = 1;
	private static final int USER_PROFILE = 2;
	private TweetListFragment fragmentHomeTimeline, fragmentMentions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		//instantiate main fragment to prevent race conditions
		fragmentHomeTimeline = new HomeTimelineFragment();
		
		//Setup view pager of tabs
		ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
		ContentPagerAdapter adapterViewPager = new ContentPagerAdapter(getSupportFragmentManager());
		vpPager.setAdapter(adapterViewPager);	
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
	
	public void onUserProfileClick(MenuItem mi) {
		Intent i = new Intent(this, UserProfileActivity.class);
		startActivityForResult(i, USER_PROFILE);
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
