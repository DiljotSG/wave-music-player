package com.team_ten.wavemusic.persistence.hsqldb;

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
		return DriverManager.getConnection("jdbc:hsqldb:file:/data/user/0/com.team_ten.wavemusic/app_db/WaveDB;shutdown=true",
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
		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("INSERT INTO PLAYLISTS VALUES('?')");
			st.setString(1, playlistName);

			st.executeUpdate();
			st.close();
		}
		catch (final SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/**
	 * Remove the given playlist.
	 *
	 * @param playlistName The name of the playlist to remove.
	 */
	public void removePlaylist(String playlistName)
	{
		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement(
					"DELETE FROM PLAYLISTS WHERE NAME = '?'");
			st.setString(1, playlistName);

			st.executeUpdate();
			st.close();
		}
		catch (final SQLException e)
		{
			throw new PersistenceException(e);
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
					"INSERT INTO PLAYLIST_SONGS VALUES('?', '?', '?')");
			st.setString(1, song.getURI());
			st.setString(2, playlistName);
			st.setInt(3, getPlaylistLength(playlistName));

			st.executeUpdate();
			st.close();

		}
		catch (final SQLException e)
		{
			throw new PersistenceException(e);
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
					"DELETE FROM PLAYLIST_SONGS WHERE URI = '?' AND PLAYLIST = '?'");
			st.setString(1, song.getURI());
			st.setString(1, playlistName);

			st.executeUpdate();
			st.close();

		}
		catch (final SQLException e)
		{
			throw new PersistenceException(e);
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
			throw new PersistenceException(e);
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
					"SELECT * FROM SONGS WHERE URI = (SELECT URI FROM PLAYLIST_SONGS WHERE " +
					"PLAYLIST_SONGS.NAME = '?')");
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
			throw new PersistenceException(e);
		}

		return result;
	}

	/**
	 * Return the length of the playlist (number of songs)
	 *
	 * @param playlistName the playlist to add the song to
	 */
	public int getPlaylistLength(String playlistName)
	{
		int result = 0;

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement(
					"SELECT COUNT (URI) FROM PLAYLIST_SONGS WHERE NAME = '?'");
			st.setString(1, playlistName);

			final ResultSet rs = st.executeQuery();
			result = rs.getInt(1);
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
