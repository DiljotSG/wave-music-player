package com.team_ten.wavemusic.tests.logic;

import android.media.MediaPlayer;

import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.music.PlaybackQueue;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.presentation.activities.NowPlayingMusicActivity;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.mock;

@SuppressWarnings("ConstantConditions") public class PlaybackControllerTest
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

	@Test public void loopModeToggleMovesToCorrectState()
	{
		System.out.println("Start testing that toggling loop mode sets the correct state.");
		int initial_mode = PlaybackController.getPlaybackModeNum();
		PlaybackController.toggleLoopMode();
		int final_mode = PlaybackController.getPlaybackModeNum();

		if (initial_mode < PlaybackController.getNumPlaybackStates() - 1)
		{
			assertEquals(
					"Final mode should be one greater than initial mode:",
					1,
					final_mode - initial_mode);
		}
		else
		{
			assertEquals("Final mode should be 0:", 0, final_mode);
		}

		System.out.println("End testing that toggling loop mode sets the correct state.");
	}

	@Test public void startSongSetsCorrectStateWithNoSongs()
	{
		System.out.println("Start testing that startSong sets the correct state with no songs.");

		try
		{
			PlaybackController.startSong(null);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			// We don't care; since there are no songs this will definitely be thrown
		}

		// State should be 0 since we can't play a song
		assertEquals("Playback state should be 0: ", PlaybackController.getPlaybackStateNum(), 0);
		System.out.println("End testing that startSong sets the correct state with no songs.");
	}

	@Test public void startSongSetsCorrectStateWithSongs()
	{
		System.out.println("Start testing that startSong sets correct state with songs.");

		ArrayList<Song> songs = new ArrayList<>();
		songs.add(new Song("name", "artist", "album", "uri", "songGenre", 0));
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
		assertNull("Song return should be null: ", PlaybackController.playNext());
		System.out.println("End testing that playNext returns the correct song value.");

	}

	@Test public void playPrevReturnsSong()
	{
		System.out.println("Start testing playPrev returns the correct song value.");
		assertNull("Song returned should be null: ", PlaybackController.playPrev());
		System.out.println("End testing playPrev returns the correct song value.");

	}

	@Test public void toggleShuffleSetsCorrectState()
	{
		System.out.println("Start testing that toggling shuffle sets the correct state.");

		if (PlaybackController.isShuffle())
		{
			PlaybackController.toggleShuffle();
			assertFalse("Shuffle should be false: ", PlaybackController.isShuffle());
		}
		else
		{
			PlaybackController.toggleShuffle();
			assertTrue("Shuffle should be true: ", PlaybackController.isShuffle());
		}
		System.out.println("End testing that toggling shuffle sets the correct state.");
	}

	@Test public void remainPausedAfterRestart()
	{
		System.out.println("Start testing that restarting when paused stays in paused state.");
		PlaybackController.pause();
		PlaybackController.restart();
		assertEquals("State num should be 0: ", PlaybackController.getPlaybackStateNum(), 0);
		System.out.println("End testing that restarting when paused stays in paused state.");

	}

	@Test public void jumpToSongNotInListReturnsNull()
	{
		System.out.println("Start testing that startSong jumps to the correct song.");
		Song song = new Song("name", "artist", "album", "uri", "songGenre", 0);

		assertNull("Song returned should be null: ", PlaybackController.startSong(song));
		System.out.println("End testing that startSong jumps to the correct song.");
	}

	@Test public void testSetActivity()
	{
		System.out.println("Start testing setting the music activity.");

		NowPlayingMusicActivity a = new NowPlayingMusicActivity();
		PlaybackController.setNowPlayingMusicActivity(a);

		assertEquals(
				"The activity should be equal to our newly created one.",
				PlaybackController.getNowPlayingMusicActivity(),
				a);

		System.out.println("Start testing setting the music activity.");
	}

	@Test public void playPrevReturnsCorrectSong()
	{
		System.out.println("Start test that playing previous song returns correct song.");
		ArrayList<Song> list = new ArrayList<>();
		Song s = null;
		list.add(s);
		PlaybackController.setPlaybackQueue(list);

		assertEquals("Playing prev should return our song: ", s, PlaybackController.playPrev());
		System.out.println("End test that playing previous song returns correct song.");
	}

	@Test public void playNextReturnsCorrectSong()
	{
		System.out.println("Start test that playing next song returns correct song.");
		ArrayList<Song> list = new ArrayList<>();
		Song s = null;
		list.add(s);
		PlaybackController.setPlaybackQueue(list);

		assertEquals("Playing prev should return our song: ", s, PlaybackController.playNext());
		System.out.println("End test that playing next song returns correct song.");
	}

	@Test public void testStartWithNullSongThrowsException()
	{
		System.out.println("Start test that starting a null song throws exception.");
		boolean startThrew = false;
		boolean initThrew = false;
		try
		{
			PlaybackController.init(null, null);
		}
		catch (NullPointerException e)
		{
			initThrew = true;
		}

		try
		{
			PlaybackController.startSong();
		}
		catch (NullPointerException e)
		{
			startThrew = true;
		}

		assertTrue("We should have thrown a null pointer exception initializing.", initThrew);
		assertTrue("We should have thrown a null pointer exception starting song.", startThrew);
		System.out.println("End test that starting a null song throws exception.");

		try
		{
			setUpClass();
		}
		catch (IOException e)
		{
			System.out.println("Error resetting test state.");
		}
		System.out.println("End test that starting a null song throws exception.");
	}

	@Test public void testPlaybackDurationAccess()
	{
		System.out.println("Begin test playback duration access.");

		int duration = 5;
		PlaybackController.setPlaybackDuration(duration);
		assertEquals(
				"Playback duration should be duration: ",
				duration,
				PlaybackController.getPlaybackDuration());

		System.out.println("End test playback duration access.");
	}

	@Test public void testSetDurationStayWithinSongDuration()
	{
		System.out.println("Begin test playback duration access.");

		int position = 5;
		PlaybackController.seekTo(position);
		assertEquals(
				"Playback duration is 0, so we should get 0: ",
				0,
				PlaybackController.getPlaybackPosition());

		System.out.println("End test playback duration access.");
	}
}