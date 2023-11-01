package com.vertex.quality.common.utils.misc;

import org.joda.money.Money;
import org.joda.money.format.MoneyFormatter;
import org.joda.money.format.MoneyFormatterBuilder;

import java.text.DecimalFormat;

/**
 * Utility class containing helper methods for handling currencies
 *
 * @author dgorecki
 */
public class VertexCurrencyUtils
{
	private VertexCurrencyUtils( )
	{
	}

	/**
	 * Parse a String into a Money object for easier manipulation
	 *
	 * @param valueToParse a string representing a value to convert into a Money object
	 *
	 * @return a Money representation of the value specified
	 *
	 * @author dgorecki
	 */
	public static Money parseMoney( String valueToParse )
	{
		MoneyFormatterBuilder moneyFormatBuilder = new MoneyFormatterBuilder();
		MoneyFormatter moneyFormatter = moneyFormatBuilder
			.appendAmount()
			.appendCurrencyCode()
			.toFormatter();

		String cleanString = valueToParse.replaceAll("[^A-Za-z0-9\\.]", "");
		Money output = moneyFormatter.parseMoney(cleanString);

		return output;
	}

	/**
	 * This method is useful to remove the special characters like ($, %, - and ,)
	 * of given string and converts the whole given amount string to a double value
	 * with two decimal places.
	 *
	 * @param amountText the original string to cleanse and convert into a double
	 *
	 * @return a double value of given string
	 *
	 * @author dgorecki
	 */
	public static double cleanseCurrencyString( String amountText )
	{
		amountText = amountText
			.replaceAll("[$,%,-]", "")
			.replaceAll(",", "")
			.trim();

		double amount = 0.0;

		if ( !amountText.isEmpty() )
		{
			amount = Double.parseDouble(amountText);
			amount = getDecimalFormatAmount(amount);
		}
		return amount;
	}

	/**
	 * This method is useful to restrict a double value decimal places to 2
	 * positions.
	 *
	 * @param amount the amount as a double
	 *
	 * @return a double value with 2 decimal places
	 *
	 * @author dgorecki
	 */
	public static double getDecimalFormatAmount( double amount )
	{
		DecimalFormat df = getTwoDigitDecimalFormatter();
		String strAmount = df.format(amount);
		double formattedAmount = Double.parseDouble(strAmount);

		return formattedAmount;
	}

	/**
	 * This method is useful to restrict a float value decimal places to 2
	 * positions.
	 *
	 * @param amount the amount as a float
	 *
	 * @return a float value with 2 decimal places
	 *
	 * @author dgorecki
	 */
	public static float getDecimalFormatAmount( float amount )
	{
		DecimalFormat df = getTwoDigitDecimalFormatter();
		String strAmount = df.format(amount);

		float formattedAmount = Float.parseFloat(strAmount);

		return formattedAmount;
	}

	/**
	 * Internal method to retrieve a formatter for 2 decimal place values
	 *
	 * @return a DecimalFormat that limits the number of decimal places to 2
	 *
	 * @author dgorecki
	 */
	public static DecimalFormat getTwoDigitDecimalFormatter( )
	{
		DecimalFormat df = new DecimalFormat("###.##");
		return df;
	}
}
