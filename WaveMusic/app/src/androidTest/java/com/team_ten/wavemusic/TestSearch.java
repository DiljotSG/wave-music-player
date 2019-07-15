package com.team_ten.wavemusic;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;

import com.team_ten.wavemusic.presentation.MainMusicActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test the functionalities related to Search, including search by song's title, albums and artists.
 *
 * Related feature number: 9
 */
public class TestSearch
{
	@Rule
	public ActivityTestRule<MainMusicActivity>
			activityRule
			= new ActivityTestRule<MainMusicActivity>(MainMusicActivity.class, true);

	private IdlingResource idlingresource;

	@Before public void setUp()
	{
		// prepare for asynchronous testing.
		idlingresource = activityRule.getActivity().getIdlingResource();
		IdlingRegistry.getInstance().register(idlingresource);
	}

	@Test public void testSearchByTitle()
	{
		// start SearchActivity
		onView(withId(R.id.search)).perform(click());
		// enter the title of the song we want to search for.
		onView(withId(R.id.searchView)).perform(typeText("Controlla"));
		// since the song is built-in the app, it should appears in the result.
		onView(withText("Controlla")).check(matches(isDisplayed()));
	}

	@Test public void testSearchByAlbum()
	{
		// start SearchActivity
		onView(withId(R.id.search)).perform(click());
		// enter the album we want to search for.
		onView(withId(R.id.searchView)).perform(typeText("Skyfall"));
		// since the album is built-in the app, it should appears in the result.
		onView(withText("Skyfall")).check(matches(isDisplayed()));
	}

	@Test public void testSearchByArtist()
	{
		// start SearchActivity
		onView(withId(R.id.search)).perform(click());
		// enter the artist we want to search for.
		onView(withId(R.id.searchView)).perform(typeText("Taylor Swift"));
		// since the artist is built-in the app, it should appears in the result.
		onView(withText("Taylor Swift")).check(matches(isDisplayed()));
	}

	@After public void release()
	{
		IdlingRegistry.getInstance().unregister(idlingresource);
	}
}
