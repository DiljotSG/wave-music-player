package com.team_ten.wavemusic.presentation.activities;

import android.os.Bundle;
import android.view.View;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.ActivityController;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.presentation.other.ListOfSongsFragment;

import java.util.ArrayList;

public class SinglePlaylistActivity extends CommonMusicActivity
{
	// Instance variables
	private ArrayList<Song> songList;
	private String nameOfPlaylist;
	private ListOfSongsFragment listOfSongsFragment;

	@SuppressWarnings("unchecked") @Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_playlist);

		initializeInstanceVariables();
		configurateFragment();
		configurateAddSongsButton();
		createMusicControls();

		if (getSupportActionBar() != null)
		{
			getSupportActionBar().setTitle(nameOfPlaylist);
		}
	}

	@Override protected void onResume()
	{
		super.onResume();
		initializeInstanceVariables();
		configurateFragment();
		configurateAddSongsButton();
		// invisible the loading panel.
		findViewById(R.id.loadingPanel).setVisibility(View.GONE);
	}

	/**
	 * To initialize the instance variables.
	 */
	private void initializeInstanceVariables()
	{

		nameOfPlaylist = getIntent().getStringExtra("nameOfPlaylist");

		songList = ActivityController.getAccessPlaylist().getSongsFromPlaylist(nameOfPlaylist);

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
			listOfSongsFragment.setData(SinglePlaylistActivity.this,
										ListActivity.TypeOfRetrieve.MY_LIBRARY.toString());
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
				ActivityController.startSelectSongsActivity(SinglePlaylistActivity.this,
															nameOfPlaylist,
															false);
			}
		});
	}
}