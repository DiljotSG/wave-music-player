package com.team_ten.wavemusic.application;

import com.team_ten.wavemusic.persistence.ILikesPersistence;
import com.team_ten.wavemusic.persistence.IPlaylistPersistence;
import com.team_ten.wavemusic.persistence.ISongPersistence;
import com.team_ten.wavemusic.persistence.hsqldb.LikePersistenceHSQLDB;
import com.team_ten.wavemusic.persistence.hsqldb.PlaylistPersistenceHSQLDB;
import com.team_ten.wavemusic.persistence.hsqldb.SongPersistenceHSQLDB;

public class Services
{
	private static ISongPersistence SongPersistence = null;
	private static ILikesPersistence LikesPersistence = null;
	private static IPlaylistPersistence PlaylistPersistence = null;

	/**
	 * Return the accessor/mutator for the database Songs table
	 *
	 * @return songPersistence: A reference to the song persistence.
	 */
	public static synchronized ISongPersistence getSongPersistence()
	{
		if (SongPersistence == null)
		{
			SongPersistence = new SongPersistenceHSQLDB(Main.getDBPathName());
		}

		return SongPersistence;
	}

	/**
	 * Return the accessor/mutator for the database Likes table
	 *
	 * @return likesPersistence: A reference to the likes persistence.
	 */

	public static synchronized ILikesPersistence getLikesPersistence()
	{
		if (LikesPersistence == null)
		{
			LikesPersistence = new LikePersistenceHSQLDB(Main.getDBPathName());
		}

		return LikesPersistence;
	}

	/**
	 * Return the accessor/mutator for the database Playlists table
	 *
	 * @return playlistPersistence: A reference to the playlist persistence.
	 */
	public static synchronized IPlaylistPersistence getPlaylistPersistence()
	{
		if (PlaylistPersistence == null)
		{
			PlaylistPersistence = new PlaylistPersistenceHSQLDB(Main.getDBPathName());
		}

		return PlaylistPersistence;
	}

	public static synchronized void clean()
	{
		SongPersistence = null;
		LikesPersistence = null;
		PlaylistPersistence = null;
	}
}

