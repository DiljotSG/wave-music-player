package com.team_ten.wavemusic.presentation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.ActivityController;
import com.team_ten.wavemusic.objects.Library;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class name: ListOfPlaylistsActivity
 * Purpose: An activity to display a list of playlists.
 */
public class ListOfPlaylistsActivity extends CommonMusicActivity
{
	// Instance variables
	private ArrayList<String> stringList;
	private ListOfSongsFragment listFragment;

	@SuppressWarnings("unchecked") @Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lists_of_playlist);
		createMusicControls();
		getSupportActionBar().setTitle("Playlists");
	}

	/**
	 * Invisible the loading panel after each time this activity resumes.
	 */
	@Override protected void onResume()
	{
		super.onResume();
		initializeInstanceVariables();
		configurateFragment();
		configurateNewPlaylistButton();
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
					ActivityController.startListActivity(ListOfPlaylistsActivity.this,
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
		// Get the list of songs and check that the list is of type ArrayList

		//stringList = Library.getCurStringLibrary();
		stringList = ActivityController.getAccessPlaylist().getAllPlaylists();
		// get the Fragment to which the listview belongs.
		listFragment
				=
				(ListOfSongsFragment) getSupportFragmentManager().findFragmentById(R.id.list_songs_fragment);
	}

	/**
	 * Pass data into listOfSongsFragment, and set its Adapter, SwipeDismissListViewTouchListener
	 * and OnItemClickListener.
	 */
	private void configurateFragment()
	{
		if (listFragment != null)
		{
			// Pass necessary data into the fragment.
			listFragment.setStringList(stringList);
			listFragment.setData(ListOfPlaylistsActivity.this,
								 ListActivity.TypeOfRetrieve.PLAYLIST.toString());

			if (!stringList.isEmpty())
			{
				// Since we don't need multi-choose here, so the style is "android.R.layout
				// .simple_list_item_1".
				listFragment.setAdapter(android.R.layout.simple_list_item_1);
				// Allow "swipe to delete" in the listview.
				listFragment.setSwipeDismissListViewTouchListener();
				// Display each single playlist after clicking on an item in the listview.
				listFragment.setOnItemClickListener();
			}
		}
	}

	/**
	 * Set the click event of the "new_Playlist_button", which will display a dialog for
	 * users to enter the name of the playlist to be created.
	 */
	private void configurateNewPlaylistButton()
	{

		findViewById(R.id.new_Playlist_button).setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View v)
			{
				final EditText editText = new EditText(ListOfPlaylistsActivity.this);
				AlertDialog.Builder
						inputDialog
						= new AlertDialog.Builder(ListOfPlaylistsActivity.this);
				inputDialog.setTitle("Please indicate a name for" + " " + "the playlist: ")
						   .setView(editText);

				inputDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					@Override public void onClick(DialogInterface dialog, int which)
					{
						ActivityController.getAccessPlaylist().addPlaylist(editText.getText().toString());

						ActivityController.startSelectSongsActivity(ListOfPlaylistsActivity.this,
																	editText.getText().toString(),
																	true);
					}
				}).show();
			}
		});
	}
}