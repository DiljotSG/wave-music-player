package com.team_ten.wavemusic.tests.logic.integration;

import com.team_ten.wavemusic.logic.access.AccessPlaylist;
import com.team_ten.wavemusic.logic.access.AccessSong;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.tests.utils.IntegrationTestSetup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class AccessPlaylistIT
{
	// Instance variable
	private AccessPlaylist accessPlaylist;
	private AccessSong accessSong;
	private Song testSong;
	private String playlistName;

	@Before public void setUp() throws IOException
	{
		System.out.println("Setting up for AccessPlaylist Integration tests.");
		IntegrationTestSetup.setupDatabase();

		accessSong = new AccessSong();
		assertNotNull(accessSong);
		accessPlaylist = new AccessPlaylist();
		assertNotNull(accessPlaylist);

		testSong = new Song("ghosts n stuff", "deadmau5", "albumName", "test.mp3", "songGenre", 0);
		playlistName = "myMix1";
		accessSong.addSong(testSong);
		accessPlaylist.addPlaylist(playlistName);
	}

	@Test public void testAddPlaylist()
	{
		System.out.println("Start: Testing adding a playlist.");
		accessPlaylist.addPlaylist(playlistName);
		assertEquals("There should only be 1 playlist.",
					 accessPlaylist.getAllPlaylists().size(),
					 1);
		System.out.println("End: Testing adding a playlist.");
	}

	@Test public void testAddingDuplicatePlaylists()
	{
		System.out.println("Start: Testing adding a duplicate playlist.");
		accessPlaylist.addPlaylist(playlistName);
		accessPlaylist.addPlaylist(playlistName);
		accessPlaylist.addPlaylist(playlistName);
		accessPlaylist.addPlaylist(playlistName);

		assertEquals("There should only be 1 playlist in the library.",
					 accessPlaylist.getAllPlaylists().size(),
					 1);
		System.out.println("End: Testing adding a duplicate playlist.");
	}

	@Test public void testAddingDuplicateSongsToPlaylist()
	{
		System.out.println("Start: Testing adding a duplicate song to a playlist.");
		accessPlaylist.addPlaylist(playlistName);

		assertEquals("There should only be 1 playlist in the library.",
					 accessPlaylist.getAllPlaylists().size(),
					 1);

		accessPlaylist.addSongToPlaylist(testSong, playlistName);
		accessPlaylist.addSongToPlaylist(testSong, playlistName);
		accessPlaylist.addSongToPlaylist(testSong, playlistName);
		accessPlaylist.addSongToPlaylist(testSong, playlistName);

		// Difference between the fake tests and the integration. The Real DB allows duplicates.

		assertEquals("There should be 4 songs in the playlist.",
					 accessPlaylist.getSongsFromPlaylist(playlistName).size(),
					 4);
		System.out.println("End: Testing adding a duplicate song to a playlist.");
	}

	@Test public void testRemovePlaylist()
	{
		System.out.println("Start: Testing removing a playlist.");
		accessPlaylist.removePlaylist(playlistName);
		assertEquals("There should only be 0 playlists.",
					 accessPlaylist.getAllPlaylists().size(),
					 0);
		System.out.println("End: Testing removing a playlist.");
	}

	@Test public void testAddSongToPlaylist()
	{
		System.out.println("Start: Testing adding songs to a playlist.");
		accessPlaylist.addPlaylist(playlistName);
		assertEquals("There should only be 1 playlist.",
					 accessPlaylist.getAllPlaylists().size(),
					 1);

		accessPlaylist.addSongToPlaylist(testSong, playlistName);
		assertEquals("There should only be 1 song in the playlist.",
					 accessPlaylist.getSongsFromPlaylist(playlistName).size(),
					 1);
		System.out.println("End: Testing adding songs to a playlist.");
	}

	@Test public void testRemoveSongsFromPlaylist()
	{
		System.out.println("Start: Testing removing songs from a playlist.");
		accessPlaylist.addPlaylist(playlistName);
		assertEquals("There should only be 1 playlist.",
					 accessPlaylist.getAllPlaylists().size(),
					 1);

		accessPlaylist.addSongToPlaylist(testSong, playlistName);
		assertEquals("There should only be 1 song in the playlist.",
					 accessPlaylist.getSongsFromPlaylist(playlistName).size(),
					 1);
		accessPlaylist.removeSongFromPlaylist(testSong, playlistName);
		assertEquals("There should only be 0 songs in the playlist.",
					 accessPlaylist.getSongsFromPlaylist(playlistName).size(),
					 0);
		System.out.println("End: Testing removing songs from a playlist.");

	}

	@Test public void testGetAllPlaylists()
	{
		System.out.println("Start: Testing getting all playlists.");
		testAddPlaylist();
		System.out.println("End: Testing getting all playlists.");
	}

	@Test public void testGetSongsFromPlaylist()
	{
		System.out.println("Start: Testing getting all playlists.");
		accessPlaylist.addPlaylist(playlistName);
		assertEquals("There should only be 1 playlist.",
					 accessPlaylist.getAllPlaylists().size(),
					 1);

		accessPlaylist.addSongToPlaylist(testSong, playlistName);
		assertEquals("There should only be 1 song in the playlist.",
					 accessPlaylist.getSongsFromPlaylist(playlistName).size(),
					 1);
		assertEquals("The song in the playlist should equal our test song.",
					 accessPlaylist.getSongsFromPlaylist(playlistName).get(0),
					 testSong);
		System.out.println("End: Testing getting all playlists.");
	}

	@After public void tearDown()
	{
		IntegrationTestSetup.clearDatabase();
		System.out.println("Completed AccessPlaylist Integration tests.");
	}
}