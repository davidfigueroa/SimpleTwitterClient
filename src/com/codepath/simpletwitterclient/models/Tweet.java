package com.codepath.simpletwitterclient.models;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
	private String body;
	private long uid;
	private String createdAt;
	private User user;
	
	public static Tweet fromJSON(JSONObject json) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = json.getString("text");
			tweet.uid = json.getLong("id");
			tweet.createdAt = json.getString("created_at");
			tweet.user = User.fromJSON(json.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public static Collection<? extends Tweet> fromJSONArray(JSONArray json) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());
		for (int i = 0; i < json.length(); i++) {
			try {
				JSONObject tweetJson = json.getJSONObject(i);
				tweets.add(fromJSON(tweetJson));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tweets;
	}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}
	
	@Override
	public String toString() {
		return body;
	}
}
