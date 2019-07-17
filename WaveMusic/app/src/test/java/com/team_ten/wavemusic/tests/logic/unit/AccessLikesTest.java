package com.team_ten.wavemusic.tests.logic.unit;

import com.team_ten.wavemusic.logic.access.AccessLikes;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.persistence.stubs.LikesPersistenceStub;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccessLikesTest
{
	// Instance variable
	private AccessLikes accessLikes;
	private Song testSong;

	@Before public void setUp()
	{
		accessLikes = new AccessLikes(new LikesPersistenceStub());
		testSong = new Song("ghosts n stuff", "deadmau5", "albumName", "test.mp3", "songGenre", 0);
	}

	@Test public void testLikeSong()
	{
		System.out.println("Start: Testing liking a song.");
		accessLikes.likeSong(testSong);
		assertEquals("Our test song should be liked.",
					 accessLikes.getLikedSongs().get(0),
					 testSong);
		System.out.println("End: Testing liking a song.");
	}

	@Test public void testLikingDuplicateSongs()
	{
		System.out.println("Start: Testing liking a duplicate song.");
		accessLikes.likeSong(testSong);
		assertEquals("Our test song should be liked.",
					 accessLikes.getLikedSongs().get(0),
					 testSong);

		accessLikes.likeSong(testSong);
		accessLikes.likeSong(testSong);
		accessLikes.likeSong(testSong);
		accessLikes.likeSong(testSong);

		assertEquals("The number of likes should still be 1.",
					 accessLikes.getLikedSongs().size(),
					 1);
		System.out.println("End: Testing liking a duplicate song.");
	}

	@Test public void testUnlikeSong()
	{
		System.out.println("Start: Testing un-liking a song.");
		accessLikes.likeSong(testSong);
		assertEquals("Our test song should be liked.",
					 accessLikes.getLikedSongs().get(0),
					 testSong);
		accessLikes.unlikeSong(testSong);
		assertEquals("Our test song should not be liked.",
					 accessLikes.getLikedSongs().size(),
					 0);
		System.out.println("End: Testing un-liking a song.");
	}

	@Test public void testGetLikedSongs()
	{
		System.out.println("Start: Testing getting a liked song.");
		testLikeSong();
		System.out.println("End: Testing getting a liked song.");
	}
}
