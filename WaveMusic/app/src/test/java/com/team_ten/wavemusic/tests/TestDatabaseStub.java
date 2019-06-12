package com.team_ten.wavemusic.tests;

import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.DatabaseStub;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestDatabaseStub
{
	// Instance variables.
	DatabaseStub databaseStub;
	Song songOne;
	Song songTwo;
	Song songThree;
	Song songFour;
	Song songFive;
	Song songSix;

	@Before public void setUp()
	{
		// Create a DatabaseStub object and add 6 songs into it.
		// Since songOne and songThree have the same URI, the latter will not be added.
		// Thus, there will be 5 songs in the DatabaseStub totally.
		databaseStub = new DatabaseStub();

		songOne = new Song("title1", "artist1", "album1", "uri1");
		songTwo = new Song("title1", "artist2", "album2", "uri2");
		songThree = new Song("title1", "artist1", "album1", "uri1");

		songFour = new Song("title4", "artist1", "album4", "uri4");

		songFive = new Song("title5", "artist5", "album1", "uri5");

		songSix = new Song("title6", "artist1", "album1", "uri6");

		databaseStub.addSong(songOne);
		databaseStub.addSong(songTwo);
		databaseStub.addSong(songThree);
		databaseStub.addSong(songFour);
		databaseStub.addSong(songFive);
		databaseStub.addSong(songSix);
	}

	@Test public void testConstructor()
	{
		// Create a new DatabaseStub object, it should be empty.
		DatabaseStub databaseStub_temp = new DatabaseStub();
		assert (databaseStub_temp.getLibrary().size() == 0);
	}

	@Test public void testHasLibrary()
	{
		// Create a new DatabaseStub object, hasLibrary method should return false because it is
		// empty.
		DatabaseStub databaseStub_temp = new DatabaseStub();
		assert (!databaseStub_temp.hasLibrary());

		// After adding a song into it, the method should return true.
		databaseStub_temp.addSong(songOne);
		assert (databaseStub_temp.hasLibrary());
	}

	@Test public void testAddSong()
	{
		// Create a new DatabaseStub object.
		DatabaseStub databaseStub_temp = new DatabaseStub();

		// After adding a song into it, the size should become 1.
		databaseStub_temp.addSong(songOne);
		assert (databaseStub_temp.getLibrary().size() == 1);

		// If we add the same song again, it size should remain unchanged.
		databaseStub_temp.addSong(songOne);
		assert (databaseStub_temp.getLibrary().size() == 1);

		// Then we add a new song, its size should be incremented.
		databaseStub_temp.addSong(songTwo);
		assert (databaseStub_temp.getLibrary().size() == 2);
	}

	@Test public void testGetSongsFromArtist()
	{
		// The databaseStub object contains 3 songs with artist "artist1".
		// We check if the number and the information of each of them are correct.
		ArrayList<Song> listForTest = databaseStub.getSongsFromArtist("artist1");

		assert (listForTest.size() == 3);
		assert (listForTest.get(0).getArtist().equals("artist1") &&
				listForTest.get(0).getURI().equals("uri1"));
		assert (listForTest.get(1).getArtist().equals("artist1") &&
				listForTest.get(1).getURI().equals("uri4"));
		assert (listForTest.get(2).getArtist().equals("artist1") &&
				listForTest.get(2).getURI().equals("uri6"));
	}

	@Test public void testGetSongsFromAlbum()
	{
		// The databaseStub object contains 3 songs with album "album1".
		// We check if the number and the information of each of them are correct.
		ArrayList<Song> listForTest = databaseStub.getSongsFromAlbum("album1");

		assert (listForTest.size() == 3);
		assert (listForTest.get(0).getAlbum().equals("album1") &&
				listForTest.get(0).getURI().equals("uri1"));
		assert (listForTest.get(1).getAlbum().equals("album1") &&
				listForTest.get(1).getURI().equals("uri5"));
		assert (listForTest.get(2).getAlbum().equals("album1") &&
				listForTest.get(2).getURI().equals("uri6"));
	}

	@Test public void testGetSong_String()
	{
		// The databaseStub object contains 2 songs with name "title1".
		// We check if the number and the information of each of them are correct.
		ArrayList<Song> listForTest = databaseStub.getSong("title1");

		assert (listForTest.size() == 2);
		assert (listForTest.get(0).getName().equals("title1") &&
				listForTest.get(0).getURI().equals("uri1"));
		assert (listForTest.get(1).getName().equals("title1") &&
				listForTest.get(1).getURI().equals("uri2"));
	}

	@Test public void testGetSong_Int()
	{
		// Since songThree is not added into databaseStub, index 4 should refer to songSix.
		Song result = databaseStub.getSong(4);
		assert (result.getName().equals("title6") && result.getURI().equals("uri6"));

		// Edge cases checking.
		result = databaseStub.getSong(-1);
		assert (result == null);
		result = databaseStub.getSong(5);
		assert (result == null);
	}
}