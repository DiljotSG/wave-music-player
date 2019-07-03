package com.team_ten.wavemusic.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ListView;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.ActivityController;
import com.team_ten.wavemusic.objects.Library;
import com.team_ten.wavemusic.objects.Song;

import java.util.ArrayList;

public class SelectSongsActivity extends AppCompatActivity
{
	// Instance variables
	private ArrayList<Song> songList;
	private String nameOfPlaylist;
	private boolean isCreateNewPlaylist;
	private ListOfSongsFragment listOfSongsFragment;

	@SuppressWarnings("unchecked") @Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_songs);

		initializeInstanceVariables();
		configurateFragment();
		configuratConfirmButton();
		getSupportActionBar().setTitle("Add songs to a playlist");
	}

	@Override protected void onResume()
	{
		super.onResume();

		// invisible the loading panel.
		findViewById(R.id.loadingPanel).setVisibility(View.GONE);
	}

	/**
	 * To initialize the instance variables.
	 */
	private void initializeInstanceVariables()
	{
		songList = Library.getCurLibrary();

		nameOfPlaylist = getIntent().getStringExtra("nameOfPlaylist");

		isCreateNewPlaylist = (Boolean) getIntent().getBooleanExtra("isCreateNewPlaylist", true);

		// Fet get fragment to display listview.
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
			// set necessary data into the fragment.
			listOfSongsFragment.setSongList(songList);
			listOfSongsFragment.setData(SelectSongsActivity.this, ListActivity.TypeOfRetrieve.MY_LIBRARY.toString());

			// Since user need to multi-choose items, we set the choice mode to be "ListView
			// .CHOICE_MODE_MULTIPLE".
			listOfSongsFragment.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

			// Set adapter to the listview, since user need to multi-choose items, we use the
			// "simple_list_item_multiple_choice" style.
			listOfSongsFragment.setAdapter(android.R.layout.simple_list_item_multiple_choice);
		}
	}

	/**
	 * set the click event of the "confirm" button, which add the selected songs into a
	 * specific playlist.
	 */
	private void configuratConfirmButton()
	{

		findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View v)
			{
				SparseBooleanArray booleanArray = ((ListView) (listOfSongsFragment.getView()
																				  .findViewById(R.id.list_songs)))
						.getCheckedItemPositions();

				ArrayList<Song> selected_songs = new ArrayList<>();

				for (int i = 0; i < booleanArray.size(); i++)
				{
					int key = booleanArray.keyAt(i);

					if (booleanArray.get(key))
					{
						selected_songs.add(songList.get(key));
					}
				}

				for(Song song: selected_songs)
				{
					ActivityController.getAccessPlaylist().addSongToPlaylist(song, nameOfPlaylist);
				}

				if (isCreateNewPlaylist)
				{
					ActivityController.startSinglePlaylistActivity(SelectSongsActivity.this,
																   nameOfPlaylist);
				}
				SelectSongsActivity.this.finish();
			}
		});
	}
}
