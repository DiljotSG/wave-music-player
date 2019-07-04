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
		System.out.println("Start testing that toggling loop mode sets the correct state.");
		int initial_mode = PlaybackController.getPlaybackModeNum();
		PlaybackController.toggleLoopMode();
		int final_mode = PlaybackController.getPlaybackModeNum();

		if (initial_mode < PlaybackController.getNumPlaybackStates() - 1)
		{
			assertEquals("Final mode should be one greater than initial mode:",1, final_mode - initial_mode);
		}
		else
			assertEquals("Final mode should be 0:",0, final_mode);

		System.out.println("End testing that toggling loop mode sets the correct state.");
	}

	@Test public void startSongSetsCorrectStateWithNoSongs()
	{
		System.out.println("Start testing that startSong sets the correct state with no songs.");

		try {
			PlaybackController.startSong(null);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			;	// We don't care; since there are no songs this will definitely be thrown
		}

		// State should be 0 since we can't play a song
		assertEquals("Playback state should be 0: ", PlaybackController.getPlaybackStateNum(), 0);
		System.out.println("End testing that startSong sets the correct state with no songs.");
	}

	@Test public void startSongSetsCorrectStateWithSongs()
	{
		System.out.println("Start testing that startSong sets correct state with songs.");

		ArrayList<Song> songs = new ArrayList<>();
		songs.add(new Song("name", "artist", "album", "uri", 0));
		PlaybackController.setPlaybackQueue(songs);

		PlaybackController.startSong();

		assertEquals("Playback state should be 1: ", 1, PlaybackController.getPlaybackStateNum());
		System.out.println("End testing that startSong sets correct state with songs.");
	}

	@Test public void pauseSongSetsCorrectState()
	{
		System.out.println("Start testing that pause song leads to the correct playback state.");
		PlaybackController.pause();
		assertEquals("Playback state should be 0: ", PlaybackController.getPlaybackStateNum(), 0);
		System.out.println("End testing that pause song leads to the correct playback state.");
	}

	@Test public void startNullSongSetsCorrectState()
	{
		System.out.println("Start testing that starting a null song sets correct playback state.");

		PlaybackController.startSong(null);
		assertEquals("Playback state should be 0: ", PlaybackController.getPlaybackStateNum(), 0);

		System.out.println("End testing that starting a null song sets correct playback state.");

	}

	@Test public void playNextReturnsSong()
	{
		System.out.println("Start testing that playNext returns the correct song value.");
		assertNotEquals("Song return should not be null: ", PlaybackController.playNext(), null);
		System.out.println("End testing that playNext returns the correct song value.");

	}

	@Test public void playPrevReturnsSong()
	{
		System.out.println("Start testing playPrev returns the correct song value.");
		assertNotEquals("Song returned should not be null: ", PlaybackController.playPrev(), null);
		System.out.println("End testing playPrev returns the correct song value.");

	}

	@Test public void toggleShuffleSetsCorrectState() {
		System.out.println("Start testing that toggling shuffle sets the correct state.");

		if (PlaybackController.isShuffle()) {
			PlaybackController.toggleShuffle();
			assertFalse("Shuffle should be false: ", PlaybackController.isShuffle());
		}
		else {
			PlaybackController.toggleShuffle();
			assertTrue("Shuffle should be true: ", PlaybackController.isShuffle());
		}
		System.out.println("End testing that toggling shuffle sets the correct state.");
	}

	@Test public void remainPausedAfterRestart() {
		System.out.println("Start testing that restarting when paused stays in paused state.");
		PlaybackController.pause();
		PlaybackController.restart();
		assertEquals("State num should be 0: ", PlaybackController.getPlaybackStateNum(), 0);
		System.out.println("End testing that restarting when paused stays in paused state.");

	}

	@Test public void startSongJumpsToSong() {
		System.out.println("Start testing that startSong jumps to the correct song.");
		Song song = new Song("name", "artist", "album", "uri", 0);

		assertEquals("Song returned should be the created song: ", PlaybackController.startSong(song), song);
		System.out.println("End testing that startSong jumps to the correct song.");

	}

}