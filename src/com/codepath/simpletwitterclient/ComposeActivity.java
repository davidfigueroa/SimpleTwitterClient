package com.codepath.simpletwitterclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
	MenuItem tweetCharRemCount;
	EditText etTweet;
	
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
		
		etTweet = (EditText) findViewById(R.id.etComposeTweet);
		etTweet.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (tweetCharRemCount != null) {
					tweetCharRemCount.setTitle(Integer.valueOf(140 - s.length()).toString());
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.compose, menu);
		tweetCharRemCount = menu.findItem(R.id.tweet_char_rem_count);
		return true;
	}
	
	public void onTweetClick(MenuItem mi) {
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
