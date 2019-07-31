package com.team_ten.wavemusic.presentation.other;

import android.app.AlertDialog;
import android.content.Context;

import com.team_ten.wavemusic.R;

public class Messages
{
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
