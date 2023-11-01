package com.vertex.quality.connectors.ariba.connector.pages.base;

import com.vertex.quality.connectors.ariba.connector.components.AribaConnTenantSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * represents one of the configuration menu webpages in the site,
 * which lets connector users configure how the connector
 * interfaces between Ariba and O-Series
 *
 * @author ssalisbury
 */
public abstract class AribaConnMenuBasePage extends AribaConnBasePage
{
	public final AribaConnTenantSelector tenantSelector;

	//note- xml log viewer doesn't have any of these things;
	// it's an odd one out, particularly in that it doesn't actually modify configurations
	protected final By successMessage = By.id("win");
	protected final By failureMessage = By.id("lose");

	protected final By submitButton = By.id("update");

	public AribaConnMenuBasePage( final WebDriver driver, final String title )
	{
		super(driver, title);

		this.tenantSelector = initializePageObject(AribaConnTenantSelector.class, this);
	}

	/**
	 * checks whether the update button is displayed on this page
	 *
	 * @return whether the update button is displayed on this page
	 *
	 * @author ssalisbury
	 */
	public boolean isSubmitButtonDisplayed( )
	{
		boolean isDisplayed = element.isElementDisplayed(submitButton);
		return isDisplayed;
	}

	/**
	 * checks whether the button for submitting changes can be seen and clicked
	 *
	 * @return whether the button for submitting changes can be seen and clicked
	 *
	 * @author ssalisbury
	 */
	public boolean isSubmitButtonEnabled( )
	{
		boolean isEnabled = element.isElementEnabled(submitButton);
		return isEnabled;
	}

	/**
	 * updates the database with the locally-pending configuration changes
	 *
	 * @author ssalisbury
	 */
	public void submitChanges( )
	{
		click.clickElementCarefully(submitButton);
	}

	/**
	 * tries to submit the locally-pending configuration changes to the database,
	 * but assumes that those changes might be rejected
	 * and might even trigger an alert,
	 * so it doesn't wait for the page to finish the process of submitting the data and reloading
	 */
	public void submitUnstableChanges( )
	{
		wait.waitForElementEnabled(submitButton);
		click.clickElement(submitButton);
	}

	/**
	 * checks whether the success submission message is displayed
	 *
	 * @return whether the success submission message is displayed to the user
	 *
	 * @author ssalisbury
	 */
	public boolean isSuccessMessageDisplayed( )
	{
		boolean isSuccessMessageDisplayed = element.isElementDisplayed(successMessage);
		return isSuccessMessageDisplayed;
	}

	/**
	 * checks whether the failure submission message is displayed
	 *
	 * @return whether the failure submission message is displayed to the user
	 *
	 * @author ssalisbury
	 */
	public boolean isFailureMessageDisplayed( )
	{
		boolean isFailureMessageDisplayed = element.isElementDisplayed(failureMessage);
		return isFailureMessageDisplayed;
	}
}
