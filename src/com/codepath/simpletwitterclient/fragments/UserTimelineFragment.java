package com.codepath.simpletwitterclient.fragments;

import com.codepath.simpletwitterclient.TwitterClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class UserTimelineFragment extends TweetListFragment {
	private long userId;
	
	@Override
	protected void getTimeline(TwitterClient client, long fromThisTweetId, AsyncHttpResponseHandler handler) {
		client.getUserTimeline(userId, fromThisTweetId, handler);
	}
	
	@Override
	protected boolean getNavigateToUserProfileOnImageClick() {
		return false;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
