package com.codepath.simpletwitterclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	private static final long serialVersionUID = -3183737097051769606L;
	private String name, screenName, profileImageUrl, description;
	private long uid;
	private int followersCount, followingCount;

	public static User fromJSON(JSONObject json) throws JSONException {
		User user = new User();
		user.name = json.getString("name");
		user.uid = json.getLong("id");
		user.screenName = json.getString("screen_name");
		user.profileImageUrl = json.getString("profile_image_url");
		user.followersCount = Integer.parseInt(json.getString("followers_count"));
		user.followingCount = Integer.parseInt(json.getString("friends_count"));
		user.description = json.getString("description");
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

	public String getDescription() {
		return description;
	}	
	
	public int getFollowersCount() {
		return followersCount;
	}

	public int getFollowingCount() {
		return followingCount;
	}
}
