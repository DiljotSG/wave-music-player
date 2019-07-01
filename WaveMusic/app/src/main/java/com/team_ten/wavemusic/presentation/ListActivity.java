package com.team_ten.wavemusic.presentation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.ActivityController;
import com.team_ten.wavemusic.objects.Song;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class name: ListActivity
 * Purpose: An Activity whose main part is just a ListView, such as the Activity for "My
 * Library", "Artist", "Album" and "Liked songs".
 * Since they have the same layout, we use this class to take care all of them, which is cleaner
 * than
 * creating 4 more Activities.
 */
public class ListActivity extends CommonMusicActivity implements Serializable
{
	// Instance variables
	private ArrayList<Song> songList;    // the list of songs to be displayed in this Activity.
	private ActivityController activityController;
	private ListOfSongsFragment listOfSongsFragment;

	@SuppressWarnings("unchecked") @Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song_list);

		initializeInstanceVariables();
		configurateFragment();

		// Set the title in ActionBar.
		setTitle();
	}

	/**
	 * Invisible the loading panel after each time this activity resumes.
	 */
	@Override protected void onResume()
	{
		super.onResume();
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
					activityController.startListActivity(ListActivity.this,
														 ListActivity.TypeOfRetrieve.SEARCH);
				}
			}).start();
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Set the title shown in the ActionBar based on the type of content this activity is
	 * displaying:
	 * MY_LIBRARY, ALBUM, ARTIST or LIKED_SONG.
	 * The type will be passed from ActivityController, which starts this activity.
	 */
	private void setTitle()
	{
		String typeOfRetrieve = getIntent().getStringExtra("TypeOfRetrieve");
		if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.MY_LIBRARY.toString()))
		{
			getSupportActionBar().setTitle("My Library");
		}
		else if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ALBUM.toString()))
		{
			getSupportActionBar().setTitle("Albums");
		}
		else if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ARTIST.toString()))
		{
			getSupportActionBar().setTitle("Artists");
		}
		else if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.LIKED_SONG.toString()))
		{
			getSupportActionBar().setTitle("Liked Songs");
		}
	}

	/**
	 * To initialize the instance variables.
	 */
	private void initializeInstanceVariables()
	{
		// Get the activityController and songList from Intent.
		activityController = (ActivityController) getIntent().getSerializableExtra(
				"activityController");
		Serializable listSongs = getIntent().getSerializableExtra("listSongs");

		// Check that the list is of type ArrayList
		songList = null;
		if (listSongs instanceof ArrayList && !((ArrayList) listSongs).isEmpty() &&
			((ArrayList) listSongs).get(0) instanceof Song)
		{
			songList = (ArrayList<Song>) listSongs;
		}

		// get the Fragment that is to display the listview.
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
			// To pass necessary data into the the Fragment to display.
			listOfSongsFragment.setData(songList, activityController, ListActivity.this);

			// To set correct Adapter for the listview in the Fragment.
			// Since the Activities for which this class is responsible to start don't need "swipe
			// to delete" function, we just set a
			// built-in type Adapter.
			// And since they don't need multi-choice listview either, we just use the style of
			// "android.R.layout.simple_list_item_1" as parameter.
			listOfSongsFragment.setAdapter(android.R.layout.simple_list_item_1);
			// To make each clicking on a Song to play it.
			listOfSongsFragment.setOnItemClickListener();
		}
	}

	// Since this Activity is responsible for displaying different types of content, we need this
	// enum to
	// indicate which type of content to display.
	public enum TypeOfRetrieve
	{
		MY_LIBRARY, ARTIST, ALBUM, PLAYLIST, LIKED_SONG, SEARCH
	}
}