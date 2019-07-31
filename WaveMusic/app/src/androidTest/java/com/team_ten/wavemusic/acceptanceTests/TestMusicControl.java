package com.team_ten.wavemusic.acceptanceTests;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.AppSettings;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.presentation.activities.MainMusicActivity;
import com.team_ten.wavemusic.util.MatcherForSong;

import org.hamcrest.BaseMatcher;
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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test the functionality of Shuffle and music control, including volume control, skip and skip
 * back songs, and pause and play.
 * Related feature number: 7 and 18
 */
@RunWith(AndroidJUnit4.class) public class TestMusicControl
{
	@Rule
	public final ActivityTestRule<MainMusicActivity>
			activityRule
			= new ActivityTestRule<>(MainMusicActivity.class, true);

	private IdlingResource idlingresource;

	@Before public void setUp()
	{
		// prepare for asynchronous testing.
		idlingresource = activityRule.getActivity().getIdlingResource();
		IdlingRegistry.getInstance().register(idlingresource);
	}

	@Test public void testMusicControl()
	{
		// start "My Library" activity, play a song.
		onView(ViewMatchers.withId(R.id.myLibrary)).perform(click());
		onData(allOf(
				is(instanceOf(Song.class)),
				MatcherForSong.myCustomObjectShouldHaveString("Shake It Off"))).perform(click());

		// set a new volume using seek bar to see if it works.
		onView(withId(R.id.seekBarForVolume)).perform(setProgress(5));
		assertEquals(5, AppSettings.getVolume());

		// Turn off shuffle, click "skip" first and then "skip_back",the positions of the
		// playing songs in playbackQueue should be in sequence.
		onView(withId(R.id.shuffleImg)).perform(setUnchecked());

		int currentPosition = PlaybackController.getPlaybackQueue().getPosition();
		onView(withId(R.id.skip_back_button)).perform(click());
		assertEquals(currentPosition - 1, PlaybackController.getPlaybackQueue().getPosition());

		currentPosition = PlaybackController.getPlaybackQueue().getPosition();
		onView(withId(R.id.skip_button)).perform(click());
		assertEquals(currentPosition + 1, PlaybackController.getPlaybackQueue().getPosition());

		// Turn on shuffle, the isShuffle() method in PlaybackController class should return true.
		// Since when shuffle is on, a random next song will play, it is possible for its position
		// in the playbackQueue to be or not to be next to that of the current playing song, so we
		// can not check the positions like we did above when shuffle is off.
		onView(withId(R.id.shuffleImg)).perform(click());
		assertTrue(PlaybackController.isShuffle());

		// Click pause button to see if the MediaPlayer stops playing.
		onView(withId(R.id.pause_button)).perform(click());
		assertFalse(PlaybackController.getMediaPlayer().isPlaying());

		// Click play button to see if the MediaPlayer starts playing.
		onView(withId(R.id.play_button)).perform(click());
		assertTrue(PlaybackController.getMediaPlayer().isPlaying());
	}

	@After public void release()
	{
		IdlingRegistry.getInstance().unregister(idlingresource);
	}

	@SuppressWarnings("SameParameterValue")
	private static ViewAction setProgress(final int progress)
	{
		return new ViewAction()
		{
			@Override public void perform(UiController uiController, View view)
			{
				SeekBar seekBar = (SeekBar) view;
				seekBar.setProgress(progress);
			}

			@Override public String getDescription()
			{
				return "Set a progress on a SeekBar";
			}

			@Override public Matcher<View> getConstraints()
			{
				return ViewMatchers.isAssignableFrom(SeekBar.class);
			}
		};
	}

	private static ViewAction setUnchecked()
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
				if (imageView.getAlpha() != 64)
				{
					view.performClick();
				}
			}
		};
	}
}
