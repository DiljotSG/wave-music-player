package com.team_ten.wavemusic.presentation.other;

import android.app.Activity;
import android.support.v7.app.AppCompatDelegate;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

public class Themes
{
	private static boolean isLight = true;

	public static void changeTheme(Activity activity)
	{
		if (isLight)
		{
			isLight = false;
			AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
		}
		else
		{
			isLight = true;
			AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
		}
		activity.recreate();
	}

	public static boolean getIsLight()
	{
		return isLight;
	}
}
