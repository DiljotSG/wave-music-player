package com.team_ten.wavemusic.persistence.interfaces;

import com.team_ten.wavemusic.objects.music.Song;

import java.util.ArrayList;

public interface ILikesPersistence
{
	/**
	 * Likes a song in the DB.
	 *
	 * @param songToLike Song that is to be Liked.
	 */
	public void likeSong(Song songToLike);

	/**
	 * Unlikes a song in the DB.
	 *
	 * @param songToUnlike Song that is to be Unliked.
	 */
	public void unlikeSong(Song songToUnlike);

	/**
	 * Get a list of all liked Songs.
	 *
	 * @return An array list of liked Songs.
	 */
	public ArrayList<Song> getLikedSongs();
}
