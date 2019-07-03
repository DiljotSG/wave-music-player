package com.team_ten.wavemusic.logic;

import com.team_ten.wavemusic.application.Services;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.ILikesPersistence;

import java.io.Serializable;
import java.util.ArrayList;

public class AccessLikes implements ILikesPersistence, Serializable
{
	private ILikesPersistence likesPersistence;

	public AccessLikes()
	{
		this.likesPersistence = Services.getLikesPersistence();
	}

	public AccessLikes(final ILikesPersistence likesPersistence)
	{
		this.likesPersistence = likesPersistence;
	}

	/**
	 * Likes a song in the DB.
	 *
	 * @param songToLike Song that is to be Liked.
	 */
	public void likeSong(Song songToLike)
	{
		likesPersistence.likeSong(songToLike);
	}

	/**
	 * Unlikes a song in the DB.
	 *
	 * @param songToUnlike Song that is to be Unliked.
	 */
	public void unlikeSong(Song songToUnlike)
	{
		likesPersistence.unlikeSong(songToUnlike);
	}

	/**
	 * Get a list of all liked Songs.
	 *
	 * @returns An array list of liked Songs.
	 */
	public ArrayList<Song> getLikedSongs()
	{
		return likesPersistence.getLikedSongs();
	}
}
