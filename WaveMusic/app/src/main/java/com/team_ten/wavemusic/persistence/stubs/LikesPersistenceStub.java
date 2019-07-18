package com.team_ten.wavemusic.persistence.stubs;

import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.persistence.interfaces.ILikesPersistence;

import java.util.ArrayList;

public class LikesPersistenceStub implements ILikesPersistence
{
	private ArrayList<Song> songList;

	public LikesPersistenceStub()
	{
		songList = new ArrayList<>();
	}

	/**
	 * Likes a song in the DB.
	 *
	 * @param songToLike Song that is to be Liked.
	 */
	public void likeSong(Song songToLike)
	{
		if (!songList.contains(songToLike))
		{
			songList.add(songToLike);
		}
	}

	/**
	 * Unlikes a song in the DB.
	 *
	 * @param songToUnlike Song that is to be Unliked.
	 */
	public void unlikeSong(Song songToUnlike)
	{
		songList.remove(songToUnlike);
	}

	/**
	 * Get a list of all liked Songs.
	 *
	 * @return An array list of liked Songs.
	 */
	public ArrayList<Song> getLikedSongs()
	{
		return songList;
	}
}
