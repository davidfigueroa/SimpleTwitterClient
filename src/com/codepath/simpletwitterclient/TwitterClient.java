package com.codepath.simpletwitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.app.Activity;
import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.codepath.simpletwitterclient.utils.JsonHttpResponseHandlerWrapper;
import com.loopj.android.http.JsonHttpResponseHandler;
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

    public void getHomeTimeline(long fromThisTweetId, Activity activity, JsonHttpResponseHandler handler) {
    	getTimeline("statuses/home_timeline.json", null, fromThisTweetId, activity, handler);
    }

    public void getUserTimeline(long userId, long fromThisTweetId, Activity activity, JsonHttpResponseHandler handler) {
    	RequestParams params = null;
    	if (userId != 0) {
    		params = new RequestParams();
    		params.put("user_id", String.valueOf(userId));
    	}
    	
    	getTimeline("statuses/user_timeline.json", params, fromThisTweetId, activity, handler);
    }   
    
    public void getMentionsTimeline(long fromThisTweetId, Activity activity, JsonHttpResponseHandler handler) {
    	getTimeline("statuses/mentions_timeline.json", null, fromThisTweetId, activity, handler);
    }

    private void getTimeline(String relUri, RequestParams params, long fromThisTweetId, Activity activity, JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl(relUri);

        if (fromThisTweetId != 0) {
        	if (params == null) {
        		params = new RequestParams();
        	}
        	params = new RequestParams();
        	params.put("max_id", Long.valueOf(fromThisTweetId - 1).toString());
        }
        client.get(apiUrl, params, new JsonHttpResponseHandlerWrapper("loading tweets", activity, handler));
    }
    
    public void postStatus(String status, Activity activity, JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");

        RequestParams params = new RequestParams();
    	params.put("status", status);
        client.post(apiUrl, params, new JsonHttpResponseHandlerWrapper("posting tweet", activity, handler));
    }
   
    public void getAccoutCredentials(Activity activity, JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");

        RequestParams params = new RequestParams();
    	params.put("include_entities", "false");
    	params.put("skip_status", "true");
        client.get(apiUrl, params, new JsonHttpResponseHandlerWrapper("loading logged in user info", activity, handler));
    }
}