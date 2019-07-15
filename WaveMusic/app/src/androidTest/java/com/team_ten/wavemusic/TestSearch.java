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

public class TestSearch
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
		//Intents.init();
	}

	@Test public void testSearchByTitle()
	{
		onView(withId(R.id.search)).perform(click());
		onView(withId(R.id.searchView)).perform(typeText("Controlla"));
		onView(withText("Controlla")).check(matches(isDisplayed()));
	}

	@Test public void testSearchByAlbum()
	{
		onView(withId(R.id.search)).perform(click());
		onView(withId(R.id.searchView)).perform(typeText("Skyfall"));
		onView(withText("Skyfall")).check(matches(isDisplayed()));
	}

	@Test public void testSearchByArtist()
	{
		onView(withId(R.id.search)).perform(click());
		onView(withId(R.id.searchView)).perform(typeText("Taylor Swift"));
		onView(withText("Taylor Swift")).check(matches(isDisplayed()));
	}

	@After public void release()
	{
		IdlingRegistry.getInstance().unregister(idlingresource);
	}
}
