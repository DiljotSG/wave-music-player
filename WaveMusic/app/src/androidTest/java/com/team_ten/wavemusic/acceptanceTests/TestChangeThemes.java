package com.team_ten.wavemusic.acceptanceTests;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatDelegate;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.presentation.activities.MainMusicActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test the functionality of changing between 2 themes.
 * <p>
 * Related feature number: 14
 */
@RunWith(AndroidJUnit4.class) public class TestChangeThemes
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

	@Test public void testChangeThemes()
	{
		// Record the Night Mode before testing, then change theme, check if the current mode is
		// different from the initial mode.
		int mode = AppCompatDelegate.getDefaultNightMode();

		onView(withId(R.id.changeTheme)).perform(click());
		assertFalse (mode == AppCompatDelegate.getDefaultNightMode());

		// Change theme again, check if the current mode is identical to the initial one.
		onView(withId(R.id.changeTheme)).perform(click());
		assertTrue (mode == AppCompatDelegate.getDefaultNightMode());
	}

	@After public void release()
	{
		IdlingRegistry.getInstance().unregister(idlingresource);
	}
}
