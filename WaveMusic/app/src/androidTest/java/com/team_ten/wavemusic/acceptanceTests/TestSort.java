package com.team_ten.wavemusic.acceptanceTests;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.presentation.activities.ListActivity;
import com.team_ten.wavemusic.presentation.activities.MainMusicActivity;
import com.team_ten.wavemusic.util.MatcherForSong;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)

/**
 * Test the functionality of sort by song's title, albums and artists.
 *
 * Related feature number: 8
 */ public class TestSort
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
		Intents.init();
	}

	@Test public void sortBySongTitle()
	{
		// start "My Library" activity.
		onView(ViewMatchers.withId(R.id.myLibrary)).perform(click());

		// verify if it is ListActivity.class that has been started and if it has the right
		// "TypeOfRetrieve".
		intended(hasComponent(ListActivity.class.getName()));
		intended(hasExtra("TypeOfRetrieve", "MY_LIBRARY"));

		// check if a built-in song is displayed.
		onData(allOf(is(instanceOf(Song.class)),
					 MatcherForSong.myCustomObjectShouldHaveString("Shake It Off"))).check(matches(
				isDisplayed()));

		Intents.release();
	}

	@Test public void sortByAlbum()
	{
		// start "Albums" activity.
		onView(withId(R.id.albums)).perform(click());

		// verify if it is ListActivity.class that has been started and if it has the right
		// "TypeOfRetrieve".
		intended(hasComponent(ListActivity.class.getName()));
		intended(hasExtra("TypeOfRetrieve", "ALBUM"));

		// check if a built-in album is displayed.
		onData(allOf(is(instanceOf(String.class)),
					 is("1989 (Deluxe)"))).check(matches(isDisplayed()));

		Intents.release();
	}

	@Test public void sortByArtist()
	{
		// start "Artists" activity.
		onView(withId(R.id.artists)).perform(click());

		// verify if it is ListActivity.class that has been started and if it has the right
		// "TypeOfRetrieve".
		intended(hasComponent(ListActivity.class.getName()));
		intended(hasExtra("TypeOfRetrieve", "ARTIST"));

		// check if a built-in artist is displayed.
		onData(allOf(is(instanceOf(String.class)), is("Adele"))).check(matches(isDisplayed()));

		Intents.release();
	}

	@After public void release()
	{
		IdlingRegistry.getInstance().unregister(idlingresource);
	}
}
