package com.team_ten.wavemusic.tests.logic.integration;

import com.team_ten.wavemusic.logic.AccessSong;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.objects.exceptions.WaveDatabaseIntegrityConstraintException;
import com.team_ten.wavemusic.tests.utils.IntegrationTestSetup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class AccessSongIT
{
	// Instance variable
	private AccessSong accessSong;
	private Song testSong;

	@Before public void setUp() throws IOException
	{
		System.out.println("Setting up for AccessSong Integration tests.");
		IntegrationTestSetup.setupDatabase();

		accessSong = new AccessSong();
		assertNotNull(accessSong);

		testSong = new Song("ghosts n stuff", "deadmau5", "albumName", "test.mp3", "songGenre", 0);

	}

	@Test public void testEmptyLibrary()
	{
		System.out.println("Start: Testing fetching empty library.");
		assertEquals("The song DB should be empty at this point.",
					 accessSong.getAllSongs().size(),
					 0);
		System.out.println("End: Testing fetching empty library.");
	}

	@Test public void testAddingDuplicateSongs()
	{
		System.out.println("Start: Testing adding duplicate songs.");
		accessSong.addSong(testSong);
		accessSong.addSong(testSong);
		accessSong.addSong(testSong);
		accessSong.addSong(testSong);

		assertEquals("There should only be 1 song in the library.",
					 accessSong.getAllSongs().size(),
					 1);
		System.out.println("End: Testing adding duplicate songs.");
	}

	@Test public void testGetSong()
	{
		System.out.println("Start: Testing getting an existing song.");
		accessSong.addSong(testSong);
		accessSong.getSong(testSong.getURI());
		assertEquals("The song we fetch should be the test song.",
					 accessSong.getAllSongs().get(0),
					 testSong);
		System.out.println("End: Testing getting an existing song.");
	}

	@Test public void testAddSong()
	{
		System.out.println("Start: Testing adding a song.");
		accessSong.addSong(testSong);
		assertEquals("The number of songs in the library, should be 1.",
					 accessSong.getAllSongs().size(),
					 1);
		assertEquals("Check that the only song is equal to the one we made",
					 accessSong.getAllSongs().get(0),
					 testSong);
		System.out.println("End: Testing adding a song.");
	}

	@Test public void testRemoveSong()
	{
		System.out.println("Start: Testing removing a song.");
		accessSong.addSong(testSong);
		accessSong.removeSong(testSong);
		assertEquals("The number of songs in the library, should be 0.",
					 accessSong.getAllSongs().size(),
					 0);
		accessSong.removeSong(testSong);
		assertEquals("Test removing the same song again, size should be 0.",
					 accessSong.getAllSongs().size(),
					 0);
		System.out.println("End: Testing removing a song.");
	}

	@Test public void testGetAllArtists()
	{
		System.out.println("Start: Testing getting all artists.");
		accessSong.addSong(testSong);
		assertEquals(
				"The artist in the library should be equal to that of the test song we added.",
				accessSong.getAllArtists().get(0),
				testSong.getArtist());
		assertEquals("There should only be 1 artist.", accessSong.getAllArtists().size(), 1);
		System.out.println("End: Testing getting all artists.");

	}

	@Test public void testGetAllAlbums()
	{
		System.out.println("Start: Testing getting all albums.");
		accessSong.addSong(testSong);
		assertEquals(
				"The albums in the library should be equal to that of the test song we added.",
				accessSong.getAllAlbums().get(0),
				testSong.getAlbum());
		assertEquals("There should only be 1 album.", accessSong.getAllAlbums().size(), 1);
		System.out.println("End: Testing getting all albums.");
	}

	@Test public void testGetSongsFromAlbum()
	{
		System.out.println("Start: Testing getting all songs from an album.");
		accessSong.addSong(testSong);
		assertEquals("There should only be only 1 song from the test album.",
					 accessSong.getSongsFromAlbum(testSong.getAlbum()).size(),
					 1);
		System.out.println("End: Testing getting all songs from an album.");
	}

	@Test public void testGetSongsFromArtist()
	{
		System.out.println("Start: Testing getting all songs from an artist.");
		accessSong.addSong(testSong);
		assertEquals("There should only be only 1 song from the test artist.",
					 accessSong.getSongsFromArtist(testSong.getArtist()).size(),
					 1);
		System.out.println("End: Testing getting all songs from an artist.");
	}

	@After public void tearDown()
	{
		IntegrationTestSetup.clearDatabase();
		System.out.println("Completed AccessSong Integration tests.");
	}
}
