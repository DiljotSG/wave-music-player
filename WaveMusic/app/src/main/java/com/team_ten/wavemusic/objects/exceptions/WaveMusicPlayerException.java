package com.team_ten.wavemusic.objects.exceptions;

class WaveMusicPlayerException extends RuntimeException
{
	WaveMusicPlayerException(String errorMessage)
	{
		super(errorMessage);
	}

	WaveMusicPlayerException(final Exception cause)
	{
		super(cause);
	}
}
