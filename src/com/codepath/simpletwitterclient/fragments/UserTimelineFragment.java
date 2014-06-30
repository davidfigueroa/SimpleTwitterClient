package com.codepath.simpletwitterclient.fragments;

import com.codepath.simpletwitterclient.SimpleTwitterClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetListFragment {
	private long userId;
	
	@Override
	protected void getTimeline(long fromThisTweetId, JsonHttpResponseHandler handler) {
		SimpleTwitterClientApp.getRestClient().getUserTimeline(userId, fromThisTweetId, getActivity(), handler);
	}
	
	@Override
	protected boolean getNavigateToUserProfileOnImageClick() {
		return false;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
