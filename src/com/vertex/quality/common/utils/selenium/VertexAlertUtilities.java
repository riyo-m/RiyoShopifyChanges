package com.vertex.quality.common.utils.selenium;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.selenium.base.VertexSeleniumUtilities;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Common functions for interacting with Alerts. This should be declared as an instance
 * variable in VertexAutomationObject
 *
 * @author hho, osabha, dgorecki, ssalisbury
 */
public class VertexAlertUtilities extends VertexSeleniumUtilities
{
	public VertexAlertUtilities( final VertexAutomationObject pageObject, final WebDriver driver )
	{
		super(pageObject, driver);
	}

	public VertexAlertUtilities( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Overloads {@link #waitForAlertPresent(int)} to wait for the default period before timing out
	 */
	public boolean waitForAlertPresent( )
	{
		boolean foundAlert = waitForAlertPresent(VertexAutomationObject.DEFAULT_TIMEOUT);
		return foundAlert;
	}

	/**
	 * Wait for an alert to be present within the specified wait time in seconds
	 * Note that this does not actually handle the alert just confirms if it appears
	 * (if the alert doesn't appear, this crashes the program)
	 *
	 * @param waitTimeInSeconds the amount of time to wait for the alert, in seconds
	 *
	 * @return boolean indicating whether the alert was found
	 */
	public boolean waitForAlertPresent( int waitTimeInSeconds )
	{
		boolean foundAlert;

		try
		{
			final WebDriverWait wait = new WebDriverWait(driver, waitTimeInSeconds);
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
		}
		catch ( TimeoutException e )
		{
			foundAlert = false;
		}
		catch ( Exception ex )
		{
			ex.printStackTrace();
			foundAlert = false;
		}

		return foundAlert;
	}

	/**
	 * Overloads {@link #acceptAlert(int)} to wait for the default period before timing out
	 */
	public void acceptAlert( )
	{
		acceptAlert(VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * waits until the alert is present and then handles it by accepting the alert
	 *
	 * @param waitTimeInSeconds the time to wait for the alert to be present.
	 */
	public void acceptAlert( int waitTimeInSeconds )
	{
		boolean alertPresent = waitForAlertPresent(waitTimeInSeconds);

		if ( alertPresent )
		{
			final Alert alert = getCurrentAlert();

			if ( alert != null )
			{
				alert.accept();
			}
		}
	}

	/**
	 * Overloads {@link #dismissAlert(int)} to wait for the default period before timing out
	 */
	public void dismissAlert( )
	{
		dismissAlert(VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * does the wait until the alert is present and then handles it by dismissing the alert
	 *
	 * @param waitTimeInSeconds the time to wait for the alert to be present.
	 */
	public void dismissAlert( int waitTimeInSeconds )
	{
		boolean alertPresent = waitForAlertPresent(waitTimeInSeconds);
		if ( alertPresent )
		{
			final Alert alert = getCurrentAlert();

			if ( alert != null )
			{
				alert.dismiss();
			}
		}
	}

	/**
	 * Overloads {@link #getAlertText(int)} to wait for the default period before timing out
	 */
	public String getAlertText( )
	{
		String alertText = getAlertText(VertexAutomationObject.DEFAULT_TIMEOUT);
		return alertText;
	}

	/**
	 * does the wait until the alert is present and then returns the text content of the alert
	 *
	 * @param waitTimeInSeconds the time to wait for the alert to be present.
	 *
	 * @return the alert text content as a String
	 */
	public String getAlertText( int waitTimeInSeconds )
	{
		String alertText = null;

		boolean alertPresent = waitForAlertPresent(waitTimeInSeconds);

		if ( alertPresent )
		{
			final Alert alert = getCurrentAlert();

			if ( alert != null )
			{
				alertText = alert.getText();
			}
		}

		return alertText;
	}

	/**
	 * Fetches the current alert if present
	 *
	 * @return the Alert if one is present, null otherwise
	 */
	protected Alert getCurrentAlert( )
	{
		Alert alert;
		try
		{
			alert = driver
				.switchTo()
				.alert();
		}
		catch ( Exception e )
		{
			alert = null;
			VertexLogger.log(e.getMessage(), VertexLogLevel.DEBUG);
		}

		return alert;
	}

	/**
	 * checks to see if an alert is present
	 *
	 * @return boolean indicating if the alert is present
	 */
	public boolean isAlertPresent( )
	{
		boolean isAlertPresent = false;
		Alert alert = getCurrentAlert();

		if ( alert != null )
		{
			isAlertPresent = true;
		}

		return isAlertPresent;
	}
}
