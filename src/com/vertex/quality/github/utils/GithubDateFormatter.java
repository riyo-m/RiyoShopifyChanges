package com.vertex.quality.github.utils;

import com.vertex.quality.github.enums.GithubDates;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Formats dates
 *
 * @author hho
 */
public class GithubDateFormatter
{
	private GithubDateFormatter( )
	{
	}

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Gets all the dates from the given past date to the current date
	 *
	 * @param pastDate the past date in the format of yyyy-MM-dd
	 *
	 * @return a list of string containing all of the past dates to the current date
	 */
	public static List<String> getDatesUntilPastDate( String pastDate )
	{
		List<String> dates = null;
		try
		{
			Date pastDateFormat = dateFormat.parse(pastDate);
			DateTime pastDateTime = new DateTime(pastDateFormat);

			Date currentDate = new Date();
			DateTime currentDateTime = new DateTime(currentDate);

			int days = Days
				.daysBetween(pastDateTime, currentDateTime)
				.getDays();
			dates = new ArrayList<>();
			for ( int i = 0 ; i <= days ; i++ )
			{
				DateTime d = pastDateTime.withFieldAdded(DurationFieldType.days(), i);
				String date = dateFormat.format(d.toDate());
				dates.add(date);
			}
		}
		catch ( Exception e )
		{
		}
		return dates;
	}

	/**
	 * Gets the past date from the current date
	 *
	 * @param date    the date format
	 * @param numDate the number of dates
	 *
	 * @return the past date
	 */
	public static String getPastDate( GithubDates date, int numDate )
	{
		Date currentDate = new Date();
		DateTime dtCurrentDate = new DateTime(currentDate);
		DateTime pastDate;

		switch ( date )
		{
			case DAILY:
				pastDate = dtCurrentDate.minusDays(numDate);
				break;
			case WEEKLY:
				pastDate = dtCurrentDate.minusWeeks(numDate);
				break;
			case MONTHLY:
				pastDate = dtCurrentDate.minusMonths(numDate);
				break;
			default:
				pastDate = dtCurrentDate.minusMonths(numDate);
		}
		return dateFormat.format(pastDate.toDate());
	}

	/**
	 * Checks if the given date is a valid date
	 *
	 * @param date the given date string
	 *
	 * @return if the given date is a valid date
	 */
	public static boolean isValidDate( String date )
	{
		dateFormat.setLenient(false);
		try
		{
			dateFormat.parse(date.trim());
		}
		catch ( ParseException e )
		{
			return false;
		}
		return true;
	}

	/**
	 * Increments the given date by one day
	 *
	 * @param date the given date
	 *
	 * @return the date incremented by one day
	 *
	 * @throws ParseException
	 */
	public static String incrementDay( String date ) throws ParseException
	{
		DateTime dateTime = new DateTime(dateFormat.parse(date));
		dateTime = dateTime.plusDays(1);
		return dateFormat.format(dateTime.toDate());
	}

	/**
	 * Gets the current unix time
	 * Unix time is the number of seconds since Jan 1, 1970
	 *
	 * @return the current unix time
	 */
	protected static long getCurrentUnixTime( )
	{
		return System.currentTimeMillis() / 1000L;
	}
}
