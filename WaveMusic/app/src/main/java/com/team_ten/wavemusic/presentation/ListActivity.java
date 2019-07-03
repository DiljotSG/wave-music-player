package com.team_ten.wavemusic.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.ActivityController;
import com.team_ten.wavemusic.objects.Library;
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
	private ArrayList<String> stringList;
	private ListOfSongsFragment listFragment;
	private String typeOfRetrieve;
	private String title;

	@SuppressWarnings("unchecked") @Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song_list);

		initializeInstanceVariables();
		configurateFragment();

		// Set the title in ActionBar.
		getSupportActionBar().setTitle(title);
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
					ActivityController.startListActivity(ListActivity.this,
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
		// Get the ActivityController and songList from Intent.

		typeOfRetrieve = getIntent().getStringExtra("TypeOfRetrieve");
		title = getIntent().getStringExtra("title");

		if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.MY_LIBRARY.toString()) ||
			typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.SEARCH.toString()) ||
			typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.LIKED_SONG.toString()))
		{
			songList = Library.getCurSongLibrary();
		}
		else if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ARTIST.toString()) ||
				 typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ALBUM.toString()) ||
				 typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.PLAYLIST.toString()))
		{
			stringList = Library.getCurStringLibrary();
		}

		// get the Fragment that is to display the listview.
		listFragment
				=
				(ListOfSongsFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);
	}

	/**
	 * Pass data into listOfSongsFragment, and set its Adapter, SwipeDismissListViewTouchListener
	 * and OnItemClickListener.
	 */
	private void configurateFragment()
	{
		if (listFragment != null)
		{
			// To pass necessary data into the the Fragment to display.
			if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.MY_LIBRARY.toString()) ||
				typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.SEARCH.toString()) ||
				typeOfRetrieve.equals(TypeOfRetrieve.LIKED_SONG.toString()))
			{
				listFragment.setSongList(songList);
			}
			else if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ARTIST.toString()) ||
					 typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ALBUM.toString()) ||
					 typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.PLAYLIST.toString()))
			{
				listFragment.setStringList(stringList);
			}
			listFragment.setData(ListActivity.this, typeOfRetrieve);

			// To set correct Adapter for the listview in the Fragment.
			// Since the Activities for which this class is responsible to start don't need "swipe
			// to delete" function, we just set a
			// built-in type Adapter.
			// And since they don't need multi-choice listview either, we just use the style of
			// "android.R.layout.simple_list_item_1" as parameter.
			listFragment.setAdapter(android.R.layout.simple_list_item_1);

			// To make each clicking on a Song to play it.
			listFragment.setOnItemClickListener();
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