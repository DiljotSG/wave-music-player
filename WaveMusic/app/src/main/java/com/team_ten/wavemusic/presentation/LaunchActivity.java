package com.team_ten.wavemusic.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.ActivityController;

/**
 * Class name: LaunchActivity
 * Purpose: Display when the app starts, covering the loading time of building music library.
 */
public class LaunchActivity extends AppCompatActivity
{

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);


		ActivityController.startMainActivity(LaunchActivity.this);
		LaunchActivity.this.finish();
	}
}
