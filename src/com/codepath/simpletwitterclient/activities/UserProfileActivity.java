package com.codepath.simpletwitterclient.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.codepath.simpletwitterclient.R;
import com.codepath.simpletwitterclient.fragments.UserInfoFragment;
import com.codepath.simpletwitterclient.fragments.UserTimelineFragment;
import com.codepath.simpletwitterclient.models.User;

public class UserProfileActivity extends FragmentActivity {
	public static final String EXTRA_USER = "u";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_user_profile);

		User user = (User) getIntent().getSerializableExtra(EXTRA_USER);
		
		if (user != null) {
			UserInfoFragment fragmentUserInfo = (UserInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragUserProfileInfo);
			fragmentUserInfo.setUser(user);
			 
			UserTimelineFragment fragUserTimeline = (UserTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragUserProfileTimeline);
			fragUserTimeline.setUserId(user.getUid());
		}
	}
}
