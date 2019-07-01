package com.team_ten.wavemusic.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.logic.ActivityController;
import com.team_ten.wavemusic.objects.Song;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
{
	// Instance variables
	private SearchView searchView;
	private ListView listView;
	private ArrayList<Song> results;
	private ArrayList<Song> allSongs;
	private ActivityController activityController;
	private ProgressBar loadingPanel;

	@SuppressWarnings("unchecked") @Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		initializeInstanceVariables();
		setOnItemClickListener();
		setOnQueryTextListener();

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
		allSongs = null;

		Serializable listSongs = getIntent().getSerializableExtra("listSongs");

		// Check that the list is of type ArrayList
		if (listSongs instanceof ArrayList && !((ArrayList) listSongs).isEmpty() &&
			((ArrayList) listSongs).get(0) instanceof Song)
		{
			allSongs = (ArrayList<Song>) listSongs;
		}
		activityController = (ActivityController) getIntent().getSerializableExtra(
				"activityController");
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
				activityController.startNowPlayingActivity(SearchActivity.this,
														   selectedSong,
														   selectedSong.getName(),
														   selectedSong.getURI());
			}
		};
		listView.setOnItemClickListener(itemClickListener);
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
						if (song.getName() != null &&
							song.getName().toLowerCase().contains(newText.toLowerCase()))
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
}
