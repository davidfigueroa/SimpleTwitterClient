package com.codepath.simpletwitterclient.fragments;

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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_user_info, container, false);

		TwitterClient client = SimpleTwitterClientApp.getRestClient();
		client.getAccoutCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				showUserInfo(v, User.fromJSON(json));
			}
			@Override
			public void onFailure(Throwable t, String arg1) {
				t.printStackTrace();
				Toast.makeText(getActivity(), "Error loading logged in user info. " + t.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
		
		return v;
	}
	
	private void showUserInfo(View v, User loggedInUser) {
		ImageView ivUser = (ImageView) v.findViewById(R.id.ivComposeProfileImage);
		ivUser.setImageResource(android.R.color.transparent);
		ImageLoader.getInstance().displayImage(loggedInUser.getProfileImageUrl(), ivUser);
		
		TextView tvUserName = (TextView) v.findViewById(R.id.tvComposeUserName);
		tvUserName.setText(loggedInUser.getName());

		TextView tvUserHandle = (TextView) v.findViewById(R.id.tvComposeHandle);
		tvUserHandle.setText("@" + loggedInUser.getScreenName());
	}
}
