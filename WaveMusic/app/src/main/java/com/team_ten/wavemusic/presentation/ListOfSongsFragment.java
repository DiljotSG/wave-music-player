package com.team_ten.wavemusic.presentation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.logic.ActivityController;
import com.team_ten.wavemusic.logic.SwipeDismissListViewTouchListener;
import com.team_ten.wavemusic.objects.Song;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfSongsFragment extends Fragment
{
	// Instance variables

	private View view;
	private ListView listView;
	private ArrayList<Song> songList;
	private ArrayList<String> stringList;
	private ActivityController activityController;
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
	 * @param activityController: The activityController object we will be using during the whole
	 *                            lifecycle of this app.
	 * @param callerActivity:     The parent Activity of this Fragment.
	 * @param typeOfRetrieve:     The type of retrieve that asks for this fragment to display
	 *                            content.
	 */
	public void setData(
			ActivityController activityController, Activity callerActivity, String typeOfRetrieve)
	{
		this.activityController = activityController;
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
					activityController.startNowPlayingActivity(callerActivity,
															   selectedSong,
															   selectedSong.getName(),
															   selectedSong.getURI());
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
					activityController.startAlbumOrArtistAct(callerActivity,
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
					activityController.startSinglePlaylistActivity(callerActivity, selectedString);
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
			Log.v("qwe", "345");
			listAdapter = new ArrayAdapter<Song>(context, resource, songList);
		}
		else if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ARTIST.toString()) ||
				 typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ALBUM.toString()) ||
				 typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.PLAYLIST.toString()))
		{
			listAdapter = new ArrayAdapter<String>(context, resource, stringList);
		}
		listView.setAdapter(listAdapter);
	}

	/**
	 * Set a SwipeDismissListViewTouchListener to the listview, which allows "swipe to delete".
	 */
	public void setSwipeDismissListViewTouchListener()
	{
		SwipeDismissListViewTouchListener touchListener = null;

		if(nameOfPlaylist != null)
		{
			touchListener = new SwipeDismissListViewTouchListener(
					listView,
					new SwipeDismissListViewTouchListener.DismissCallbacks()
					{

						@Override public boolean canDismiss(
								int position)
						{
							return true;
						}

						@Override public void onDismiss(
								ListView listView,
								int[] reverseSortedPositions)
						{

							for (int index : reverseSortedPositions)
							{
								Song temp = songList.get(index);
								activityController.removeSongFromPlaylist(temp, nameOfPlaylist);
								songList.remove(index);
							}
							listAdapter.notifyDataSetChanged();
						}
					});
		}
		else if (nameOfPlaylist != null)
		{
			touchListener = new SwipeDismissListViewTouchListener(
					listView,
					new SwipeDismissListViewTouchListener.DismissCallbacks()
					{

						@Override public boolean canDismiss(
								int position)
						{
							return true;
						}

						@Override public void onDismiss(
								ListView listView,
								int[] reverseSortedPositions)
						{

							for (int index : reverseSortedPositions)
							{
								String temp = stringList.get(index);
								activityController.removePlaylist(temp);
								stringList.remove(index);
							}
							listAdapter.notifyDataSetChanged();
						}
					});
		}
		listView.setOnTouchListener(touchListener);
	}
}
