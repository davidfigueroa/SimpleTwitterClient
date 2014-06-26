package com.codepath.simpletwitterclient.fragments;

import java.util.Collection;

import org.json.JSONArray;

import com.codepath.simpletwitterclient.EndlessScrollListener;
import com.codepath.simpletwitterclient.R;
import com.codepath.simpletwitterclient.SimpleTwitterClientApp;
import com.codepath.simpletwitterclient.TweetArrayAdapter;
import com.codepath.simpletwitterclient.TwitterClient;
import com.codepath.simpletwitterclient.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public abstract class TweetListFragment extends Fragment {
	private TweetArrayAdapter adapter;

	protected abstract void getTimeline(TwitterClient client, long fromThisTweetId, AsyncHttpResponseHandler handler);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweet_list, container, false);

		adapter = new TweetArrayAdapter(getActivity());
		ListView lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(adapter);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Tweet t = adapter.getItem(totalItemsCount - 1);
				populateTimeline(t.getUid());
			}
		});
		
		populateTimeline(0);
		return v;
	}

	public void insertTweet(Tweet tweet, int index) {
		adapter.insert(tweet, index);
	}

	private void populateTimeline(long fromThisTweetId) {
		TwitterClient client = SimpleTwitterClientApp.getRestClient();
		AsyncHttpResponseHandler handler = new JsonHttpResponseHandler() {
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
		};
		getTimeline(client, fromThisTweetId, handler);
	}
}
