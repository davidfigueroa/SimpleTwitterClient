package com.codepath.simpletwitterclient.fragments;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.simpletwitterclient.R;
import com.codepath.simpletwitterclient.SimpleTwitterClientApp;
import com.codepath.simpletwitterclient.TwitterClient;
import com.codepath.simpletwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserInfoFragment extends Fragment {
	private boolean compactView;
	private User user;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_user_info, container, false);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		final View v = getView();

		//show/hide controls
		if (compactView) {
			v.findViewById(R.id.tvFollowers).setVisibility(View.GONE);
			v.findViewById(R.id.tvFollowing).setVisibility(View.GONE);
			v.findViewById(R.id.tvUserDescription).setVisibility(View.GONE);
			v.findViewById(R.id.tvFollowingLabel).setVisibility(View.GONE);
			v.findViewById(R.id.tvFollowersLabel).setVisibility(View.GONE);
		}

		//if user is not passed in load current user
		if (user == null) {
			TwitterClient client = SimpleTwitterClientApp.getRestClient();
			client.getAccoutCredentials(getActivity(), new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject json) {
					try {
						user = User.fromJSON(json);
						showUserInfo(v);
					} catch (JSONException e) {
						onFailure(e, (String)null);
					}
				}
			});
		} else {
			showUserInfo(v);
		}
		
		super.onActivityCreated(savedInstanceState);
	}
	
	private void showUserInfo(View v) {
		ImageView ivUser = (ImageView) v.findViewById(R.id.ivComposeProfileImage);
		ivUser.setImageResource(android.R.color.transparent);
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivUser);
		
		TextView tvUserName = (TextView) v.findViewById(R.id.tvComposeUserName);
		tvUserName.setText(user.getName());

		TextView tvUserHandle = (TextView) v.findViewById(R.id.tvComposeHandle);
		tvUserHandle.setText("@" + user.getScreenName());

		if (!compactView) {
			TextView tvUserDescription = (TextView) v.findViewById(R.id.tvUserDescription);
			tvUserDescription.setText(user.getDescription());
	
			TextView tvFollowers = (TextView) v.findViewById(R.id.tvFollowers);
			tvFollowers.setText(String.valueOf(user.getFollowersCount()));
	
			TextView tvFollowing = (TextView) v.findViewById(R.id.tvFollowing);
			tvFollowing.setText(String.valueOf(user.getFollowingCount()));
		}
	}

	public void setCompactView(boolean compactView) {
		this.compactView = compactView;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
