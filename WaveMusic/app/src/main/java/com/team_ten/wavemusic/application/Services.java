package com.team_ten.wavemusic.application;

import com.team_ten.wavemusic.persistence.ISongPersistence;
import com.team_ten.wavemusic.persistence.ILikesPersistence;
import com.team_ten.wavemusic.persistence.IPlaylistPersistence;
import com.team_ten.wavemusic.persistence.hsqldb.LikePersistenceHSQLDB;
import com.team_ten.wavemusic.persistence.hsqldb.PlaylistPersistenceHSQLDB;
import com.team_ten.wavemusic.persistence.hsqldb.SongPersistenceHSQLDB;

public class Services
{
	private static ISongPersistence SongPersistence = null;

	public static synchronized ISongPersistence getSongPersistence()
	{
		if (SongPersistence == null)
		{
			SongPersistence = new SongPersistenceHSQLDB(Main.getDBPathName());
		}

		return SongPersistence;
	}

	private static ILikesPersistence LikesPersistence = null;

	public static synchronized ILikesPersistence getLikesPersistence()
	{
		if (LikesPersistence == null)
		{
			LikesPersistence = new LikePersistenceHSQLDB(Main.getDBPathName());
		}

		return LikesPersistence;
	}

	private static IPlaylistPersistence PlaylistPersistence = null;

	public static synchronized IPlaylistPersistence getPlaylistPersistence()
	{
		if (PlaylistPersistence == null)
		{
			PlaylistPersistence = new PlaylistPersistenceHSQLDB(Main.getDBPathName());
		}

		return PlaylistPersistence;
	}
}

