package com.vertex.quality.connectors.commercetools.ui.tests.oseries;

import com.vertex.quality.connectors.bigcommerce.ui.pages.refund.oseries.OSeriesSignOnPage;
import org.openqa.selenium.WebDriver;

/**
 * this class represents OSeries login test, contains all the helper methods used in all the test cases.
 *
 * @author rohit-mogane
 */
public class OSeriesLoginTest extends OSeriesBaseTest
{
	/**
	 * this method opens O-Series login page URL and login with admin account
	 */
	public void signIntoOSeries( WebDriver driver )
	{
		driver.get(OSERIES_ADMIN_URL);

		OSeriesSignOnPage signOnPage = new OSeriesSignOnPage(driver);

		signOnPage.enterUserName(OSERIES_ADMIN_USERNAME_CT);

		signOnPage.enterPassword(OSERIES_ADMIN_PASSWORD_CT);

		signOnPage.clickLoginButton();
	}
}
