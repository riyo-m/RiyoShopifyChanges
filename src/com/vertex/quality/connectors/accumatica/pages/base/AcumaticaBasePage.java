package com.vertex.quality.connectors.accumatica.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * represents a page on the Acumatica site
 * contains data and functionality which are shared by all pages on the Acumatica site
 */
public abstract class AcumaticaBasePage extends VertexPage
{
	protected By SAVE_BUTTON = By.cssSelector("[data-cmd='Save'] div[icon='Save']");
	protected By DISABLED_SAVE_BUTTON = By.cssSelector("[enabled='false'][data-cmd='Save'] div[icon='Save']");

	public AcumaticaBasePage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Overloads {@link #clickSaveButton(boolean)}
	 * to assume that no popups will be triggered
	 */
	public void clickSaveButton( )
	{
		clickSaveButton(false);
	}

	/**
	 * clicks on save button,
	 * then waits for the save button to become disabled, unless a popup is going to be triggered by clicking the
	 * save button (because the disabled save button might hide behind the popup)
	 *
	 * @param triggersPopup whether any popup will load after clicking the save button
	 */
	public void clickSaveButton( final boolean triggersPopup )
	{
		click.clickElementCarefully(SAVE_BUTTON);
		if ( !triggersPopup )
		{
			wait.waitForElementDisplayed(DISABLED_SAVE_BUTTON);
		}
	}

	/**
	 * clicks the page's save button & accepts the alert which pops up
	 */
	public void clickSaveAndAcceptAlert( )
	{
		click.clickElementCarefully(SAVE_BUTTON, true);
		element.isElementDisplayed(DISABLED_SAVE_BUTTON);
	}

	/**
	 * clicks the page's save button & dismisses the alert which pops up
	 */
	public void clickSaveAndDismissAlert( )
	{
		wait.waitForElementEnabled(SAVE_BUTTON);
		click.clickElement(SAVE_BUTTON);
		alert.dismissAlert(DEFAULT_TIMEOUT);
		waitForPageLoad();
		element.isElementDisplayed(DISABLED_SAVE_BUTTON);
	}
}
