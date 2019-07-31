package com.team_ten.wavemusic.application.managers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.team_ten.wavemusic.presentation.activities.MainMusicActivity;

public class PermissionManager
{
	// Constants.
	private static final int PERMISSIONS_REQUEST_READ_STORAGE_CODE = 0;
	private static final int PERMISSIONS_REQUEST_WRITE_STORAGE_CODE = 1;

	// Instance variables.
	private MainMusicActivity mainView;

	/**
	 * The constructor for PermissionManager.
	 */
	public PermissionManager(MainMusicActivity mainMusicActivity)
	{
		mainView = mainMusicActivity;
	}

	/**
	 * Sets the permissions of the android app for reading/writing files.
	 */
	public void getFilePermissions()
	{
		// Get the current permissions.
		int readPerm = ContextCompat.checkSelfPermission(mainView,
														 Manifest.permission.READ_EXTERNAL_STORAGE);
		int writePerm = ContextCompat.checkSelfPermission(mainView,
														  Manifest.permission.WRITE_EXTERNAL_STORAGE);

		// If not, request those permissions.
		if (readPerm != PackageManager.PERMISSION_GRANTED)
		{
			ActivityCompat.requestPermissions(mainView,
											  new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
											  PERMISSIONS_REQUEST_READ_STORAGE_CODE);
		}

		if (writePerm != PackageManager.PERMISSION_GRANTED)
		{
			ActivityCompat.requestPermissions(mainView,
											  new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
											  PERMISSIONS_REQUEST_WRITE_STORAGE_CODE);
		}
	}
}