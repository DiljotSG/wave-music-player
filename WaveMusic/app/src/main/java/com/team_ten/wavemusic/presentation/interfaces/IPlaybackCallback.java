package com.team_ten.wavemusic.presentation.interfaces;

import com.team_ten.wavemusic.objects.music.Song;

public interface IPlaybackCallback
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
