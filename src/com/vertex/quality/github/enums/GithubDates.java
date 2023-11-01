package com.vertex.quality.github.enums;

/**
 * The type of date formats that can be chosen to view commits by
 *
 * @author hho
 */
public enum GithubDates
{
	DAILY(1),
	WEEKLY(7),
	MONTHLY(28);

	public int date;

	GithubDates( int date )
	{
		this.date = date;
	}
}
