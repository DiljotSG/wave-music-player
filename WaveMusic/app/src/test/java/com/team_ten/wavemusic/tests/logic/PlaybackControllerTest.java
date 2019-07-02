package com.team_ten.wavemusic.tests.logic;

import android.media.MediaPlayer;
import android.media.AudioManager;

import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.PlaybackQueue;
import com.team_ten.wavemusic.objects.Song;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PlaybackControllerTest
{

	@BeforeClass public static void setUpClass() throws IOException
	{
		MediaPlayer mp = mock(MediaPlayer.class);
		PlaybackQueue pq = mock(PlaybackQueue.class);
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

	@Test public void loopmode_toggle_moves_to_correct_state()
	{
		int initial_mode = PlaybackController.get_playback_mode_num();
		PlaybackController.toggleLoopMode();
		int final_mode = PlaybackController.get_playback_mode_num();

		// test that
		if (initial_mode < PlaybackController.get_num_playback_states() - 1)
		{
			assertEquals(1, final_mode - initial_mode);
		}
		else
			assertEquals(0, final_mode);
	}

	@Test public void start_song_sets_correct_state_with_no_songs()
	{
		try {
			PlaybackController.startSong(null);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			;	// We don't care; since there are no songs this will definitely be thrown
		}

		// State should be 0 since we can't play a song
		assertEquals(PlaybackController.get_playback_state_num(), 0);
	}

	@Test public void start_song_sets_correct_state_with_songs()
	{
		ArrayList<Song> songs = new ArrayList<Song>();
		songs.add(new Song("name", "artist", "album", "uri", 0));
		PlaybackController.setPlaybackQueue(songs);

		try {
			PlaybackController.startSong();
		}
		catch (ArrayIndexOutOfBoundsException e) {
			;	// We don't care; since there are no songs this will definitely be thrown
		}

		// State should be 0 since we can't play a song
		assertEquals(PlaybackController.get_playback_state_num(), 1);
	}

	@Test public void pause_song_sets_correct_state()
	{
		PlaybackController.pause();
		assertEquals(PlaybackController.get_playback_state_num(), 0);
	}

	@Test public void start_null_song_sets_correct_state()
	{
		PlaybackController.startSong(null);
		assertEquals(PlaybackController.get_playback_state_num(), 0);
	}

	@Test public void play_next_returns_null_song()
	{
		assertEquals(PlaybackController.playNext(), null);
	}

	@Test public void play_prev_returns_null_song()
	{
		assertEquals(PlaybackController.playPrev(), null);
	}

	@Test public void restart_returns_null_song()
	{
		assertEquals(PlaybackController.restart(), null);
	}
}