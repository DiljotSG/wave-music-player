package com.team_ten.wavemusic.tests.logic;

import com.team_ten.wavemusic.application.Main;
import com.team_ten.wavemusic.logic.AccessSong;
import com.team_ten.wavemusic.tests.logic.stubs.SongPersistenceStub;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AccessSongTest
{
	// Instance variable
	private AccessSong accessSong;

	@Before public void setUp()
	{
		accessSong = new AccessSong(new SongPersistenceStub());
	}

	@Test public void getEmptyLibrary()
	{
		assertEquals(
				"The song DB should be empty at this point",
				accessSong.getAllSongs().size(),
				0);
	}
}
