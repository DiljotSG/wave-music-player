package com.team_ten.wavemusic.presentation;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.ActivityController;
import com.team_ten.wavemusic.application.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

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

		copyDatabaseToDevice();
		ActivityController.buildUserLibrary();

		ActivityController.startMainActivity(LaunchActivity.this);
		LaunchActivity.this.finish();
	}

	private void copyDatabaseToDevice()
	{
		final String DB_PATH = "db";

		String[] assetNames;
		Context context = getApplicationContext();
		File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
		AssetManager assetManager = getAssets();

		try
		{

			assetNames = assetManager.list(DB_PATH);
			for (int i = 0; i < assetNames.length; i++)
			{
				assetNames[i] = DB_PATH + "/" + assetNames[i];
			}

			copyAssetsToDirectory(assetNames, dataDirectory);
			Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

		}
		catch (final IOException ioe)
		{
			Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
		}
	}

	public void copyAssetsToDirectory(String[] assets, File directory) throws IOException
	{
		AssetManager assetManager = getAssets();

		for (String asset : assets)
		{
			String[] components = asset.split("/");
			String copyPath = directory.toString() + "/" + components[components.length - 1];

			char[] buffer = new char[1024];
			int count;

			File outFile = new File(copyPath);

			if (!outFile.exists())
			{
				InputStreamReader in = new InputStreamReader(assetManager.open(asset));
				FileWriter out = new FileWriter(outFile);

				count = in.read(buffer);
				while (count != -1)
				{
					out.write(buffer, 0, count);
					count = in.read(buffer);
				}

				out.close();
				in.close();
			}
		}
	}
}
