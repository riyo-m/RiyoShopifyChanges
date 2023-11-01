package com.vertex.quality.connectors.bigcommerce.ui.tests.devcloud;

import com.vertex.quality.connectors.bigcommerce.ui.pages.devcloud.OseriesCloudLoginPage;
import org.openqa.selenium.WebDriver;

/**
 * CBC-279
 * this class represents the vertex dev cloud login instance
 *
 * @author rohit.mogane
 */
public class OseriesCloudLoginTests extends OseriesCloudBaseTest

{
	/**
	 * this method opens vertex dev cloud login page URL and login with admin account
	 *
	 * @param driver
	 */
	public void loginToDevCloudTest( boolean openChromeInstance, WebDriver driver, String username, String password )
	{
		if ( openChromeInstance )
		{
			driver.get(OSERIES_CLOUD_DEV_ADMIN_URL);
		}
		OseriesCloudLoginPage cloudLogin = new OseriesCloudLoginPage(this.driver);
		cloudLogin.enterUserName(username);
		cloudLogin.clickContinue();
		cloudLogin.enterPassword(password);
		cloudLogin.clickContinue();
	}
}
