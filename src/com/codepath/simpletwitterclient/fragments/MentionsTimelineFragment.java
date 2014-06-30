package com.codepath.simpletwitterclient.fragments;

import com.codepath.simpletwitterclient.SimpleTwitterClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetListFragment {
	@Override
	protected void getTimeline(long fromThisTweetId, JsonHttpResponseHandler handler) {
		SimpleTwitterClientApp.getRestClient().getMentionsTimeline(fromThisTweetId, getActivity(), handler);
	}

	@Override
	protected boolean getNavigateToUserProfileOnImageClick() {
		return true;
	}
}
