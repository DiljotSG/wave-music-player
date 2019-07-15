package com.team_ten.wavemusic;

import android.support.test.espresso.matcher.BoundedMatcher;

import com.team_ten.wavemusic.objects.Song;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;

public class MatcherForSong
{
	public static Matcher<Object> myCustomObjectShouldHaveString(String expectedTest)
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
}
