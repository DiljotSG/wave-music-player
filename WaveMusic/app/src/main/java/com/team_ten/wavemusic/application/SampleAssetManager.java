package com.team_ten.wavemusic.application;

import android.content.res.AssetManager;
import android.os.Environment;

import com.team_ten.wavemusic.presentation.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SampleAssetManager
{
	// Constants
	private static final String DEFAULT_LOCATION = "Music";
	private static final String[] SAMPLE_TRACKS = {
			"music/sample1.mp3",
			"music/sample2.mp3",
			"music/sample3.mp3",
			"music/sample4.mp3"
	};

	// Instance variables
	private MainActivity mainView;

	/**
	 * Purpose: The constructor for SampleAssetManager
	 */
	public SampleAssetManager(MainActivity mainActivity)
	{
		mainView = mainActivity;
	}

	/**
	 * Extracts the sample music assets into the Music directory on the device
	 */
	public void extractMusicAssets()
	{
		for (String file : SAMPLE_TRACKS)
		{
			if (!fileExists(file))
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

	// Private helper methods

	/**
	 * Extracts a specific asset from the APK to the system.
	 *
	 * @param fileName The specific file to extract.
	 *
	 * @throws IOException Throws an IOException in case something goes sour.
	 */
	private void extractFile(String fileName) throws IOException
	{
		// Create an asset manager
		AssetManager manager = mainView.getApplicationContext().getResources().getAssets();

		// Create a buffer to read the asset to
		InputStream inputStream = manager.open(fileName);
		int size = inputStream.available();
		byte[] fileBuffer = new byte[size];
		inputStream.read(fileBuffer);
		inputStream.close();

		// Write the buffer to an object
		FileOutputStream fileOutputStream =
				new FileOutputStream(new File(getOutputPath(fileName)));
		fileOutputStream.write(fileBuffer);
		fileOutputStream.close();
	}

	/**
	 * Purpose: Does the given file exist on the system?
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
	 * Purpose: Gives the output path of the asset.
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
	 * Purpose: Gives the full path to the asset on the system.
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
	 * Purpose: Gives the music directory.
	 *
	 * @return The default music directory in Android.
	 */
	private String getMusicDirectory()
	{
		return getExternalPath() + "/" + DEFAULT_LOCATION;
	}

	/**
	 * Purpose: Gives the path to the external directory.
	 *
	 * @return The path to the external storage directory.
	 */
	private String getExternalPath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
}
