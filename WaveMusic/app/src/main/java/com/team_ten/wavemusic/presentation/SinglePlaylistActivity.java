package com.team_ten.wavemusic.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.logic.ActivityController;
import com.team_ten.wavemusic.objects.Song;

import java.io.Serializable;
import java.util.ArrayList;

public class SinglePlaylistActivity extends AppCompatActivity
{
	// Instance variables
	private ArrayList<Song> songList;
	private ActivityController activityController;
	private String nameOfPlaylist;
	private ListOfSongsFragment listOfSongsFragment;

	@SuppressWarnings("unchecked") @Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_playlist);

		initializeInstanceVariables();
		configurateFragment();
		configurateAddSongsButton();

		getSupportActionBar().setTitle(nameOfPlaylist);
	}

	@Override protected void onResume()
	{
		super.onResume();
		// invisible the loading panel.
		findViewById(R.id.loadingPanel).setVisibility(View.GONE);
	}

	/**
	 * Inflate the "search" button on the right-top corner.
	 */
	@Override public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Set the click event of the "search" button on the right-top corner, which will start the
	 * SearchActivity.
	 */
	@Override public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.search)
		{
			new Thread(new Runnable()
			{
				@Override public void run()
				{
					activityController.startListActivity(SinglePlaylistActivity.this,
														 ListActivity.TypeOfRetrieve.SEARCH);
				}
			}).start();
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * To initialize the instance variables.
	 */
	private void initializeInstanceVariables()
	{
		activityController = (ActivityController) getIntent().getSerializableExtra(
				"activityController");
		nameOfPlaylist = getIntent().getStringExtra("nameOfPlaylist");
		Serializable listSongs = getIntent().getSerializableExtra("listSongs");

		// Check that the list is of type ArrayList
		songList = null;
		if (listSongs instanceof ArrayList && !((ArrayList) listSongs).isEmpty() &&
			((ArrayList) listSongs).get(0) instanceof Song)
		{
			songList = (ArrayList<Song>) listSongs;
		}

		// get the fragment to display listview.
		listOfSongsFragment
				=
				(ListOfSongsFragment) getSupportFragmentManager().findFragmentById(R.id.list_songs_fragment);
	}

	/**
	 * Pass data into listOfSongsFragment, and set its Adapter, SwipeDismissListViewTouchListener
	 * and OnItemClickListener.
	 */
	private void configurateFragment()
	{
		if (listOfSongsFragment != null)
		{
			// Pass necessary data into the fragment.
			listOfSongsFragment.setSongList(songList);
			listOfSongsFragment.setData(activityController, SinglePlaylistActivity.this, ListActivity.TypeOfRetrieve.MY_LIBRARY.toString());
			listOfSongsFragment.setNameOfPlaylist(nameOfPlaylist);

			// Since we need "swipe to delete" function here, we set a CustomAdapter to the
			// listview.
			if (!songList.isEmpty())
			{
				// Since we don't need multi-choose here, so the style is "android.R.layout
				// .simple_list_item_1".
				listOfSongsFragment.setAdapter(android.R.layout.simple_list_item_1);
				// Allow "swipe to delete" in the listview.
				listOfSongsFragment.setSwipeDismissListViewTouchListener();
				// Display each single playlist after clicking on an item in the listview.
				listOfSongsFragment.setOnItemClickListener();
			}
		}
	}

	/**
	 * Set click event for the "add Songs" button, which starts SelectSongsActivity for
	 * user to select songs and add them into this playlist.
	 */
	private void configurateAddSongsButton()
	{
		findViewById(R.id.addSongs_button).setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View v)
			{
				activityController.startSelectSongsActivity(SinglePlaylistActivity.this,
															nameOfPlaylist,
															false);
			}
		});
	}
}