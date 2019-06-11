package com.team_ten.wavemusic.tests;

import com.team_ten.wavemusic.objects.Song;

import org.junit.Test;

public class TestSongClass
{
	@Test public void TestEqualsMethod()
	{
		// Create 3 Song Objects. songOne and songThree have the same URI, while that of songTwo
        // is different.
		Song songOne = new Song("title1", "artist1", "album1", "uri1");
		Song songTwo = new Song("title2", "artist2", "album2", "uri2");
		Song songThree = new Song("title1", "artist1", "album1", "uri1");

		// Since two Song Objects are equal if they have the same URI,
		// songOne and songTwo should NOT be equal and songOne and songThree should be equal.
		assert (!songOne.equals(songTwo));
		assert (songOne.equals(songThree));
	}
}