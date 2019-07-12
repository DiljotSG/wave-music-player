package com.team_ten.wavemusic.presentation;

import android.support.v7.app.AppCompatActivity;

import com.team_ten.wavemusic.objects.Song;

interface IPlaybackCallback
{
	/**
	 * Optional behaviour called after a song is skipped forward.
	 *
	 * @param newSong The song that we switched to.
	 */
	void afterNext(Song newSong);

	/**
	 * Optional behaviour called after a song is played.
	 *
	 * @param newSong The song that we switched to.
	 */
	void afterPlay(Song newSong);

	/**
	 * Optional behaviour called after a song is paused.
	 */
	void afterPause();

	/**
	 * Optional behaviour called after a song is restarted.
	 *
	 * @param newSong The song that we switched to.
	 */
	void afterRestart(Song newSong);

	/**
	 * Optional behaviour called after a song is skipped back.
	 *
	 * @param newSong The song that we switched to.
	 */
	void afterBack(Song newSong);
}

/**
 * THIS CLASS OFFERS DEFAULT BEHAVIOURS FOR THE INTERFACE ABOVE.
 * ANDROID API 23 DOES NOT SUPPORT JAVA 8'S DEFAULT INTERFACE METHODS.
 * THINGS MUST BE DONE THIS WAY, AS WE DO NOT WANT TO ENFORCE SUBCLASSES OF THIS CLASS
 * TO IMPLEMENT ALL METHODS IN THE `IPlaybackCallback` INTERFACE, ONLY SOME OF THE METHODS
 * CAN BE OVERRIDDEN IF DESIRED.
 */
public abstract class PlaybackCallback extends AppCompatActivity implements IPlaybackCallback
{
	/**
	 * Optional behaviour called after a song is skipped forward.
	 *
	 * @param newSong The song that we switched to.
	 */
	public void afterNext(Song newSong)
	{
		System.out.println("Default `afterNext()` callback executed.");
	}

	/**
	 * Optional behaviour called after a song is played.
	 *
	 * @param newSong The song that we switched to.
	 */
	public void afterPlay(Song newSong)
	{
		System.out.println("Default `afterPlay()` callback executed.");
	}

	/**
	 * Optional behaviour called after a song is paused.
	 */
	public void afterPause()
	{
		System.out.println("Default `afterPause()` callback executed.");
	}

	/**
	 * Optional behaviour called after a song is restarted.
	 *
	 * @param newSong The song that we switched to.
	 */
	public void afterRestart(Song newSong)
	{
		System.out.println("Default `afterRestart()` callback executed.");
	}

	/**
	 * Optional behaviour called after a song is skipped back.
	 *
	 * @param newSong The song that we switched to.
	 */
	public void afterBack(Song newSong)
	{
		System.out.println("Default `afterBack()` callback executed.");
	}
}