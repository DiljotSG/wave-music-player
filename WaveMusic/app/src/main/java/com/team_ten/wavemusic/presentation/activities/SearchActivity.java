package com.team_ten.wavemusic.presentation.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.ActivityController;
import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.presentation.other.Themes;

import java.util.ArrayList;

public class SearchActivity extends CommonMusicActivity
{
	// Instance variables
	private SearchView searchView;
	private ListView listView;
	private ArrayList<Song> results;
	private ArrayList<Song> allSongs;
	private ProgressBar loadingPanel;

	@SuppressWarnings("unchecked") @Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		initializeInstanceVariables();
		setOnItemClickListener();
		setOnQueryTextListener();
		createMusicControls();

		getSupportActionBar().setTitle("Search");
		loadingPanel.setVisibility(View.GONE);
	}

	/**
	 * To initialize the instance variables.
	 */
	private void initializeInstanceVariables()
	{
		searchView = (SearchView) findViewById(R.id.searchView);
		listView = (ListView) findViewById(R.id.resultOfSearch);
		listView.setTextFilterEnabled(true);
		loadingPanel = findViewById(R.id.loadingPanel);
		results = new ArrayList<Song>();

		allSongs = ActivityController.getAccessSong().getAllSongs();
	}

	/**
	 * When a song in the ListView is clicked, start a new NowPlaying activity and pass the
	 * song's title and URI into it.
	 */
	private void setOnItemClickListener()
	{

		AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Song selectedSong = results.get(position);
				ActivityController.startNowPlayingActivity(SearchActivity.this,
														   selectedSong);
				ArrayList<Song> listWithOneSong = new ArrayList<Song>();
				listWithOneSong.add(selectedSong);
				PlaybackController.setPlaybackQueue(listWithOneSong);
			}
		};
		listView.setOnItemClickListener(itemClickListener);
	}

	/**
	 * Return whether or not a song should be included based on search text
	 */
	private boolean includedInSearch(Song song, String target)
	{
		if (song.getName() == null)
		{
			return false;
		}

		boolean titleIncluded = song.getName().toLowerCase().contains(target.toLowerCase());
		boolean artistIncluded = song.getArtist().toLowerCase().contains(target.toLowerCase());
		boolean albumIncluded = song.getAlbum().toLowerCase().contains(target.toLowerCase());

		return titleIncluded || artistIncluded || albumIncluded;
	}

	/**
	 * The listview will refresh after each time the user enter a character.
	 * If a song's title has all the characters the user entered, it will appear in the
	 * listview.
	 */
	private void setOnQueryTextListener()
	{

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
		{
			@Override public boolean onQueryTextSubmit(String query)
			{
				return false;
			}

			@Override public boolean onQueryTextChange(String newText)
			{
				results.clear();
				loadingPanel.setVisibility(View.VISIBLE);

				if (!TextUtils.isEmpty(newText))
				{
					for (Song song : allSongs)
					{
						if (includedInSearch(song, newText))
						{
							results.add(song);
						}
					}
					ArrayAdapter<Song> listAdapter = new ArrayAdapter<Song>(SearchActivity.this,
																			android.R.layout.simple_list_item_1,
																			results);
					listView.setAdapter(listAdapter);
				}
				else
				{
					listView.setAdapter(null);
				}
				loadingPanel.setVisibility(View.GONE);
				return false;
			}
		});
	}


	/**
	 * Since this activity doesn't need "Search" button, we inflate "change themes" button only.
	 */
	@Override public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_only_change_theme, menu);
		if (Themes.getIsLight())
		{
			menu.getItem(0).setIcon(R.drawable.ic_wb_sunny_black_24dp);
		}
		else
		{
			menu.getItem(0).setIcon(R.drawable.ic_brightness_3_black_24dp);
		}
		return true;
	}

	/**
	 * Handler for item selection.
	 *
	 * @return if the item is selected
	 */
	@Override public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.changeTheme)
		{
			Themes.changeTheme(this);
		}
		return true;
	}
}
