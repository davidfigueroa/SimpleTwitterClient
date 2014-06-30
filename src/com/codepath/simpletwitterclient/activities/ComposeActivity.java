package com.codepath.simpletwitterclient.activities;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.simpletwitterclient.R;
import com.codepath.simpletwitterclient.SimpleTwitterClientApp;
import com.codepath.simpletwitterclient.TwitterClient;
import com.codepath.simpletwitterclient.fragments.UserInfoFragment;
import com.codepath.simpletwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends FragmentActivity {
	public static final String EXTRA_TWEET = "t";
	MenuItem tweetCharRemCount;
	EditText etTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_compose);

		UserInfoFragment fragmentUserInfo = (UserInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_compose_user_info);
		fragmentUserInfo.setCompactView(true);
				
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

		TwitterClient client = SimpleTwitterClientApp.getRestClient();
		client.postStatus(etTweet.getText().toString(), this, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				try {
					Tweet tweet = Tweet.fromJSON(json);
					Intent i = new Intent();
					i.putExtra(EXTRA_TWEET, tweet);
					setResult(RESULT_OK, i);
					finish();
				} catch (Exception e) {
					onFailure(e, (String)null);
				}
			}
		});
	}
}
