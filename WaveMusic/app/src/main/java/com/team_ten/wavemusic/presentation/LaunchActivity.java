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
	// Instance variables
	private ActivityController activityController;

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);

		activityController = new ActivityController();

		activityController.startMainActivity(LaunchActivity.this, activityController);
		LaunchActivity.this.finish();
	}
}
