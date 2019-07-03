package com.team_ten.wavemusic.tests.logic;

import android.media.MediaMetadataRetriever;

import com.team_ten.wavemusic.logic.MusicDirectoryManager;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class TestMusicDirectoryManager
{
	//Instance variables
	private MusicDirectoryManager musicDir;
	private MusicDirectoryManager unavailableDir;
	private String pathWithMusic;

	@Before public void setUpDirectoryManager()
	{
		// The mocked MediaMetadataRetriever
		MediaMetadataRetriever mmdr = mock(MediaMetadataRetriever.class);

		pathWithMusic = System.getProperty("user.dir");
		pathWithMusic += "/src/main/assets/music/";

		// Path to the assets folder, this contains music.
		musicDir = new MusicDirectoryManager(pathWithMusic, mmdr);

		// Path to the root directory, we can't read this.
		unavailableDir = new MusicDirectoryManager("/", mmdr);
	}

	@Test public void TestUnavailableDir()
	{
		System.out.println("Start: Testing fetching songs from the unavailable directory.");

		// There is no next song.
		assertFalse("There should be no songs in this directory.", unavailableDir.hasNext());

		// Getting the next song, should give null.
		assertNull("Asking for the next song should not work.", unavailableDir.getNextSong());

		// Test retrieving the directory.
		assertEquals("The directory should be the '/' directory.",
					 unavailableDir.getDirectory(),
					 "/");

		System.out.println("End: Testing fetching songs from the unavailable directory.");
	}

	@Test public void TestMusicDir()
	{
		System.out.println("Start: Testing fetching songs from the asset directory.");

		// There is a next song.
		assertTrue("There should be songs in this directory.", musicDir.hasNext());

		// Getting the next song, should not give null.
		assertNotNull("Asking for the next song should work.", musicDir.getNextSong());

		// Test retrieving the directory.
		assertEquals("The directory should be the 'assets' directory.",
					 musicDir.getDirectory(),
					 pathWithMusic);

		System.out.println("End: Testing fetching songs from the asset directory.");
	}
}