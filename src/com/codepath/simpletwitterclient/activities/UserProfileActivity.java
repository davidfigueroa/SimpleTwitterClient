package com.codepath.simpletwitterclient.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.codepath.simpletwitterclient.R;

public class UserProfileActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
	}
}