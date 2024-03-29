package com.team_ten.wavemusic.util;

import android.support.test.espresso.matcher.BoundedMatcher;

import com.team_ten.wavemusic.objects.music.Song;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalToIgnoringCase;

public class MatcherForSong
{
	public static Matcher<Object> myCustomObjectShouldHaveString(String expectedTest)
	{
		return myCustomObjectShouldHaveString(equalToIgnoringCase(expectedTest));
	}

	private static Matcher<Object> myCustomObjectShouldHaveString(final Matcher<String> expectedObject)
	{
		return new BoundedMatcher<Object, Song>(Song.class)
		{
			@Override public boolean matchesSafely(final Song actualObject)
			{
				return expectedObject.matches(actualObject.getName());
			}

			@Override public void describeTo(final Description description)
			{
			}
		};
	}
}
