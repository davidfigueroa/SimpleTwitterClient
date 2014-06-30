package com.codepath.simpletwitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; 
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "3XWzBHDJB27VK1aWPfjTMohb0"; 
    public static final String REST_CONSUMER_SECRET = "wl2qBhArbA2wFyCaa1PuRQ3COlYnJ0GOm1z8P2ET14vFcGeXe2";
    public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; 
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getHomeTimeline(long fromThisTweetId, AsyncHttpResponseHandler handler) {
    	getTimeline("statuses/home_timeline.json", null, fromThisTweetId, handler);
    }

    public void getUserTimeline(long userId, long fromThisTweetId, AsyncHttpResponseHandler handler) {
    	RequestParams params = null;
    	if (userId != 0) {
    		params = new RequestParams();
    		params.put("user_id", String.valueOf(userId));
    	}
    	
    	getTimeline("statuses/user_timeline.json", params, fromThisTweetId, handler);
    }   
    
    public void getMentionsTimeline(long fromThisTweetId, AsyncHttpResponseHandler handler) {
    	getTimeline("statuses/mentions_timeline.json", null, fromThisTweetId, handler);
    }

    private void getTimeline(String relUri, RequestParams params, long fromThisTweetId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl(relUri);

        if (fromThisTweetId != 0) {
        	if (params == null) {
        		params = new RequestParams();
        	}
        	params = new RequestParams();
        	params.put("max_id", Long.valueOf(fromThisTweetId - 1).toString());
        }
        client.get(apiUrl, params, handler);
    }
    
    public void postStatus(String status, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");

        RequestParams params = new RequestParams();
    	params.put("status", status);
        client.post(apiUrl, params, handler);
    }
   
    public void getAccoutCredentials(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");

        RequestParams params = new RequestParams();
    	params.put("include_entities", "false");
    	params.put("skip_status", "true");
        client.get(apiUrl, params, handler);
    }
}