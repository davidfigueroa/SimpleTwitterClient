package com.codepath.simpletwitterclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.simpletwitterclient.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	public TweetArrayAdapter(Context context) {
		super(context, R.layout.tweet_item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
		}
		
		ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		
		TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
		tvUserName.setText(tweet.getUser().getName());
		
		TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
		tvBody.setText(tweet.getBody());

		return convertView;
	}
}
