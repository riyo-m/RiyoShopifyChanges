package com.vertex.quality.connectors.bigcommerce.ui.tests.refund.oseries;

import com.vertex.quality.connectors.bigcommerce.ui.pages.refund.oseries.OSeriesTaxJournalPurgePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * this class represents OSeries tax journal purge test, contains all the helper methods used in all the test cases.
 *
 * @author rohit-mogane
 */
@Test
public class OSeriesTaxJournalPurgeTests extends OSeriesLoginTest
{

	OSeriesTaxJournalPurgePage taxPurge;
	Date purgeCalendarDate;
	DateFormat messageLogFormat, purgeDateFormat;

	/**
	 * this method will do tax export purge for date selected
	 */
	public void taxJournalPurge( WebDriver driver, String purgeDate )
	{
		taxPurge = new OSeriesTaxJournalPurgePage(driver);

		taxPurge.clickDataManagementButton();
		taxPurge.clickTaxJournalPurgeLink();

		messageLogFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		purgeDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try
		{
			purgeCalendarDate = messageLogFormat.parse(purgeDate);
		}
		catch ( ParseException e )
		{
			e.printStackTrace();
		}

		String currentDate = purgeDateFormat.format(purgeCalendarDate);

		taxPurge.enterPurgeDate(currentDate);
		taxPurge.clickRunButton();
	}
}
