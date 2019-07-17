package com.team_ten.wavemusic.logic.access;

import com.team_ten.wavemusic.application.db.Services;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.persistence.interfaces.ILikesPersistence;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class uses:
 *   ___  ___ ___ ___ _  _ ___  ___ _  _  _____   __  ___ _  _    _ ___ ___ _____ ___ ___  _  _
 *  |   \| __| _ \ __| \| |   \| __| \| |/ __\ \ / / |_ _| \| |_ | | __/ __|_   _|_ _/ _ \| \| |
 *  | |) | _||  _/ _|| .` | |) | _|| .` | (__ \ V /   | || .` | || | _| (__  | |  | | (_) | .` |
 *  |___/|___|_| |___|_|\_|___/|___|_|\_|\___| |_|   |___|_|\_|\__/|___\___| |_| |___\___/|_|\_|
 */
public class AccessLikes implements ILikesPersistence, Serializable
{
	private ILikesPersistence likesPersistence;

	public AccessLikes()
	{
		this.likesPersistence = Services.getLikesPersistence();
	}

	public AccessLikes(final ILikesPersistence likesPersistence)
	{
		this();
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
