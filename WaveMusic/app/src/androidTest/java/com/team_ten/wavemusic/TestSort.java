package com.team_ten.wavemusic;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.presentation.ListActivity;
import com.team_ten.wavemusic.presentation.MainMusicActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)

public class TestSort
{
	@Rule
	public ActivityTestRule<MainMusicActivity>
			activityRule
			= new ActivityTestRule<MainMusicActivity>(MainMusicActivity.class, true);

	private IdlingResource idlingresource;

	@Before public void setUp()
	{
		idlingresource = activityRule.getActivity().getIdlingResource();
		IdlingRegistry.getInstance().register(idlingresource);
		Intents.init();
	}


	@Test public void sortBySongTitle()
	{
		onView(withId(R.id.myLibrary)).perform(click());

		//verify
		intended(hasComponent(ListActivity.class.getName()));
		intended(hasExtra("TypeOfRetrieve", "MY_LIBRARY"));
		onData(allOf(is(instanceOf(Song.class)), myCustomObjectShouldHaveString("Shake It Off"))).check(
				matches(isDisplayed()));
		Intents.release();
	}

	private static Matcher<Object> myCustomObjectShouldHaveString(String expectedTest)
	{
		return myCustomObjectShouldHaveString(equalTo(expectedTest));
	}

	private static Matcher<Object> myCustomObjectShouldHaveString(final Matcher<String> expectedObject)
	{
		return new BoundedMatcher<Object, Song>(Song.class)
		{
			@Override public boolean matchesSafely(final Song actualObject)
			{
				// next line is important ... requiring a String having an "equals" method
				if (expectedObject.matches(actualObject.getName()))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			@Override
			public void describeTo(final Description description) {
				description.appendText("getnumber should return ");
			}
		};
	}

	@Test public void sortByAlbum()
	{
		onView(withId(R.id.albums)).perform(click());

		//verify
		intended(hasComponent(ListActivity.class.getName()));
		intended(hasExtra("TypeOfRetrieve", "ALBUM"));
		onData(allOf(
				is(instanceOf(String.class)),
				is("1989 (Deluxe)"))).check(matches(isDisplayed()));
		Intents.release();
	}

	@Test public void sortByArtist()
	{
		onView(withId(R.id.artists)).perform(click());

		//verify
		intended(hasComponent(ListActivity.class.getName()));
		intended(hasExtra("TypeOfRetrieve", "ARTIST"));
		onData(allOf(is(instanceOf(String.class)), is("Adele"))).check(matches(isDisplayed()));
		Intents.release();
	}

	@After public void release()
	{
		IdlingRegistry.getInstance().unregister(idlingresource);
	}
}
