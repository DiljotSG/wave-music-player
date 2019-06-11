package com.team_ten.wavemusic.tests;

import com.team_ten.wavemusic.logic.MusicDirectoryManager;

import org.junit.Test;

public class TestMusicDirectoryManager
{
	@Test public void TestDirectoryManager()
	{
		// This folder should be empty...
		// Or we don't have permission to read it.
		String extPath = "/";

		MusicDirectoryManager test = new MusicDirectoryManager(extPath);

		// There is no next song.
		assert (!test.hasNext());

		// Getting the next song, should give null.
		assert (test.getNextSong() == null);
	}
}