package com.team_ten.wavemusic.tests.logic;

import com.team_ten.wavemusic.logic.AccessLikes;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.tests.logic.stubs.LikesPersistenceStub;

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
		testSong = new Song("ghosts n stuff", "deadmau5", "albumName", "test.mp3", 0);
	}

	@Test public void testLikeSong()
	{
		accessLikes.likeSong(testSong);
		assertEquals("Our test song should be liked.",
					 accessLikes.getLikedSongs().get(0),
					 testSong);
	}

	@Test public void testLikingDuplicateSOngs()
	{
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
	}

	@Test public void testUnlikeSong()
	{
		accessLikes.likeSong(testSong);
		assertEquals("Our test song should be liked.",
					 accessLikes.getLikedSongs().get(0),
					 testSong);
		accessLikes.unlikeSong(testSong);
		assertEquals("Our test song should not be liked.",
					 accessLikes.getLikedSongs().size(),
					 0);
	}

	@Test public void testGetLikedSongs()
	{
		testLikeSong();
	}
}
