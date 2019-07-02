package com.team_ten.wavemusic.persistence.hsqldb;

public class WaveDBIntegrityConstraintException extends WaveDBPersistenceException
{
	public WaveDBIntegrityConstraintException(final Exception cause)
	{
		super(cause);
	}
}
