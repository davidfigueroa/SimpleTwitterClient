package com.codepath.simpletwitterclient.fragments;

import com.codepath.simpletwitterclient.TwitterClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MentionsTimelineFragment extends TweetListFragment {
	@Override
	protected void getTimeline(TwitterClient client, long fromThisTweetId, AsyncHttpResponseHandler handler) {
		client.getMentionsTimeline(fromThisTweetId, handler);
	}

	@Override
	protected boolean getNavigateToUserProfileOnImageClick() {
		return true;
	}
}
