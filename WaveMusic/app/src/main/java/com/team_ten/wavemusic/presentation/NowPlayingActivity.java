package com.team_ten.wavemusic.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.team_ten.wavemusic.R;

public class NowPlayingActivity extends AppCompatActivity
{
	// The title of the song playing.
	private String title;
	// The URI of the song playing.
	private String URI;

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		// Default code on creation of an activity.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_now_playing);

		// To get the title and URI from the intent.
		Intent intent = getIntent();

		// TODO: should have two null checks here.
		title = intent.getStringExtra("title");
		URI = intent.getStringExtra("URI");

		// Let the action bar display the title of the song playing.
		getSupportActionBar().setTitle(title);
	}
}