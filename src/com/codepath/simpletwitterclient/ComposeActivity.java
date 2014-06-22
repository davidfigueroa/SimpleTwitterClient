package com.codepath.simpletwitterclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.simpletwitterclient.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {
	public static final String EXTRA_TWEET = "t";
	public static final String EXTRA_USER = "u";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		User loggedInUser = (User) getIntent().getSerializableExtra(EXTRA_USER);
		if (loggedInUser != null) {
			ImageView ivUser = (ImageView) findViewById(R.id.ivComposeProfileImage);
			ivUser.setImageResource(android.R.color.transparent);
			ImageLoader.getInstance().displayImage(loggedInUser.getProfileImageUrl(), ivUser);
			
			TextView tvUserName = (TextView) findViewById(R.id.tvComposeUserName);
			tvUserName.setText(loggedInUser.getName());

			TextView tvUserHandle = (TextView) findViewById(R.id.tvComposeHandle);
			tvUserHandle.setText("@" + loggedInUser.getScreenName());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	public void onTweetClick(MenuItem mi) {
		EditText etTweet = (EditText) findViewById(R.id.etComposeTweet);
		if (etTweet.getText().length() == 0) {
			Toast.makeText(this, getString(R.string.enter_tweet_hint), Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent i = new Intent();
		i.putExtra(EXTRA_TWEET, etTweet.getText().toString());
		setResult(RESULT_OK, i);
		finish();
	}
}
