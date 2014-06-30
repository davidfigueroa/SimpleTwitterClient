package com.codepath.simpletwitterclient.utils;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class JsonHttpResponseHandlerWrapper extends JsonHttpResponseHandler {
	private final JsonHttpResponseHandler inner;
	private final WeakReference<Activity> activityRef;
	private final String opDesc;
	private static AtomicInteger opCount = new AtomicInteger();
	
	public JsonHttpResponseHandlerWrapper(String opDesc, Activity activity, JsonHttpResponseHandler inner) {
		this.activityRef = new WeakReference<Activity>(activity);
		this.inner = inner;
		this.opDesc = opDesc;
	}

	@Override
	public void onFailure(Throwable t, String arg1) {
		Activity a = activityRef.get();
		if (a != null) {
			Toast.makeText(a, "Error " + opDesc + ". " + t.getMessage(), Toast.LENGTH_LONG).show();
			t.printStackTrace();
		}
		inner.onFailure(t, arg1);
	}

	@Override
	public void onFinish() {
		int val = opCount.addAndGet(-1);
		if (val == 0) {
			Activity a = activityRef.get();
			if (a != null) {
				a.setProgressBarIndeterminateVisibility(false);
			}
		}

		inner.onFinish();
	}

	@Override
	public void onStart() {
		int val = opCount.addAndGet(1);
		if (val == 1) {
			Activity a = activityRef.get();
			if (a != null) {
				a.setProgressBarIndeterminateVisibility(true);
			}
		}

		inner.onStart();
	}

	@Override
	public void onFailure(Throwable arg0, JSONArray arg1) {
		inner.onFailure(arg0, arg1);
	}

	@Override
	public void onFailure(Throwable arg0, JSONObject arg1) {
		inner.onFailure(arg0, arg1);
	}

	@Override
	public void onSuccess(int arg0, JSONArray arg1) {
		inner.onSuccess(arg0, arg1);
	}

	@Override
	public void onSuccess(int arg0, JSONObject arg1) {
		inner.onSuccess(arg0, arg1);
	}

	@Override
	public void onSuccess(JSONArray arg0) {
		inner.onSuccess(arg0);
	}

	@Override
	public void onSuccess(JSONObject arg0) {
		inner.onSuccess(arg0);
	}

	@Override
	public void onSuccess(int arg0, String arg1) {
		inner.onSuccess(arg0, arg1);
	}

	@Override
	public void onSuccess(String arg0) {
		inner.onSuccess(arg0);
	}
}
