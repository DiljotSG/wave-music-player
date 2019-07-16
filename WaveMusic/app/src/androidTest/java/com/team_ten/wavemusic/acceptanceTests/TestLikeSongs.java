package com.team_ten.wavemusic.acceptanceTests;

import android.graphics.drawable.Drawable;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.ImageView;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.presentation.MainMusicActivity;
import com.team_ten.wavemusic.util.MatcherForSong;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
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
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.not;

/**
 * Test the functionality of like and unlike songs.
 *
 * Related feature number: 10
 */
@RunWith(AndroidJUnit4.class) public class TestLikeSongs
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

	@Test public void testLikeAndUnlikeASong()
	{
		// start "My Library" activity, play a song and like it.
		onView(ViewMatchers.withId(R.id.myLibrary)).perform(click());
		onData(allOf(
				is(instanceOf(Song.class)),
				MatcherForSong.myCustomObjectShouldHaveString("Shake It Off"))).perform(click());
		onView(withId(R.id.likeImg)).perform(setChecked());

		// Back to MainActivity and start Liked Songs activity.
		Espresso.pressBack();
		Espresso.pressBack();
		onView(withId(R.id.likedSongs)).perform(click());

		// Play the song we just liked, then unlike it and check if it no longer exist in the list.
		onData(allOf(
				is(instanceOf(Song.class)),
				MatcherForSong.myCustomObjectShouldHaveString("Shake It Off"))).perform(click());
		onView(withId(R.id.likeImg)).perform(click());
		Espresso.pressBack();
		onView(withId(R.id.list_songs)).check(matches(not(MatcherForSong.myCustomObjectShouldHaveString(
				"Shake It Off"))));
	}

	@After public void release()
	{
		IdlingRegistry.getInstance().unregister(idlingresource);
	}

	private static ViewAction setChecked()
	{
		return new ViewAction()
		{
			@Override public BaseMatcher<View> getConstraints()
			{
				return new BaseMatcher<View>()
				{
					@Override public boolean matches(Object item)
					{
						return isA(ImageView.class).matches(item);
					}

					@Override
					public void describeMismatch(Object item, Description mismatchDescription)
					{
					}

					@Override public void describeTo(Description description)
					{
					}
				};
			}

			@Override public String getDescription()
			{
				return null;
			}

			@Override public void perform(UiController uiController, View view)
			{
				ImageView imageView = (ImageView) view;
				Drawable.ConstantState drawable = imageView.getDrawable().getConstantState();
				Drawable.ConstantState drawable1 = ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_border_black_24dp).getConstantState();
				if(drawable.equals(drawable1))
				{
					view.performClick();
				}
			}
		};
	}
}
