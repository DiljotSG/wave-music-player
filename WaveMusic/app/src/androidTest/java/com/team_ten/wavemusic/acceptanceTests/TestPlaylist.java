package com.team_ten.wavemusic.acceptanceTests;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

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
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Test the functionalities related to playlists, including creating and deleting a playlist, adding
 * and removing songs to it, and playing a song in it.
 *
 * Related feature number: 16
 */
@RunWith(AndroidJUnit4.class) public class TestPlaylist
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

	@Test public void testAddSongs()
	{
		// starts Playlists activity.
		onView(ViewMatchers.withId(R.id.playLists)).perform(click());

		// create a new playlist, add a song into it, play it, and check if it is the song we
		// added.
		onView(withId(R.id.new_Playlist_button)).perform(click());
		onView(isAssignableFrom(EditText.class)).perform(typeText("test"));
		onView(withText("OK")).perform(click());
		onData(allOf(
				is(instanceOf(Song.class)),
				MatcherForSong.myCustomObjectShouldHaveString("Shake It Off"))).perform(click());
		onView(withId(R.id.confirm_button)).perform(click());
		onView(withText("test")).perform(click());
		onData(allOf(
				is(instanceOf(Song.class)),
				MatcherForSong.myCustomObjectShouldHaveString("Shake It Off"))).perform(click());
		onView(withId(R.id.title)).check(matches(withText("Song: Shake It Off")));
		onView(withId(R.id.album)).check(matches(withText("Album: 1989 (Deluxe)")));
		onView(withId(R.id.artist)).check(matches(withText("Artist: Taylor Swift")));
		onView(withId(R.id.genre)).check(matches(withText("Genre: Country")));

		// back to the activity that displays the playlist we just created.
		// delete the song we just added and check if it no longer exists.
		Espresso.pressBack();
		onData(allOf(
				is(instanceOf(Song.class)),
				MatcherForSong.myCustomObjectShouldHaveString("Shake It Off"))).perform(swipeRight());
		onView(withId(R.id.list_songs)).check(matches(not(MatcherForSong.myCustomObjectShouldHaveString(
				"Shake It Off"))));

		// back to the activity that displays all playlists.
		// delete the playlist we just created and check if it no longer exists.
		Espresso.pressBack();
		onView(withText("test")).perform(swipeRight());
		onView(withId(R.id.list_songs)).check(matches(not(withText("test"))));
	}

	@After public void release()
	{
		IdlingRegistry.getInstance().unregister(idlingresource);
	}
}
