package com.team_ten.wavemusic.tests;

import com.team_ten.wavemusic.tests.logic.PlaybackControllerTest;
import com.team_ten.wavemusic.tests.logic.TestMusicDirectoryManager;
import com.team_ten.wavemusic.tests.logic.unit.AccessLikesTest;
import com.team_ten.wavemusic.tests.logic.unit.AccessPlaylistTest;
import com.team_ten.wavemusic.tests.logic.unit.AccessSongTest;
import com.team_ten.wavemusic.tests.objects.TestSongClass;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class) @SuiteClasses({
		PlaybackControllerTest.class,
		AccessSongTest.class,
		AccessPlaylistTest.class,
		AccessLikesTest.class,
		TestMusicDirectoryManager.class,
		TestSongClass.class,}) public class AllUnitTests
{
}