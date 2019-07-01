package com.team_ten.wavemusic.presentation;

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
import com.team_ten.wavemusic.application.SwipeDismissListViewTouchListener;
import com.team_ten.wavemusic.objects.Song;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfSongsFragment extends Fragment
{
	// Instance variables

	private View view;
	private ListView listView;
	private ArrayList<Song> songList;
	private ActivityController activityController;
	private Activity callerActivity;
	private Context context;
	private int position;
	ArrayAdapter<Song> listAdapter;

	public ListOfSongsFragment()
	{
		// Required empty public constructor
	}

	/**
	 * Set necessary data into the instance variables.
	 *
	 * @param songList:           The list of songs to be displayed in this fragment.
	 * @param activityController: The activityController object we will be using during the whole
	 *                            lifecycle of this app.
	 * @param callerActivity:     The parent Activity of this Fragment.
	 */
	public void setData(
			ArrayList<Song> songList,
			ActivityController activityController,
			Activity callerActivity)
	{
		this.songList = songList;
		this.activityController = activityController;
		this.callerActivity = callerActivity;
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
			songList = null;
			Serializable listSongs = savedInstanceState.getSerializable("listSongs");

			// Check that the list is of type ArrayList
			songList = null;
			if (listSongs instanceof ArrayList && !((ArrayList) listSongs).isEmpty() &&
				((ArrayList) listSongs).get(0) instanceof Song)
			{
				songList = (ArrayList<Song>) listSongs;
			}
		}
	}

	@Override public View onCreateView(
			LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_list_of_songs, container, false);
		context = inflater.getContext();
		return view;
	}

	@Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		listView = (ListView) getView().findViewById(R.id.list_songs);
		super.onViewCreated(view, savedInstanceState);
	}

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

	@Override public void onResume()
	{
		super.onResume();

		// Resume the position of the listview after each time the screen is rotated.
		listView.setSelection(position);
	}

	@Override public void onSaveInstanceState(@NonNull Bundle outState)
	{
		super.onSaveInstanceState(outState);

		// Sava data into Bundle so that they don't need to be rebuilt after each time the device
		// is rotated.
		outState.putSerializable("songList", songList);
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
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Song selectedSong = songList.get(position);
				// Update the playback queue to match our playlist
				PlaybackController.setPlaybackQueue(songList);
				activityController.startNowPlayingActivity(callerActivity,
														   selectedSong,
														   selectedSong.getName(),
														   selectedSong.getURI());
			}
		});
	}

	/**
	 * Set a built-in type ArrayAdapter to the listview.
	 */
	public void setAdapter(int resource)
	{
		listAdapter = new ArrayAdapter<Song>(context, resource, songList);
		listView.setAdapter(listAdapter);
	}

	/**
	 * Set a SwipeDismissListViewTouchListener to the listview, which allows "swipe to delete".
	 */
	public void setSwipeDismissListViewTouchListener()
	{
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
				listView,
				new SwipeDismissListViewTouchListener.DismissCallbacks()
				{
					@Override
					public boolean canDismiss(
							int position)
					{
						return true;
					}
	
					@Override
					public void onDismiss(
							ListView listView,
							int[] reverseSortedPositions)
					{

						for (int index : reverseSortedPositions)
						{
							songList.remove(index);
						}
						listAdapter.notifyDataSetChanged();
					}
				});
		listView.setOnTouchListener(touchListener);
	}
}
