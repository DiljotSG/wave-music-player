package com.team_ten.wavemusic.objects.exceptions;

public class WaveMusicPlayerException extends RuntimeException
{
	public WaveMusicPlayerException(String errorMessage)
	{
		super(errorMessage);
	}

	public WaveMusicPlayerException(final Exception cause)
	{
		super(cause);
	}
}
