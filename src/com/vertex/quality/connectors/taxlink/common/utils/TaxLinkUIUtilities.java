package com.vertex.quality.connectors.taxlink.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * This class contains all the utility methods for TaxLink UI
 *
 * @author mgaikwad, Shilpi Verma
 */

public class TaxLinkUIUtilities
{

	Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	/**
	 * Method to generate random number
	 */
	public String randomNumber( String count )
	{

		return RandomStringUtils.randomNumeric(Integer.parseInt(count));
	}

	/**
	 * Method to generate random text
	 */
	public String randomText( )
	{

		return RandomStringUtils.randomAlphabetic(3) + "Test";
	}

	/**
	 * Method to generate random alphanumeric text
	 */
	public String randomAlphaNumericText( )
	{

		return RandomStringUtils.randomAlphanumeric(3) + "Test";
	}

	/**
	 * Method to generate random alphanumeric text along with currnt timestamp
	 */
	public String alphaNumericWithTimeStampText( )
	{
		String inputTimeStamp = new SimpleDateFormat("ddHHmmss").format(timestamp);
		return "Test" + inputTimeStamp;
	}

	/**
	 * @return today's date
	 */
	public String getCurrentDate( )
	{
		String[] len = LocalDate
			.now()
			.toString()
			.split("-");

		String[] newLen = len[2].split("");

		if ( newLen[0].contains("0") )
		{
			return newLen[1];
		}
		else
		{
			return newLen[0] + newLen[1];
		}
	}

	/**
	 * @return tomorrow's date
	 */
	public String getNextDayDate( )
	{
		return LocalDate
			.now()
			.plusDays(1)
			.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	}

	/**
	 * @return Today's date in mm/dd/yyyy format
	 */
	public String getFormattedDate( )
	{
		return LocalDate
			.now()
			.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	}

	/**
	 * This method is for E2E scenarios in OracleCloud
	 *
	 * @return Today's date in format passed as argument
	 */
	public String getCurrDate( String format )
	{
		return LocalDate
			.now()
			.format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * @return Tomorrow's date in yyyy-MM-dd format
	 * to verify end date in Summary table
	 */
	public String getEndDateFormatted( )
	{
		return LocalDate
			.now()
			.plusDays(1)
			.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
	}

	/**
	 * @return Yesterday's date in mm/dd/yyyy format
	 */
	public String getYesterdayDate( )
	{
		return LocalDate
			.now()
			.minusDays(1)
			.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	}

	/**
	 * @return yesterday's date in M/dd/yy format
	 */
	public String getYesterdaysDateERP( )
	{
		return LocalDate
			.now()
			.minusDays(1)
			.format(DateTimeFormatter.ofPattern("M/dd/yy"));
	}

	/**
	 * @return date in MM/dd/yyyy format
	 * convert the date format yyyy-MM-dd to MM/dd/yyyy for export to CSV testing
	 */
	public String getCSVFormattedDate( String dateCSV ) throws ParseException
	{
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dt.parse(dateCSV);
		SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
		return dt1.format(date);
	}

	/**
	 * @return date in yyyy-MM-dd format
	 * convert the date format MM/dd/yyyy to yyyy-MM-dd for export to CSV testing
	 */
	public String getSummaryFormattedDate( String dateCSV ) throws ParseException
	{
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
		Date date = dt.parse(dateCSV);
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		return dt1.format(date);
	}

	/**
	 * Method to clear text from any text field
	 *
	 * @param element
	 */
	public void clearTextField( WebElement element )
	{
		while ( !element
			.getAttribute("value")
			.equals("") )
		{
			element.sendKeys(Keys.BACK_SPACE);
		}
	}
}

