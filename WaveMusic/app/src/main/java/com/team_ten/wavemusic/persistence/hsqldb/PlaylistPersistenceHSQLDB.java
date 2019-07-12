package com.team_ten.wavemusic.persistence.hsqldb;

import com.team_ten.wavemusic.objects.exceptions.WaveDatabaseIntegrityConstraintException;
import com.team_ten.wavemusic.objects.exceptions.WaveDatabaseException;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.IPlaylistPersistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistPersistenceHSQLDB implements IPlaylistPersistence, Serializable
{
	private final String dbPath;

	public PlaylistPersistenceHSQLDB(final String dbPath)
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
	 * Adds a playlist to the DB.
	 *
	 * @param playlistName The name of the playlist to add
	 */
	public void addPlaylist(String playlistName)
	{
		if (getPlaylist(playlistName) == null)
		{
			try (final Connection c = connection())
			{
				final PreparedStatement st = c.prepareStatement("INSERT INTO PLAYLISTS VALUES(?)");
				st.setString(1, playlistName);

				st.executeUpdate();
				st.close();
			}
			catch (final SQLException e)
			{
				throw wrapException(e);
			}
		}
	}

	/**
	 * Remove the given playlist from the DB.
	 *
	 * @param playlistName The name of the playlist to remove.
	 */
	public void removePlaylist(String playlistName)
	{
		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement(
					"DELETE FROM PLAYLISTS WHERE NAME = " + "?");
			st.setString(1, playlistName);

			st.executeUpdate();
			st.close();
		}
		catch (final SQLException e)
		{
			throw wrapException(e);
		}
	}

	/**
	 * Adds a Song to a Playlist in  the DB via the PLAYLIST_SONGS table
	 *
	 * @param song         song to add to playlist
	 * @param playlistName the playlist to add the song to
	 */
	public void addSongToPlaylist(Song song, String playlistName)
	{
		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement(
					"INSERT INTO PLAYLIST_SONGS VALUES(?, ?)");
			st.setString(1, song.getURI());
			st.setString(2, playlistName);

			st.executeUpdate();
			st.close();

		}
		catch (final SQLException e)
		{
			throw wrapException(e);
		}
	}

	/**
	 * Removes the given song from the given playlist
	 *
	 * @param song         The song to be removed.
	 * @param playlistName The playlist to remove the song from.
	 */
	public void removeSongFromPlaylist(Song song, String playlistName)
	{
		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement(
					"DELETE FROM PLAYLIST_SONGS WHERE URI = ? AND NAME = ?");
			st.setString(1, song.getURI());
			st.setString(2, playlistName);

			st.executeUpdate();
			st.close();

		}
		catch (final SQLException e)
		{
			throw wrapException(e);
		}
	}

	/**
	 * Gets all of the playlist names.
	 *
	 * @return An array list of playlist names
	 */
	public ArrayList<String> getAllPlaylists()
	{
		final ArrayList<String> result = new ArrayList<>();

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("SELECT NAME FROM PLAYLISTS");

			final ResultSet rs = st.executeQuery();

			while (rs.next())
			{
				result.add(rs.getString("NAME"));
			}
			st.close();

		}
		catch (final SQLException e)
		{
			throw wrapException(e);
		}

		return result;
	}

	/**
	 * Gets all of the songs in a playlist.
	 *
	 * @return An array list of playlist names
	 */
	public ArrayList<Song> getSongsFromPlaylist(String playlistName)
	{
		final ArrayList<Song> result = new ArrayList<>();

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement(
					"SELECT URI, ARTIST, NAME, ALBUM, GENRE, PLAY_COUNT FROM SONGS " +
					"INNER JOIN PLAYLIST_SONGS " +
					"ON SONGS.URI = PLAYLIST_SONGS.URI AND PLAYLIST_SONGS.NAME = ?");
			st.setString(1, playlistName);

			final ResultSet rs = st.executeQuery();

			while (rs.next())
			{
				result.add(fromResultSet(rs));
			}
			st.close();
		}
		catch (final SQLException e)
		{
			throw wrapException(e);
		}

		return result;
	}

	/**
	 * Get the specified playlist name from the DB.
	 *
	 * @return A String that is the playlist name.
	 */
	private String getPlaylist(String playlistName)
	{
		String result = null;

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement(
					"SELECT NAME FROM PLAYLISTS WHERE NAME = ?");
			st.setString(1, playlistName);

			final ResultSet rs = st.executeQuery();

			if (rs.next())
			{
				result = rs.getString("NAME");
			}
			st.close();

		}
		catch (final SQLException e)
		{
			throw wrapException(e);
		}

		return result;
	}

	private Song fromResultSet(final ResultSet rs) throws SQLException
	{
		final String songUri = rs.getString("URI");
		final String artistName = rs.getString("ARTIST");
		final String songName = rs.getString("NAME");
		final String albumName = rs.getString("ALBUM");
		final String genreName = rs.getString("GENRE");
		final int playCount = rs.getInt("PLAY_COUNT");
		return new Song(songName, artistName, albumName, songUri, genreName, playCount);
	}

	private WaveDatabaseException wrapException(SQLException e)
	{
		final String INTEGRITY_CONSTRAINT = "integrity constraint violation";
		if (e.getCause().toString().contains(INTEGRITY_CONSTRAINT))
		{
			return new WaveDatabaseIntegrityConstraintException(e);
		}
		else
		{
			return new WaveDatabaseException(e);
		}
	}
}
