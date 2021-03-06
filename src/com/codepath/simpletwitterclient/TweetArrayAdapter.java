package com.codepath.simpletwitterclient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.simpletwitterclient.activities.UserProfileActivity;
import com.codepath.simpletwitterclient.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	private static final DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, Locale.ENGLISH);
	private static final DateFormat monthDayFormat = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
	private final boolean navigateToUserProfileOnImageClick;
	
	public TweetArrayAdapter(Context context, boolean navigateToUserProfileOnImageClick) {
		super(context, R.layout.tweet_item);
		this.navigateToUserProfileOnImageClick = navigateToUserProfileOnImageClick;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Tweet tweet = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
		}
		
		ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		if (navigateToUserProfileOnImageClick) {
			ivProfileImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getContext(), UserProfileActivity.class);
					i.putExtra(UserProfileActivity.EXTRA_USER, tweet.getUser());
					getContext().startActivity(i);
				}
			});
		}
		
		TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
		tvUserName.setText(tweet.getUser().getName());
		
		TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
		tvBody.setText(tweet.getBody());

		TextView tvUserHandle = (TextView) convertView.findViewById(R.id.tvUserHandle);
		tvUserHandle.setText("@" + tweet.getUser().getScreenName());
		
		TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
		tvTime.setText(getTimeString(tweet.getCreatedAt()));

		return convertView;
	}
	
	private static String getTimeString(Date time) {
		final int msInMinute = 1000 * 60;
		final int msInHour = msInMinute * 60;
		final int msInDay = msInHour * 24;
		final int msInYear = msInDay * 365;
		
		long ms = new Date().getTime() - time.getTime(); 
		if (ms > msInYear) {
			return dateFormat.format(time);
		}
		if (ms > msInDay) {
			return monthDayFormat.format(time);
		} 
		if (ms > msInHour) {
			return "" + ms / msInHour + "h";
		} 
		if (ms > msInMinute) {
			return "" + ms / msInMinute + "m";
		}
		if (ms < 0) {
			ms = 0;
		}
		return "" + ms / 1000 + "s";
	}
}
