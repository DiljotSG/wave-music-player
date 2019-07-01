package com.team_ten.wavemusic.presentation;

import android.app.AlertDialog;
import android.content.Context;

import com.team_ten.wavemusic.R;

public class Messages
{
	public static void fatalError(final Context context, String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(context.getString(R.string.fatalError));
		alertDialog.setMessage(message);
		alertDialog.show();
	}

	public static void warning(final Context context, String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(context.getString(R.string.warning));
		alertDialog.setMessage(message);
		alertDialog.show();
	}

}
