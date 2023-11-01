package com.vertex.quality.connectors.mirakl.ui.base;

import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.connectors.mirakl.api.enums.MiraklOperatorsData;
import com.vertex.quality.connectors.mirakl.ui.pages.OseriesLoginPage;

/**
 * this class represents all the helper methods used in all the test cases for O-Series.
 *
 * @author rohit-mogane
 */
public abstract class OseriesUIBaseTest extends VertexUIBaseTest
{
	protected OseriesLoginPage signOnPage;
	protected static String[] taxPayersToIgnore = { "OP1", "OP2" };

	/**
	 * this method opens O-Series login page URL and login with mirakl QA account
	 */
	protected void loginToOSeries( )
	{
		driver.get(MiraklOperatorsData.OSERIES_URL_NEW.data + "oseries-ui");
		signOnPage = new OseriesLoginPage(driver);
		signOnPage.oseriesEnterUsername(MiraklOperatorsData.OSERIES_QA_USERNAME.data);
		signOnPage.oseriesEnterPassword(MiraklOperatorsData.OSERIES_QA_PASSWORD.data);
		signOnPage.oseriesLogin();
		driver
			.navigate()
			.to(MiraklOperatorsData.OSERIES_URL_NEW.data + "oseries-ui");
		driver
			.navigate()
			.refresh();
	}
}
