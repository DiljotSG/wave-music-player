package com.team_ten.wavemusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class NowPlayingActivity extends AppCompatActivity
{

	private String title;	// The title of the song playing.
	private String URI;		// The URI of the song playing.

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// Default code on creation of an activity.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_now_playing);

		// To get the title and URI from the intent.
		Intent intent = getIntent();
		title = intent.getStringExtra("title");
		URI = intent.getStringExtra("URI");

		// Let the action bar display the title of the song playing.
		getSupportActionBar().setTitle(title);
	}
}