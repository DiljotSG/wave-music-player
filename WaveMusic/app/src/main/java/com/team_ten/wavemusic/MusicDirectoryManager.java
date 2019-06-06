package com.team_ten.wavemusic;

// Import statements
import java.io.File;
import java.util.Arrays;
import android.media.MediaMetadataRetriever;

public class MusicDirectoryManager
{
	// Instance variables
	private String directory;
	private File folder;
	private File[] files;
	private int curr;

	// Constants
	// A list of the valid extensions the application can handle
	private final static String[] VALID_MUSIC_FORMATS = {
			".mp3",
			".wav"
	};

	/**
	 * Purpose: Constructor for the music directory manager
	 *
	 * @param fullPath The absolute path to the desired directory
	 */
	public MusicDirectoryManager(String fullPath)
	{
		// Sets the instance variables
		directory = fullPath;
		folder = new File(directory);
		files = folder.listFiles();
		curr = 0;
	}

	/**
	 * Purpose: Gives the directory currently associated with this object.
	 *
	 * @return The directory as a string.
	 */
	public String getDirectory()
	{
		return directory;
	}

	/**
	 * Purpose: Checks to see if there are more music files in the folder.
	 *
	 * @return Does the director have more music files?
	 */
	public boolean hasNext()
	{
		// Basic error checking
		if (files != null && curr < files.length)
		{
			// Grab the file extension
			int extensionPosition = files[curr].getName().lastIndexOf(".");
			String extension;
			if (extensionPosition != -1)
			{
				// Check to see if the extension is one associated with music files
				extension = files[curr].getName().substring(extensionPosition);
				if (isValidExtension(extension))
				{
					return true;
				} else
				{
					curr++;
					return hasNext();
				}
			}
		}
		return false;
	}

	/**
	 * Purpose: Gives the next song in the folder.
	 *
	 * @return Returns a Song object representing the next song in the folder.
	 */
	public Song getNextSong()
	{
		Song result = null;
		if (hasNext())
		{
			// Parse the metadata from the file
			MediaMetadataRetriever parser = new MediaMetadataRetriever();
			parser.setDataSource(files[curr].getAbsolutePath());
			result = new Song(
					parser.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
					parser.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
					parser.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM),
					files[curr].toURI().toString()
			);

			// Iterate our "pointer"
			curr++;
		} else if (!hasNext() && curr < files.length)
		{
			// If we encounter a non-music file, but we have more files in the folder
			// Move on
			curr++;
		}
		return result;
	}

	/**
	 * Purpose: Checks to see if the given extension is valid.
	 *
	 * @param extension The desired extension to check.
	 * @return A boolean that is true, if the extension is valid, false otherwise.
	 */
	private boolean isValidExtension(String extension)
	{
		return Arrays.asList(VALID_MUSIC_FORMATS).contains(extension);
	}
}
