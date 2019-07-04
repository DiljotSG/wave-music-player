package com.team_ten.wavemusic.tests.logic;

import android.media.MediaPlayer;

import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.PlaybackQueue;
import com.team_ten.wavemusic.objects.Song;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PlaybackControllerTest
{

	@BeforeClass public static void setUpClass() throws IOException
	{
		MediaPlayer mp = mock(MediaPlayer.class);
		PlaybackQueue pq = new PlaybackQueue();
		doNothing().when(mp).pause();
		doNothing().when(mp).start();
		doNothing().when(mp).setAudioStreamType(isA(Integer.class));
		doNothing().when(mp).reset();
		doNothing().when(mp).setDataSource(anyString());
		doNothing().when(mp).prepare();
		PlaybackController.init(mp, pq);
	}

	@AfterClass public static void tearDownClass()
	{
		//pass;
	}

	@Test public void loopmodeToggleMovesToCorrectState()
	{
		int initial_mode = PlaybackController.getPlaybackModeNum();
		PlaybackController.toggleLoopMode();
		int final_mode = PlaybackController.getPlaybackModeNum();

		// test that
		if (initial_mode < PlaybackController.getNumPlaybackStates() - 1)
		{
			assertEquals(1, final_mode - initial_mode);
		}
		else
			assertEquals(0, final_mode);
	}

	@Test public void startSongSetsCorrectStateWithNoSongs()
	{
		try {
			PlaybackController.startSong(null);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			;	// We don't care; since there are no songs this will definitely be thrown
		}

		// State should be 0 since we can't play a song
		assertEquals(PlaybackController.getPlaybackStateNum(), 0);
	}

	@Test public void startSongSetsCorrectStateWithSongs()
	{
		ArrayList<Song> songs = new ArrayList<>();
		songs.add(new Song("name", "artist", "album", "uri", 0));
		PlaybackController.setPlaybackQueue(songs);

		PlaybackController.startSong();

		assertEquals(1, PlaybackController.getPlaybackStateNum());
	}

	@Test public void pauseSongSetsCorrectState()
	{
		PlaybackController.pause();
		assertEquals(PlaybackController.getPlaybackStateNum(), 0);
	}

	@Test public void startNullSongSetsCorrectState()
	{
		PlaybackController.startSong(null);
		assertEquals(PlaybackController.getPlaybackStateNum(), 0);
	}

	@Test public void playNextReturnsSong()
	{
		assertNotEquals(PlaybackController.playNext(), null);
	}

	@Test public void playPrevReturnsSong()
	{
		assertNotEquals(PlaybackController.playPrev(), null);
	}

	@Test public void toggleShuffleSetsCorrectState() {
		if (PlaybackController.isShuffle()) {
			PlaybackController.toggleShuffle();
			assertFalse(PlaybackController.isShuffle());
		}
		else {
			PlaybackController.toggleShuffle();
			assertTrue(PlaybackController.isShuffle());
		}
	}

	@Test public void remainPausedAfterRestart() {
		PlaybackController.pause();
		PlaybackController.restart();
		assertEquals(PlaybackController.getPlaybackStateNum(), 0);
	}

	@Test public void startSongJumpsToSong() {
		Song song = new Song("name", "artist", "album", "uri", 0);

		assertEquals(PlaybackController.startSong(song), song);
	}

}