package com.team_ten.wavemusic.tests.objects;

import com.team_ten.wavemusic.objects.Song;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestSongClass
{
	@Test public void TestEqualsMethod()
	{
		System.out.println("Start: Testing the equals method.");
		// Create 3 Song Objects. songOne and songThree have the same URI, while that of songTwo
		// is different.
		Song songOne = new Song("title1", "artist1", "album1", "uri1", 0);
		Song songTwo = new Song("title2", "artist2", "album2", "uri2", 0);
		Song songThree = new Song("title1", "artist1", "album1", "uri1", 0);

		// Since two Song Objects are equal if they have the same URI,
		// songOne and songTwo should NOT be equal and songOne and songThree should be equal.
		assertNotEquals("These two songs should be unique", songOne, songTwo);
		assertEquals("These two songs should be different", songOne, songThree);
		System.out.println("End: Testing the equals method.");
	}
}