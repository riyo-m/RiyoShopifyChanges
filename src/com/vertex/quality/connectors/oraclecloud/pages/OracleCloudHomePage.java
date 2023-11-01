package com.vertex.quality.connectors.oraclecloud.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Representation of the site before any page or menu has been navigated to
 *
 * @author cgajes
 */
public class OracleCloudHomePage extends OracleCloudNavPage
{
	protected By titleTag = By.tagName("TITLE");

	public OracleCloudHomePage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * checks whether the open page is the one that you reach right after signing in
	 * successfully
	 *
	 * @return whether the open page is the one that you reach right after signing
	 * in successfully
	 */
	public String checkJustLoggedIn( )
	{
		String textAttribute = "innerText";

		WebElement header = wait.waitForElementPresent(titleTag);
		String loggedInHeaderText = attribute.getElementAttribute(header, textAttribute);

		if ( loggedInHeaderText == null || loggedInHeaderText == "" )
		{
			String errorMsg = "Login header text not found :(";
			throw new RuntimeException(errorMsg);
		}

		return loggedInHeaderText;
	}
}
