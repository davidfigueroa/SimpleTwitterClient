package com.codepath.simpletwitterclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	private static final long serialVersionUID = -3183737097051769606L;
	private String name, screenName, profileImageUrl;
	private long uid;
	private int followersCount ;

	public static User fromJSON(JSONObject json) {
		User user = new User();
		try {
			user.name = json.getString("name");
			user.uid = json.getLong("id");
			user.screenName = json.getString("screen_name");
			user.profileImageUrl = json.getString("profile_image_url");
			user.followersCount = Integer.parseInt(json.getString("followers_count"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
	
	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	public int getFollowersCount() {
		return followersCount;
	}}
