package com.team_ten.wavemusic;

import com.team_ten.wavemusic.acceptanceTests.TestChangeThemes;
import com.team_ten.wavemusic.acceptanceTests.TestLikeSongs;
import com.team_ten.wavemusic.acceptanceTests.TestMusicControl;
import com.team_ten.wavemusic.acceptanceTests.TestPlaylist;
import com.team_ten.wavemusic.acceptanceTests.TestSearch;
import com.team_ten.wavemusic.acceptanceTests.TestSort;
import com.team_ten.wavemusic.acceptanceTests.TestViewSongsProperties;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class) @Suite.SuiteClasses({
		TestChangeThemes.class,
		TestLikeSongs.class,
		TestMusicControl.class,
		TestPlaylist.class,
		TestSearch.class,
		TestSort.class,
		TestViewSongsProperties.class}) public class AllAcceptancetests
{
}
