package com.codepath.simpletwitterclient.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet implements Serializable {
	private static final long serialVersionUID = -797017492296562890L;
	
	private String body;
	private long uid;
	private Date createdAt;
	private User user;
	private static final SimpleDateFormat dateFormat;
	
	static {
		dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH); //"Tue Aug 28 21:08:15 +0000 2012"
		dateFormat.setLenient(true);
	}
	
	public static Tweet fromJSON(JSONObject json) throws JSONException, ParseException {
		Tweet tweet = new Tweet();
		tweet.body = json.getString("text");
		tweet.uid = json.getLong("id");
		tweet.createdAt = dateFormat.parse(json.getString("created_at"));
		tweet.user = User.fromJSON(json.getJSONObject("user"));
		return tweet;
	}

	public static Collection<? extends Tweet> fromJSONArray(JSONArray json) throws JSONException, ParseException {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());
		for (int i = 0; i < json.length(); i++) {
			Tweet tweet = fromJSON(json.getJSONObject(i));
			tweets.add(tweet);
		}
		return tweets;
	}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public Date getCreatedAt() {
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
