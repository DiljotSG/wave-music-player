package com.team_ten.wavemusic.application;

public class Main
{
	private static String dbName = "WaveDB";

	public synchronized static void setDBPathName(final String name)
	{
		try
		{
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		dbName = name;
	}

	public synchronized static String getDBPathName()
	{
		return dbName;
	}
}
