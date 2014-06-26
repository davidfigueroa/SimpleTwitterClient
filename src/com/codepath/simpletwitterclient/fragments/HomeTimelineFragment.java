package com.codepath.simpletwitterclient.fragments;

import com.codepath.simpletwitterclient.TwitterClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class HomeTimelineFragment extends TweetListFragment {
	@Override
	protected void getTimeline(TwitterClient client, long fromThisTweetId, AsyncHttpResponseHandler handler) {
		client.getHomeTimeline(fromThisTweetId, handler);
	}
}
