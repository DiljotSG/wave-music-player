package com.team_ten.wavemusic.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		PlaybackControllerTest.class,
		TestMusicDirectoryManager.class,
		TestPlaylistClass.class,
		TestSongClass.class,
		TestDatabaseStub.class
})
public class AllTests
{

}