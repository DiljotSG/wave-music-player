package com.team_ten.wavemusic.tests;

import com.team_ten.wavemusic.tests.logic.integration.AccessLikesIT;
import com.team_ten.wavemusic.tests.logic.integration.AccessPlaylistIT;
import com.team_ten.wavemusic.tests.logic.integration.AccessSongIT;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		AccessSongIT.class,
		AccessLikesIT.class,
		AccessPlaylistIT.class
})
public class AllIntegrationTests
{
}