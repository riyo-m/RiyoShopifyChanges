package com.vertex.quality.connectors.ariba.portal.dialogs.requisition;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.portal.dialogs.base.AribaPortalDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the dialog to select supplier contact info from
 * contains all the methods necessary to interact with it
 *
 * @author osabha
 */
public class AribaPortalEditLineItemContactDialog extends AribaPortalDialog
{
	protected final By DIALOG_CLASS = By.className("w-dlg-dialog");
	protected final By DIALOG_BUTTONS_CONTAINER_CLASS = By.className("w-dlg-buttons");
	protected final By DIALOG_BUTTON_TAG = By.tagName("button");
	protected final By searchFieldLoc = By.cssSelector("input[aria-label='Search for a specific value']");
	protected final By searchButtonLoc = By.cssSelector("button[title='Search for a specific value in the list']");
	protected final By selectButtonLoc = By.cssSelector("button[title='Select this value for the field']");
	protected final By pageContentsLoc = By.className("a-arw-wizard-content");
	protected final String doneButtonText = "Done";

	public AribaPortalEditLineItemContactDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * Clicks the Done button on the dialog
	 */
	public void clickDoneButton( )
	{
		WebElement doneButton = findDoneButton();

		wait.waitForElementEnabled(doneButton);

		click.clickElement(doneButton, PageScrollDestination.VERT_CENTER);
	}

	/**
	 * Helper method for finding the Done button on the dialog
	 *
	 * @return the Done button if found, otherwise null
	 *
	 * @author ssalisbury dgorecki
	 */
	protected WebElement findDoneButton( )
	{
		WebElement doneButton = null;

		WebElement dialog = wait.waitForElementDisplayed(DIALOG_CLASS);
		WebElement buttonContainer = wait.waitForElementDisplayed(DIALOG_BUTTONS_CONTAINER_CLASS, dialog);
		List<WebElement> buttons = wait.waitForAllElementsEnabled(DIALOG_BUTTON_TAG, buttonContainer);

		doneButton = element.selectElementByText(buttons, doneButtonText);
		return doneButton;
	}

	/**
	 * to search for specific contact name and select it
	 *
	 * @param contactName
	 */
	public void selectContactValue( String contactName )
	{
		WebElement dialog = wait.waitForElementDisplayed(DIALOG_CLASS);
		WebElement searchField = wait.waitForElementEnabled(searchFieldLoc, dialog);
		text.enterText(searchField, contactName);
		WebElement searchButton = wait.waitForElementEnabled(searchButtonLoc, dialog);
		click.clickElement(searchButton);
		locateAndClickSelectButton();
	}

	/**
	 * Helper method to locate and click select button
	 * from the contact value dialog
	 */
	public void locateAndClickSelectButton( )
	{
		WebElement selectButton;
		boolean result = false;
		int attempts = 0;
		while ( result == false && attempts < 10 )
		{
			try
			{
				selectButton = wait.waitForElementEnabled(selectButtonLoc);
				click.clickElementCarefully(selectButton);
				result = true;
			}
			catch (org.openqa.selenium.StaleElementReferenceException ex)
			{
				selectButton = wait.waitForElementEnabled(selectButtonLoc);
				click.clickElementCarefully(selectButton);
				result= true;
			}
			attempts++;
		}

		try
		{
			if ( element.isElementDisplayed(DIALOG_CLASS) )
			{

				selectButton = wait.waitForElementEnabled(selectButtonLoc);
				click.clickElement(selectButton);
			}
		}
		catch ( StaleElementReferenceException e2 )
		{
			e2.printStackTrace();
		}
	}
}










