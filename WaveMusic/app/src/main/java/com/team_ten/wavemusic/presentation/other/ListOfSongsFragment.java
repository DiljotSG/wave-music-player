package com.team_ten.wavemusic.presentation.other;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.ActivityController;
import com.team_ten.wavemusic.application.third_party.IDismissCallbacks;
import com.team_ten.wavemusic.application.third_party.SwipeDismissListViewTouchListener;
import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.presentation.activities.ListActivity;

import java.util.ArrayList;

public class ListOfSongsFragment extends Fragment
{
	// Instance variables

	private View view;
	private ListView listView;
	private ArrayList<Song> songList;
	private ArrayList<String> stringList;
	private Activity callerActivity;
	private Context context;
	private int position;
	private ArrayAdapter listAdapter;
	private String typeOfRetrieve;
	private String nameOfPlaylist;

	public ListOfSongsFragment()
	{
		// Required empty public constructor
	}

	public void setSongList(ArrayList<Song> songList)
	{
		this.songList = songList;
	}

	public void setStringList(ArrayList<String> stringList)
	{
		this.stringList = stringList;
	}

	/**
	 * Set necessary data into the instance variables.
	 *
	 * @param callerActivity: The parent Activity of this Fragment.
	 * @param typeOfRetrieve: The type of retrieve that asks for this fragment to display
	 *                        content.
	 */
	public void setData(Activity callerActivity, String typeOfRetrieve)
	{
		this.callerActivity = callerActivity;
		this.typeOfRetrieve = typeOfRetrieve;
	}

	public void setNameOfPlaylist(String nameOfPlaylist)
	{
		this.nameOfPlaylist = nameOfPlaylist;
	}

	@SuppressWarnings("unchecked") @Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Get data back from savedInstanceState so that they don't to be rebuilt after each time
		// the fragment is destroyed.
		if (savedInstanceState != null)
		{
			position = savedInstanceState.getInt("position");
			songList = (ArrayList<Song>) savedInstanceState.getSerializable("songList");
			stringList = (ArrayList<String>) savedInstanceState.getSerializable("stringList");
		}
	}

	/**
	 * Create the view with context
	 *
	 * @return the view
	 */
	@Override public View onCreateView(
			LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_list_of_songs, container, false);
		context = inflater.getContext();
		return view;
	}

	/**
	 * Populate the view
	 */
	@Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		listView = (ListView) getView().findViewById(R.id.list_songs);
		super.onViewCreated(view, savedInstanceState);
	}

	/**
	 * Set all handlers for the view
	 */

	@Override public void onStart()
	{
		super.onStart();

		// let the listview record the position the user is viewing, so that after rotating the
		// screen, the position can be resumed.
		listView.setOnScrollListener(new AbsListView.OnScrollListener()
		{
			@Override public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
				{
					position = listView.getFirstVisiblePosition();
				}
			}

			@Override public void onScroll(
					AbsListView view,
					int firstVisibleItem,
					int visibleItemCount,
					int totalItemCount)
			{
			}
		});

		super.onStart();
	}

	/**
	 * Handler for resuming the activity
	 */
	@Override public void onResume()
	{
		super.onResume();
		if (listAdapter != null)
		{
			listAdapter.notifyDataSetChanged();
		}
		// Resume the position of the listview after each time the screen is rotated.
		listView.setSelection(position);
	}

	/**
	 * Save necessary information for state saving
	 */

	@Override public void onSaveInstanceState(@NonNull Bundle outState)
	{
		super.onSaveInstanceState(outState);

		// Sava data into Bundle so that they don't need to be rebuilt after each time the device
		// is rotated.
		outState.putSerializable("songList", songList);
		outState.putSerializable("stringList", stringList);
		outState.putInt("position", position);
	}

	/**
	 * Set the choice mode of the listview
	 *
	 * @param mode: the mode.
	 */
	public void setChoiceMode(int mode)
	{
		listView.setChoiceMode(mode);
	}

	/**
	 * Set click event of the items in the listview: start a NowPlayingActivity and play that song.
	 */
	public void setOnItemClickListener()
	{
		if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.MY_LIBRARY.toString()) ||
			typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.SEARCH.toString()) ||
			typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.LIKED_SONG.toString()))
		{
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					Song selectedSong = songList.get(position);
					ActivityController.startNowPlayingActivity(callerActivity, selectedSong);
					PlaybackController.setPlaybackQueue(songList);
				}
			});
		}
		else if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ARTIST.toString()) ||
				 typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ALBUM.toString()))
		{
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					String selectedString = stringList.get(position);
					ActivityController.startAlbumOrArtistAct(callerActivity,
															 typeOfRetrieve,
															 selectedString);
				}
			});
		}
		else if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.PLAYLIST.toString()))
		{
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					String selectedString = stringList.get(position);
					ActivityController.startSinglePlaylistActivity(callerActivity, selectedString);
				}
			});
		}
	}

	/**
	 * Set a built-in type ArrayAdapter to the listview.
	 */
	public void setAdapter(int resource)
	{
		if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.MY_LIBRARY.toString()) ||
			typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.SEARCH.toString()) ||
			typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.LIKED_SONG.toString()))
		{
			listAdapter = new ArrayAdapter<Song>(context, resource, songList);
		}
		else if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ARTIST.toString()) ||
				 typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ALBUM.toString()) ||
				 typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.PLAYLIST.toString()))
		{
			listAdapter = new ArrayAdapter<String>(context, resource, stringList);
		}

		if (songList != null || stringList != null)
		{
			listView.setAdapter(listAdapter);
		}
	}

	/**
	 * Set a SwipeDismissListViewTouchListener to the listview, which allows "swipe to delete".
	 */
	public void setSwipeDismissListViewTouchListener()
	{
		SwipeDismissListViewTouchListener touchListener = null;

		if (nameOfPlaylist != null)
		{
			touchListener = new SwipeDismissListViewTouchListener(listView, new IDismissCallbacks()
			{
				@Override public boolean canDismiss(
						int position)
				{
					return true;
				}

				@Override public void onDismiss(
						ListView listView, int[] reverseSortedPositions)
				{

					for (int index : reverseSortedPositions)
					{
						Song temp = songList.get(index);
						ActivityController.getAccessPlaylist().removeSongFromPlaylist(
								temp,
								nameOfPlaylist);
						songList.remove(index);
					}
					listAdapter.notifyDataSetChanged();
				}
			});
		}
		else
		{
			touchListener = new SwipeDismissListViewTouchListener(listView, new IDismissCallbacks()
			{

				@Override public boolean canDismiss(
						int position)
				{
					return true;
				}

				@Override public void onDismiss(
						ListView listView, int[] reverseSortedPositions)
				{

					for (int index : reverseSortedPositions)
					{
						String temp = stringList.get(index);
						ActivityController.getAccessPlaylist().removePlaylist(temp);
						stringList.remove(index);
					}
					listAdapter.notifyDataSetChanged();
				}
			});
		}
		listView.setOnTouchListener(touchListener);
	}
}
