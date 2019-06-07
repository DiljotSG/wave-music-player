package com.team_ten.wavemusic.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.ActivityController;
import com.team_ten.wavemusic.application.PermissionManager;
import com.team_ten.wavemusic.application.SampleAssetManager;

public class MainActivity extends AppCompatActivity
{
	private void buildUserLibraryView(final ActivityController activityController)
	{
		// Build the view asynchronously
		new Thread(new Runnable()
		{
			@Override public void run()
			{
				activityController.buildLibraryView();
			}
		}).start();
	}

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		// Default code on creation of an activity.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		PermissionManager permissionManager = new PermissionManager(this);
		ActivityController activityController = new ActivityController(this);
		SampleAssetManager sampleAssetManager = new SampleAssetManager(this);

		// Limit the code in this method to high level method calls only.
		permissionManager.getFilePermissions();
		sampleAssetManager.extractMusicAssets();
		buildUserLibraryView(activityController);
	}
}
