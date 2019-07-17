package com.team_ten.wavemusic.application.db;

public class Main
{
	private static String dbName = "WaveDB";

	/**
	 * Set the path name of the database
	 *
	 * @return dbName: the path name as a string
	 */
	public synchronized static String getDBPathName()
	{
		return dbName;
	}

	/**
	 * Set the path name of the database
	 *
	 * @param name: the path to use
	 */
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
}
