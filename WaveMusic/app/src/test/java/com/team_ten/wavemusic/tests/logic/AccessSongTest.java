package com.team_ten.wavemusic.tests.logic;

import com.team_ten.wavemusic.logic.AccessSong;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.tests.logic.stubs.SongPersistenceStub;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AccessSongTest
{
	// Instance variable
	private AccessSong accessSong;
	private Song testSong;

	@Before public void setUp()
	{
		accessSong = new AccessSong(new SongPersistenceStub());
		testSong = new Song("ghosts n stuff", "deadmau5", "albumName", "test.mp3", 0);

	}

	@Test public void testEmptyLibrary()
	{
		assertEquals("The song DB should be empty at this point.",
					 accessSong.getAllSongs().size(),
					 0);
	}

	@Test public void testAddingDuplicateSongs()
	{
		accessSong.addSong(testSong);
		accessSong.addSong(testSong);
		accessSong.addSong(testSong);
		accessSong.addSong(testSong);

		assertEquals("There should only be 1 song in the library.",
					 accessSong.getAllSongs().size(),
					 1);
	}

	@Test public void testAddSong()
	{
		accessSong.addSong(testSong);
		assertEquals(
				"The number of songs in the library, should be 1.",
				accessSong.getAllSongs().size(),
				1);
		assertEquals("Check that the only song is equal to the one we made",
					 accessSong.getAllSongs().get(0),
					 testSong
					 );
	}

	@Test public void testRemoveSong()
	{
		accessSong.addSong(testSong);
		accessSong.removeSong(testSong);
		assertEquals(
				"The number of songs in the library, should be 0.",
				accessSong.getAllSongs().size(),
				0);
		accessSong.removeSong(testSong);
		assertEquals(
				"Test removing the same song again, size should be 0.",
				accessSong.getAllSongs().size(),
				0);
	}

	@Test public void testGetAllArtists()
	{
		accessSong.addSong(testSong);
		assertEquals(
				"The artist in the library should be equal to that of the test song we added.",
				accessSong.getAllArtists().get(0),
				testSong.getArtist());
		assertEquals(
				"There should only be 1 artist.",
				accessSong.getAllArtists().size(),
				1);
	}

	@Test public void testGetAllAlbums()
	{
		accessSong.addSong(testSong);
		assertEquals(
				"The albums in the library should be equal to that of the test song we added.",
				accessSong.getAllAlbums().get(0),
				testSong.getAlbum());
		assertEquals(
				"There should only be 1 album.",
				accessSong.getAllAlbums().size(),
				1);
	}

	@Test public void testGetSongsFromAlbum()
	{
		accessSong.addSong(testSong);
		assertEquals(
				"There should only be only 1 song from the test album.",
				accessSong.getSongsFromAlbum(testSong.getAlbum()).size(),
				1);
	}

	@Test public void testGetSongsFromArtist()
	{
		accessSong.addSong(testSong);
		assertEquals(
				"There should only be only 1 song from the test artist.",
				accessSong.getSongsFromArtist(testSong.getArtist()).size(),
				1);
	}
}
