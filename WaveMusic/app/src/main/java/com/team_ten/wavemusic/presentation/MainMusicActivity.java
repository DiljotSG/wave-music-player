package com.team_ten.wavemusic.presentation;

import android.os.Bundle;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.PermissionManager;
import com.team_ten.wavemusic.application.SampleAssetManager;
import com.team_ten.wavemusic.logic.ActivityController;
import com.team_ten.wavemusic.logic.PlaybackController;

public class MainMusicActivity extends CommonMusicActivity
{
	/**
	 * Builds the user library view asynchronously.
	 *
	 * @param activityController the activity controller to use to build the library.
	 */
	private void buildUserLibraryView(final ActivityController activityController)
	{
		// Build the view asynchronously.
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
		PlaybackController.init();

		// Limit the code in this method to high level method calls only.
		permissionManager.getFilePermissions();
		sampleAssetManager.extractMusicAssets();
		buildUserLibraryView(activityController);
		createMusicControls();
	}
}