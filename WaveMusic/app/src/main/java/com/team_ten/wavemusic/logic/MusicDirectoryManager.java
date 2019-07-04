package com.team_ten.wavemusic.logic;

import android.media.MediaMetadataRetriever;
import android.os.Environment;

import com.team_ten.wavemusic.objects.Song;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class MusicDirectoryManager
{
	// Constants.

	// A list of the valid extensions the application can handle.
	private final static String[] VALID_MUSIC_FORMATS = {".mp3", ".wav"};
	private static final String DEFAULT_LOCATION = "Music";

	// Instance variables.
	private String directory;
	private File folder;
	private File[] files;
	private int curr;
	private MediaMetadataRetriever mmdr;

	/**
	 * Constructor for the music directory manager.
	 */
	public MusicDirectoryManager()
	{
		// Sets the instance variables.
		directory = getExternalPath() + "/" + DEFAULT_LOCATION + "/";
		folder = new File(directory);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		files = folder.listFiles();
		curr = 0;
		mmdr = new MediaMetadataRetriever();
	}

	/**
	 * Constructor for the music directory manager.
	 *
	 * @param fullPath The absolute path to the desired directory.
	 */
	public MusicDirectoryManager(String fullPath)
	{
		// Sets the instance variables
		directory = fullPath;
		folder = new File(directory);
		files = folder.listFiles();
		curr = 0;
		mmdr = new MediaMetadataRetriever();
	}

	/**
	 * Constructor for the music directory manager.
	 *
	 * @param fullPath The absolute path to the desired directory.
	 * @param mmdr     The mocked MediaMetadataRetriever (for test cases).
	 */
	public MusicDirectoryManager(String fullPath, MediaMetadataRetriever mmdr)
	{
		this(fullPath);
		this.mmdr = mmdr;
	}

	/**
	 * Gives the directory currently associated with this object.
	 *
	 * @return The directory as a string.
	 */
	public String getDirectory()
	{
		return directory;
	}

	/**
	 * Checks to see if there are more music files in the folder.
	 *
	 * @return Does the director have more music files?
	 */
	public boolean hasNext()
	{
		// Basic error checking.
		boolean result = false;
		if (files != null && curr < files.length)
		{
			// Grab the file extension.
			String extension = getCurrentExtension();

			// Check to see if the extension is one associated with music files.
			if (extension != null && isValidExtension(extension))
			{
				result = true;
			}
			else
			{
				// This specific file is not a music file, keep going.
				curr++;
				result = hasNext();
			}
		}
		return result;
	}

	/**
	 * Gives the next song in the folder.
	 *
	 * @return Returns a Song object representing the next song in the folder.
	 */
	public Song getNextSong()
	{
		Song result = null;
		if (hasNext())
		{
			// Parse the metadata from the file.

			MediaMetadataRetriever parser = mmdr;
			parser.setDataSource(files[curr].getAbsolutePath());
			result = new Song(
					parser.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
					parser.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
					parser.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM),
					files[curr].toURI().toString(),
					0);

			// Iterate our "pointer".
			curr++;
		}
		else if (!hasNext() && curr < files.length)
		{
			// If we encounter a non-music file, but we have more files in the folder, move on.
			curr++;
		}
		return result;
	}

	// Private helper methods.

	/**
	 * Checks to see if the given extension is valid.
	 *
	 * @param extension The desired extension to check.
	 *
	 * @return A boolean that is true, if the extension is valid, false otherwise.
	 */
	private boolean isValidExtension(String extension)
	{
		return Arrays.asList(VALID_MUSIC_FORMATS).contains(extension);
	}

	/**
	 * Gives the extension for the current file in the directory.
	 *
	 * @return The current file in the directory's extension.
	 */
	private String getCurrentExtension()
	{
		// Value to return
		String result = null;
		// Get the position
		int extensionPosition = files[curr].getName().lastIndexOf(".");
		if (extensionPosition != -1)
		{
			// If it is valid, store the result.
			result = files[curr].getName().substring(extensionPosition);
		}
		return result;
	}

	/**
	 * Gives the path to the external directory.
	 *
	 * @return The path to the external storage directory.
	 */
	private String getExternalPath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
}
