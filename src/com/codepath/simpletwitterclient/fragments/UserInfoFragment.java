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
import android.widget.Toast;

import com.codepath.simpletwitterclient.R;
import com.codepath.simpletwitterclient.SimpleTwitterClientApp;
import com.codepath.simpletwitterclient.TwitterClient;
import com.codepath.simpletwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserInfoFragment extends Fragment {
	private boolean compactView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_user_info, container, false);

		TwitterClient client = SimpleTwitterClientApp.getRestClient();
		client.getAccoutCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				try {
					showUserInfo(v, User.fromJSON(json));
				} catch (JSONException e) {
					onFailure(e, (String)null);
				}
			}
			@Override
			public void onFailure(Throwable t, String arg1) {
				t.printStackTrace();
				Toast.makeText(getActivity(), "Error loading logged in user info. " + t.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
				
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		View v = getView();
		
		if (compactView) {
			v.findViewById(R.id.tvFollowers).setVisibility(View.GONE);
			v.findViewById(R.id.tvFollowing).setVisibility(View.GONE);
			v.findViewById(R.id.tvUserDescription).setVisibility(View.GONE);
			v.findViewById(R.id.tvFollowingLabel).setVisibility(View.GONE);
			v.findViewById(R.id.tvFollowersLabel).setVisibility(View.GONE);
		}

		super.onActivityCreated(savedInstanceState);
	}
	
	private void showUserInfo(View v, User loggedInUser) {
		ImageView ivUser = (ImageView) v.findViewById(R.id.ivComposeProfileImage);
		ivUser.setImageResource(android.R.color.transparent);
		ImageLoader.getInstance().displayImage(loggedInUser.getProfileImageUrl(), ivUser);
		
		TextView tvUserName = (TextView) v.findViewById(R.id.tvComposeUserName);
		tvUserName.setText(loggedInUser.getName());

		TextView tvUserHandle = (TextView) v.findViewById(R.id.tvComposeHandle);
		tvUserHandle.setText("@" + loggedInUser.getScreenName());

		if (!compactView) {
			TextView tvUserDescription = (TextView) v.findViewById(R.id.tvUserDescription);
			tvUserDescription.setText(loggedInUser.getDescription());
	
			TextView tvFollowers = (TextView) v.findViewById(R.id.tvFollowers);
			tvFollowers.setText(String.valueOf(loggedInUser.getFollowersCount()));
	
			TextView tvFollowing = (TextView) v.findViewById(R.id.tvFollowing);
			tvFollowing.setText(String.valueOf(loggedInUser.getFollowingCount()));
		}
	}

	public boolean isCompactView() {
		return compactView;
	}

	public void setCompactView(boolean compactView) {
		this.compactView = compactView;
	}
}
