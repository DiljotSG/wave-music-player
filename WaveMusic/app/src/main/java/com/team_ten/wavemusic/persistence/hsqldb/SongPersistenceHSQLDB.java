package com.team_ten.wavemusic.persistence.hsqldb;

import com.team_ten.wavemusic.objects.exceptions.WaveDatabaseIntegrityConstraintException;
import com.team_ten.wavemusic.objects.exceptions.WaveDatabaseException;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.persistence.interfaces.ISongPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SongPersistenceHSQLDB implements ISongPersistence
{
	private final String dbPath;
	private ArrayList<Song> library;

	public SongPersistenceHSQLDB(final String dbPath)
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
	 * Adds a Song to the DB.
	 *
	 * @param theSong The song to add.
	 */
	public void addSong(Song theSong)
	{
		// Populate the current library for faster duplicate checking
		if (library == null)
		{
			library = getAllSongs();
		}

		if (!library.contains(theSong))
		{
			try (final Connection c = connection())
			{
				final PreparedStatement st = c.prepareStatement(
						"INSERT INTO SONGS VALUES(?, ?, ?, ?, ?, ?)");
				st.setString(1, theSong.getURI());
				st.setString(2, theSong.getArtist());
				st.setString(3, theSong.getName());
				st.setString(4, theSong.getAlbum());
				st.setString(5, theSong.getGenre());
				st.setInt(6, theSong.getPlayCount());

				st.executeUpdate();
				st.close();
				library.add(theSong);
			}
			catch (final SQLException e)
			{
				throw wrapException(e);
			}
		}
	}

	/**
	 * Removes a Song from the user's library
	 *
	 * @param toRemove The song to remove from the user's library.
	 */
	public void removeSong(Song toRemove)
	{
		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("DELETE FROM SONGS WHERE URI = ?");
			st.setString(1, toRemove.getURI());

			st.executeUpdate();
			st.close();
			library.remove(toRemove);
		}
		catch (final SQLException e)
		{
			System.out.println("[!] Exception while removing song from database.");
			throw wrapException(e);
		}
	}

	/**
	 * Returns a Song object for the given URI.
	 *
	 * @param songURI The URI of the Song.
	 *
	 * @return A Song object for the Song.
	 */
	public Song getSong(String songURI)
	{
		Song song = null;

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("SELECT * FROM SONGS WHERE URI = ?");
			st.setString(1, songURI);

			final ResultSet rs = st.executeQuery();

			if (rs.next())
			{
				// The size of the result set should be 1.
				song = fromResultSet(rs);
			}

			rs.close();
			st.close();
		}
		catch (final SQLException e)
		{
			throw wrapException(e);
		}

		return song;
	}

	/**
	 * Gets every song in the library.
	 *
	 * @return Returns an ArrayList of every Song.
	 */
	public ArrayList<Song> getAllSongs()
	{
		ArrayList<Song> result = new ArrayList<>();

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("SELECT * FROM SONGS");

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
			throw wrapException(e);
		}

		return result;
	}

	/**
	 * Gets all of the artists names.
	 *
	 * @return An array list of artists names
	 */
	public ArrayList<String> getAllArtists()
	{
		final ArrayList<String> result = new ArrayList<>();

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("SELECT DISTINCT ARTIST FROM SONGS");

			final ResultSet rs = st.executeQuery();

			while (rs.next())
			{
				result.add(rs.getString(1));
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
	 * Gets all of the album names.
	 *
	 * @return An array list of album names
	 */
	public ArrayList<String> getAllAlbums()
	{
		final ArrayList<String> result = new ArrayList<>();

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("SELECT DISTINCT ALBUM FROM SONGS");

			final ResultSet rs = st.executeQuery();

			while (rs.next())
			{
				result.add(rs.getString(1));
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
	 * Returns a all songs in a given album.
	 *
	 * @param albumName The URI of the Song.
	 *
	 * @return An array list of songs in the album.
	 */
	public ArrayList<Song> getSongsFromAlbum(String albumName)
	{
		ArrayList<Song> result = new ArrayList<>();

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("SELECT * FROM SONGS WHERE ALBUM = ?");
			st.setString(1, albumName);

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
			throw wrapException(e);
		}

		return result;
	}

	/**
	 * Returns a all songs from a given artist.
	 *
	 * @param artistName The name of the artist.
	 *
	 * @return An array list of songs by the artist.
	 */
	public ArrayList<Song> getSongsFromArtist(String artistName)
	{
		ArrayList<Song> result = new ArrayList<>();

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("SELECT * FROM SONGS WHERE ARTIST = " +
															"?");
			st.setString(1, artistName);

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
