package com.team_ten.wavemusic.tests;

import com.team_ten.wavemusic.objects.Playlist;
import com.team_ten.wavemusic.objects.Song;

import org.junit.Test;

public class TestPlaylistClass
{
	@Test public void TestAddMethod()
	{
		Playlist playList = new Playlist("For Test");

		// Since two songs with the same URI are about to be added, the second adding should fail,
		// because playlist should not allow duplicates.
		assert (playList.add(new Song("title1", "artist1", "album1", "uri1")));
		assert (!playList.add(new Song("title1", "artist1", "album1", "uri1")));
	}

	@Test public void TestGetSongMethod()
	{
		Playlist playList = new Playlist("Test");

		playList.add(new Song("title1", "artist1", "album1", "uri1"));

		playList.add(new Song("title2", "artist2", "album2", "uri2"));

		playList.add(new Song("title3", "artist3", "album3", "uri3"));

		playList.add(new Song("title4", "artist4", "album4", "uri4"));

		int size = playList.getSize();

		// We call getSong method 4 times with 4 different position parameters,
		// among which only the second is valid.
		Song songOne = playList.getSong(-1);
		Song songTwo = playList.getSong(0);
		Song songThree = playList.getSong(size);
		Song songFour = playList.getSong(size + 1);

		// Thus, only the second call should return a Song object,
		// and all others should return null.
		assert (songOne == null);
		assert (songTwo != null);
		assert (songThree == null);
		assert (songFour == null);
	}
}
