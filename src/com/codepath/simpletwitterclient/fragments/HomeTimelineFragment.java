package com.codepath.simpletwitterclient.fragments;

import com.codepath.simpletwitterclient.SimpleTwitterClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetListFragment {
	@Override
	protected void getTimeline(long fromThisTweetId, JsonHttpResponseHandler handler) {
		SimpleTwitterClientApp.getRestClient().getHomeTimeline(fromThisTweetId, getActivity(), handler);
	}

	@Override
	protected boolean getNavigateToUserProfileOnImageClick() {
		return true;
	}
}
