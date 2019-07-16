package com.team_ten.wavemusic.util;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.util.Log;

import com.team_ten.wavemusic.objects.Song;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
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
				if (expectedObject.matches(actualObject.getName()))
				{
					return true;
				}
				else
				{
					return false;
				}
			}

			@Override public void describeTo(final Description description)
			{
			}
		};
	}
}
