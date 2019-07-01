package com.team_ten.wavemusic.tests.objects;

import com.team_ten.wavemusic.objects.Playlist;
import com.team_ten.wavemusic.objects.Song;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

public class TestPlaylistClass
{
	@Test public void TestAddMethod()
	{
		Playlist playList = new Playlist("For Test");

		// Since two songs with the same URI are about to be added, the second adding should fail,
		// because playlist should not allow duplicates.
		assertTrue(
				"Adding this song should work.",
				playList.add(new Song("title1", "artist1", "album1", "uri1", 0)));
		assertFalse(
				"Adding this song should fail.",
				playList.add(new Song("title1", "artist1", "album1", "uri1", 0)));
	}

	@Test public void TestGetSongMethod()
	{
		Playlist playList = new Playlist("Test");

		playList.add(new Song("title1", "artist1", "album1", "uri1", 0));

		playList.add(new Song("title2", "artist2", "album2", "uri2", 0));

		playList.add(new Song("title3", "artist3", "album3", "uri3", 0));

		playList.add(new Song("title4", "artist4", "album4", "uri4", 0));

		int size = playList.getSize();

		// We call getSong method 4 times with 4 different position parameters,
		// among which only the second is valid.
		Song songOne = playList.getSong(-1);
		Song songTwo = playList.getSong(0);
		Song songThree = playList.getSong(size);
		Song songFour = playList.getSong(size + 1);

		// Thus, only the second call should return a Song object,
		// and all others should return null.
		assertNull("This song does not exist", songOne);
		assertNotNull("This song does exist.", songTwo);
		assertNull("This song does not exist", songThree);
		assertNull("This song does not exist", songFour);
	}
}
