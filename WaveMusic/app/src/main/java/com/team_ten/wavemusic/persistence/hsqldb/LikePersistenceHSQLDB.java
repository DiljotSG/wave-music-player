package com.team_ten.wavemusic.persistence.hsqldb;

import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.ILikesPersistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LikePersistenceHSQLDB implements ILikesPersistence, Serializable
{
	private final String dbPath;

	public LikePersistenceHSQLDB(final String dbPath)
	{
		this.dbPath = dbPath;
	}

	private Connection connection() throws SQLException
	{
		return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true",
										   "SA",
										   "");
	}

	/**
	 * Likes a song in the DB.
	 *
	 * @param songToLike Song that is to be Liked.
	 */
	public void likeSong(Song songToLike)
	{
		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("INSERT INTO LIKES VALUES(?)");
			st.setString(1, songToLike.getURI());

			st.executeUpdate();
			st.close();
		}
		catch (final SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/**
	 * Unlikes a song in the DB.
	 *
	 * @param songToUnlike Song that is to be Unliked.
	 */
	public void unlikeSong(Song songToUnlike)
	{
		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("DELETE FROM LIKES WHERE URI = '?'");
			st.setString(1, songToUnlike.getURI());

			st.executeUpdate();
			st.close();
		}
		catch (final SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/**
	 * Get a list of all liked Songs.
	 *
	 * @return An array list of liked Songs.
	 */
	public ArrayList<Song> getLikedSongs()
	{
		final ArrayList<Song> result = new ArrayList<>();

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement(
					"SELECT * FROM SONGS WHERE SONGS.URI IN (SELECT URI FROM LIKES)");

			final ResultSet rs = st.executeQuery();

			while (rs.next())
			{
				result.add(fromResultSet(rs));
			}

			rs.close();
			st.close();
		}
		catch (final SQLException e)
		{
			throw new PersistenceException(e);
		}

		return result;
	}

	private Song fromResultSet(final ResultSet rs) throws SQLException
	{
		final String songUri = rs.getString("URI");
		final String artistName = rs.getString("ARTIST");
		final String songName = rs.getString("NAME");
		final String albumName = rs.getString("ALBUM");
		final int playCount = rs.getInt("PLAY_COUNT");
		return new Song(songName, artistName, albumName, songUri, playCount);
	}
}
