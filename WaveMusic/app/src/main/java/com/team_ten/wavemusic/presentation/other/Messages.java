package com.team_ten.wavemusic.presentation.other;

import android.app.AlertDialog;
import android.content.Context;

import com.team_ten.wavemusic.R;

public class Messages
{
	/**
	 * Create an alert if the app encounters a fatal error
	 */
	public static void fatalError(final Context context, String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(context.getString(R.string.fatalError));
		alertDialog.setMessage(message);
		alertDialog.show();
	}

	/**
	 * Create an alert dialogue for a warning
	 */
	public static void warning(final Context context, String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(context.getString(R.string.warning));
		alertDialog.setMessage(message);
		alertDialog.show();
	}

}
