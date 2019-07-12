package com.team_ten.wavemusic.logic;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.team_ten.wavemusic.objects.PlaybackQueue;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.presentation.NowPlayingMusicActivity;

import java.io.IOException;
import java.util.ArrayList;


// Enumeration for the current audio playback state.
enum PlaybackState
{
	PAUSED, PLAYING
}

// Enumeration for the current method of repeating songs.
enum PlaybackMode
{
	PLAY_ALL, LOOP_ALL, LOOP_ONE;

	public PlaybackMode nextMode()
	{
		return values()[(this.ordinal() + 1) % values().length];
	}
}

// A controller which allows other classes to control audio playback.
public class PlaybackController
{
	//Instance variables.
	private static PlaybackQueue playbackQueue;
	private static PlaybackState state;
	private static PlaybackMode playbackMode;
	private static MediaPlayer mediaPlayer;
	private static boolean initialized;
	private static boolean shuffle;
	private static NowPlayingMusicActivity nowPlayingMusicActivity;

	/**
	 * Initializes the media player of the playback controller.
	 */
	public static void init(MediaPlayer mp, PlaybackQueue pq)
	{
		if (!initialized)
		{
			initialized = true;
			playbackQueue = pq;
			state = PlaybackState.PAUSED;
			playbackMode = PlaybackMode.PLAY_ALL;
			mediaPlayer = mp;
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mediaPlayer) {
					// If the playback is not paused
					if (PlaybackController.getPlaybackStateNum() > 0) {
						if (PlaybackController.getPlaybackMode() == PlaybackMode.LOOP_ONE) {
							PlaybackController.restart();
						} else {
							PlaybackController.playNext();
						}
					}
				}
			});
			shuffle = true;
		}
	}

	public static void setNowPlayingMusicActivity(NowPlayingMusicActivity activity)
	{
		nowPlayingMusicActivity = activity;
	}

	public static PlaybackState getPlaybackState() {
		return state;
	}

	/**
	 * Returns the playback state as an integer; intended mainly for unit testing.
	 *
	 * @return an integer representation of the current playback mode.
	 */
	public static int getPlaybackStateNum()
	{
		return getPlaybackState().ordinal();
	}

	/**
	 * Returns the playback mode.
	 *
	 * @return the playback mode.
	 */
	public static PlaybackMode getPlaybackMode() {
		return playbackMode;
	}

	/**
	 * Returns the playback state as an integer; intended mainly for unit testing.
	 *
	 * @return an integer representation of the current playback mode.
	 */
	public static int getPlaybackModeNum()
	{
		return getPlaybackMode().ordinal();
	}

	/**
	 * Returns the number of playback modes as an integer, generally for unit testing.
	 *
	 * @return an integer representation of the number of available playback modes.
	 */

	public static int getNumPlaybackStates()
	{
		return PlaybackMode.values().length;
	}

	/**
	 * Stop audio from playing.
	 */
	public static void pause()
	{
		mediaPlayer.pause();
		state = PlaybackState.PAUSED;
	}

	/**
	 * Start playing audio.
	 */
	public static Song startSong()
	{
		if (playbackQueue.hasSongs()) {
			if (playbackQueue.getCurrentSong() != null) {
				mediaPlayer.start();
			} else {
				try {
					PlaybackController.startSong(playbackQueue.jumpFirst());
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("[!] Could not start song; index out of bounds.");
					throw e;
				}

			}

			state = PlaybackState.PLAYING;
		}
		return getCurrentSong();
	}

	/**
	 * Move to next song repeat mode.
	 */
	public static void toggleLoopMode()
	{
		if (playbackMode != null)
		{
			playbackMode = playbackMode.nextMode();
		}
	}

	/**
	 * Start playing a specific song.
	 */
	public static Song startSong(Song song)
	{
		if (mediaPlayer == null)
		{
			System.out.println("[!] Tried to start song with null MediaPlayer.");
			throw new NullPointerException();
		}
		else if (song == null)
		{
			state = PlaybackState.PAUSED;
		}
		else
		{
			if (playbackQueue.getCurrentSong() == null ||
				!(playbackQueue.getCurrentSong().equals(song)))
			{
				playbackQueue.jumpSong(song);
			}

			state = PlaybackState.PLAYING;
			mediaPlayer.reset();

			try
			{
				mediaPlayer.setDataSource(song.getURI());
			}
			catch (IllegalArgumentException e)
			{
				System.out.printf("Illegal argument playing <%s>.", song.getURI());
				System.out.println(e);
			}
			catch (IOException e)
			{
				System.out.printf("IO Exception playing <%s>.", song.getURI());
				System.out.println(e);
			}

			try
			{
				mediaPlayer.prepare();
			}
			catch (Exception e)
			{
				System.out.println("Exception while preparing audio.");
				System.out.println(e);
			}

			try
			{
				mediaPlayer.start();
				nowPlayingMusicActivity.getSupportActionBar().setTitle(song.getName());
			}
			catch (Exception e)
			{
				System.out.println("Exception while starting audio.");
				System.out.println(e);
			}
		}

		return getCurrentSong();
	}


	/**
	 * Start playing the next song in the playback queue.
	 */
	public static Song playNext()
	{
		if (playbackQueue.hasSongs()) {
			boolean was_paused = state == PlaybackState.PAUSED;

			if (shuffle)
				startSong(playbackQueue.jumpRandom());
			else
				startSong(playbackQueue.jumpNext());

			if (was_paused) {
				pause();
			}
		}

		return getCurrentSong();
	}

	/**
	 * Start playing the previous song in the playback queue.
	 */
	public static Song playPrev()
	{
		if (playbackQueue.hasSongs()) {
			boolean was_paused = state == PlaybackState.PAUSED;

			startSong(playbackQueue.jumpPrev());

			if (was_paused) {
				pause();
			}
		}

		return getCurrentSong();
	}

	/**
	 * Start current song from beginning.
	 */
	public static Song restart()
	{
		boolean was_paused = state == PlaybackState.PAUSED;

		startSong(playbackQueue.getCurrentSong());

		if (was_paused)
		{
			pause();
		}

		return getCurrentSong();
	}

	/**
	 * Set the current queue of songs to play.
	 */
	public static void setPlaybackQueue(ArrayList<Song> newQueue)
	{
		playbackQueue.setQueue(newQueue);
	}

	/**
	 * Return the currently playing song.
	 */
	public static Song getCurrentSong()
	{
		return playbackQueue.getCurrentSong();
	}

	/**
	 * Toggle whether or not we are shuffling playback (true -> false, false ->true)
	 */
	public static void toggleShuffle() {
		PlaybackController.shuffle = !PlaybackController.shuffle;
	}

	public static boolean isShuffle() {
		return shuffle;
	}
}
