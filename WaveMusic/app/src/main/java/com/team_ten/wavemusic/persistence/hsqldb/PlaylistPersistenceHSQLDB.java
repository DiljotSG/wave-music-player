package com.team_ten.wavemusic.persistence.hsqldb;

import com.team_ten.wavemusic.objects.Playlist;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.IPlaylistPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistPersistenceHSQLDB implements IPlaylistPersistence
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
	 * @param name The name of the playlist to add
	 */
	public void addPlaylist(String name)
	{
		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("INSERT INTO PLAYLISTS VALUES('%s')");
			st.setString(1, name);

			st.executeUpdate();
			st.close();
		}
		catch (final SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/**
	 * Return the length of the playlist (number of songs)
	 *
	 * @param playlist the playlist to add the song to
	 */
	public int getLength(Playlist playlist)
	{
		int result = 0;

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement(
					"SELECT COUNT (URI) FROM PLAYLIST_SONGS WHERE NAME = '%s'");
			st.setString(1, playlist.getTitle());

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


	/**
	 * Adds a Song to a Playlist in  the DB via the PLAYLIST_SONGS table
	 *
	 * @param song     song to add to playlist
	 * @param playlist the playlist to add the song to
	 */
	public void addSong(Song song, Playlist playlist)
	{
		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement(
					"INSERT INTO PLAYLIST_SONGS VALUES('%s', '%s', '%d')");
			st.setString(1, song.getURI());
			st.setString(2, playlist.getTitle());
			st.setInt(3, getLength(playlist));

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
	public ArrayList<Song> getSongsFromPlaylist()
	{
		final ArrayList<Song> result = new ArrayList<>();

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement(
					"SELECT * FROM SONGS WHERE URI = (SELECT * FROM PLAYLIST_SONGS WHERE SONGS" +
					".URI" + " = PLAYLIST_SONGS.URI)");

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
