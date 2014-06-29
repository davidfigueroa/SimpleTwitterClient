package com.codepath.simpletwitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codepath.oauth.OAuthLoginActivity;
import com.codepath.simpletwitterclient.R;
import com.codepath.simpletwitterclient.TwitterClient;

public class LoginActivity extends OAuthLoginActivity<TwitterClient> {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
    @Override
    public void onLoginSuccess() {
    	Intent i = new Intent(this, TimelineActivity.class);
    	startActivity(i);
    }
    
    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }
    
    public void loginToRest(View view) {
        getClient().connect();
    }
}
