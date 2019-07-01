package com.team_ten.wavemusic.tests.logic;

import com.team_ten.wavemusic.logic.AccessPlaylist;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.tests.logic.stubs.PlaylistPersistenceStub;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccessPlaylistTest
{
	// Instance variable
	private AccessPlaylist accessPlaylist;
	private Song testSong;
	private String playlistName;

	@Before public void setUp()
	{
		accessPlaylist = new AccessPlaylist(new PlaylistPersistenceStub());
		testSong = new Song("ghosts n stuff", "deadmau5", "albumName", "test.mp3", 0);
		playlistName = "myMix1";
	}

	@Test public void testAddPlaylist()
	{
		accessPlaylist.addPlaylist(playlistName);
		assertEquals("There should only be 1 playlist.",
					 accessPlaylist.getAllPlaylists().size(),
					 1);
	}

	@Test public void testAddingDuplicatePlaylists()
	{
		accessPlaylist.addPlaylist(playlistName);
		accessPlaylist.addPlaylist(playlistName);
		accessPlaylist.addPlaylist(playlistName);
		accessPlaylist.addPlaylist(playlistName);

		assertEquals("There should only be 1 playlist in the library.",
					 accessPlaylist.getAllPlaylists().size(),
					 1);
	}

	@Test public void testAddingDuplicateSongsToPlaylist()
	{
		accessPlaylist.addPlaylist(playlistName);

		assertEquals("There should only be 1 playlist in the library.",
					 accessPlaylist.getAllPlaylists().size(),
					 1);

		accessPlaylist.addSongToPlaylist(testSong, playlistName);
		accessPlaylist.addSongToPlaylist(testSong, playlistName);
		accessPlaylist.addSongToPlaylist(testSong, playlistName);
		accessPlaylist.addSongToPlaylist(testSong, playlistName);

		assertEquals("There should only be 1 song in the playlist.",
					 accessPlaylist.getSongsFromPlaylist(playlistName).size(),
					 1);
	}

	@Test public void testRemovePlaylist()
	{
		accessPlaylist.removePlaylist(playlistName);
		assertEquals("There should only be 0 playlists.",
					 accessPlaylist.getAllPlaylists().size(),
					 0);
	}

	@Test public void testAddSongToPlaylist()
	{
		accessPlaylist.addPlaylist(playlistName);
		assertEquals("There should only be 1 playlist.",
					 accessPlaylist.getAllPlaylists().size(),
					 1);

		accessPlaylist.addSongToPlaylist(testSong, playlistName);
		assertEquals("There should only be 1 song in the playlist.",
					 accessPlaylist.getSongsFromPlaylist(playlistName).size(),
					 1);
	}

	@Test public void testRemoveSongsFromPlaylist()
	{
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
	}

	@Test public void testGetAllPlaylists()
	{
		testAddPlaylist();
	}

	@Test public void testGetSongsFromPlaylist()
	{
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
	}

	@Test public void testGetPlaylistLength()
	{
		accessPlaylist.addPlaylist(playlistName);
		assertEquals("There should only be 1 playlist.",
					 accessPlaylist.getAllPlaylists().size(),
					 1);

		accessPlaylist.addSongToPlaylist(testSong, playlistName);
		assertEquals("There should only be 1 song in the playlist.",
					 accessPlaylist.getSongsFromPlaylist(playlistName).size(),
					 1);
		assertEquals("The number of songs in the playlist should be 1..",
					 accessPlaylist.getPlaylistLength(playlistName),
					 1);
	}
}