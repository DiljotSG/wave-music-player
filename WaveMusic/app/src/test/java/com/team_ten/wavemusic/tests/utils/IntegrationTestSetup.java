package com.team_ten.wavemusic.tests.utils;

import com.team_ten.wavemusic.application.db.Main;
import com.team_ten.wavemusic.application.db.Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IntegrationTestSetup
{
	private static final String PATH_TO_DB_SCRIPT = "src/main/assets/db/WaveDB.script";
	private static final int ONE_BYTE = 1024;
	private static File theDB;
	private static File testDB;

	public static void setupDatabase() throws IOException
	{
		// Create a temp file, and copy the DB there.
		testDB = File.createTempFile("TestWaveDB", ".script");

		// Get the correct path to the DB
		theDB = new File(PATH_TO_DB_SCRIPT);
		copyDatabase(theDB, testDB);

		Main.setDBPathName(testDB.getAbsolutePath().replace(".script", ""));
	}

	private static void copyDatabase(File source, File destination) throws IOException
	{
		try (InputStream inputStream = new FileInputStream(source))
		{
			try (OutputStream outputStream = new FileOutputStream(destination))
			{
				byte[] buf = new byte[ONE_BYTE];
				int amountRead;

				// Write the DB to the destination
				while ((amountRead = inputStream.read(buf)) > 0)
				{
					outputStream.write(buf, 0, amountRead);
				}
			}
		}
	}

	public static void clearDatabase()
	{
		testDB.delete();
		Services.clean();
	}
}