package com.team_ten.wavemusic.acceptanceTests;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.objects.music.Song;
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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)

/**
 * Test the functionality's related to View Song's Properties, including viewing title, album,
 * artist and genre.
 *
 * Related feature number: 17
 */ public class TestViewSongsProperties
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

	@Test public void testViewSongsProperties()
	{
		// start "My Library" activity, play a song and check if it is the right song.
		onView(ViewMatchers.withId(R.id.myLibrary)).perform(click());
		onData(allOf(
				is(instanceOf(Song.class)),
				MatcherForSong.myCustomObjectShouldHaveString("Shake It Off"))).perform(click());
		onView(withId(R.id.title)).check(matches(withText("Song: Shake It Off")));
		onView(withId(R.id.album)).check(matches(withText("Album: 1989 (Deluxe)")));
		onView(withId(R.id.artist)).check(matches(withText("Artist: Taylor Swift")));
		onView(withId(R.id.genre)).check(matches(withText("Genre: Country")));
	}

	@After public void release()
	{
		IdlingRegistry.getInstance().unregister(idlingresource);
	}
}
