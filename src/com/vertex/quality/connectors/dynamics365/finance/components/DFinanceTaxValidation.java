package com.vertex.quality.connectors.dynamics365.finance.components;

import com.vertex.quality.common.utils.VertexLogger;

import static org.testng.Assert.assertTrue;

/**
 * Text validation method class
 */
public class DFinanceTaxValidation
{
	/**
	 * ========================This Class Common Methods ==================================================================
	 */
	/**
	 * Text validation
	 *
	 * @param textName
	 * @param text
	 */
	public void textValidation( String textName, String text )
	{
		assertTrue(textName != null, "No text is displayed for " + text);
		String adapterVersionMessage = String.format(text + " : %s", textName);
		VertexLogger.log(adapterVersionMessage);
	}

	/**
	 * text comparision
	 *
	 * @param expected
	 * @param actual
	 * @param nameOfEle
	 */
	public void textValidationWithStrings( String expected, String actual, String nameOfEle )
	{
		assertTrue(actual.equalsIgnoreCase(expected), "Strings are not matching: " + nameOfEle);
		String adapterVersionMessage = String.format(nameOfEle + " : %s", expected);
		VertexLogger.log(adapterVersionMessage);
	}
}
