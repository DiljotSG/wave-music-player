package com.team_ten.wavemusic.application.managers;

import android.content.res.AssetManager;
import android.os.Environment;

import com.team_ten.wavemusic.presentation.activities.MainMusicActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class uses:
 * ___  ___ ___ ___ _  _ ___  ___ _  _  _____   __  ___ _  _    _ ___ ___ _____ ___ ___  _  _
 * |   \| __| _ \ __| \| |   \| __| \| |/ __\ \ / / |_ _| \| |_ | | __/ __|_   _|_ _/ _ \| \| |
 * | |) | _||  _/ _|| .` | |) | _|| .` | (__ \ V /   | || .` | || | _| (__  | |  | | (_) | .` |
 * |___/|___|_| |___|_|\_|___/|___|_|\_|\___| |_|   |___|_|\_|\__/|___\___| |_| |___\___/|_|\_|
 */
public class BundledAssetManager
{
	// Constants
	private static final String DEFAULT_LOCATION = "Music";

	// Instance variables
	private final MainMusicActivity mainView;
	private final String[] sampleTracks;

	/**
	 * The constructor for BundledAssetManager.
	 */
	public BundledAssetManager(MainMusicActivity mainMusicActivity, String[] samples)
	{
		// DEPENDENCY INJECTION
		mainView = mainMusicActivity;
		sampleTracks = samples;
	}

	/**
	 * Extracts the sample music assets into the Music directory on the device.
	 */
	public void extractMusicAssets()
	{
		for (String file : sampleTracks)
		{
			if (!fileExists(getOutputPath(file)))
			{
				try
				{
					extractFile(file);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	// Private helper methods.

	/**
	 * Extracts a specific asset from the APK to the system.
	 *
	 * @param fileName The specific file to extract.
	 *
	 * @throws IOException Throws an IOException in case something goes sour.
	 */
	@SuppressWarnings("ResultOfMethodCallIgnored") private void extractFile(String fileName) throws IOException
	{
		// Create an asset manager.
		AssetManager manager = mainView.getApplicationContext().getResources().getAssets();

		// Create a buffer to read the asset to.
		InputStream inputStream = manager.open(fileName);
		int size = inputStream.available();
		byte[] fileBuffer = new byte[size];
		inputStream.read(fileBuffer);
		inputStream.close();

		// Write the buffer to an object.
		FileOutputStream fileOutputStream =
				new FileOutputStream(new File(getOutputPath(fileName)));
		fileOutputStream.write(fileBuffer);
		fileOutputStream.close();
	}

	/**
	 * Checks whether the given file exists on the system.
	 *
	 * @param fileName The file name to check.
	 *
	 * @return Is the file on the system?
	 */
	private boolean fileExists(String fileName)
	{
		String path = getOutputPath(fileName);
		return new File(path).exists();
	}

	/**
	 * Gives the output path of the asset.
	 *
	 * @param fileName The file name we want to extract.
	 *
	 * @return The output path of the asset (where we want to save it).
	 */
	private String getOutputPath(String fileName)
	{
		return getFullPathToAsset(fileName.split("/")[1]);
	}

	/**
	 * Gives the full path to the asset on the system.
	 *
	 * @param fileName The file name.
	 *
	 * @return The asset on the system.
	 */
	private String getFullPathToAsset(String fileName)
	{
		return getMusicDirectory() + "/" + fileName;
	}

	/**
	 * Gives the music directory.
	 *
	 * @return The default music directory in Android.
	 */
	private String getMusicDirectory()
	{
		return getExternalPath() + "/" + DEFAULT_LOCATION;
	}

	/**
	 * Gives the path to the external directory.
	 *
	 * @return The path to the external storage directory.
	 */
	private String getExternalPath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
}
