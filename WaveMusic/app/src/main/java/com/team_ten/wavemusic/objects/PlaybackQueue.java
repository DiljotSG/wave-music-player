package com.team_ten.wavemusic.objects;

import java.util.ArrayList;
import java.util.Random;

public class PlaybackQueue
{
	// Instance variables.
	private ArrayList<Song> playbackQueue;
	private Song curSong;
	private int position;

	/**
	 * The constructor for a playback queue object.
	 */
	public PlaybackQueue()
	{
		playbackQueue = new ArrayList<>();
		position = 0;
		curSong = null;
	}

	/**
	 * Will set the queue to a new song list.
	 * Useful when the user switches playlists for example.
	 *
	 * @param songList The new song list to set as the active queue.
	 */
	public void setQueue(ArrayList<Song> songList)
	{
		playbackQueue = songList;
	}

	/**
	 * Returns the current song at the front of the queue.
	 *
	 * @return The current song at the front of the queue.
	 */
	public Song getCurrentSong()
	{
		return curSong;
	}

	/**
	 * Sets the current song to the first song in the Queue.
	 *
	 * @return The song that we just switched to.
	 */
	public Song jumpFirst()
	{
		return jumpIndex(0);
	}

	/**
	 * Goes to the next song in the queue.
	 *
	 * @return The song that we switched to.
	 */
	public Song jumpNext()
	{
		curSong = null;

		if (playbackQueue.size() == 0)
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		else
		{
			position++;
			if (position >= playbackQueue.size())
			{
				position = 0;
			}
			curSong = playbackQueue.get(position);
		}

		return curSong;
	}

	/**
	 * Goes to the previous song in the queue.
	 *
	 * @return Returns song we just switched to.
	 */
	public Song jumpPrev()
	{
		curSong = null;

		if (playbackQueue.size() == 0)
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		else
		{
			position--;
			if (position < 0)
			{
				position = playbackQueue.size() - 1;
			}
			curSong = playbackQueue.get(position);
		}

		return curSong;
	}

	/**
	 * Jumps to a specific song in the queue.
	 *
	 * @param song The desired song to skip to.
	 *
	 * @return The song we just skipped to, null if the song is not in the queue.
	 */
	public Song jumpSong(Song song)
	{
		curSong = null;

		// If the song is not in the queue, return null and don't mess up the queue.
		if (playbackQueue.contains(song))
		{
			for (position = 0; position < playbackQueue.size(); position++)
			{
				if (playbackQueue.get(position).equals(song))
				{
					curSong = song;
					break;
				}
			}
		}

		return curSong;
	}

	/**
	 * Jumps to a specific index in the queue.
	 *
	 * @param index The desired index to skip to.
	 *
	 * @return The song we just skipped to.
	 */
	public Song jumpIndex(int index)
	{
		curSong = null;

		if (index < 0 || index >= playbackQueue.size())
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		else
		{
			position = index;
			curSong = playbackQueue.get(position);
		}

		return curSong;
	}

	public Song jumpRandom() {
		Random random = new Random();
		int index = random.nextInt(playbackQueue.size());

		return jumpIndex(index);
	}

	public boolean hasSongs() {
		System.out.println(playbackQueue.size());
		return playbackQueue.size() > 0;
	}
}
